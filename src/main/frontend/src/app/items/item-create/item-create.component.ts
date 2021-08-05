import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { filter, mergeMap } from 'rxjs/operators';
import { DialogService } from '../../core/services/dialog.service';
import { ItemsService } from '../shared/items.service';
import { ToastService } from '../../core/services/toast.service';
import { Item } from '../shared/item.model';

@Component({
  selector: 'app-item-create',
  templateUrl: './item-create.component.html',
  styleUrls: ['./item-create.component.css']
})
export class ItemCreateComponent implements OnInit {
  item: Item = {} as Item

  errors: any = {}

  constructor(
    private itemsService: ItemsService,
    private location: Location,
    private dialogService: DialogService,
    private toastService: ToastService
  ) { }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back()
  }

  onSubmit(form: NgForm): void {
    const confirmMsg = '登録しますか？'
    const successMsg = '登録しました'
    this.dialogService.open(confirmMsg).pipe(
      filter(isOK => isOK),
      mergeMap(() => this.itemsService.add(this.item))
    ).subscribe(
      () => {
        this.toastService.success(successMsg)
        this.goBack()
      },
      errors => {
        console.error(errors)
        this.errors = errors.errors ?? {}
        this.errors.system?.forEach((err: string) => {
          this.toastService.danger(err)
        })
        form.form.markAsPristine()
        form.form.markAsUntouched()
      }
    )
  }

  readFile(event: any): void {
    const files = event.target.files
    if (files.lenght) {
      this.item.file = files[0]
    }
  }
}
