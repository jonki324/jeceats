import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastService } from '../../core/services/toast.service';
import { UsersService } from '../shared/users.service';
import { User } from '../shared/user.model';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {
  user: Partial<User> = {} as User

  errors: any = {}

  constructor(
    private usersService: UsersService,
    private router: Router,
    private toastService: ToastService
  ) { }

  ngOnInit(): void {
  }

  onSubmit(form: NgForm): void {
    this.usersService.doLogin(this.user).subscribe(
      () => this.router.navigate(['/items']),
      errors => {
        this.errors = errors.errors ?? {}
        this.errors.system?.forEach((err: string) => {
          this.toastService.danger(err)
        })
        form.form.markAsPristine()
        form.form.markAsUntouched()
      }
    )
  }
}
