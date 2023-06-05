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
    price: '',
    qtt: '',
    description: '',
    menusIncluded: [],
    restaurant: 2,
    combosIncluded: []
  });
  
  ngOnInit(): void {}

  saveForm(){
    console.log('Form data: ', this.productForm.value);
  }
  // submit() {
  //   this.menu.addProduct(this.productForm.value).subscribe((result) => {
  //     console.warn(result);
  //     if (result) {
  //       this.addProductMessage = 'Product is added successfully';
  //     }
  //   });

  //   setTimeout(() => {
  //     this.addProductMessage=undefined
  //   }, 3000);
  // }
}