import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/models/product';
import { MenuService } from 'src/app/services/menu.service';
import { FormBuilder, NgForm } from '@angular/forms';

@Component({
  selector: 'app-menu-form',
  templateUrl: './menu-form.component.html',
  styleUrls: ['./menu-form.component.css']
})
export class MenuFormComponent implements OnInit {

  addProductMessage: string | undefined;
  constructor(private formBuilder:FormBuilder, private menu: MenuService) {}
  productForm = this.formBuilder.group({
    name: '',
    price: null,
    qtt: null,
    description: '',
    menusIncluded: [],
    restaurant: 2,
    combosIncluded: []
  });
  
  
  ngOnInit(): void {}

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