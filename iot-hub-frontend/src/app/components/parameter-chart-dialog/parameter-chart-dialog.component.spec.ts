import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ParameterChartDialogComponent } from './parameter-chart-dialog.component';

describe('ParameterChartDialogComponent', () => {
  let component: ParameterChartDialogComponent;
  let fixture: ComponentFixture<ParameterChartDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ParameterChartDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ParameterChartDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
