import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { element } from 'protractor';
import { Observable } from 'rxjs';
import { StorageServiceService } from './storage-service.service';

@Injectable({
  providedIn: 'root'
})
export class HttpIntercepterServiceService implements HttpInterceptor {

  private inSecureUrl = [`register-user`, `get-token`, `send-query`, `resource`, `get-captcha`, `verify-email`];

  constructor(private storageService: StorageServiceService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let doesNeedToken = true;
    for (let i = 0; i < this.inSecureUrl.length; i++) {
      let element = this.inSecureUrl[i];
      if (req.url.includes(element)) {
        doesNeedToken = false;
        break;
      }
    }
    if (doesNeedToken) {
      let str: string = this.storageService.getToken();
      if (str) {
        req = req.clone({
          setHeaders: {
            Authorization: "Bearer " + str
          }
        });
      }
    }
    return next.handle(req);
  }
}
