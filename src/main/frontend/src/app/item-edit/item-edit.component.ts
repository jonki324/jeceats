import { Component, OnInit } from '@angular/core';
import { Item } from '../models/item.model';

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
  constructor() { }

  ngOnInit(): void {
  }

}
