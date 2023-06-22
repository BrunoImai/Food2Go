import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Login } from '../models/login';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  url = 'http://localhost:8080/foodserver/api'

  constructor(private http:HttpClient) { 
    
   }
   private ls: any = JSON.parse(localStorage.getItem('token')!);

  authLogin(login : Login): Observable<any>
  {
    return this.http.post(`${this.url}/restaurant/login`, login);
  }

  authRegister(register : any): Observable<any>{
    return this.http.post(`${this.url}/restaurant`, register);
  }


  editRestaurant(restaurantId: number, restaurant : any): Observable<any>{
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + this.ls.token
      })
    };
    return this.http.put(`${this.url}/restaurant/${restaurantId}`, restaurant, httpOptions);
  }

}
