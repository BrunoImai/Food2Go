import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { MenuService } from 'src/app/services/menu.service';
import { FormBuilder, NgForm, Validators } from '@angular/forms';
import { AngularFireStorage } from '@angular/fire/compat/storage';

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
  
  constructor(private formBuilder:FormBuilder, private menu: MenuService, private fireStorage: AngularFireStorage) {}
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
  
  
  ngOnInit() : void{
    if(localStorage.getItem('token') != null){
      this.ls = JSON.parse(localStorage.getItem('token')!);
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
        window.location.reload();
        console.warn(result);
        if (result) {
          this.addProductMessage = 'Product is added successfully';
  
          setTimeout(() => {
            this.addProductMessage = undefined;
          }, 3000);
        }
      });
    }
  }
}