import { TestBed } from '@angular/core/testing';

import { HttpIntercepterServiceService } from './http-intercepter-service.service';

describe('HttpIntercepterServiceService', () => {
  let service: HttpIntercepterServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HttpIntercepterServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
