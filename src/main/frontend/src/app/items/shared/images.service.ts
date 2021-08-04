import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiService } from 'src/app/core/services/api.service';
import { PresignedObjectUrl } from './presigned-object-url.model';

@Injectable({
  providedIn: 'root'
})
export class ImagesService {
  private apiUrl = '/images'

  constructor(private apiService: ApiService) { }

  getPresignedObjectUrlForCreatePut(): Observable<PresignedObjectUrl> {
    return this.apiService.put(this.apiUrl)
  }

  getPresignedObjectUrlGet(id: number, objectName: string): Observable<PresignedObjectUrl> {
    const url = `${this.apiUrl}/${id}/${objectName}`
    return this.apiService.get(url)
  }

  getPresignedObjectUrlForUpdatePut(id: number, objectName: string): Observable<PresignedObjectUrl> {
    const url = `${this.apiUrl}/${id}/${objectName}`
    return this.apiService.put(url)
  }

  get(signedUrl: PresignedObjectUrl): Observable<any> {
    return this.apiService.get(signedUrl.presignedObjectUrl)
  }

  put(signedUrl: PresignedObjectUrl, file: FormData): Observable<any> {
    return this.apiService.put(signedUrl.presignedObjectUrl, file)
  }
}
