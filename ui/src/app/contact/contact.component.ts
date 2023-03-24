import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StorageServiceService } from '../storage-service.service';
import { ValueAndText, WebServiceService } from '../web-service.service';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})
export class ContactComponent implements OnInit {

  queryDetails: any = {};
  countries: ValueAndText[] = [];
  showAlert: boolean = false;
  errorMsg: string;
  successResult: string = null;

  constructor(private webService: WebServiceService,private storageService: StorageServiceService, private activedRoute: ActivatedRoute) {
    this.resetQueryData();
    this.countries = webService.getCountries();
    this.initilizeErrorMsg();
    this.queryDetails.fullname = this.storageService.getFullName();
    this.queryDetails.country = this.storageService.getCountry();
    this.queryDetails.queryStr = activedRoute.snapshot.queryParamMap.get(`body`);
    
  }

  ngOnInit(): void {
  }

  initilizeErrorMsg() {
    this.errorMsg = `You should check some of those fields below.`
  }

  sendQuery() {
    this.showAlert = false;
    this.successResult = null;
    if (!this.isValidInput()) {
      this.showAlert = true;
    } else {
      this.webService.sendQuery(this.queryDetails).subscribe(resp => this.handleQueryRespone(resp), errorResp => this.handleErrorResp(errorResp));
    }
  }

  public handleQueryRespone(response) {
    this.successResult = response.msg;
    this.resetQueryData();
    this.storageService.hideLoader();
  }

  private resetQueryData() {
    this.queryDetails = {
      "fullname": "",
      "country": "",
      "mobileNo": "",
      "email": "",
      "queryStr": ""
    };
  }

  public handleErrorResp(errorResp) {
    this.showAlert = true;
    if (errorResp.status == 0) {
      this.errorMsg = `Server under maintainance, please try after sometime`;
    } else {
      this.errorMsg = errorResp.error;
    }
    this.storageService.hideLoader();
  }

  isValidInput(): boolean {
    let valid = true;
    this.initilizeErrorMsg();
    if (this.queryDetails.fullname == ``) {
      valid = false;
    } else if (this.queryDetails.country == ``) {
      valid = false;
    } else if (this.queryDetails.mobileNo == '') {
      valid = false;
    } else if (this.queryDetails.email == ``) {
      valid = false;
    } else if (this.queryDetails.queryStr == ``) {
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
    }
    return valid;
  }

  public isValidName() {
    let regexpFullName = new RegExp('^\\S+\\s+\\S+');
    return regexpFullName.test(this.queryDetails.fullname);
  }

  public isValidEmail() {
    let regexpEmail = new RegExp('^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$');
    return regexpEmail.test(this.queryDetails.email);
  }

  public isValidMobileNo() {
    let regexpEmail = new RegExp('[0-9]{10,}');
    return regexpEmail.test(this.queryDetails.mobileNo);
  }

  closeAlert() {
    this.showAlert = false;
  }

  closeSuccessAlert() {
    this.successResult = null;
  }

}
