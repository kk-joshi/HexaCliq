import { Component, OnInit } from '@angular/core';
import { StorageServiceService } from '../storage-service.service';
import { WebServiceService } from '../web-service.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  public profileDetails: any = null;
  public walletDetails: any = null;
  public errorMsg: string = ``;
  public successMsg: string = ``;
  public referralErrorMsg: string = ``;
  public referralResult: string = ``;
  public referralEmail: string = ``;

  closeAlert() {
    this.errorMsg = ``;
    this.successMsg = ``;    
  }

  closeReferralErrorAlert(){
    this.referralErrorMsg = ``;
  }

  closeReferralSuccessAlert(){
    this.referralResult = ``;
  }

  constructor(private webService: WebServiceService, private storageService: StorageServiceService) {
    this.webService.getProfile().subscribe(resp => {
      this.profileDetails = resp;
      if (this.profileDetails.profilePicResorceId != 0) {
        this.webService.storeImageUrl(this.profileDetails.profilePicResorceId);
      }
      this.storageService.hideLoader();
      }, errMsg => this.handleError(errMsg));
    this.webService.getWalletDetails().subscribe(resp => this.handleWalletDetails(resp), errMsg => this.handleError(errMsg));
  }

  public handleWalletDetails(resp) {
    this.walletDetails = resp;
    this.storageService.saveCreditAmt(this.walletDetails.creditAmountPerQuestion);
    this.storageService.hideLoader();
  }

  public handleError(errorResp) {
    if (errorResp.status == 0) {
      this.errorMsg = `Server under maintainance, please try after sometime`;
    } else {
      this.errorMsg = errorResp.error;
    }
    this.storageService.hideLoader();
  }

  ngOnInit(): void {
  }

  getCurrecySymbol() {
    return this.webService.getUserCurrencySymbol();
  }

  getcreditRefferalAmount() {
    return this.webService.getUserAmountPerCaptcha() * 5;
  }

  sendVerificationMail() {
    this.closeAlert();
    this.webService.resendVerificationLink().subscribe(resp => this.handleResp(resp), error => this.handleError(error));
  }

  handleResp(resp) {
    this.successMsg = resp.msg;
    this.storageService.hideLoader();
  }

  sendInvitation() {
    this.referralErrorMsg = ``;
    this.referralResult = ``;
    this.webService.sendInvitation(this.referralEmail).subscribe(resp => this.handleReferralResp(resp), error => this.handleReferralError(error));  
  }
  
  handleReferralResp(resp) {
    this.referralResult = resp.msg;
    this.storageService.hideLoader();
  }

  public handleReferralError(errorResp) {
    if (errorResp.status == 0) {
      this.referralErrorMsg = `Server under maintainance, please try after sometime`;
    } else {
      this.referralErrorMsg = errorResp.error;
    }
    this.storageService.hideLoader();
  }

}
