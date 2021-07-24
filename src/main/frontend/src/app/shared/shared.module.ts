import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ErrorListComponent } from './error-list/error-list.component';
import { InvalidStyleDirective } from './invalid-style.directive';



@NgModule({
  declarations: [
    ErrorListComponent,
    InvalidStyleDirective
  ],
  imports: [
    CommonModule
  ],
  exports: [
    ErrorListComponent,
    InvalidStyleDirective
  ]
})
export class SharedModule { }
