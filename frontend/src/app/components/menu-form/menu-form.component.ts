import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { MenuService } from 'src/app/services/menu.service';
import { FormBuilder, NgForm, Validators } from '@angular/forms';

@Component({
  selector: 'app-menu-form',
  templateUrl: './menu-form.component.html',
  styleUrls: ['./menu-form.component.css']
})
export class MenuFormComponent implements OnInit {
  addProductMessage: string | undefined;
  isAuthenticated: boolean = false;
  constructor(private formBuilder:FormBuilder, private menu: MenuService) {}
  productForm = this.formBuilder.group({
    name: ['', Validators.required],
    price: [null, Validators.required],
    qtt: [null, Validators.required],
    description: ['', Validators.required],
    menusIncluded: [[]],
    restaurant: [1],
    combosIncluded: [[]]
  });
  
  
  ngOnInit(): void {
    // const credentials = JSON.parse(localStorage.getItem('user') || '{}');
    console.log(localStorage.getItem('user'))
    console.log("carregou")
    if(localStorage.getItem('token') == "ADMIN"){
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
        menusIncluded: formValue.menusIncluded || [],
        combosIncluded: formValue.combosIncluded || []
      };
  
      this.menu.addProduct(product).subscribe((result) => {
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