import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { JobGroup } from './job-group.model';
import { JobGroupPopupService } from './job-group-popup.service';
import { JobGroupService } from './job-group.service';

@Component({
    selector: 'jhi-job-group-delete-dialog',
    templateUrl: './job-group-delete-dialog.component.html'
})
export class JobGroupDeleteDialogComponent {

    jobGroup: JobGroup;

    constructor(
        private jobGroupService: JobGroupService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.jobGroupService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'jobGroupListModification',
                content: 'Deleted an jobGroup'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-job-group-delete-popup',
    template: ''
})
export class JobGroupDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private jobGroupPopupService: JobGroupPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.jobGroupPopupService
                .open(JobGroupDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
