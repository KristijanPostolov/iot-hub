import {Component, Input, OnInit} from '@angular/core';
import {DeviceParameter} from "../../models/device-parameter";
import {MatDialog} from "@angular/material/dialog";
import {EditActuatorDialogComponent} from "../edit-actuator-dialog/edit-actuator-dialog.component";
import {ParameterChartDialogComponent} from "../parameter-chart-dialog/parameter-chart-dialog.component";
import {last} from "rxjs/operators";

@Component({
  selector: 'device-parameter',
  templateUrl: './device-parameter.component.html',
  styleUrls: ['./device-parameter.component.css']
})
export class DeviceParameterComponent implements OnInit {

  @Input() parameter: DeviceParameter;
  lastUpdated: string;

  constructor(private dialog: MatDialog) { }

  ngOnInit() {
    this.lastUpdated = new Date(this.parameter.lastUpdate).toLocaleString();
  }

  editActuator(parameter) {
    let input = {
      parameter: parameter
    };
    const editDialogRef = this.dialog.open(EditActuatorDialogComponent, { data: input });

    editDialogRef.afterClosed().subscribe(newValue => {
      this.parameter.lastValue = newValue;
    });
  }

  openChart() {
    let input = {
      parameterId: this.parameter.id
    };
    this.dialog.open(ParameterChartDialogComponent, { data: input });
  }
}
