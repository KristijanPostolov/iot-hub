import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Device} from "../../models/device";
import {DeviceService} from "../../services/device.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-add-device',
  templateUrl: './add-device.component.html',
  styleUrls: ['./add-device.component.css']
})
export class AddDeviceComponent implements OnInit {

  form: FormGroup;
  responseError: boolean;
  device: Device;

  constructor(private fb: FormBuilder, private deviceService: DeviceService, private snackBar: MatSnackBar) { }

  ngOnInit() {
    this.form = this.fb.group({
      name: ['', Validators.required],
      description: ['']
    });
  }

  onSubmit() {
    let device: Device = {
      name: this.form.get('name').value,
      description: this.form.get('description').value
    };
    this.responseError = false;
    this.deviceService.createDevice(device).subscribe(response => {
      this.device = response;
    }, () => {
      this.responseError = true;
    })
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

}
