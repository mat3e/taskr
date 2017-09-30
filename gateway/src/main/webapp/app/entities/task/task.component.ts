import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Task } from './task.model';
import { TaskService } from './task.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-task',
    templateUrl: './task.component.html'
})
export class TaskComponent implements OnInit, OnDestroy {
    tasks: Task[];
    currentAccount: any;
    eventSubscriber: Subscription;
    tasksSubscriber: Subscription;

    displaySentTasks: boolean;

    constructor(
        private taskService: TaskService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal,
        private route: ActivatedRoute
    ) {
    }

    changeStatus(task) {
        this.taskService.changeStatus(task.id).subscribe((updatedTask) => {
            var taskFromArray = this.tasks.find((t) => t.id === updatedTask.id);
            taskFromArray.status = updatedTask.status;
        });
    }

    loadAll() {
        this.taskService.query({sent: this.displaySentTasks}).subscribe(
            (res: ResponseWrapper) => {
                this.tasks = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {

        this.tasksSubscriber = this.route.params.subscribe((params) => {
            this.whichChanged(params['which']);
        });

        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });

        this.registerChangeInTasks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
        this.eventManager.destroy(this.tasksSubscriber);
    }

    whichChanged(which: string) {
        this.displaySentTasks = which === 'sent';
        this.loadAll();
    }

    trackId(index: number, item: Task) {
        return item.id;
    }

    registerChangeInTasks() {
        this.eventSubscriber = this.eventManager.subscribe('taskListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
