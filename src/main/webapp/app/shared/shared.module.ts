import { NgModule } from '@angular/core';
import { MakeBabiesProjectSharedLibsModule } from './shared-libs.module';
import { JhiAlertComponent } from './alert/alert.component';
import { JhiAlertErrorComponent } from './alert/alert-error.component';
import { JhiLoginModalComponent } from './login/login.component';
import { HasAnyAuthorityDirective } from './auth/has-any-authority.directive';
import {FileUploadModule} from 'primeng/fileupload';

@NgModule({
  imports: [MakeBabiesProjectSharedLibsModule, FileUploadModule],
  declarations: [JhiAlertComponent, JhiAlertErrorComponent, JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [MakeBabiesProjectSharedLibsModule, FileUploadModule, JhiAlertComponent, JhiAlertErrorComponent, JhiLoginModalComponent, HasAnyAuthorityDirective]
})
export class MakeBabiesProjectSharedModule {}
