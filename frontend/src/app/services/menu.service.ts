import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Product } from '../models/product';
import { BehaviorSubject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class MenuService {
  url = 'http://localhost:8080/foodserver/api'


constructor(private http:HttpClient) {  }
  private dataSource: BehaviorSubject<Product[]> = new BehaviorSubject<Product[]>([]);
  private ls: any = JSON.parse(localStorage.getItem('token')!);

  
  getProducts(restaurantId: number): Observable<any>
  {
    return this.http.get(`${this.url}/restaurant/${restaurantId}/products`);
  }

  getProductById(restaurantId: number, orderId: number): Observable<any>
  {
    return this.http.get(`${this.url}/${restaurantId}/${orderId}/products`);
  }

  addProduct(restaurantId: number, product : Product): Observable<any>
  {
    console.log(this.ls.token)
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + this.ls.token
      })
    };
    return this.http.post(`${this.url}/restaurant/${restaurantId}/products`, product, httpOptions);
  }

  updateProduct(restaurantId: number, product : Product): Observable<any>
  {
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + this.ls.token
      })
    };
    return this.http.put(`${this.url}/restaurant/${restaurantId}/products/${product.id}`, product, httpOptions);
  }

  deleteProduct(id: number): Observable<any>{
    const httpOptions = {
      headers: new HttpHeaders({
        'Content-Type':  'application/json',
        'Authorization': 'Bearer ' + this.ls.token
      })
    };
    return this.http.delete(`${this.url}/restaurant/1/products/${id}`, httpOptions);
  }

  getDataSource() {
    return this.dataSource.asObservable();
  }

  updateDataSource(newData: Product[]) {
    this.dataSource.next(newData);
  }

}
