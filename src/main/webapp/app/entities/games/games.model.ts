import { BaseEntity } from './../../shared';

export class Games implements BaseEntity {
    constructor(
        public id?: number,
        public tenTaiKhoan?: string,
        public thoiGianTao?: any,
        public thoiGianTruyCapCuoi?: any,
        public status?: number,
    ) {
    }
}
