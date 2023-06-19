import { Component, OnInit } from '@angular/core';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { FormBuilder, FormGroup, Validators, AbstractControl } from '@angular/forms';

@Component({
  selector: 'app-login-register',
  templateUrl: './login-register.component.html',
  styleUrls: ['./login-register.component.css']
})
export class LoginRegisterComponent implements OnInit {
  loginForm!:FormGroup;
  submitted:boolean = false;
  registerForm!:FormGroup;
  imageUrl: string | null = null;


  constructor(private fireStorage: AngularFireStorage, private formBuilder: FormBuilder) {
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
    this.submitted = true;
    if (this.loginForm.valid) {
      // Form válido
      console.log('form certo!');
    } else {
      // Form inválido
      console.log('form contem erros!');
    }
  }

  submitRegisterForm() {
    this.submitted = true;
    if (this.registerForm.valid) {
      // Form válido
      console.log('form certo!');
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
