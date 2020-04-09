import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {SignupRequest} from "../models/signup-request";
import {AuthenticationService} from "../services/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  form: FormGroup;
  public loginInvalid: boolean;

  constructor(private fb: FormBuilder, private authenticationService: AuthenticationService, private router: Router) {
  }

  ngOnInit() {
    this.form = this.fb.group({
      fullName: ['', Validators.required],
      email: ['', Validators.email],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    }, { validator: SignupComponent.passwordMatchValidator });
  }

  static passwordMatchValidator(control: AbstractControl) {
    const password: string = control.get('password').value;
    const confirmPassword: string = control.get('confirmPassword').value;

    if (password !== confirmPassword) {
      control.get('confirmPassword').setErrors({ PasswordMismatch: true });
    }
  }

  onSubmit() {
    this.loginInvalid = false;
    if (this.form.valid) {
      let signupRequest: SignupRequest = {
        email: this.form.get('email').value,
        password: this.form.get('password').value,
        name: this.form.get('fullName').value
      };
      this.authenticationService.signup(signupRequest).subscribe(() => {
        this.router.navigateByUrl('/home');
      }, () => {
        this.loginInvalid = true;
      });

    }
  }

}
