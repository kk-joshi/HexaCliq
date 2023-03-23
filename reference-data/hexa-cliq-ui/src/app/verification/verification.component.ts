import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { StorageServiceService } from '../storage-service.service';
import { WebServiceService } from '../web-service.service';

@Component({
  selector: 'app-verification',
  templateUrl: './verification.component.html',
  styleUrls: ['./verification.component.css']
})
export class VerificationComponent implements OnInit {

  public successMsg: string = null;
  public errorMsg: string = null;

  constructor(private webService: WebServiceService, private storageService: StorageServiceService, private routerAct: ActivatedRoute) {
    let actCode = routerAct.snapshot.queryParamMap.get(`activationCode`)
    let userId = routerAct.snapshot.queryParamMap.get(`userId`)
    webService.verifyEmail(actCode, userId).subscribe(resp => this.handleResp(resp), error => {
      if (error.status == 0) {
        this.errorMsg = `Server under maintainance, please try after sometime`;
      } else {
        this.errorMsg = error.error;
      }
      this.storageService.hideLoader();
    });
  }

  private handleResp(resp) {
    this.successMsg = resp.msg;
    this.storageService.hideLoader();
  }

  ngOnInit(): void {
  }

}