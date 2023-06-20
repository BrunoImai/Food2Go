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
  resName: string | null = null;
  ls!: any;

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

  isAuthenticated: boolean = false;
 
  

  exit(){
    localStorage.clear();
    window.location.href = 'home';
  }
}
