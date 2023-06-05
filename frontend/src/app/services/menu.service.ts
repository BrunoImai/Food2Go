import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product';


@Injectable({
  providedIn: 'root'
})
export class MenuService {
  url = 'http://localhost:8080/foodserver/api'

constructor(private http:HttpClient) {  }

  getProducts(): Observable<any>
  {
    return this.http.get(`${this.url}/restaurant/1/products`);
  }

  getProductById(restaurantId: number, orderId: number): Observable<any>
  {
    return this.http.get(`${this.url}/${restaurantId}/${orderId}/products`);
  }

  addProduct(product : Product): Observable<any>
  {
    return this.http.post(`${this.url}/restaurant/2/orders`, product);
  }

  // TODO
  // updateProduct(product : Product): Observable<any>
  // {
  //   return this.http.post(`${this.url}/restaurant/2/orders`, product);
  // }

}
