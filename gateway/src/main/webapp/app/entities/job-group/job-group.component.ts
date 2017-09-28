import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { JobGroup } from './job-group.model';
import { JobGroupService } from './job-group.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-job-group',
    templateUrl: './job-group.component.html'
})
export class JobGroupComponent implements OnInit, OnDestroy {
jobGroups: JobGroup[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jobGroupService: JobGroupService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.jobGroupService.query().subscribe(
            (res: ResponseWrapper) => {
                this.jobGroups = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInJobGroups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: JobGroup) {
        return item.id;
    }
    registerChangeInJobGroups() {
        this.eventSubscriber = this.eventManager.subscribe('jobGroupListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
