import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { map, take } from 'rxjs/operators';
import { ToastService } from './toast.service';
import { UsersService } from '../../users/shared/users.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  constructor(
    private router: Router,
    private usersService: UsersService,
    private toastService: ToastService
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.usersService.isAuthenticated.pipe(
      take(1),
      map(isAuth => {
        if (isAuth) {
          return true
        } else {
          this.toastService.danger('再度ログインしてください')
          return this.router.createUrlTree(['users', 'login'])
        }
      })
    )
  }
}
