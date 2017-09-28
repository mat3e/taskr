import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { JobGroup } from './job-group.model';
import { JobGroupPopupService } from './job-group-popup.service';
import { JobGroupService } from './job-group.service';

@Component({
    selector: 'jhi-job-group-dialog',
    templateUrl: './job-group-dialog.component.html'
})
export class JobGroupDialogComponent implements OnInit {

    jobGroup: JobGroup;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private jobGroupService: JobGroupService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.jobGroup.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jobGroupService.update(this.jobGroup));
        } else {
            this.subscribeToSaveResponse(
                this.jobGroupService.create(this.jobGroup));
        }
    }

    private subscribeToSaveResponse(result: Observable<JobGroup>) {
        result.subscribe((res: JobGroup) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: JobGroup) {
        this.eventManager.broadcast({ name: 'jobGroupListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-job-group-popup',
    template: ''
})
export class JobGroupPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobGroupPopupService: JobGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.jobGroupPopupService
                    .open(JobGroupDialogComponent as Component, params['id']);
            } else {
                this.jobGroupPopupService
                    .open(JobGroupDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
