import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LoginRequest} from "../models/login-request";
import {Observable} from "rxjs";
import {SignupRequest} from "../models/signup-request";
import {tap} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) { }

  signup(signupRequest: SignupRequest): Observable<any> {
    return this.http.post('api/signup', signupRequest)
      .pipe(tap(this.saveJwtToLocalStorage));
  }

  login(loginRequest: LoginRequest): Observable<any> {
    return this.http.post('api/login', loginRequest)
      .pipe(tap(this.saveJwtToLocalStorage));
  }

  logout() {
    localStorage.removeItem('jwt_token');
    // todo: backend token blacklisting
  }

  saveJwtToLocalStorage = response => {
    localStorage.setItem('jwt_token', response.jwt);
  }

}
