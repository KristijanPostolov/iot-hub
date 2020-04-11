import {Component, Input, OnInit} from '@angular/core';
import {DeviceParameter} from "../../models/device-parameter";
import {MatDialog} from "@angular/material/dialog";
import {EditActuatorDialogComponent} from "../edit-actuator-dialog/edit-actuator-dialog.component";

@Component({
  selector: 'device-parameter',
  templateUrl: './device-parameter.component.html',
  styleUrls: ['./device-parameter.component.css']
})
export class DeviceParameterComponent implements OnInit {

  @Input() parameter: DeviceParameter;

  constructor(private dialog: MatDialog) { }

  ngOnInit() {
  }

  editActuator(parameter) {
    let input = {
      parameter: parameter
    };
    this.dialog.open(EditActuatorDialogComponent, { data: input });
  }
}
