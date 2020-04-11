import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Device} from "../models/device";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {

  constructor(private http: HttpClient) { }

  getDevicesForLoggedUser(): Observable<any> {
    return this.http.get('api/devices');
  }

  createDevice(device: Device): Observable<Device> {
    return this.http.post<Device>('api/devices', device);
  }

  getDeviceDetails(id: number): Observable<Device> {
    return this.http.get<Device>(`api/devices/${id}`);
  }
}
