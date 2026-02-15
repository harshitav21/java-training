import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AccountService } from '../../../core/services/account.service';
import { Router } from '@angular/router';
import { Location } from '@angular/common';


@Component({
  selector: 'app-create-account',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './create-account.component.html',
})
export class CreateAccountComponent {

  clientName: string = '';
  initialBalance: number = 0;

  constructor(
    private accountService: AccountService,
    private router: Router,
    private location: Location
  ) { }

  createAccount() {

    const payload = {
      clientName: this.clientName,
      initialDeposit: this.initialBalance  // ✅ FIXED
    };

    this.accountService.createAccount(payload).subscribe({
      next: () => {
        alert('Account created successfully');
        this.router.navigate(['/manager']);
      },
      error: (err: any) => {
        console.error(err);
        alert('Failed to create account');
      }
    });
  }

  goBack() {
    this.location.back();
  }
}
