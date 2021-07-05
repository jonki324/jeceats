import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { ItemsService } from 'src/app/services/items.service';
import { Item } from '../../models/item.model';

@Component({
  selector: 'app-item-detail',
  templateUrl: './item-detail.component.html',
  styleUrls: ['./item-detail.component.css']
})
export class ItemDetailComponent implements OnInit {
  item: Item = {
    id: null,
    name: "",
    price: 0,
    description: "",
    version: null
  }

  constructor(
    private itemsService: ItemsService,
    private route: ActivatedRoute,
    private location: Location
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
}
