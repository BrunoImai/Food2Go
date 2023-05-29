import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { FooterComponent } from './components/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { LoginRegisterComponent } from './components/login-register/login-register.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { OrderDashboardComponent } from './components/order-dashboard/order-dashboard.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatDividerModule } from '@angular/material/divider';
import { MatToolbar } from '@angular/material/toolbar';
import { MatToolbarModule } from '@angular/material/toolbar';

@NgModule({
  declarations: [		
    AppComponent,
      NavbarComponent,
      FooterComponent,
      HomeComponent,
      LoginRegisterComponent,
      OrderDashboardComponent
   ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatListModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatSidenavModule,
    MatDividerModule,
    MatToolbarModule,
      ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
