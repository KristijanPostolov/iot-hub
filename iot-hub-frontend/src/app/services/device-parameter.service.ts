import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {DeviceParameter} from "../models/device-parameter";

@Injectable({
  providedIn: 'root'
})
export class DeviceParameterService {

  constructor(private http: HttpClient) { }

  getDeviceParameterDetails(id: number): Observable<DeviceParameter> {
    return this.http.get<DeviceParameter>(`api/parameters/${id}`);
  }
}
