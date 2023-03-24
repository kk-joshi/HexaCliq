import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class StorageServiceService {

  constructor(private cookieService: CookieService) { }

  public TOKEN: string = "ARMSessionToken";
  public CREDIT_AMT: string = "ARMCreditAmount";
  public IMAGE_URL: string = "ARMUserImageUrl";
  public FULL_NAME: string = "ARMFullName";
  public COUNTRY: string = "ARMCountry";
  public LOADER: string = "ARMLoader";

  clearSessionInfo() {
    this.cookieService.deleteAll();
    sessionStorage.setItem(this.TOKEN, ``);
    sessionStorage.setItem(this.IMAGE_URL, ``);
    sessionStorage.setItem(this.FULL_NAME, ``);
    sessionStorage.setItem(this.COUNTRY, ``);
  }

  public saveToken(response: any) {
    this.cookieService.set(this.TOKEN, response.token);
  }

  public getToken(): string {
    return this.cookieService.get(this.TOKEN);
  }

  public saveProfileUrl(url: string) {
    sessionStorage.setItem(this.IMAGE_URL, url);
  }

  public getProfileUrl(): string {
    return sessionStorage.getItem(this.IMAGE_URL);
  }

  public saveCountry(country: string) {
    sessionStorage.setItem(this.COUNTRY, country);
  }

  public getCountry(): string {
    return sessionStorage.getItem(this.COUNTRY);
  }

  saveFullName(fullName: string) {
    sessionStorage.setItem(this.FULL_NAME, fullName);
  }

  public getFullName(): string {
    return sessionStorage.getItem(this.FULL_NAME);
  }

  public showLoader() {
    sessionStorage.setItem(this.LOADER, `true`);
  }

  public hideLoader() {
    sessionStorage.setItem(this.LOADER, `false`);
  }

  isLoading(): boolean {
    return sessionStorage.getItem(this.LOADER) === `true`;
  }

  getCreditAmt(): number {
    return parseFloat(this.cookieService.get(this.CREDIT_AMT));
  }

  saveCreditAmt(amt: number) {
    this.cookieService.set(this.CREDIT_AMT, amt + "");
  }

}
