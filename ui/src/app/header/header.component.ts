import { Component, OnInit } from '@angular/core';
import { StorageServiceService } from '../storage-service.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private storageService: StorageServiceService) { }

  ngOnInit(): void {
  }

  public isUserLoggedIn(): boolean {
    return (this.storageService.getToken() != null && this.storageService.getToken() != ``);
  }

  getUserPicture() {
    let url:string = this.storageService.getProfileUrl()
    return (url == null ||  url == ``) ? "assets/avatar.svg" : url;
  }

  getUserName() {
    return (this.storageService.getFullName() != null) ? this.storageService.getFullName() : ``;
  }

}
