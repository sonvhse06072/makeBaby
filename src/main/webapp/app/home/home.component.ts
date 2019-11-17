import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Account } from 'app/core/user/account.model';
import { MakeBabyService } from 'app/core/make-baby/make-baby.service';
import { MakeBabyRequestDTO } from 'app/core/make-baby/make-baby-request.model';
import { SERVER_API_URL } from 'app/app.constants';
import { MakeBabyDTO } from './MakeBabyDTO.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
  account: Account;
  authSubscription: Subscription;
  modalRef: NgbModalRef;
  makeBabyDTO: MakeBabyDTO[] = [];
  imgMom: any;
  gender = 'either';
  ethnicity = 'auto';
  babyname = 'Huy';
  isLoadingMom = false;
  constructor(
    private eventManager: JhiEventManager,
    private makeBabyService: MakeBabyService
  ) { }

  ngOnInit() {
  }

  clear() {
    this.imgMom = null;
    this.makeBabyDTO = [];
    this.gender = 'either';
    this.ethnicity = 'auto';
    this.babyname = 'Huy';
  }

  clearDad() {
    this.makeBabyDTO = [];
  }

  uploadMom(event, fileUpload) {
    this.isLoadingMom = true;
    this.makeBabyService.upload(event.files[0]).subscribe(
      (res: any) => {
        // eslint-disable-next-line no-console
        console.log('res: ', res.body);
        this.imgMom = res.body.img;
        this.isLoadingMom = false;
      });
    fileUpload.clear();
  }

  uploadDads(event, fileUpload) {
    for (const file of event.files) {
      const dto: MakeBabyDTO = new MakeBabyDTO(file, '', '');
      this.makeBabyDTO.push(dto);
    }
    this.makeBabyDTO.map(element => {
      this.makeBabyService.upload(element.file).subscribe((res: any) => {
        element.dad = res.body.img;
        // eslint-disable-next-line no-console
        console.log('res dad: ', res.body);
        const data = new MakeBabyRequestDTO(res.body.img, this.imgMom, this.gender, this.ethnicity, this.babyname);
        this.makeBabyService.generate(data).subscribe(
          (result: any) => {
            // eslint-disable-next-line no-console
            console.log('res baby: ', result.body);
            element.baby = result.body.result_url;
          }
        )
      });
    });
    fileUpload.clear();
  }
}
