import { BaseEntity } from './../../shared';

export class Worker implements BaseEntity {
    constructor(
        public id?: string,
        public name?: string,
        public surname?: string,
        public userLogin?: string,
        public userEmail?: string,
        public jobId?: string,
        public jobTitle?: string,
        public authorityLvl?: number,
        public city?: string,
        public street?: string,
        public house?: string,
        public apartment?: string,
        public postalCode?: string,
        public post?: string,
    ) {
    }
}
