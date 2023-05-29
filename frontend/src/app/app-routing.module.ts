import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginRegisterComponent } from './components/login-register/login-register.component';
import { OrderDashboardComponent } from './components/order-dashboard/order-dashboard.component';
import { OrderTableComponent } from './components/order-table/order-table.component';	

const routes: Routes = [
  { path: '', component: HomeComponent},
  { path: 'home', component: HomeComponent},
  { path: 'login-register', component: LoginRegisterComponent},
  { path: 'home/login-register', component: LoginRegisterComponent},
  { path: 'order-dashboard', component: OrderDashboardComponent, 
  children: [
    { path: 'order-table', component: OrderTableComponent},
  ]},
  { path: 'home/order-dashboard', component: OrderDashboardComponent},
  

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
