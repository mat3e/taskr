import { BaseEntity } from './../../shared';

export class Job implements BaseEntity {
    constructor(
        public id?: string,
        public title?: string,
        public minSalary?: number,
        public maxSalary?: number,
        public groupId?: string,
        public groupName?: string,
    ) {
    }
}
