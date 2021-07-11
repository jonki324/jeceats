import { Component, OnInit } from '@angular/core';
import { ToastConfig } from './toast-config.model';
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-toast',
  templateUrl: './toast.component.html'
})
export class ToastComponent implements OnInit {
  toast: ToastConfig = {} as ToastConfig

  constructor(private toastService: ToastService) { }

  ngOnInit(): void {
    this.toastService.getToast().subscribe(toast => this.toast = toast)
  }

  close(toast: ToastConfig): void {
    toast.hide()
  }
}
