import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { MenuService } from 'src/app/services/menu.service';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { AngularFireStorage } from '@angular/fire/compat/storage';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-menu-form',
  templateUrl: './menu-form.component.html',
  styleUrls: ['./menu-form.component.css']
})
export class MenuFormComponent implements OnInit {
  addProductMessage: string | undefined;
  isAuthenticated: boolean = false;
  productUrl: string | null = null;
  previewProduct: string | null = null;
  ls!: any;
  isAdmin: boolean = false;
  
  constructor(private formBuilder:FormBuilder, private menu: MenuService, private fireStorage: AngularFireStorage, private _snackBar: MatSnackBar) {}
  productForm = this.formBuilder.group({
    name: ['', Validators.required],
    price: [null, Validators.required],
    qtt: [null, Validators.required],
    description: ['', Validators.required],
    menusIncluded: [[]],
    restaurant: [1],
    productUrl: [null]
  });

  async onFileChange(event:any) {
    const file = event.target.files[0];
    if(file){
      const path = `img/${file.name}`;
      const uploadTask = await this.fireStorage.upload(path, file);
      const url = await uploadTask.ref.getDownloadURL();
      this.productUrl = url;
      console.log(url);
    }
  }
  openSnackBar(message: string, action: string) {
    this._snackBar.open(message, action, { duration: 2000 });
  }
  
  ngOnInit() : void{
    if(localStorage.getItem('token') != null){
      this.ls = JSON.parse(localStorage.getItem('token')!);
      this.isAdmin = this.ls.restaurant.roles && this.ls.restaurant.roles.includes('ADMIN') ? true : false;
      this.isAuthenticated = true;
    }
    else{
      this.isAuthenticated = false;
    }
  }


  saveForm(){
    console.log('Form data: ', this.productForm.value);
  }
  submit() {
    if (this.productForm.valid) {
      const formValue = this.productForm.value;
      
      
      const product: Product = {
        name: formValue.name || '',
        price: formValue.price || 0,
        qtt: formValue.qtt || 0,
        description: formValue.description || '',
        productImage: this.productUrl || '',
        menusIncluded: formValue.menusIncluded || [],
      };

      
  
      this.menu.addProduct(this.ls.restaurant.id, product).subscribe((result) => {
        this.openSnackBar('Adicionado com sucesso', '✅');
        console.warn(result);
        if (result) {  
          setTimeout(() => {
            window.location.reload();
          }, 2000);
        }
      },(error)=> {
        this.openSnackBar('Erro', '❗');
      });
    }else{
      this.openSnackBar('Favor verifique os campos vazios', '❌');
    }
  }
}