import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserLoginComponent } from './user-login/user-login.component';
import { FormsModule } from '@angular/forms';
import { UsersRoutingModule } from './users-routing.module';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [
    UserLoginComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    UsersRoutingModule,
    SharedModule
  ]
})
export class UsersModule { }
