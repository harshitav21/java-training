import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from '../../../core/services/auth.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-create-clerk',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './create-clerk.component.html',
  styleUrls: ['./create-clerk.component.css']
})
export class CreateClerkComponent {

  message = '';
  error = '';

  clerkForm = this.fb.group({
    username: ['', [Validators.required, Validators.minLength(4)]],
    password: ['', [Validators.required, Validators.minLength(4)]]
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private location: Location
  ) { }

  submit() {

    this.message = '';
    this.error = '';

    if (this.clerkForm.invalid) {
      this.clerkForm.markAllAsTouched();
      return;
    }

    this.authService.createClerk(this.clerkForm.value).subscribe({
      next: () => {
        this.message = 'Clerk created successfully';
        this.clerkForm.reset();
      },
      error: (err: any) => {
        console.error(err);
        this.error = 'Failed to create clerk';
      }
    });

  }

  goBack() {
    this.location.back();
  }

}
