import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TaskrSharedModule } from '../../shared';
import {
    JobService,
    JobPopupService,
    JobComponent,
    JobDetailComponent,
    JobDialogComponent,
    JobPopupComponent,
    JobDeletePopupComponent,
    JobDeleteDialogComponent,
    jobRoute,
    jobPopupRoute,
} from './';

const ENTITY_STATES = [
    ...jobRoute,
    ...jobPopupRoute,
];

@NgModule({
    imports: [
        TaskrSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        JobComponent,
        JobDetailComponent,
        JobDialogComponent,
        JobDeleteDialogComponent,
        JobPopupComponent,
        JobDeletePopupComponent,
    ],
    entryComponents: [
        JobComponent,
        JobDialogComponent,
        JobPopupComponent,
        JobDeleteDialogComponent,
        JobDeletePopupComponent,
    ],
    providers: [
        JobService,
        JobPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TaskrJobModule {}
