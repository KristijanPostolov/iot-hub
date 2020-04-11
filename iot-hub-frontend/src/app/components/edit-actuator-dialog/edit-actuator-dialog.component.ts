import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DeviceParameter} from "../../models/device-parameter";
import {DeviceParameterService} from "../../services/device-parameter.service";

@Component({
  selector: 'app-edit-actuator-dialog',
  templateUrl: './edit-actuator-dialog.component.html',
  styleUrls: ['./edit-actuator-dialog.component.css']
})
export class EditActuatorDialogComponent implements OnInit {

  errorMessage = false;
  parameter: DeviceParameter;
  value: string;

  constructor(private dialogRef: MatDialogRef<EditActuatorDialogComponent>, @Inject(MAT_DIALOG_DATA) data,
              private deviceParameterService: DeviceParameterService) {
    this.parameter = data.parameter;
  }

  ngOnInit() {
    this.value = this.parameter.lastValue;
  }

  reset() {
    this.value = this.parameter.lastValue;
  }

  close() {
    this.dialogRef.close();
  }

  update() {
    this.errorMessage = false;
    this.deviceParameterService.updateActuatorValue(this.parameter.id, this.value).subscribe(() => {
      this.dialogRef.close(this.value);
    }, () => {
      this.errorMessage = true;
    })
  }

  isValueChanged() {
    return this.value !== this.parameter.lastValue;
  }

  toggleValue() {
    if (this.value.toLowerCase() === 'true') {
      this.value = 'false';
    } else {
      this.value = 'true';
    }
  }

}
