import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageServiceService } from '../storage-service.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.css']
})
export class WelcomeComponent implements OnInit {

  constructor(private router: Router, private storageService: StorageServiceService) { }

  ngOnInit(): void {
    let str: string = this.storageService.getToken();
    if (str) {
      this.router.navigate([`dashboard`]);
    }
  }

  public contactUs() {
    this.router.navigate(['/contact-us']);
  }

  public register() {
    this.router.navigate(['/register']);
  }

}
