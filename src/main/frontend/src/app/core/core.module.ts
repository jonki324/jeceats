import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FooterComponent } from './layout/footer/footer.component';
import { HeaderComponent } from './layout/header/header.component';
import { MdbCollapseModule } from 'mdb-angular-ui-kit/collapse';
import { CoreRoutingModule } from './core-routing.module';
import { SpinnerComponent } from './layout/spinner/spinner.component';



@NgModule({
  declarations: [
    FooterComponent,
    HeaderComponent,
    SpinnerComponent
  ],
  imports: [
    CommonModule,
    CoreRoutingModule,
    MdbCollapseModule
  ],
  exports: [
    FooterComponent,
    HeaderComponent,
    SpinnerComponent
  ]
})
export class CoreModule { }
