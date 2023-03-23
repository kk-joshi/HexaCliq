import { Component, OnInit } from '@angular/core';
import { StorageServiceService } from '../storage-service.service';
import { WebServiceService } from '../web-service.service';

@Component({
  selector: 'app-earn-money',
  templateUrl: './earn-money.component.html',
  styleUrls: ['./earn-money.component.css']
})
export class EarnMoneyComponent implements OnInit {

  allQuestions = [];
  showAlert = false;
  errorMsg: string = null;
  successResult = null;
  imageUrl: string = null;
  answerDetails = {};
  currentPosition: number = 0;
  answer: number;
  isPrevAllowed: boolean = false;
  isNextAllowed: boolean = false;

  constructor(private webService: WebServiceService, private storageService: StorageServiceService) {
    this.webService.getAllCaptcha().subscribe(resp => this.handleSuccessResp(resp), error => this.handleError(error));
  }

  ngOnInit(): void {
  }

  closeAlert() {
    this.showAlert = false;
  }

  closeSuccessAlert() {
    this.successResult = null;
  }

  public handleSuccessResp(response) {
    this.allQuestions = response;
    this.showImage(0);
    this.storageService.hideLoader();
  }

  public showImage(position: number) {
    if (position == 0) {
      this.isPrevAllowed = false;
    } else {
      this.isPrevAllowed = true;
    }
    if (position == this.allQuestions.length - 1) {
      this.isNextAllowed = false;
    } else {
      this.isNextAllowed = true;
    }
    this.currentPosition = position;
    this.imageUrl = this.webService.getCaptchaUrl(this.allQuestions[position]);
  }

  getImageUrl() {
    return this.imageUrl;
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

  public viewNextCaptcha() {
    this.resetAlerts();
    this.showImage(this.currentPosition + 1);
  }

  public viewPreviousCaptcha() {
    this.resetAlerts();
    this.showImage(this.currentPosition - 1);
  }

  public checkAnswer() {
    if (isNaN(+this.answer) || !Number(this.answer)) {
      this.showAlert = true;
      this.errorMsg = `Please provide valid answer`;
      return;
    }
    this.answerDetails = {
      "questionId": this.allQuestions[this.currentPosition],
      "answer": this.answer
    };
    this.resetAlerts();
    this.webService.checkAnswer(this.answerDetails).subscribe(resp => this.handleSuccessAnswerResp(resp), error => this.handleError(error));
  }

  public handleSuccessAnswerResp(response) {
    this.answer = 0;
    this.successResult = response.msg;
    //TODO decide if we wish to show next
    // if (this.isNextAllowed){
    //   this.viewNextCaptcha();
    // }
    this.storageService.hideLoader();
  }

  resetAlerts() {
    this.successResult = null;
    this.showAlert = false;
  }

  getcreditAmtPerCaptcha() {
    return this.webService.getUserAmountPerCaptcha();
  }

  getCurrecySymbol() {
    return this.webService.getUserCurrencySymbol();
  }
}
