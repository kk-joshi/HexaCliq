import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class StorageServiceService {

  // constructor(private cookieService: CookieService) { }

  public TOKEN: string = "HEXA_CLIQ_SessionToken";
  public CREDIT_AMT: string = "HEXA_CLIQ_CreditAmount";
  public IMAGE_URL: string = "HEXA_CLIQ_UserImageUrl";
  public FULL_NAME: string = "HEXA_CLIQ_FullName";
  public COUNTRY: string = "HEXA_CLIQ_Country";
  public LOADER: string = "HEXA_CLIQ_Loader";

  clearSessionInfo() {
    sessionStorage.deleteAll();
    sessionStorage.setItem(this.TOKEN, ``);
    sessionStorage.setItem(this.IMAGE_URL, ``);
    sessionStorage.setItem(this.FULL_NAME, ``);
    sessionStorage.setItem(this.COUNTRY, ``);
  }

  public saveToken(response: any) {
    sessionStorage.set(this.TOKEN, response.token);
  }

  public getToken(): string {
    return sessionStorage.get(this.TOKEN);
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
    return parseFloat(sessionStorage.get(this.CREDIT_AMT));
  }

  saveCreditAmt(amt: number) {
    sessionStorage.set(this.CREDIT_AMT, amt + "");
  }

}
