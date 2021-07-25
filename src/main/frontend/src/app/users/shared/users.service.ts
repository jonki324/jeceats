import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, ReplaySubject } from 'rxjs';
import { distinctUntilChanged, map } from 'rxjs/operators';
import { Login } from './login.model';
import { User } from './user.model';
import { ApiService } from '../../core/services/api.service';
import { JwtService } from '../../core/services/jwt.service';

@Injectable({
  providedIn: 'root'
})
export class UsersService {
  private apiUrl = '/users'

  private currentUserSubject = new BehaviorSubject<User>({} as User)
  public currentUser = this.currentUserSubject.asObservable().pipe(distinctUntilChanged())

  private isAuthenticaticatedSubject = new ReplaySubject<boolean>(1)
  public isAuthenticated = this.isAuthenticaticatedSubject.asObservable()

  constructor(
    private apiService: ApiService,
    private jwtService: JwtService
  ) { }

  doLogin(login: Login): Observable<User> {
    const url = `${this.apiUrl}/login`
    return this.apiService.post(url, login).pipe(
      map(data => {
        this.saveAuth(data.user, data.token)
        return data.user
      })
    )
  }

  saveAuth(user: User, token: string) {
    this.jwtService.saveToken(token)
    this.currentUserSubject.next(user)
    this.isAuthenticaticatedSubject.next(true)
  }

  doLogout(): void {
    this.jwtService.destroyToken()
    this.currentUserSubject.next({} as User)
    this.isAuthenticaticatedSubject.next(false)
  }

  getCurrentUser(): User {
    return this.currentUserSubject.value
  }
}
