import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';	
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class OrderService {
  url = 'http://localhost:8080/foodserver/api'

constructor(private http:HttpClient) {  } 

  getOrders(): Observable<any>
  {
    return this.http.get(`${this.url}/restaurant/2/orders`);
  }

  getCustomers(): Observable<any> 
  {
    return this.http.get(`${this.url}/costumers`);
  }

  getCustomerById(id: number): Observable<any> 
  {
    return this.http.get(`${this.url}/costumers/${id}`);
  }
 
  getProductById(restaurantId: number, orderId: number): Observable<any> 
  {
    return this.http.get(`${this.url}/${restaurantId}/${orderId}/products`);
  }
}
