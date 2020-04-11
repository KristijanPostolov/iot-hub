import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../services/authentication.service";
import {LoginRequest} from "../models/login-request";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  loginInvalid: boolean;

  constructor(private fb: FormBuilder, private authenticationService: AuthenticationService, private router: Router) {
  }

  ngOnInit() {
    this.form = this.fb.group({
      email: ['', Validators.email],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    this.loginInvalid = false;
    if (this.form.valid) {
      let loginRequest: LoginRequest = {
        email: this.form.get('email').value,
        password: this.form.get('password').value,
      };
      this.authenticationService.login(loginRequest).subscribe(() => {
        this.router.navigateByUrl('/');
      }, () => {
        this.loginInvalid = true;
      })
    }
  }
}
