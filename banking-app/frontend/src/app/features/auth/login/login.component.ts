import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']  // ✅ fixed here
})
export class LoginComponent {

  loginForm: FormGroup;
  loading = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {

    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login(): void {

    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    this.loading = true;
    this.error = '';

    this.authService.login(this.loginForm.value).subscribe({
      next: (res: any) => {

        this.authService.saveAuthData(res);

        if (res.role === 'MANAGER') {
          this.router.navigate(['/manager']);
        } else {
          this.router.navigate(['/clerk/accounts']);
        }

        this.loading = false;
      },
      error: (err) => {

        if (err?.error?.message) {
          this.error = err.error.message;
        } else {
          this.error = 'Invalid username or password';
        }

        this.loading = false;
      }
    });
  }

}
