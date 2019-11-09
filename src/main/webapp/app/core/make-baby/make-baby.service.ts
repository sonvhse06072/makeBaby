import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { MakeBabyRequestDTO } from './make-baby-response.model';

@Injectable({
  providedIn: 'root'
})
export class MakeBabyService {

  constructor(private http: HttpClient) { }

  upload(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);
    return this.http.post<any>(SERVER_API_URL + 'api/upload', formData, { observe: 'response' });
  }

  generate(data: MakeBabyRequestDTO): Observable<any> {
    return this.http.post<any>(SERVER_API_URL + 'api/generate', data, { observe: 'response' });
  }

  getAll(): Observable<any> {
    return this.http.get<any>(SERVER_API_URL + 'api/get-all', { observe: 'response' });
  }
}
