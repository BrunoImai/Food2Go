import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-order-dashboard',
  templateUrl: './order-dashboard.component.html',
  styleUrls: ['./order-dashboard.component.css']
})
export class OrderDashboardComponent implements OnInit {

  resName: string | null = null;
  ls!: any;
  isAuthenticated: boolean = false;

  constructor() { }

  ngOnInit() {
    if(localStorage.getItem('token') != null){
      this.ls = JSON.parse(localStorage.getItem('token')!);
      this.isAuthenticated = true;
      this.resName = this.ls.restaurant.name;
    }
    else{
      this.isAuthenticated = false;
    }
  }
  
  exit(){
    localStorage.clear();
    window.location.href = 'home';
  }

}
