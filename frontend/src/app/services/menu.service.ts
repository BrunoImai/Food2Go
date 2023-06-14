import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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
    return this.http.post(`${this.url}/restaurant/1/products`, product);
  }

  updateProduct(product : Product): Observable<any>
  {
    return this.http.put(`${this.url}/restaurant/1/products/${product.id}`, product);
  }

  deleteProduct(id: number): Observable<any>{
    return this.http.delete(`${this.url}/restaurant/1/products/${id}`);
  }

  getDataSource() {
    return this.dataSource.asObservable();
  }

  updateDataSource(newData: Product[]) {
    this.dataSource.next(newData);
  }

}
