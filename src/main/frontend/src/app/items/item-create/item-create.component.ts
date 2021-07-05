import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ItemsService } from 'src/app/services/items.service';
import { Item } from '../../models/item.model';

@Component({
  selector: 'app-item-create',
  templateUrl: './item-create.component.html',
  styleUrls: ['./item-create.component.css']
})
export class ItemCreateComponent implements OnInit {
  item: Item = {
    id: null,
    name: "",
    price: 0,
    description: "",
    version: null
  }

  constructor(
    private itemsService: ItemsService,
    private location: Location
  ) { }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back()
  }

  add(): void {
    this.itemsService.add(this.item).subscribe(() => this.goBack())
  }
}
