import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { StorageServiceService } from '../storage-service.service';
import { WebServiceService } from '../web-service.service';

@Component({
  selector: 'bluebits-attendance-page',
  templateUrl: './attendance.component.html',
})
export class AttendanceComponent implements OnInit {
  constructor(private webService: WebServiceService, private storageService: StorageServiceService) { }

  successResult: string = null;
  errorMsg: string = null;
  showAlert: boolean = true;
  attendanceDetails: any = { dates: [] };
  hours: any = [{
    Value: 1,
    Text: 'Half Day'
  }, {
    Value: 2,
    Text: 'Full Day'
  }, {
    Value: 3,
    Text: 'Extended Full Day'
  }, {
    Value: 4,
    Text: 'Overtime'
  }, {
    Value: 5,
    Text: 'LEAVE'
  }, {
    Value: 6,
    Text: 'HOILDAY'
  }]
  format = 'dd/MM/yyyy';
  locale = 'en-US';

  ngOnInit(): void {
    this.webService.getAttendance(formatDate(Date.now(), 'MM/yyyy', this.locale)).subscribe(resp => this.handleUpdateProfileSuccessRespone(resp), error => this.handleError(error));
  }

  markMyAttendance() {
    let formattedDates = [];
    this.attendanceDetails.dates.forEach(element => {
      formattedDates.push(formatDate(element, this.format, this.locale));
    });
    this.attendanceDetails.formattedDates = formattedDates;
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
