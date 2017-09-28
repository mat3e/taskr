import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TaskrSharedModule } from '../../shared';
import {
    JobGroupService,
    JobGroupPopupService,
    JobGroupComponent,
    JobGroupDetailComponent,
    JobGroupDialogComponent,
    JobGroupPopupComponent,
    JobGroupDeletePopupComponent,
    JobGroupDeleteDialogComponent,
    jobGroupRoute,
    jobGroupPopupRoute,
} from './';

const ENTITY_STATES = [
    ...jobGroupRoute,
    ...jobGroupPopupRoute,
];

@NgModule({
    imports: [
        TaskrSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        JobGroupComponent,
        JobGroupDetailComponent,
        JobGroupDialogComponent,
        JobGroupDeleteDialogComponent,
        JobGroupPopupComponent,
        JobGroupDeletePopupComponent,
    ],
    entryComponents: [
        JobGroupComponent,
        JobGroupDialogComponent,
        JobGroupPopupComponent,
        JobGroupDeleteDialogComponent,
        JobGroupDeletePopupComponent,
    ],
    providers: [
        JobGroupService,
        JobGroupPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TaskrJobGroupModule {}
