export interface IShare {
    imgLink?: any;
    option?: any;
}
export class Share implements IShare {
    constructor(
        public imgLink?: any,
        public option?: any,
    ) {
        this.imgLink = imgLink ? imgLink : null;
        this.option = option ? option : null;
    }
}
