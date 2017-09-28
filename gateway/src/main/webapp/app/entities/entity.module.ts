import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { TaskrTaskModule } from './task/task.module';
import { TaskrJobModule } from './job/job.module';
import { TaskrJobGroupModule } from './job-group/job-group.module';
import { TaskrWorkerModule } from './worker/worker.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        TaskrTaskModule,
        TaskrJobModule,
        TaskrJobGroupModule,
        TaskrWorkerModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TaskrEntityModule {}
