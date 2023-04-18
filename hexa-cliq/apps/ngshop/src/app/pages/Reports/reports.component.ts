import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { StorageServiceService } from '../storage-service.service';
import { WebServiceService } from '../web-service.service';

@Component({
  selector: 'bluebits-report-page',
  templateUrl: './reports.component.html',
})
export class ReportComponent implements OnInit {
  constructor(private webService: WebServiceService, private storageService: StorageServiceService) { }

  successResult: string = null;
  errorMsg: string = null;
  showAlert: boolean = true;
  attendanceDetails: any = { dates: [] };
  months : any = null;
  format = 'mm-yyyy';
  locale = 'en-US';

  ngOnInit(): void {
    this.webService.getAttendance(formatDate(Date.now(), 'MM/yyyy', this.locale)).subscribe(resp => this.handleUpdateProfileSuccessRespone(resp), error => this.handleError(error));
  }

  viewReports() {
    let formattedMonths ;
    formattedMonths= formatDate(this.months, this.format, this.locale);
    //});
   // this.attendanceDetails.formattedDates = formattedDates;
    this.webService.getReports(formattedMonths).subscribe(resp => this.handleUpdateProfileSuccessRespone(resp), error => this.handleError(error));
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
