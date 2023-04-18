import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StorageServiceService } from './storage-service.service';

export class ValueAndText {
  constructor(public Value: string, public Text: string, public Symbol: string, public CurrencyExchange: number) { }
}

@Injectable({
  providedIn: 'root'
})
export class WebServiceService {

  //TODO get these values from server
  getCountries(): ValueAndText[] {
    return [
      new ValueAndText("BD", "BANGLADESH", `৳`, 18),
      new ValueAndText("KH", "CAMBODIA", `៛`, 790),
      new ValueAndText("ID", "INDONESIA", `Rp`, 300),
      new ValueAndText("IN", "INDIA", `₹`, 15),
      new ValueAndText("PH", "PHILIPPINES", `₱`, 10),
      new ValueAndText("SL", "SRI LANKA", `රු`, 38),
      new ValueAndText("TW", "TAIWAN", `NT$`, 475),
    ];
  }

  hostUrl = `http://dockerserver.centralindia.cloudapp.azure.com:28080/`
  registrationUrl = this.hostUrl + `register-user`;
  sendQueryUrl = this.hostUrl + `send-query`;
  getToken = this.hostUrl + `get-token`;
  uploadImageUrl = this.hostUrl + `upload`;
  viewImageUrl = this.hostUrl + `resource/`;
  getProfileUrl = this.hostUrl + `get-user-profile`;
  getAttendanceUrl = this.hostUrl + `/attendance/get-attendance`;
  markAttendanceUrl = this.hostUrl + `/attendance/mark-attendance`;
  updateProfileUrl = this.hostUrl + `update-user-profile`;
  changePasswordUrl = this.hostUrl + `change-password`;
  getCaptchasUrl = this.hostUrl + `generate-all-captcha`;
  viewCaptchaUrl = this.hostUrl + `get-captcha/`;
  checkAnswerUrl = this.hostUrl + `answer-captcha`;
  getWalletDetailsUrl = this.hostUrl + `get-wallet-details`;
  withdrawMoneyUrl = this.hostUrl + `withdraw-money`;
  forgotPasswordUrl = this.hostUrl + `forget-password`;
  verifyEmailUrl = this.hostUrl + `verify-email/`;
  resendVerificationEmailUrl = this.hostUrl + `resend-verification-email`;
  inviteUserUrl = this.hostUrl + `refer-a-friend`;
  getReportsUrl = this.hostUrl + 'get-reports';


  constructor(private httpClient: HttpClient, private storageService: StorageServiceService) { }

  storeImageUrl(imageId: any): string {
    let imageUrl = this.viewImageUrl + imageId + "?token=" + this.storageService.getToken();
    this.storageService.saveProfileUrl(imageUrl);
    return imageUrl;
  }

  getCaptchaUrl(imageId: any): string {
    let captchaUrl = this.viewCaptchaUrl + imageId + "?token=" + this.storageService.getToken();
    return captchaUrl;
  }

  getImage(imageUrl: string): Observable<Blob> {
    this.storageService.showLoader();
    return this.httpClient.get(imageUrl, { responseType: 'blob' });
  }

  public registerUser(registrationDetails: any) {
    this.storageService.showLoader();
    return this.httpClient.post(this.registrationUrl, registrationDetails);
  }

  public login(loginDetails: any) {
    this.storageService.showLoader();
    return this.httpClient.post(this.getToken, loginDetails);
  }

  public sendQuery(queryDetails: any) {
    this.storageService.showLoader();
    return this.httpClient.post(this.sendQueryUrl, queryDetails);
  }

  public uploadImage(image: File, type: string) {
    this.storageService.showLoader();
    const formData = new FormData();
    formData.append('imageFile', image);
    formData.append('type', type);
    return this.httpClient.post(this.uploadImageUrl, formData);
  }

  getProfile() {
    this.storageService.showLoader();
    return this.httpClient.get(this.getProfileUrl);
  }

  getAttendance(date: string) {
    this.storageService.showLoader();
    return this.httpClient.get(this.getProfileUrl + '?month=' + date);
  }

  
  getReports(formattedMonths : string) {
    this.storageService.showLoader();
    return this.httpClient.get(this.getReportsUrl);
  }

  markAttendance(attendanceDetails: any) {
    this.storageService.showLoader();
    return this.httpClient.post(this.markAttendanceUrl, attendanceDetails);
  }

  updateProfile(userDetails: any) {
    this.storageService.showLoader();
    return this.httpClient.post(this.updateProfileUrl, userDetails);
  }

  changePassword(passwordDetails: any) {
    this.storageService.showLoader();
    return this.httpClient.post(this.changePasswordUrl, passwordDetails);
  }

  getAllCaptcha() {
    this.storageService.showLoader();
    return this.httpClient.get(this.getCaptchasUrl);
  }

  checkAnswer(answerDetails) {
    this.storageService.showLoader();
    return this.httpClient.post(this.checkAnswerUrl, answerDetails);
  }

  getWalletDetails() {
    this.storageService.showLoader();
    return this.httpClient.get(this.getWalletDetailsUrl);
  }

  raiseWithdrawalRequest(amount: any) {
    this.storageService.showLoader();
    return this.httpClient.post(this.withdrawMoneyUrl, { amount });
  }

  forgotPassword(forgotPassword: any) {
    this.storageService.showLoader();
    return this.httpClient.post(this.forgotPasswordUrl, forgotPassword);
  }

  verifyEmail(actCode: string, userId: string) {
    this.storageService.showLoader();
    return this.httpClient.get(this.verifyEmailUrl + userId + "/" + actCode);
  }

  resendVerificationLink() {
    this.storageService.showLoader();
    return this.httpClient.get(this.resendVerificationEmailUrl);
  }

  sendInvitation(referralEmail: string) {
    this.storageService.showLoader();
    return this.httpClient.post(this.inviteUserUrl, referralEmail);
  }

  getTransactionType(type: string): string {
    let index: number = type.indexOf("_");
    return type.substr(0, index);
  }

  getTransactionDescription(type: string): string {
    let index: number = type.indexOf("_");
    let desc: string = type;
    if (index > 0) {
      desc = type.substr(index);
    }
    return desc.replace(/_/gi, " ");
  }

  //TODO get these values from server
  getUserCurrencySymbol(): string {
    let symbol: string = `₱`;
    let countryName = this.storageService.getCountry();
    this.getCountries().find((country) => {
      if (country.Value == countryName) {
        symbol = country.Symbol;
      }
    });
    return symbol;
  }

  getUserAmountPerCaptcha(): number {
    let amt: number = this.storageService.getCreditAmt();
    if (amt == null || amt == 0) {
      let countryName = this.storageService.getCountry();
      this.getCountries().find((country) => {
        if (country.Value == countryName) {
          amt = country.CurrencyExchange;
        }
      });
    }
    return amt;
  }

  logout() {
    this.storageService.clearSessionInfo();
  }

}
