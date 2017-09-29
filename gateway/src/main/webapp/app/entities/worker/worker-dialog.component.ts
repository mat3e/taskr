import {Component, OnInit, OnDestroy} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Response} from '@angular/http';

import {Observable} from 'rxjs/Rx';
import {NgbActiveModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {JhiEventManager, JhiAlertService} from 'ng-jhipster';

import {ResponseWrapper} from '../../shared';
import {UserService} from '../../shared/user/user.service';
import {User} from '../../shared/user/user.model';

import {Job} from '../job/job.model';
import {JobService} from '../job//job.service';
import {JobGroupService} from '../job-group/job-group.service';
import {Worker} from './worker.model';
import {WorkerPopupService} from './worker-popup.service';
import {WorkerService} from './worker.service';

@Component({
    selector: 'jhi-worker-dialog',
    templateUrl: './worker-dialog.component.html'
})
export class WorkerDialogComponent implements OnInit {

    worker: Worker;
    isSaving: boolean;

    job: Job;
    jobs: Job[];

    user: User;
    users: User[];

    constructor(public activeModal: NgbActiveModal,
                private jhiAlertService: JhiAlertService,
                private workerService: WorkerService,
                private eventManager: JhiEventManager,
                private jobService: JobService,
                private groupService: JobGroupService,
                private userService: UserService) {
    }

    ngOnInit() {
        this.isSaving = false;

        this.jobService.query().subscribe(
            (res: ResponseWrapper) => {
                this.jobs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
        this.userService.query().subscribe(
            (res: ResponseWrapper) => {
                this.users = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        this.worker.jobId = this.job.id;
        this.worker.jobTitle = this.job.title;
        this.worker.userLogin = this.user.login;
        this.worker.userEmail = this.user.email;
        if (this.worker.id !== undefined) {
            this.subscribeToSaveResponse(
                this.workerService.update(this.worker));
        } else {
            this.subscribeToSaveResponse(
                this.workerService.create(this.worker));
        }
    }

    refreshAuthority() {
        this.groupService.find(this.job.groupId).subscribe((group) => {
            this.worker.authorityLvl = group.authorityLvl;
        });
    }

    private subscribeToSaveResponse(result: Observable<Worker>) {
        result.subscribe((res: Worker) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Worker) {
        this.eventManager.broadcast({name: 'workerListModification', content: 'OK'});
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
    selector: 'jhi-worker-popup',
    template: ''
})
export class WorkerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(private route: ActivatedRoute,
                private workerPopupService: WorkerPopupService) {
    }

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if (params['id']) {
                this.workerPopupService
                    .open(WorkerDialogComponent as Component, params['id']);
            } else {
                this.workerPopupService
                    .open(WorkerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
