import { BaseEntity } from './../../shared';

export class JobGroup implements BaseEntity {
    constructor(
        public id?: string,
        public name?: string,
        public authorityLvl?: number,
    ) {
    }
}
