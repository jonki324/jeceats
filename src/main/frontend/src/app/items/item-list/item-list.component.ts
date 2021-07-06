import { Component, OnInit } from '@angular/core';
import { Item } from '../../models/item.model';
import { ItemsService } from 'src/app/services/items.service';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {
  items: Item[] = []

  constructor(private itemsService: ItemsService) { }

  ngOnInit(): void {
    this.getAll()
  }

  getAll(): void {
    this.itemsService.getAll().subscribe(items => this.items = items,
      error => this.items = [])
  }

  delete(item: Item): void {
    this.itemsService.delete(item).subscribe(() => this.getAll())
  }
}
