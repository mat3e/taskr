import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiAlertService} from 'ng-jhipster';

import {ResponseWrapper} from '../../shared';

import {JobGroup} from '../job-group/job-group.model';
import {JobGroupService} from '../job-group/job-group.service';
import {Job} from './job.model';
import {JobPopupService} from './job-popup.service';
import {JobService} from './job.service';

@Component({
    selector: 'jhi-job-dialog',
    templateUrl: './job-dialog.component.html'
})
export class JobDialogComponent implements OnInit {

    job: Job;
    isSaving: boolean;

    jobGroups: JobGroup[];
    group: JobGroup;

    constructor(public activeModal: NgbActiveModal,
                private jhiAlertService: JhiAlertService,
                private jobService: JobService,
                private eventManager: JhiEventManager,
                private groupService: JobGroupService) {
    }

    ngOnInit() {
        this.isSaving = false;

        this.groupService.query().subscribe(
            (res: ResponseWrapper) => {
                this.jobGroups = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.job.groupId = this.group.id;
        this.job.groupName = this.group.name;
        if (this.job.id !== undefined) {
            this.subscribeToSaveResponse(
                this.jobService.update(this.job));
        } else {
            this.subscribeToSaveResponse(
                this.jobService.create(this.job));
        }
    }

    private subscribeToSaveResponse(result: Observable<Job>) {
        result.subscribe((res: Job) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Job) {
        this.eventManager.broadcast({name: 'jobListModification', content: 'OK'});
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
    selector: 'jhi-job-popup',
    template: ''
})
export class JobPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private jobPopupService: JobPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.jobPopupService
                    .open(JobDialogComponent as Component, params['id']);
            } else {
                this.jobPopupService
                    .open(JobDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
