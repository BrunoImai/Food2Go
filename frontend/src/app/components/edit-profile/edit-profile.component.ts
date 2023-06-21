import { Component, OnInit } from '@angular/core';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { AngularFireStorage } from '@angular/fire/compat/storage';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  imageUrl: string | null = null;
  isAuthenticated: boolean = false;
  restaurantForm = this.formBuilder.group({
    nameRegister: ['', Validators.required],
    emailRegister: ['', [Validators.required, Validators.email]],
    });

  constructor(private formBuilder:FormBuilder, private fireStorage:AngularFireStorage) { }
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

  saveProfile() {
    // Perform logic to save the profile details
    console.log(this.restaurantForm.value);
  }

  async onFileChange(event:any) {
    const file = event.target.files[0];
    if(file){
      const path = `img/${file.name}`;
      const uploadTask = await this.fireStorage.upload(path, file);
      const url = await uploadTask.ref.getDownloadURL();
      this.imageUrl = url;
      console.log(url);
    }
  }

}
