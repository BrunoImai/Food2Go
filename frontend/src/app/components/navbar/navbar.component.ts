import { Component, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { Router } from '@angular/router';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router, private fireStorage:AngularFireStorage, private formBuilder: FormBuilder) { }
  resName: string | null = null;
  ls!: any;
  imageUrl: string | null = null;
  isAuthenticated: boolean = false;
  selectedFileName: string | null = null;
  restaurantForm = this.formBuilder.group({
    name: ['', Validators.required],
    email: ['', Validators.required],
    imageUrl: [null, Validators.required],
    isAdmin: [false] 
  });

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

  async onFileChange(event:any) {
    const file = event.target.files[0];
    this.selectedFileName = file.name;
    if(file){
      const path = `img/${file.name}`;
      const uploadTask = await this.fireStorage.upload(path, file);
      const url = await uploadTask.ref.getDownloadURL();
      this.imageUrl = url;
      console.log(url);
    }
  }
  
  
 
  saveProfile() {
    // Perform logic to save the profile details
    console.log(this.restaurantForm.value);
  }

  exit(){
    localStorage.clear();
    window.location.href = 'home';
  }
}
