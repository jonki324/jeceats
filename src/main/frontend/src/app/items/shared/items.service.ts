import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';
import { Item } from './item.model';
import { ApiService } from '../../core/services/api.service';
import { ImagesService } from './images.service';

@Injectable({
  providedIn: 'root'
})
export class ItemsService {
  private apiUrl = '/items'

  constructor(
    private apiService: ApiService,
    private imagesService: ImagesService
  ) { }

  getAll(): Observable<Item[]> {
    return this.apiService.get(this.apiUrl).pipe(map((data => data.items)))
  }

  get(id: number): Observable<Item> {
    const url = `${this.apiUrl}/${id}`
    return this.apiService.get(url).pipe(map((data => data.item)))
  }

  add(item: Item): Observable<any> {
    return this.imagesService.getPresignedObjectUrlForCreatePut().pipe(
      mergeMap(
        signedUrl => {
          item.objectName = signedUrl.objectName
          return this.imagesService.put(signedUrl, item.file)
        }
      ),
      mergeMap(() => this.apiService.post(this.apiUrl, item))
    )
  }

  update(item: Item): Observable<any> {
    const url = `${this.apiUrl}/${item.id}`
    if (item.file) {
      return this.imagesService.getPresignedObjectUrlForUpdatePut(item.id, item.objectName).pipe(
        mergeMap(
          signedUrl => {
            return this.imagesService.put(signedUrl, item.file)
          }
        ),
        mergeMap(() => this.apiService.put(url, item))
      )
    } else {
      return this.apiService.put(url, item)
    }
  }

  delete(item: Item): Observable<any> {
    const url = `${this.apiUrl}/${item.id}`
    return this.apiService.delete(url, item)
  }
}
