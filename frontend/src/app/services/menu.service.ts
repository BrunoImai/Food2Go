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

<<<<<<< Updated upstream
constructor(private http:HttpClient) {  } 
=======
constructor(private http:HttpClient) {  }
  private dataSource: BehaviorSubject<Product[]> = new BehaviorSubject<Product[]>([]);

>>>>>>> Stashed changes

  getProducts(): Observable<any> 
  {
    return this.http.get(`${this.url}/restaurant/2/products`);
  }

  getProductById(restaurantId: number, orderId: number): Observable<any> 
  {
    return this.http.get(`${this.url}/${restaurantId}/${orderId}/products`);
  }

  addProduct(product : Product): Observable<any>
  {
<<<<<<< Updated upstream
    return this.http.post(`${this.url}/restaurant/2/orders`, product);
  }
  
  // TODO
  // updateProduct(product : Product): Observable<any>
  // {
  //   return this.http.post(`${this.url}/restaurant/2/orders`, product);
  // }
  
=======
    return this.http.post(`${this.url}/restaurant/1/product`, product);
  }


  updateProduct(product : Product): Observable<any>{
    return this.http.put(`${this.url}/restaurant/1/product/${product.id}`, product);
  }

  deleteProduct(id: number): Observable<any>{
    return this.http.delete(`${this.url}/restaurant/1/product/${id}`);
  }

  getDataSource() {
    return this.dataSource.asObservable();
  }

  updateDataSource(newData: Product[]) {
    this.dataSource.next(newData);
  }

>>>>>>> Stashed changes
}
