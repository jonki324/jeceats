import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { ItemsService } from 'src/app/items/shared/items.service';
import { ToastService } from 'src/app/core/services/toast.service';
import { Item } from '../shared/item.model';

@Component({
  selector: 'app-item-detail',
  templateUrl: './item-detail.component.html',
  styleUrls: ['./item-detail.component.css']
})
export class ItemDetailComponent implements OnInit {
  item: Item = {} as Item

  constructor(
    private itemsService: ItemsService,
    private route: ActivatedRoute,
    private location: Location,
    private router: Router,
    private toastService: ToastService
  ) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe((params: ParamMap) => {
      const id = Number(params.get('id'))
      this.get(id)
    })
  }

  goBack(): void {
    this.location.back()
  }

  get(id: number): void {
    this.itemsService.get(id).subscribe(
      item => this.item = item,
      errors => {
        errors.errors?.system?.forEach((err: string) => {
          this.toastService.danger(err)
        })
        this.router.navigate(['/items'])
      }
    )
  }
}
