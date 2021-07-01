import { Component, OnInit } from '@angular/core';
import { Item } from '../models/item.model';

@Component({
  selector: 'app-item-detail',
  templateUrl: './item-detail.component.html',
  styleUrls: ['./item-detail.component.css']
})
export class ItemDetailComponent implements OnInit {
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
