import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { DialogService } from '../services/dialog.service';
import { UsersService } from '../services/users.service';

@Component({
  selector: 'app-layout-header',
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {
  navItems = [
    { title: 'Home', url: '' },
    { title: 'Items', url: 'items' },
  ]

  isAuthenticated = this.usersService.isAuthenticated

  currentUser = this.usersService.currentUser

  constructor(
    private usersService: UsersService,
    private router: Router,
    private dialogService: DialogService
  ) { }

  ngOnInit(): void {
  }

  onLogin(): void {
    this.usersService.doLogout()
    this.router.navigate(['users', 'login'])
  }

  onLogout(): void {
    this.dialogService.open('ログアウトしますか？').pipe(
      filter(isOK => isOK)
    ).subscribe(
      () => {
        this.usersService.doLogout()
        this.router.navigate(['users', 'login'])
      }
    )
  }
}
