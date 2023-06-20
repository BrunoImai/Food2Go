import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
    if(localStorage.getItem('token') != null){
      this.isAuthenticated = true;
    }
    else{
      this.isAuthenticated = false;
    }
  }

  isAuthenticated: boolean = false;
 
  

  exit(){
    localStorage.clear();
    window.location.href = 'home';
  }
}
