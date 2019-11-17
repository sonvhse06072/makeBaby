export interface IHistoryRequestDTO {
    imgMom?: any;
    gender?: any;
    ethnicity?: any;
    babyname?: any;
    dadAndSons?: any;
}
export class HistoryRequestDTO implements IHistoryRequestDTO {
    constructor(
        public imgMom?: any,
        public gender?: any,
        public ethnicity?: any,
        public babyname?: any,
        public dadAndSons?: any
    ) {
        this.imgMom = imgMom ? imgMom : null;
        this.gender = gender ? gender : null;
        this.ethnicity = ethnicity ? ethnicity : null;
        this.babyname = babyname ? babyname : null;
        this.dadAndSons = dadAndSons ? dadAndSons : null;
    }
}
