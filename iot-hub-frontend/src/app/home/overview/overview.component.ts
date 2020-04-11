import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Device} from "../../models/device";
import {Subject} from "rxjs";
import {debounceTime, distinctUntilChanged} from "rxjs/operators";

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {

  allDevices: Device[] = [];
  devices: Device[] = [];
  searchTerm = new Subject<string>();

  constructor(private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data.subscribe(data => {
      this.allDevices = data.devices;
      this.devices = this.allDevices;
    });
    this.searchTerm.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(term => {
      let upperCaseTerm = term.toUpperCase();
      this.devices = this.allDevices.filter(device => device.name.toUpperCase().indexOf(upperCaseTerm) != -1);
    })
  }

}
