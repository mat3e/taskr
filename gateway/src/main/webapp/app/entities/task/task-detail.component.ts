import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Task, TaskStatus } from './task.model';
import { TaskService } from './task.service';

@Component({
    selector: 'jhi-task-detail',
    templateUrl: './task-detail.component.html'
})
export class TaskDetailComponent implements OnInit, OnDestroy {

    task: Task;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    displaySentTasks: boolean;

    constructor(
        private eventManager: JhiEventManager,
        private taskService: TaskService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {

        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
            this.displaySentTasks = params['which'] === 'sent';
        });
        this.registerChangeInTasks();
    }

    changeStatus() {
        this.taskService.changeStatus(this.task.id).subscribe((task) => {
            this.task = task;
        });
    }

    load(id) {
        this.taskService.find(id).subscribe((task) => {
            this.task = task;
            if (task.status === TaskStatus.SENT && !this.displaySentTasks) {
                this.taskService.changeStatus(task.id).subscribe((updatedTask) => {
                    this.task = updatedTask;
                });
            }
        });
    }

    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTasks() {
        this.eventSubscriber = this.eventManager.subscribe(
            'taskListModification',
            (response) => this.load(this.task.id)
        );
    }
}
