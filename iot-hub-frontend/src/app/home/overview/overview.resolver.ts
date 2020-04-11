import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs";
import {DeviceService} from "../../services/device.service";

@Injectable({providedIn: 'root'})
export class OverviewResolver implements Resolve<any> {

  constructor(private deviceService: DeviceService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return this.deviceService.getDevicesForLoggedUser();
  }

}
