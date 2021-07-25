import { Injectable } from '@angular/core';
import { MdbModalRef, MdbModalService } from 'mdb-angular-ui-kit/modal';
import { Observable } from 'rxjs';
import { DialogComponent } from '../layout/dialog/dialog.component';

@Injectable({
  providedIn: 'root'
})
export class DialogService {
  modalRef: MdbModalRef<DialogComponent> = {} as MdbModalRef<DialogComponent>

  config: any = {
    modalClass: 'modal-dialog-centered modal-sm',
    data: {
      message: ''
    }
  }

  constructor(private modalService: MdbModalService) { }

  open(message: string): Observable<any> {
    this.config.data.message = message
    this.modalRef = this.modalService.open(DialogComponent, this.config)
    return this.modalRef.onClose
  }
}
