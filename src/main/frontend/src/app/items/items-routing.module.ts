import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ItemListComponent } from './item-list/item-list.component';
import { ItemCreateComponent } from './item-create/item-create.component';
import { ItemDetailComponent } from './item-detail/item-detail.component';
import { ItemEditComponent } from './item-edit/item-edit.component';

const routes: Routes = [
  {
    path: '',
    component: ItemListComponent
  },
  {
    path: 'create',
    component: ItemCreateComponent
  },
  {
    path: ':id',
    component: ItemDetailComponent
  },
  {
    path: ':id/edit',
    component: ItemEditComponent
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ItemsRoutingModule { }
