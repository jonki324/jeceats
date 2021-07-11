import { Component, OnInit } from '@angular/core';
import { MdbModalRef } from 'mdb-angular-ui-kit/modal';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html'
})
export class DialogComponent implements OnInit {
  message: string = ''

  constructor(private modalRef: MdbModalRef<DialogComponent>) { }

  ngOnInit(): void {
  }

  onClose(): void {
    this.modalRef.close(false)
  }

  onCancel(): void {
    this.onClose()
  }

  onOK(): void {
    this.modalRef.close(true)
  }
}
