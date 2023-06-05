import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginRegisterComponent } from './components/login-register/login-register.component';
import { OrderDashboardComponent } from './components/order-dashboard/order-dashboard.component';
import { OrderTableComponent } from './components/order-table/order-table.component';	
import { MenuComponent } from './components/menu/menu.component';
import { HomeDashboardComponent } from './components/home-dashboard/home-dashboard.component';
import { MenuFormComponent } from './components/menu-form/menu-form.component';



const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'home', component: HomeComponent},
  { path: 'login-register', component: LoginRegisterComponent},
  { path: 'home/login-register', component: LoginRegisterComponent},
  { path: 'order-dashboard', component: OrderDashboardComponent, 
  children: [
    { path: 'order-table', component: OrderTableComponent},
    { path: 'menu', component: MenuComponent},
    { path: 'home-dashboard', component: HomeDashboardComponent},
    { path: 'menu-form', component: MenuFormComponent},
  ]},
  { path: 'home/order-dashboard', component: OrderDashboardComponent},
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
