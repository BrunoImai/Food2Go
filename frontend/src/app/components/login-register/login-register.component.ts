import { Component, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';
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


  constructor(private fireStorage: AngularFireStorage, private formBuilder: FormBuilder, private login: AuthService, private router: Router) {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
    this.registerForm = this.formBuilder.group({
      nameRegister: ['', Validators.required],
      emailRegister: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
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
  }

  matchPassword(control: AbstractControl): { [key: string]: boolean } | null {
    const password = this.registerForm.get('password')?.value;
    const repeatPassword = control.value;

    if (password !== repeatPassword) {
      return { 'passwordMismatch': true };
    }

    return null;
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
        window.location.href = 'home';
        console.log(result);
        localStorage.setItem('token', JSON.stringify(result));
        console.log(localStorage.getItem('token'));
      });      
    } else {
      // Form inválido
      console.log('form contem erros!');
    }
  }

  submitRegisterForm() {
    const formValue = this.loginForm.value;
    this.submittedRegister = true;
    const credentials: Register = {
      name: formValue.nameRegister,
      // imgUrl: formValue.imageUrl || '',
      email: formValue.emailRegister,
      password: formValue.passwordRegister,
    };
    if (this.registerForm.valid) {
      this.login.authLogin(credentials).subscribe((result) => {
        window.location.href = 'login-register';
        console.log(result);
        localStorage.setItem('token', JSON.stringify(result));
        console.log(localStorage.getItem('token'));
      });  
      console.log(this.registerForm.value);
    } else {
      // Form inválido
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
