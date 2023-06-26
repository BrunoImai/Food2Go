import { Component, OnInit } from '@angular/core';
import { MenuService } from 'src/app/services/menu.service';
import { OrderService } from 'src/app/services/order.service';

@Component({
  selector: 'app-home-dashboard',
  templateUrl: './home-dashboard.component.html',
  styleUrls: ['./home-dashboard.component.css']
})
export class HomeDashboardComponent implements OnInit {
  ls!: any;
  isAuthenticated: boolean = false;
  total = 0;
  orders: any;
  products: any;
  stock = 0;
  


  constructor(private orderService: OrderService, private menuService: MenuService) { }

  ngOnInit() {
    if(localStorage.getItem('token') != null){
      this.ls = JSON.parse(localStorage.getItem('token')!);
      this.isAuthenticated = true;
    }
    else{
      this.isAuthenticated = false;
    }
    this.menuService.getProducts(this.ls.restaurant.id).subscribe((res: any) => {
      console.log(res)
      if (Array.isArray(res)) {
        this.products = res.length;
        this.stock = 0;
        res.forEach(element => {
          console.log(element.qtt);
          this.stock += element.qtt; 
          console.log(this.stock)
        });
        console.log("Data length: " + this.products);
      } else {
        console.log("Data is not an array.");
      }
    })
    this.orderService.getOrders(this.ls.restaurant.id).subscribe((data: any)=>{
      console.log(data);
      if (Array.isArray(data)) {
        this.orders = data.length;
        this.total = 0;
        data.forEach(element => {
          this.total = data.reduce((sum, order) => {
            const products = order.products;
            if (Array.isArray(products)) {
              const orderTotal = products.reduce((acc, product) => {
                return acc + (product.price);
              }, 0);
              return sum + orderTotal;
            }
            return sum;
          }, 0);
          console.log(this.total)
        });
        console.log("Data length: " + this.orders);
      } else {
        console.log("Data is not an array.");
      }
    });
  }

}
