import { BaseEntity } from './../../shared';

export class Mannager implements BaseEntity {
    constructor(
        public id?: number,
        public spentMoney?: string,
        public spentContent?: string,
        public createdBy?: string,
        public createdDate?: any,
        public status?: number,
        public count?: number,
    ) {
    }
}
