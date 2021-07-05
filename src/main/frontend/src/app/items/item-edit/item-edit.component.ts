import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ItemsService } from 'src/app/services/items.service';
import { Item } from '../../models/item.model';

@Component({
  selector: 'app-item-edit',
  templateUrl: './item-edit.component.html',
  styleUrls: ['./item-edit.component.css']
})
export class ItemEditComponent implements OnInit {
  item: Item = {
    id: 1,
    name: "name1",
    price: 100,
    description: "desc1",
    version: 1
  }
  constructor(
    private itemsService: ItemsService,
    private location: Location,
    private route: ActivatedRoute
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
    this.itemsService.get(id).subscribe(item => this.item = item.item)
  }

  edit(): void {
    this.itemsService.update(this.item).subscribe(() => this.goBack())
  }
}
