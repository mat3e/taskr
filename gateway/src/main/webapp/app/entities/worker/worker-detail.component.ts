import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Worker } from './worker.model';
import { WorkerService } from './worker.service';

@Component({
    selector: 'jhi-worker-detail',
    templateUrl: './worker-detail.component.html'
})
export class WorkerDetailComponent implements OnInit, OnDestroy {

    worker: Worker;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private workerService: WorkerService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInWorkers();
    }

    load(id) {
        this.workerService.find(id).subscribe((worker) => {
            this.worker = worker;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInWorkers() {
        this.eventSubscriber = this.eventManager.subscribe(
            'workerListModification',
            (response) => this.load(this.worker.id)
        );
    }
}
