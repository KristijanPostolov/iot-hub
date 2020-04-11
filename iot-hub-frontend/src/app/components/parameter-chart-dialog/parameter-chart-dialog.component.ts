import {Component, Inject, OnInit} from '@angular/core';
import {DeviceParameter} from "../../models/device-parameter";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {DeviceParameterService} from "../../services/device-parameter.service";
import { Chart } from 'chart.js'

@Component({
  selector: 'app-parameter-chart-dialog',
  templateUrl: './parameter-chart-dialog.component.html',
  styleUrls: ['./parameter-chart-dialog.component.css']
})
export class ParameterChartDialogComponent implements OnInit {

  parameterId: number;
  parameter: DeviceParameter;
  chart: any;

  constructor(private dialogRef: MatDialogRef<ParameterChartDialogComponent>, @Inject(MAT_DIALOG_DATA) data,
              private deviceParameterService: DeviceParameterService) {
    this.parameterId = data.parameterId;
  }

  ngOnInit() {
    this.deviceParameterService.getDeviceParameterDetails(this.parameterId).subscribe(response => {
      this.parameter = response;

      let parameterValues = this.parameter.values;
      let timestamps = [];
      let values = [];
      for (let timestamp in parameterValues) {
        if (Object.prototype.hasOwnProperty.call(parameterValues, timestamp)) {
          timestamps.push(new Date(timestamp).toLocaleString());
          if (this.parameter.type === 'BOOLEAN') {
            values.push((parameterValues[timestamp].toLowerCase() === 'true') ? 1 : 0);
          }else {
            values.push(parameterValues[timestamp]);
          }
        }
      }

      this.chart = new Chart('canvas', {
        type: 'line',
        data: {
          labels: timestamps,
          datasets: [
            {
              data: values,
              borderColor: '#3CBAAF',
              fill: false,
              steppedLine: this.parameter.type === 'BOOLEAN'
            }
          ]
        },
        options: {
          legend: { display: false },
          scales: {
            xAxes: [{ display: true }],
            yAxes: [{ display: true }]
          }
        }
      })

    });
  }

  close() {
    this.dialogRef.close();
  }

}
