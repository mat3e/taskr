import { BaseEntity } from './../../shared';

export const enum TaskStatus {
    'DRAFT',
    'PENDING',
    'SENT',
    'READ',
    'IN_PROGRESS',
    'DONE',
    'ERROR'
}

export class Task implements BaseEntity {
    constructor(
        public id?: string,
        public dispatchDate?: any,
        public readDate?: any,
        public userLogin?: string,
        public userEmail?: string,
        public status?: TaskStatus,
        public title?: string,
        public body?: string,
    ) {
    }

    get isDraft(): boolean { return this.status === "DRAFT" as any; }
    get isRead(): boolean { return this.status === "READ" as any; }
    get isInProgress(): boolean { return this.status === "IN_PROGRESS" as any; }
}
