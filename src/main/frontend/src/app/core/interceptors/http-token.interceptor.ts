import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { EMPTY, Observable, throwError } from 'rxjs';
import { JwtService } from '../services/jwt.service';
import { catchError } from 'rxjs/operators';
import { Router } from '@angular/router';
import { ToastService } from '../services/toast.service';

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {

  constructor(
    private jwtService: JwtService,
    private router: Router,
    private toastService: ToastService
  ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let req = request
    if (!(req.method.toLowerCase() === 'put' && req.serializeBody() instanceof File)) {
      const headersConfig: any = {
        'Content-Type': 'application/json'
      }
      const token = this.jwtService.getToken()
      if (token) {
        headersConfig['Authorization'] = `Bearer ${token}`
      }
      req = request.clone({ setHeaders: headersConfig })
    }

    return next.handle(req).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.toastService.danger('再度ログインしてください')
          this.router.navigate(['users', 'login'])
          return EMPTY
        } else if (error.status === 403) {
          this.toastService.warn('権限がありません')
        }
        return throwError(error)
      })
    )
  }
}
