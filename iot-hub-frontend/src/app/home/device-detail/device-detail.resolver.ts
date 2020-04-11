import {ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot} from "@angular/router";
import {Observable} from "rxjs";
import {DeviceService} from "../../services/device.service";
import {tap} from "rxjs/operators";
import {Injectable} from "@angular/core";

@Injectable({providedIn: 'root'})
export class DeviceDetailResolver implements Resolve<any> {

  constructor(private deviceService: DeviceService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<any> | Promise<any> | any {
    return this.deviceService.getDeviceDetails(route.params.id).pipe(
      tap(() => {
      }, () => {
        this.router.navigateByUrl('/');
      })
    );
  }

}
