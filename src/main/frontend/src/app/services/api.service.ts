import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-type': 'application/json' })
  }

  constructor(private http: HttpClient) { }

  get(path: string, params: HttpParams = new HttpParams()): Observable<any> {
    return this.http.get(`${environment.apiUrl}${path}`, { params })
      .pipe(catchError(this.handleError))
  }

  post(path: string, body: Object = {}): Observable<any> {
    return this.http.post(`${environment.apiUrl}${path}`, body, this.httpOptions)
      .pipe(catchError(this.handleError))
  }

  put(path: string, body: Object = {}): Observable<any> {
    return this.http.put(`${environment.apiUrl}${path}`, body, this.httpOptions)
      .pipe(catchError(this.handleError))
  }

  delete(path: string, body: Object = {}): Observable<any> {
    return this.http.delete(`${environment.apiUrl}${path}`, { ...this.httpOptions, body })
      .pipe(catchError(this.handleError))
  }

  private handleError(error: any) {
    console.error(error);
    return throwError(error.error)
  }
}
