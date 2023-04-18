import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppComponent } from './app.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { RouterModule, Routes } from '@angular/router';
import { HeaderComponent } from './shared/header/header.component';
import { FooterComponent } from './shared/footer/footer.component';
import { ProductsModule } from '@bluebits/products';
import { UiModule } from '@bluebits/ui';
import { AccordionModule } from 'primeng/accordion';
import { NavComponent } from './shared/nav/nav.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { OrdersModule } from '@bluebits/orders';
import { ToastModule } from 'primeng/toast';
import { MessagesComponent } from './shared/messages/messages.component';
import { MessageService } from 'primeng/api';
import { CalendarModule } from 'primeng/calendar';
import { LogOutComponent } from './pages/log-out/log-out.component';
import { RegisterComponent } from './pages/register/register.component';
import { ContactComponent } from './pages/contact/contact.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';

import { AttendanceComponent } from './pages/attendance/attendance.component';
import { CommonModule, DatePipe } from '@angular/common';
// import { CookieService } from 'ngx-cookie-service';
import { HttpIntercepterServiceService } from './pages/http-intercepter-service.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CreateClientComponent } from './pages/create-client/create-client.component';
import { CreateProjectComponent } from './pages/create-project/create-project.component';
import {MatNativeDateModule} from '@angular/material/core';
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatInputModule} from '@angular/material/input';
import { ReportComponent } from './pages/Reports/reports.component';

const routes: Routes = [
  { path: 'login', component: RegisterComponent },
  { path: 'home', component: HomePageComponent },
{ path: 'logout-user', component: LogOutComponent },
{ path: 'register', component: RegisterComponent },
{ path: 'contact-us', component: ContactComponent },
{ path: 'attendance', component: AttendanceComponent },
{ path: 'client', component: CreateClientComponent },
{ path: 'project', component: CreateProjectComponent },
{ path: 'report', component: ReportComponent },
{ path: '**', component: WelcomeComponent },

];

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    AttendanceComponent,
    HeaderComponent,
    FooterComponent,
    NavComponent,
    MessagesComponent,
    CreateClientComponent,
    CreateProjectComponent,
    ReportComponent,
    
  
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    ProductsModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    CalendarModule,
    AccordionModule,
    BrowserAnimationsModule,
    UiModule,
    OrdersModule,
    ToastModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatInputModule,
  ],
  providers: [DatePipe, MessageService,
    { provide: HTTP_INTERCEPTORS, useClass: HttpIntercepterServiceService, multi: true }],
  bootstrap: [AppComponent]
})
export class AppModule { }
