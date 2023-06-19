import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Login } from '../models/login';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  url = 'http://localhost:8080/foodserver/api'

  constructor(private http:HttpClient) {  }


  authLogin(login : Login): Observable<any>
  {
    return this.http.post(`${this.url}/restaurant/login`, login);
  }

}
