import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Item, ItemList, ItemOne } from '../models/item.model';

@Injectable({
  providedIn: 'root'
})
export class ItemsService {
  private apiUrl = '/api/items'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  }

  constructor(private http: HttpClient) { }

  getAll(): Observable<ItemList> {
    return this.http.get<ItemList>(this.apiUrl).pipe(
      catchError(this.handleError<ItemList>('getAll', { items: [] }))
    )
  }

  get(id: number): Observable<ItemOne> {
    const url = `${this.apiUrl}/${id}`
    return this.http.get<ItemOne>(url).pipe(
      catchError(this.handleError<any>('get'))
    )
  }

  add(item: Item): Observable<any> {
    return this.http.post(this.apiUrl, item, this.httpOptions).pipe(
      catchError(this.handleError<any>('add'))
    )
  }

  update(item: Item): Observable<any> {
    const url = `${this.apiUrl}/${item.id}`
    return this.http.put(url, item, this.httpOptions).pipe(
      catchError(this.handleError<any>('update'))
    )
  }

  delete(item: Item): Observable<any> {
    const url = `${this.apiUrl}/${item.id}`
    return this.http.delete(url, { ...this.httpOptions, body: item }).pipe(
      catchError(this.handleError<any>('delete'))
    )
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error)
      return of(result as T)
    }
  }
}
