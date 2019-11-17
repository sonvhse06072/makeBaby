import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Account } from 'app/core/user/account.model';
import { MakeBabyService } from 'app/core/make-baby/make-baby.service';
import { MakeBabyRequestDTO } from 'app/core/make-baby/make-baby-request.model';
import { MakeBabyDTO } from './MakeBabyDTO.model';
import { Share } from 'app/core/make-baby/share.model';
import { DomSanitizer } from '@angular/platform-browser';

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
  shareLink;
  embedLink;
  loadEmbed = false;
  constructor(
    private makeBabyService: MakeBabyService,
    public sanitizer: DomSanitizer
  ) { }

  ngOnInit() {
  }

  clear() {
    this.imgMom = null;
    this.makeBabyDTO = [];
    this.gender = 'either';
    this.ethnicity = 'auto';
    this.babyname = 'Huy';
    this.shareLink = null;
    this.embedLink = null;
  }

  createPoll() {
    const dataShare = [];
    this.loadEmbed = true;
    this.makeBabyDTO.forEach((element, index) => {
      if (element.baby) {
        const tmp = new Share(element.baby, index + 1);
        dataShare.push(tmp);
      }
    });
    this.makeBabyService.share(dataShare).subscribe(
      (res: any) => {
        // eslint-disable-next-line no-console
        console.log('res share: ', res.body);
        this.shareLink = res.body.data.json.link;
        this.embedLink = this.sanitizer.bypassSecurityTrustResourceUrl(res.body.data.json.link.replace('/vote/', '/embed/'));
        // eslint-disable-next-line no-console
        console.log('share link: ', this.shareLink);
        this.loadEmbed = false;
      }
    )
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
