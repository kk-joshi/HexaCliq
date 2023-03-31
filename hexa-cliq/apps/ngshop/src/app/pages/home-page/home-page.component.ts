import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { StorageServiceService } from '../storage-service.service';
import { WebServiceService } from '../web-service.service';

@Component({
  selector: 'bluebits-home-page',
  templateUrl: './home-page.component.html',
})
export class HomePageComponent implements OnInit {
  constructor(private webService: WebServiceService, private storageService: StorageServiceService) { }

  successResult: string = null;
  errorMsg: string = null;
  showAlert: boolean = true;
  attendanceDetails: any = { category : 2};
  format = 'dd/MM/yyyy';
  locale = 'en-US';

  ngOnInit(): void {
  }

  swipe4Today() {
    this.attendanceDetails.formattedDates = [formatDate(Date.now(), this.format, this.locale)];
    this.webService.markAttendance(this.attendanceDetails).subscribe(resp => this.handleUpdateProfileSuccessRespone(resp), error => this.handleError(error));
  }

  public handleUpdateProfileSuccessRespone(response) {
    this.successResult = response.msg;
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

}
