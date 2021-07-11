import { Injectable } from '@angular/core';
import { Observable, of, Subject } from 'rxjs';
import { STYLE, ToastConfig } from '../layout/toast-config.model';

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  toastConfig: ToastConfig = {} as ToastConfig

  toast = new Subject<ToastConfig>()

  constructor() { }

  getToast(): Observable<ToastConfig> {
    return this.toast.asObservable()
  }

  info(message: string): void {
    this.setConfig(message, STYLE.info)
    this.toast.next(this.toastConfig)
  }

  success(message: string): void {
    this.setConfig(message, STYLE.success)
    this.toast.next(this.toastConfig)
  }

  warn(message: string): void {
    this.setConfig(message, STYLE.warning)
    this.toast.next(this.toastConfig)
  }

  danger(message: string): void {
    this.setConfig(message, STYLE.danger)
    this.toast.next(this.toastConfig)
  }

  private setConfig(message: string, style: STYLE): void {
    this.toastConfig = {
      message: message,
      style: style,
      isShow: new Observable<boolean>(subscriber => {
        subscriber.next(true)
        setTimeout(() => {
          subscriber.next(false)
        }, 3000)
      }),
      hide: function () { this.isShow = of(false) }
    }
  }
}
