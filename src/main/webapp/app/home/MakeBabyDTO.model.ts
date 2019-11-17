export interface IMakeBabyDTO {
    file?: any;
    dad?: any;
    baby?: any;
}
export class MakeBabyDTO implements IMakeBabyDTO {
    constructor(
        public file?: any,
        public dad?: any,
        public baby?: any,
    ) {
        this.file = file ? file : null;
        this.dad = dad ? dad : null;
        this.baby = baby ? baby : null;
    }
}
