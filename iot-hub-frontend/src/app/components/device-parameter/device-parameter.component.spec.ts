import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeviceParameterComponent } from './device-parameter.component';

describe('DeviceParameterComponent', () => {
  let component: DeviceParameterComponent;
  let fixture: ComponentFixture<DeviceParameterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeviceParameterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeviceParameterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
