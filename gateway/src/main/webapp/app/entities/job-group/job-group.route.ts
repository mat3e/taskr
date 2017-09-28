import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { JobGroupComponent } from './job-group.component';
import { JobGroupDetailComponent } from './job-group-detail.component';
import { JobGroupPopupComponent } from './job-group-dialog.component';
import { JobGroupDeletePopupComponent } from './job-group-delete-dialog.component';

export const jobGroupRoute: Routes = [
    {
        path: 'job-group',
        component: JobGroupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'taskrApp.jobGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'job-group/:id',
        component: JobGroupDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'taskrApp.jobGroup.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobGroupPopupRoute: Routes = [
    {
        path: 'job-group-new',
        component: JobGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'taskrApp.jobGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-group/:id/edit',
        component: JobGroupPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'taskrApp.jobGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'job-group/:id/delete',
        component: JobGroupDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'taskrApp.jobGroup.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
