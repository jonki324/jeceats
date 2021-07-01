import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ItemListComponent } from './item-list/item-list.component';
import { ItemCreateComponent } from './item-create/item-create.component';
import { ItemDetailComponent } from './item-detail/item-detail.component';
import { ItemEditComponent } from './item-edit/item-edit.component';

const routes: Routes = [
  {
    path: 'items',
    component: ItemListComponent
  },
  {
    path: 'items/create',
    component: ItemCreateComponent
  },
  {
    path: 'items/:id',
    component: ItemDetailComponent
  },
  {
    path: 'items/:id/edit',
    component: ItemEditComponent
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
