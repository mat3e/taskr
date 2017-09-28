import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { JobGroup } from './job-group.model';
import { JobGroupService } from './job-group.service';

@Component({
    selector: 'jhi-job-group-detail',
    templateUrl: './job-group-detail.component.html'
})
export class JobGroupDetailComponent implements OnInit, OnDestroy {

    jobGroup: JobGroup;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private jobGroupService: JobGroupService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInJobGroups();
    }

    load(id) {
        this.jobGroupService.find(id).subscribe((jobGroup) => {
            this.jobGroup = jobGroup;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInJobGroups() {
        this.eventSubscriber = this.eventManager.subscribe(
            'jobGroupListModification',
            (response) => this.load(this.jobGroup.id)
        );
    }
}
