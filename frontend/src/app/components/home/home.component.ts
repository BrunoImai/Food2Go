import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor() {

   }
   isAuthenticated: boolean = false;

  ngOnInit() {
    if(localStorage.getItem('token') != null){
      this.isAuthenticated = true;
    }
    else{
      this.isAuthenticated = false;
    }
  }

}
