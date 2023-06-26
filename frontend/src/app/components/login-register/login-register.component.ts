import { Component, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Login } from 'src/app/models/login';
import { Register } from 'src/app/models/register';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login-register',
  templateUrl: './login-register.component.html',
  styleUrls: ['./login-register.component.css']
})
export class LoginRegisterComponent implements OnInit {
  loginForm!:FormGroup;
  submittedLogin:boolean = false;
  submittedRegister:boolean = false;
  registerForm!:FormGroup;
  imageUrl: string | null = null;
  role: [string] | null = null;
  ls!: any;
  isAuthenticated: boolean = false;


  constructor(private fireStorage: AngularFireStorage, private formBuilder: FormBuilder, private login: AuthService, private router: Router, private _snackBar: MatSnackBar) {
    
    
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
    this.registerForm = this.formBuilder.group({
      nameRegister: ['', Validators.required],
      emailRegister: ['', [Validators.required, Validators.email]],
      passwordRegister: ['', Validators.required],
      confirmPassword: ['', Validators.required],
      imageUrl: [null],
      role: [2]
    }, { validators: this.passwordMatchValidator });
  }

  passwordMatchValidator(formGroup: FormGroup) {
    const passwordControl = formGroup.get('password');
    const confirmPasswordControl = formGroup.get('repeatPassword');
  
    if (passwordControl && confirmPasswordControl && passwordControl.value !== confirmPasswordControl.value) {
      confirmPasswordControl.setErrors({ passwordMismatch: true });
    } else {
      if (confirmPasswordControl) {
        confirmPasswordControl.setErrors(null);
      }
    }
  }

  ngOnInit() {
    if(localStorage.getItem('token') != null){
      this.ls = JSON.parse(localStorage.getItem('token')!);
      this.isAuthenticated = true;
    }
    else{
      this.isAuthenticated = false;
    }
  }

  matchPassword(control: AbstractControl): { [key: string]: boolean } | null {
    const password = this.registerForm.get('password')?.value;
    const repeatPassword = control.value;

    if (password !== repeatPassword) {
      return { 'passwordMismatch': true };
    }

    return null;
  }

  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, { duration: 2000 });
  }

  submitLoginForm() {
    const formValue = this.loginForm.value;
    this.submittedLogin = true;
    const credentials: Login = {
      email: formValue.email,
      password: formValue.password,
    };

    if (this.loginForm.valid) {
      this.login.authLogin(credentials).subscribe((result) => {
        this.openSnackBar('Login realizado com sucesso!', '✅');
        console.log(result);
        localStorage.setItem('token', JSON.stringify(result));
        console.log(localStorage.getItem('token'));
        setTimeout(() => {
          window.location.href = 'home';
        }, 1500); 
      }, (error) => {
        this.openSnackBar('Email ou senha incorretos!', '❌');
      });      
    } else {
      this.openSnackBar('Formato de email incorreto ou campos vazios!', '❌');
    }
  }

  submitRegisterForm() {
    const formValue = this.registerForm.value;
    this.submittedRegister = true;
    if (formValue.role === '1') {
      this.role = ['ADMIN'];
    } else if (formValue.role === '2') {
      this.role = ['EMPLOYEE'];
    } else {
      this.role = ['EMPLOYEE']; 
    }
    const credentialsRegister: Register = {
      name: formValue.nameRegister,
      restaurantImage: this.imageUrl || '',
      roles: this.role,
      email: formValue.emailRegister,
      password: formValue.passwordRegister,
    };
    console.log(credentialsRegister);
    if (this.registerForm.valid) {
      this.login.authRegister(credentialsRegister).subscribe((result) => {
        this.openSnackBar('Conta criada com sucesso!', '✅');
        setTimeout(() => {
          window.location.href = 'home';
        }, 1500); 
      });  
      console.log(this.registerForm.value);
    } else {
      this.openSnackBar('Formato de email incorreto ou campos vazios!', '❌');
      console.log('form contem erros!');
    }
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
