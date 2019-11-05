export interface IMakeBabyRequestDTO {
    img1?: any;
    img2?: any;
}
export class MakeBabyRequestDTO implements IMakeBabyRequestDTO {
    constructor(
        public img1?: any,
        public img2?: any
    ) {
        this.img1 = img1 ? img1 : null;
        this.img2 = img2 ? img2 : null;
    }
}
