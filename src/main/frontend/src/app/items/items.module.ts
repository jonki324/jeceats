import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { ItemsRoutingModule } from './items-routing.module';
import { ItemListComponent } from './item-list/item-list.component';
import { ItemDetailComponent } from './item-detail/item-detail.component';
import { ItemEditComponent } from './item-edit/item-edit.component';
import { ItemCreateComponent } from './item-create/item-create.component';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [
    ItemListComponent,
    ItemDetailComponent,
    ItemEditComponent,
    ItemCreateComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    ItemsRoutingModule,
    SharedModule
  ]
})
export class ItemsModule { }
