import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageServiceService } from '../storage-service.service';
import { WebServiceService } from '../web-service.service';

@Component({
  selector: 'app-log-out',
  templateUrl: './log-out.component.html',
  styleUrls: ['./log-out.component.css']
})
export class LogOutComponent implements OnInit {

  constructor(private storageService: WebServiceService, private router: Router) { }

  ngOnInit(): void {
    this.storageService.logout();
    this.router.navigate([``]);
  }

}
