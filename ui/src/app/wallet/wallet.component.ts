import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageServiceService } from '../storage-service.service';
import { WebServiceService } from '../web-service.service';

@Component({
  selector: 'app-wallet',
  templateUrl: './wallet.component.html',
  styleUrls: ['./wallet.component.css']
})
export class WalletComponent implements OnInit {

  public showWalletDetails = true;
  public walletDetails: any = {};
  showAlert = false;
  errorMsg: string = null;
  public currecySymbol: string = ``;
  creditAmtPerQuestion: number = 0;
  withdrawAmount: number = 0;
  captchaUrl: string = ``;
  widthwalResult: string = ``;
  withdrawErrorMsg: string = ``;

  constructor(private webservice: WebServiceService, private storageService: StorageServiceService, private router: Router) {
    this.getUserWalletDetails();
  }

  getUserWalletDetails() {
    this.webservice.getWalletDetails().subscribe(resp => this.handleSuccessResp(resp), error => this.handleError(error));
    this.currecySymbol = this.webservice.getUserCurrencySymbol();
    this.creditAmtPerQuestion = this.webservice.getUserAmountPerCaptcha();
    this.withdrawAmount = this.walletDetails.availableBalance;
  }

  resetMsg() {
    this.showAlert = false;
    this.widthwalResult = null;
    this.errorMsg = null;
    this.withdrawErrorMsg = null;
  }

  ngOnInit(): void {
  }

  closeAlert() {
    this.showAlert = false;
    this.errorMsg = null;
    this.withdrawErrorMsg = null;
  }

  closeSuccessAlert() {
    this.widthwalResult = null;
  }

  public handleSuccessResp(response) {
    this.walletDetails = response;
    this.creditAmtPerQuestion = this.walletDetails.creditAmountPerQuestion;
    this.storageService.saveCreditAmt(this.walletDetails.creditAmountPerQuestion);
    this.withdrawAmount = this.walletDetails.availableBalance;
    this.storageService.hideLoader();
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

  changeTab(value: boolean) {
    this.showWalletDetails = value;
  }

  getTranactionDesc(transaction: any): string {
    return this.webservice.getTransactionDescription(transaction.type);
  }

  isCredit(transaction: any): boolean {
    return this.webservice.getTransactionType(transaction.type) == `CREDIT`;
  }

  canShowImage(transaction: any): boolean {
    return transaction.questionId > 0;
  }

  showImageInNewTab(transaction: any) {
    this.captchaUrl = this.webservice.getCaptchaUrl(transaction.questionId);
  }

  raiseDispute() {
    this.openContactUs(`*****Wallet Balance Dispute*****`);
  }

  requestAllTransactions() {
    this.openContactUs(`*****Request for all transaction details*****`);
  }

  openContactUs(query: string) {
    this.router.navigate([`contact-us`], { queryParams: { body: query } });
  }

  requestWithdraw() {
    this.resetMsg();
    if (this.withdrawAmount < this.walletDetails.minWidrawnAmount) {
      this.withdrawErrorMsg = `Minimum withdrawn amount is ` + this.walletDetails.minWidrawnAmount;
      return;
    }
    return this.webservice.raiseWithdrawalRequest(this.withdrawAmount).subscribe(resp => this.handleWithdrawalRequest(resp), error => this.handleWithdrawalError(error));
  }

  handleWithdrawalRequest(resp) {
    this.widthwalResult = resp.msg;
    this.getUserWalletDetails();
    this.storageService.hideLoader();
  }

  handleWithdrawalError(errorResp) {
    if (errorResp.status == 0) {
      this.errorMsg = `Server under maintainance, please try after sometime`;
    } else {
      this.withdrawErrorMsg = errorResp.error;
    }
    this.storageService.hideLoader();
  }

}
