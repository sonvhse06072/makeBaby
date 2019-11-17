export interface IMakeBabyRequestDTO {
    img1?: any;
    img2?: any;
    gender?: any;
    ethnicity?: any;
    babyname?: any;
}
export class MakeBabyRequestDTO implements IMakeBabyRequestDTO {
    constructor(
        public img1?: any,
        public img2?: any,
        public gender?: any,
        public ethnicity?: any,
        public babyname?: any
    ) {
        this.img1 = img1 ? img1 : null;
        this.img2 = img2 ? img2 : null;
        this.gender = gender ? gender : null;
        this.ethnicity = ethnicity ? ethnicity : null;
        this.babyname = babyname ? babyname : null;
    }
}
