import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MakeBabiesProjectSharedModule } from 'app/shared/shared.module';
import { HOME_ROUTE } from './home.route';
import { HomeComponent } from './home.component';
import { JwSocialButtonsModule } from 'jw-angular-social-buttons';
import {TableModule} from 'primeng/table';

@NgModule({
  imports: [MakeBabiesProjectSharedModule, TableModule, JwSocialButtonsModule, RouterModule.forChild([HOME_ROUTE])],
  declarations: [HomeComponent]
})
export class MakeBabiesProjectHomeModule {}
