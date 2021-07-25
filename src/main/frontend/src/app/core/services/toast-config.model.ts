import { Observable } from "rxjs";

export enum STYLE {
  info = 'bg-info',
  success = 'bg-success',
  warning = 'bg-warning',
  danger = 'bg-danger'
}

export interface ToastConfig {
  isShow: Observable<boolean>
  message: string
  style: STYLE
  hide(): void
}
