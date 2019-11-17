import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { LoginModalService } from 'app/core/login/login-modal.service';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/user/account.model';
import { MakeBabyService } from 'app/core/make-baby/make-baby.service';
import { MakeBabyRequestDTO } from 'app/core/make-baby/make-baby-response.model';
import { DomSanitizer } from '@angular/platform-browser';
import { SERVER_API_URL } from 'app/app.constants';
import { MakeBabyDTO } from './MakeBabyDTO.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit, OnDestroy {
  account: Account;
  authSubscription: Subscription;
  modalRef: NgbModalRef;
  baseDir = SERVER_API_URL + '/api/upload/files/';
  viewOld = false;
  makeBabyDTO: MakeBabyDTO[] = [];
  imgMom: any;
  gender = 'either';
  ethnicity = 'auto';
  babyname = 'Huy';
  history = [];
  isLoadingMom = false;
  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private eventManager: JhiEventManager,
    private makeBabyService: MakeBabyService,
    private sanitizer: DomSanitizer
  ) { }

  ngOnInit() {
    this.accountService.identity().subscribe((account: Account) => {
      this.account = account;
    });
    this.registerAuthenticationSuccess();
    this.getAllHistory();
  }

  getAllHistory() {
    this.makeBabyService.getAll().subscribe(
      (res: any) => {
        this.history = res.body;
        // eslint-disable-next-line no-console
        console.log('res: ', res.body);
      }
    )
  }

  loadHistory(item) {
    this.imgMom = this.baseDir + item.img2;
    this.gender = item.gender;
    this.ethnicity = item.ethnicity;
    this.babyname = item.babyname;
    this.viewOld = true;
  }

  clear() {
    this.imgMom = null;
    this.makeBabyDTO = [];
    this.gender = 'either';
    this.ethnicity = 'auto';
    this.babyname = 'Huy';
    this.viewOld = false;
  }

  clearDad() {
    this.makeBabyDTO = [];
  }

  registerAuthenticationSuccess() {
    this.authSubscription = this.eventManager.subscribe('authenticationSuccess', message => {
      this.accountService.identity().subscribe(account => {
        this.account = account;
      });
    });
  }

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }

  login() {
    this.modalRef = this.loginModalService.open();
  }

  ngOnDestroy() {
    if (this.authSubscription) {
      this.eventManager.destroy(this.authSubscription);
    }
  }

  uploadMom(event, fileUpload) {
    this.isLoadingMom = true;
    if (this.viewOld) {
      this.imgMom = null;
      this.makeBabyDTO = [];
      this.viewOld = false;
    }
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
            this.getAllHistory();
          }
        )
      });
    });
    fileUpload.clear();
  }
}
