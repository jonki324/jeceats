import { Component, OnInit } from '@angular/core';
import { Item } from '../shared/item.model';
import { ItemsService } from 'src/app/items/shared/items.service';
import { ToastService } from 'src/app/services/toast.service';
import { DialogService } from 'src/app/core/services/dialog.service';
import { filter, mergeMap } from 'rxjs/operators';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {
  items: Item[] = []

  constructor(
    private itemsService: ItemsService,
    private toastService: ToastService,
    private dialogService: DialogService
  ) { }

  ngOnInit(): void {
    this.getAll()
  }

  getAll(): void {
    this.itemsService.getAll().subscribe(
      items => this.items = items,
      errors => {
        this.items = []
        errors.errors?.system?.foreach((err: string) => {
          this.toastService.danger(err)
        })
      }
    )
  }

  delete(item: Item): void {
    const confirmMsg = '削除しますか？'
    const successMsg = '削除しました'
    this.dialogService.open(confirmMsg).pipe(
      filter(isOK => isOK),
      mergeMap(() => this.itemsService.delete(item))
    ).subscribe(
      () => {
        this.toastService.success(successMsg)
        this.getAll()
      },
      errors => {
        errors.errors?.system?.foreach((err: string) => {
          this.toastService.danger(err)
        })
      }
    )
  }
}
