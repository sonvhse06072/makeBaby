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
  img1: any;
  img2: any;
  imgBaby: any;
  gender = 'either';
  ethnicity = 'auto';
  babyname = 'Huy';
  history = [];
  constructor(
    private accountService: AccountService,
    private loginModalService: LoginModalService,
    private eventManager: JhiEventManager,
    private makeBabyService: MakeBabyService,
    private sanitizer:DomSanitizer
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
    this.img1 = this.baseDir + item.img1;
    this.img2 = this.baseDir + item.img2;
    this.imgBaby = this.baseDir + item.imgRes;
    this.gender = item.gender;
    this.ethnicity = item.ethnicity;
    this.babyname = item.babyname;
    this.viewOld = true;
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

  uploadFile(event, check, fileUpload) {
    if (this.viewOld) {
      this.img1 = null;
      this.img2 = null;
      this.imgBaby = null;
      this.viewOld = false;
    }
    this.makeBabyService.upload(event.files[0]).subscribe(
      (res: any) => {
        // eslint-disable-next-line no-console
        console.log('res: ', res.body);
        if (check === 1) {
          this.img1 = res.body.img;
        } else {
          this.img2 = res.body.img;
        }
      });
    fileUpload.clear();
  }

  generate() {
    if (this.img1 && this.img2) {
      const data = new MakeBabyRequestDTO(this.img1, this.img2, this.gender, this.ethnicity, this.babyname);
      this.makeBabyService.generate(data).subscribe(
        (res: any) => {
          // eslint-disable-next-line no-console
        console.log('res: ', res.body);
        this.imgBaby = res.body.result_url;
        this.getAllHistory();
        }
      )
    }
  }
}
