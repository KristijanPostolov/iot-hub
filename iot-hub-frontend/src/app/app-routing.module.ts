import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {SignupComponent} from "./signup/signup.component";
import {HomeComponent} from "./home/home.component";
import {OverviewResolver} from "./home/overview/overview.resolver";
import {OverviewComponent} from "./home/overview/overview.component";
import {AddDeviceComponent} from "./home/add-device/add-device.component";
import {DeviceDetailComponent} from "./home/device-detail/device-detail.component";
import {DeviceDetailResolver} from "./home/device-detail/device-detail.resolver";

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    children: [
      {
        path: '',
        component: OverviewComponent,
        resolve: {
          devices: OverviewResolver
        }
      },
      {
        path: 'add',
        component: AddDeviceComponent
      },
      {
        path: 'device/:id',
        component: DeviceDetailComponent,
        resolve: {
          device: DeviceDetailResolver
        }
      }
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: '**', redirectTo: '', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
