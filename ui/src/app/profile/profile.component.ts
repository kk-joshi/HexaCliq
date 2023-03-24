import { Component, OnInit } from '@angular/core';
import { count } from 'console';
import { StorageServiceService } from '../storage-service.service';
import { ValueAndText, WebServiceService } from '../web-service.service';

class ImageSnippet {
  pending: boolean = false;
  status: string = 'init';

  constructor(public src: string, public file: File) { }
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  selectedFile: ImageSnippet;
  countries: ValueAndText[] = [];
  userDetails: any = {};
  userProfileImg: string | ArrayBuffer;
  showAlert = false;
  successResult: string = null;
  errorMsg: string;

  constructor(private webService: WebServiceService, private storageService: StorageServiceService) {
    this.userProfileImg = "assets/avatar.svg";
    this.countries = webService.getCountries();
    this.initilizeErrorMsg();
    this.fetchUserDetails();
  }

  initilizeErrorMsg() {
    this.errorMsg = `Something went wrong.. please contact support`
  }

  closeAlert() {
    this.showAlert = false;
  }

  closeSuccessAlert() {
    this.successResult = null;
  }

  fetchUserDetails() {
    this.webService.getProfile().subscribe(resp => {
      this.userDetails = resp;
      this.countries.find((country) => {
        if (country.Value == this.userDetails.country) {
          this.userDetails.nationality = country.Text;
        }
      });
      if (this.userDetails.profilePicResorceId != 0) {
        this.userProfileImg = this.webService.storeImageUrl(this.userDetails.profilePicResorceId);
      }
      this.storageService.saveCountry(this.userDetails.country);
      this.storageService.hideLoader();
    }, errorResp => { this.showAlert = true; this.storageService.hideLoader(); });
  }

  ngOnInit(): void {
  }

  changePic() {
    let element: HTMLElement = document.getElementById('fileUploader') as HTMLElement;
    element.click();
  }

  updateProfile() {
    this.successResult = null;
    this.showAlert = false;
    if (!this.isValidUserDetails()) {
      this.showAlert = true;
    } else {
      this.webService.updateProfile(this.userDetails).subscribe(resp => this.handleUpdateProfileSuccessRespone(resp), error => this.handleError(error));
    }
  }

  isValidUserDetails(): boolean {
    let valid = true;
    this.initilizeErrorMsg();
    if (this.userDetails.country == ``) {
      valid = false;
    } else if (this.userDetails.mobileNo == ``) {
      valid = false;
    } else if (this.userDetails.payPalAccountId == ``) {
      valid = false;
    } else if (this.userDetails.occupation == ``) {
      valid = false;
    } else if (!this.isValidMobileNo()) {
      this.errorMsg = `Please enter valid mobile`;
      valid = false;
    }
    return valid;
  }


  public isValidMobileNo() {
    let regexpEmail = new RegExp('[0-9]{10,}');
    return regexpEmail.test(this.userDetails.mobileNo);
  }

  public handleUpdateProfileSuccessRespone(response) {
    this.successResult = response.msg;
    this.fetchUserDetails();
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

  addAddresses() {
  }

  onSelectFile(event) {
    if (event.target.files && event.target.files[0]) {
      var reader = new FileReader();
      let file1: File = event.target.files[0];
      let file: File = event.target.files[0];
      reader.readAsDataURL(file); // read file as data url

      reader.onload = (event: any) => { // called once readAsDataURL is completed
        this.selectedFile = new ImageSnippet(event.target.result, file);
        this.webService.uploadImage(this.selectedFile.file, 'PROFILE_PICTURE').subscribe(
          (res: any) => {
            this.userProfileImg = this.webService.storeImageUrl(res.msg);
            this.storageService.hideLoader();
          },
          (err) => {
            this.storageService.hideLoader();
          });
      }
    }
  }

  /**** Change password code */
  public changePassword = {
    "oldPassword": "",
    "newPassword": "",
    "confirmPassword": ""
  };
  passwordError = null;

  public changeThePassword() {
    if (this.validChangePassword()) {
      this.webService.changePassword(this.changePassword).subscribe(resp => this.handleChangePassword(resp), err => this.handlePasswordError(err))
    }
  }

  public validChangePassword() {
    let isValid = true;
    if (this.changePassword.oldPassword == ``) {
      this.passwordError = `Enter old password`;
      isValid = false;
    } else if (this.changePassword.newPassword == ``) {
      this.passwordError = `Enter new password`;
      isValid = false;
    } else if (this.changePassword.confirmPassword == ``) {
      this.passwordError = `Enter confirm password`;
      isValid = false;
    } else if (this.changePassword.confirmPassword != this.changePassword.newPassword) {
      this.passwordError = `Passwords doesn't match`;
      isValid = false;
    } else if (!this.isValidPasswordLength()) {
      this.passwordError = `Please enter strong password`;
      isValid = false;
    } 
    return isValid;
  }

  public isValidPasswordLength() {
    return this.changePassword.newPassword.length > 4;
  }

  handleChangePassword(resp) {
    let element: HTMLElement = document.getElementById('passwordClose') as HTMLElement;
    element.click();
    this.successResult = resp.msg;
    this.storageService.hideLoader();
  }

  handlePasswordError(errorResp) {
    if (errorResp.status == 0) {
      this.passwordError = `Server under maintainance, please try after sometime`;
    } else {
      this.passwordError = errorResp.error;
    }
    this.storageService.hideLoader();
  }


  /**** Add Address code */
  isDisabled = false;
  mouseEnter(div: string) {
    this.isDisabled = true;
    let element: HTMLElement = document.getElementById(div) as HTMLElement;
    element.classList.remove(`btn-primary`);
    element.classList.add(`btn-secondary`);
  }

  mouseLeave(div: string) {
    this.isDisabled = false;
    let element: HTMLElement = document.getElementById(div) as HTMLElement;
    element.classList.remove(`btn-secondary`);
    element.classList.add(`btn-primary`);
  }

}
