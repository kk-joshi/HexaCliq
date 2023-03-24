import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { HomeComponent } from './home/home.component';
import { AboutComponent } from './about/about.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { RegisterComponent } from './register/register.component';
import { FormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { HttpIntercepterServiceService } from './http-intercepter-service.service';
import { LogOutComponent } from './log-out/log-out.component';
import { ContactComponent } from './contact/contact.component';
import { ProfileComponent } from './profile/profile.component';
import { EarnMoneyComponent } from './earn-money/earn-money.component';
import { WalletComponent } from './wallet/wallet.component';
import { VerificationComponent } from './verification/verification.component';
import { SpinnerComponent } from './spinner/spinner.component';
import { CookieService } from 'ngx-cookie-service';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    HomeComponent,
    AboutComponent,
    HeaderComponent,
    FooterComponent,
    RegisterComponent,
    LogOutComponent,
    ContactComponent,
    ProfileComponent,
    EarnMoneyComponent,
    WalletComponent,
    VerificationComponent,
    SpinnerComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [DatePipe, CookieService,
  {provide:HTTP_INTERCEPTORS, useClass: HttpIntercepterServiceService, multi:true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
