import { Component, OnInit } from '@angular/core';
import { Item } from '../models/item.model';

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
  constructor() { }

  ngOnInit(): void {
  }

}
