import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AboutComponent } from './about/about.component';
import { ContactComponent } from './contact/contact.component';
import { EarnMoneyComponent } from './earn-money/earn-money.component';
import { HomeComponent } from './home/home.component';
import { LogOutComponent } from './log-out/log-out.component';
import { ProfileComponent } from './profile/profile.component';
import { RegisterComponent } from './register/register.component';
import { VerificationComponent } from './verification/verification.component';
import { WalletComponent } from './wallet/wallet.component';
import { WelcomeComponent } from './welcome/welcome.component';

const routes: Routes = [
  //listed from right-to-left as per ui
  { path: 'logout-user', component: LogOutComponent },
  { path: 'login', component: RegisterComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'contact-us', component: ContactComponent },
  // { path: 'how-it-works', component: AboutComponent },
  // { path: 'profile', component: ProfileComponent },
  // { path: 'wallet-balance', component: WalletComponent },
  // { path: 'earn-money', component: EarnMoneyComponent },
  { path: 'dashboard', component: HomeComponent },
  // { path: 'verification', component: VerificationComponent },
  { path: '**', component: WelcomeComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
