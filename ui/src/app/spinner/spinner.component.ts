import { Component, OnInit } from '@angular/core';
import { StorageServiceService } from '../storage-service.service';

@Component({
  selector: 'app-spinner',
  templateUrl: './spinner.component.html',
  styleUrls: ['./spinner.component.css']
})
export class SpinnerComponent implements OnInit {

  constructor(private storageService: StorageServiceService) { }

  public isLoading(): boolean {
    return this.storageService.isLoading();
  }
  ngOnInit(): void {
  }

}
