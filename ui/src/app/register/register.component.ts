import { DatePipe } from '@angular/common';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { StorageServiceService } from '../storage-service.service';
import { ValueAndText, WebServiceService } from '../web-service.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  @ViewChild('alert', { static: true }) alert: ElementRef;

  now: Date = new Date();
  registrationDetails: any = {};
  showAlert = false;
  loginDetails: any = {};
  countries: ValueAndText[] = [];
  successResult: string = null;
  errorMsg: string;
  terms: boolean = false;
  forgotPassword: any = {};

  constructor(private webService: WebServiceService, private datePipe: DatePipe, private router: Router, private storageService: StorageServiceService) {
    this.now.setFullYear(this.now.getFullYear() - 14);
    this.resetRegistrationDetails();
    this.resetLoginData();
    this.countries = webService.getCountries();
    this.initilizeErrorMsg();
  }

  ngOnInit(): void {
    let str: string = this.storageService.getToken();
    if (str) {
      this.router.navigate([`dashboard`]);
    }
    if (window.location.href.indexOf("login") != -1) {
      this.changeToLoginTab();
    }
  }

  initilizeErrorMsg() {
    this.errorMsg = `You should check some of those fields below.`
  }

  closeAlert() {
    this.showAlert = false;
  }

  closeSuccessAlert() {
    this.successResult = null;
  }

  login() {
    this.successResult = null;
    this.showAlert = false;
    if (!this.isValidLoginDetails()) {
      this.showAlert = true;
    } else {
      this.webService.login(this.loginDetails).subscribe(response => this.handleLoginResponse(response), erroResp => this.handleError(erroResp));
    }
  }

  public handleLoginResponse(response) {
    this.successResult = response.msg;
    this.storageService.saveToken(response);
    this.getProfileDetails();
    this.storageService.hideLoader();
    this.router.navigate([`dashboard`]);
  }

  getProfileDetails() {
    this.webService.getProfile().subscribe(resp => this.storePermanentInfo(resp), error => { this.storageService.hideLoader(); });
  }

  storePermanentInfo(userDetails) {
    this.storageService.saveFullName(userDetails.fullName);
    this.storageService.saveCountry(userDetails.country);
    this.storageService.hideLoader();
  }

  private resetLoginData() {
    this.loginDetails = {
      "username": "",
      "password": ""
    };
    this.forgotPassword = {
      "birthdate": this.now
    };

  }

  public registerUser() {
    this.successResult = null;
    this.showAlert = false;
    if (!this.isValidRegistrationDetails()) {
      this.showAlert = true;
    }
    else {
      this.registrationDetails[`dob`] = this.datePipe.transform(this.registrationDetails[`birthdate`], "dd-MM-yyyy");
      this.webService.registerUser(this.registrationDetails).subscribe(response => this.handleRegistrationSuccessRespone(response), errorResp => this.handleError(errorResp));
    }
  }

  public handleRegistrationSuccessRespone(response) {
    this.successResult = response.msg;
    this.resetRegistrationDetails();
    this.changeToLoginTab();
    this.storageService.hideLoader();
  }

  private resetRegistrationDetails() {
    this.registrationDetails = {
      "fullName": "",
      "password": "",
      "country": "",
      "mobileNo": "",
      "email": "",
      "birthdate": this.now
    };
  }

  public changeToLoginTab() {
    let element: HTMLElement = document.getElementById('loginId') as HTMLElement;
    element.click();
  }

  public handleError(errorResp) {
    this.showAlert = true;
    if (errorResp.status == 0) {
      this.errorMsg = `Server under maintainance, please try after sometime`;
    } else {
      this.errorMsg = errorResp.error;
    }
    this.storageService.hideLoader();
  }

  public isValidRegistrationDetails() {
    let valid = true;
    this.initilizeErrorMsg();
    if (this.registrationDetails.fullName == ``) {
      valid = false;
    } else if (this.registrationDetails.password == ``) {
      valid = false;
    } else if (this.registrationDetails.country == ``) {
      valid = false;
    } else if (this.registrationDetails.phoneno == ``) {
      valid = false;
    } else if (this.registrationDetails.email == ``) {
      valid = false;
    } else if (!this.isValidName()) {
      this.errorMsg = `Please enter your full name`;
      valid = false;
    } else if (!this.isValidMobileNo()) {
      this.errorMsg = `Please enter valid mobile`;
      valid = false;
    } else if (!this.isValidEmail()) {
      this.errorMsg = `Please enter valid email`;
      valid = false;
    } else if (!this.isValidPasswordLength()) {
      this.errorMsg = `Please enter strong password`;
      valid = false;
    } else if (!this.terms) {
      this.errorMsg = `Please accept the terms and conditions`;
      valid = false;
    }
    return valid;
  }

  public isValidName() {
    let regexpFullName = new RegExp('^\\S+\\s+\\S+');
    return regexpFullName.test(this.registrationDetails.fullName);
  }

  public isValidPasswordLength() {
    return this.registrationDetails.password.length > 4;
  }
  
  public isValidEmail() {
    let regexpEmail = new RegExp('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$');
    return regexpEmail.test(this.registrationDetails.email);
  }

  public isValidMobileNo() {
    let regexpEmail = new RegExp('[0-9]{10,}');
    return regexpEmail.test(this.registrationDetails.mobileNo);
  }

  public isValidLoginDetails() {
    let valid = true;
    this.initilizeErrorMsg();
    if (this.loginDetails.username == ``) {
      valid = false;
    }
    if (this.loginDetails.password == ``) {
      valid = false;
    }
    return valid;
  }

  agreedTerms() {
    this.terms = true;
  }

  // Reset Password

  passwordError = null;
  passwordSuccess = null;

  clearPasswordMsg() {
    this.passwordError = null;
    this.passwordSuccess = null;
  }

  public requestForgotPassword() {
    this.clearPasswordMsg();
    if (this.validateResetDetails()) {
      this.forgotPassword[`dob`] = this.datePipe.transform(this.forgotPassword[`birthdate`], "dd-MM-yyyy");
      this.webService.forgotPassword(this.forgotPassword).subscribe(resp => this.handleForgotPassword(resp), error => this.handleResetPasswordError(error));
    }
  }

  handleForgotPassword(resp) {
    this.passwordSuccess = resp.msg;
    this.storageService.hideLoader();
  }

  handleResetPasswordError(errorResp) {
    if (errorResp.status == 0) {
      this.passwordError = `Server under maintainance, please try after sometime`;
    } else {
      this.passwordError = errorResp.error;
    }
    this.storageService.hideLoader();
  }

  public validateResetDetails() {
    let valid = true;
    this.initilizeErrorMsg();
    if (this.forgotPassword.usernameOrEmail == ``) {
      this.passwordError = `Enter User name or Email`;
      valid = false;
    }
    if (this.forgotPassword.birthdate == ``) {
      this.passwordError = `Select Birthdate`;
      valid = false;
    }
    return valid;
  }

}
