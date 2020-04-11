import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { HomeComponent } from './home/home.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatMenuModule} from "@angular/material/menu";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatButtonToggleModule} from "@angular/material/button-toggle";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatInputModule} from "@angular/material/input";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {AuthenticationInterceptor} from "./services/authentication.interceptor";
import {ErrorInterceptor} from "./services/error.interceptor";
import { OverviewComponent } from './home/overview/overview.component';
import { AddDeviceComponent } from './home/add-device/add-device.component';
import { StatusIndicatorComponent } from './components/status-indicator/status-indicator.component';
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatSnackBar, MatSnackBarModule} from "@angular/material/snack-bar";
import { DeviceDetailComponent } from './home/device-detail/device-detail.component';
import {MatGridListModule} from "@angular/material/grid-list";
import {MatDialogModule} from "@angular/material/dialog";
import {MatListModule} from "@angular/material/list";
import { DeviceParameterComponent } from './components/device-parameter/device-parameter.component';
import {MatSliderModule} from "@angular/material/slider";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import { EditActuatorDialogComponent } from './components/edit-actuator-dialog/edit-actuator-dialog.component';
import { ParameterChartDialogComponent } from './components/parameter-chart-dialog/parameter-chart-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    HomeComponent,
    OverviewComponent,
    AddDeviceComponent,
    StatusIndicatorComponent,
    DeviceDetailComponent,
    DeviceParameterComponent,
    EditActuatorDialogComponent,
    ParameterChartDialogComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    MatToolbarModule,
    MatIconModule,
    MatMenuModule,
    MatButtonModule,
    MatCardModule,
    MatFormFieldModule,
    MatProgressSpinnerModule,
    MatButtonToggleModule,
    ReactiveFormsModule,
    MatInputModule,
    HttpClientModule,
    MatTooltipModule,
    MatSnackBarModule,
    FormsModule,
    MatGridListModule,
    MatDialogModule,
    MatListModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatDialogModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true
    },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [EditActuatorDialogComponent, ParameterChartDialogComponent]
})
export class AppModule { }
