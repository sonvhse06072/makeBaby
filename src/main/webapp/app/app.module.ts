import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { MakeBabiesProjectSharedModule } from 'app/shared/shared.module';
import { MakeBabiesProjectCoreModule } from 'app/core/core.module';
import { MakeBabiesProjectAppRoutingModule } from './app-routing.module';
import { MakeBabiesProjectHomeModule } from './home/home.module';
import { MakeBabiesProjectEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { JhiMainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';
import {FileUploadModule} from 'primeng/fileupload';

@NgModule({
  imports: [
    BrowserModule,
    MakeBabiesProjectSharedModule,
    MakeBabiesProjectCoreModule,
    MakeBabiesProjectHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    MakeBabiesProjectEntityModule,
    MakeBabiesProjectAppRoutingModule,
    FileUploadModule
  ],
  declarations: [JhiMainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [JhiMainComponent]
})
export class MakeBabiesProjectAppModule {}
