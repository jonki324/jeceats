import { Component, OnInit } from '@angular/core';
import { Item } from '../../models/item.model';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {
  items: Item[] = [
    {
      id: 1,
      name: 'name1',
      price: 100,
      description: 'desc1',
      version: 1
    },
    {
      id: 2,
      name: 'name2',
      price: 200,
      description: 'desc2',
      version: 1
    },
    {
      id: 3,
      name: 'name3',
      price: 300,
      description: 'desc3',
      version: 1
    },
  ]

  constructor() { }

  ngOnInit(): void {
  }

}
