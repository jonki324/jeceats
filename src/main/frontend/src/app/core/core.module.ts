import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './layout/footer/footer.component';
import { HeaderComponent } from './layout/header/header.component';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { CoreRoutingModule } from './core-routing.module';
import { SpinnerComponent } from './layout/spinner/spinner.component';
import { DialogComponent } from './layout/dialog/dialog.component';
import { ToastComponent } from './layout/toast/toast.component';



@NgModule({
  declarations: [
    FooterComponent,
    HeaderComponent,
    SpinnerComponent,
    DialogComponent,
    ToastComponent
  ],
  imports: [
    CommonModule,
    CoreRoutingModule,
    MdbCollapseModule
  ],
  exports: [
    FooterComponent,
    HeaderComponent,
    SpinnerComponent,
    ToastComponent
  ]
})
export class CoreModule { }
