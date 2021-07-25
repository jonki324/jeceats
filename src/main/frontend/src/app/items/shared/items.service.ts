import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Item } from './item.model';
import { ApiService } from '../../core/services/api.service';

@Injectable({
  providedIn: 'root'
})
export class ItemsService {
  private apiUrl = '/items'

  constructor(private apiService: ApiService) { }

  getAll(): Observable<Item[]> {
    return this.apiService.get(this.apiUrl).pipe(map((data => data.items)))
  }

  get(id: number): Observable<Item> {
    const url = `${this.apiUrl}/${id}`
    return this.apiService.get(url).pipe(map((data => data.item)))
  }

  add(item: Item): Observable<any> {
    return this.apiService.post(this.apiUrl, item)
  }

  update(item: Item): Observable<any> {
    const url = `${this.apiUrl}/${item.id}`
    return this.apiService.put(url, item)
  }

  delete(item: Item): Observable<any> {
    const url = `${this.apiUrl}/${item.id}`
    return this.apiService.delete(url, item)
  }
}
