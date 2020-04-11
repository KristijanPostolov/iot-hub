import { TestBed, inject } from '@angular/core/testing';

import { DeviceParameterService } from './device-parameter.service';

describe('DeviceParameterService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DeviceParameterService]
    });
  });

  it('should be created', inject([DeviceParameterService], (service: DeviceParameterService) => {
    expect(service).toBeTruthy();
  }));
});
