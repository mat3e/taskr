import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Worker } from './worker.model';
import { WorkerPopupService } from './worker-popup.service';
import { WorkerService } from './worker.service';

@Component({
    selector: 'jhi-worker-delete-dialog',
    templateUrl: './worker-delete-dialog.component.html'
})
export class WorkerDeleteDialogComponent {

    worker: Worker;

    constructor(
        private workerService: WorkerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.workerService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'workerListModification',
                content: 'Deleted an worker'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-worker-delete-popup',
    template: ''
})
export class WorkerDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private workerPopupService: WorkerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.workerPopupService
                .open(WorkerDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
