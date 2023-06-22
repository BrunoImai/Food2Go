import { Component, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { Router } from '@angular/router';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth.service';
import { Restaurant } from 'src/app/models/restaurant';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private authService: AuthService, private router: Router, private fireStorage:AngularFireStorage, private formBuilder: FormBuilder, private _snackBar: MatSnackBar) { }
  ls!: any;
  imageUrl: string | null = null;
  isAuthenticated: boolean = false;
  selectedFileName: string | null = null;
  previewName: string | null = null;
  submittedEdit: boolean = false;
  role: [string] | null = null;
  restaurantForm = this.formBuilder.group({
    name: ['', Validators.required],
    email: ['', Validators.required],
    restaurantImage: [''],
    role: ['1'] 
  });

  ngOnInit() {
    if(localStorage.getItem('token') != null){
      this.ls = JSON.parse(localStorage.getItem('token')!);
      this.isAuthenticated = true;
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

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, { duration: 2000 });
  }
  
  
 
  saveProfile() {
    const formValue = this.restaurantForm.value;
    this.submittedEdit = true;
    if (formValue.role === '1') {
      this.role = ['ADMIN'];
    } else if (formValue.role === '2') {
      this.role = ['EMPLOYEE'];
    } else {
      this.role = ['EMPLOYEE']; 
    }
    const credentialsEdit: Restaurant = {
      name: formValue.name || '', 
      restaurantImage: this.imageUrl !== null || undefined ? this.imageUrl : this.ls.restaurant.restaurantImage,
      roles: this.role,
      email: formValue.email || '', 
    };
    this.previewName = credentialsEdit.name;
    console.log(credentialsEdit);
    if (this.restaurantForm.valid) {
      this.authService.editRestaurant(this.ls.restaurant.id, credentialsEdit).subscribe((result) => {
        window.location.href = '/home';
        console.log(result);
        localStorage.clear();
        this.openSnackBar('Edição realizada com sucesso!', 'Fechar');

        localStorage.setItem('token', JSON.stringify(result));
        console.log(localStorage.getItem('token'));
      });  
    } else {
      // Form inválido
      console.log('form contem erros!');
    }
  }

  exit(){
    localStorage.clear();
    window.location.href = 'home';
  }
}
