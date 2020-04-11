import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Device} from "../../models/device";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-device-detail',
  templateUrl: './device-detail.component.html',
  styleUrls: ['./device-detail.component.css']
})
export class DeviceDetailComponent implements OnInit {

  device: Device;

  constructor(private route: ActivatedRoute, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.device = data.device;
    });
  }

  copyToClipboard(text) {
    document.addEventListener('copy', (e: ClipboardEvent) => {
      e.clipboardData.setData('text/plain', (text));
      e.preventDefault();
      document.removeEventListener('copy', null);
    });
    document.execCommand('copy');
    this.snackBar.open('Copied to clipboard', null, {duration: 1000});
  }

  reloadDeviceDetails() {
    window.location.reload();
  }
}
