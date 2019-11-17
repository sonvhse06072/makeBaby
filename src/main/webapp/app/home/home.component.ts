import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';
import { Account } from 'app/core/user/account.model';
import { MakeBabyService } from 'app/core/make-baby/make-baby.service';
import { MakeBabyRequestDTO } from 'app/core/make-baby/make-baby-request.model';
import { SERVER_API_URL } from 'app/app.constants';
import { MakeBabyDTO } from './MakeBabyDTO.model';
import { HistoryRequestDTO } from 'app/core/make-baby/history-save.model';

@Component({
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrls: ['home.scss']
})
export class HomeComponent implements OnInit {
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
  historyId;
  isLoadingMom = false;
  constructor(
    private eventManager: JhiEventManager,
    private makeBabyService: MakeBabyService
  ) { }

  ngOnInit() {
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

  save() {
    const dadAndSon = [];
    this.makeBabyDTO.forEach(item => {
      dadAndSon.push({ imgDad: item.dad, imgSon: item.baby });
    })
    // eslint-disable-next-line no-console
    console.log('test dad and son: ', dadAndSon);
    const history: HistoryRequestDTO = new HistoryRequestDTO(this.imgMom, this.gender, this.ethnicity, this.babyname, dadAndSon);
    // eslint-disable-next-line no-console
    console.log('test save: ', history);
    this.makeBabyService.saveHistory(history).subscribe(
      (res: any) => {
        // eslint-disable-next-line no-console
        console.log('save res: ', res.body);
        this.getAllHistory();
      }
    )
  }

  loadHistory(item) {
    this.makeBabyDTO = [];
    this.imgMom = item.imgMom;
    this.gender = item.gender;
    this.ethnicity = item.ethnicity;
    this.babyname = item.babyname;
    this.historyId = item.id;
    item.dadAndSons.map(element => {
      const dto = new MakeBabyDTO(null, element.imgDad, element.imgSon);
      this.makeBabyDTO.push(dto);
    })
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
    if (this.viewOld) {
      this.clear();
      this.makeBabyService.deleteHistory(this.historyId).subscribe(
        () => this.getAllHistory()
      )
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
          }
        )
      });
    });
    fileUpload.clear();
  }
}
