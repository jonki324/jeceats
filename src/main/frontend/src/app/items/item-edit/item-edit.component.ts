import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { filter, mergeMap } from 'rxjs/operators';
import { DialogService } from '../../core/services/dialog.service';
import { ItemsService } from '../shared/items.service';
import { ToastService } from '../../core/services/toast.service';
import { Item } from '../shared/item.model';

@Component({
  selector: 'app-item-edit',
  templateUrl: './item-edit.component.html',
  styleUrls: ['./item-edit.component.css']
})
export class ItemEditComponent implements OnInit {
  item: Item = {} as Item

  reader = new FileReader()
  preview: any = null

  errors: any = {}

  constructor(
    private itemsService: ItemsService,
    private location: Location,
    private route: ActivatedRoute,
    private router: Router,
    private dialogService: DialogService,
    private toastService: ToastService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => {
      const id = Number(params.get('id'))
      this.get(id)
    })
    this.reader.onload = e => this.preview = this.reader.result
  }

  goBack(): void {
    this.location.back()
  }

  get(id: number): void {
    this.itemsService.get(id).subscribe(
      item => this.item = item,
      errors => {
        this.errors = errors.errors ?? {}
        this.errors.system?.foreach((err: string) => {
          this.toastService.danger(err)
        })
        this.router.navigate(['/items'])
      }
    )
  }

  onSubmit(form: NgForm): void {
    const confirmMsg = '変更しますか？'
    const successMsg = '変更しました'
    this.dialogService.open(confirmMsg).pipe(
      filter(isOK => isOK),
      mergeMap(() => this.itemsService.update(this.item))
    ).subscribe(
      () => {
        this.toastService.success(successMsg)
        this.goBack()
      },
      errors => {
        this.errors = errors.errors ?? {}
        this.errors.system?.foreach((err: string) => {
          this.toastService.danger(err)
        })
        form.form.markAsPristine()
        form.form.markAsUntouched()
      }
    )
  }

  readFile(event: any): void {
    const files: File[] = event.target.files
    if (files.length > 0) {
      this.item.file = files[0]
      this.reader.readAsDataURL(files[0])
    } else {
      this.item.file = {} as File
      this.preview = null
    }
  }
}
