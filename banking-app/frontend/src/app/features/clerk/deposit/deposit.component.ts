import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { AccountService, Account, TransactionResponse } from '../../../core/services/account.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-deposit',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './deposit.component.html',
  styleUrl: './deposit.component.css'
})
export class DepositComponent implements OnInit {

  account?: Account;
  amount: number = 0;

  loading = true;
  successMessage = '';
  errorMessage = '';

  constructor(
    private route: ActivatedRoute,
    private accountService: AccountService,
    private location: Location
  ) {}

  ngOnInit(): void {
    const accountNumber = this.route.snapshot.paramMap.get('accountNumber');

    if (accountNumber) {
      this.accountService.getAccountByNumber(accountNumber)
        .subscribe({
          next: (data: Account) => {
            this.account = data;
            this.loading = false;
          },
          error: (err: any) => {
            console.error(err);
            this.errorMessage = 'Failed to load account details.';
            this.loading = false;
          }
        });
    }
  }

  deposit(): void {
    if (!this.account) return;

    if (this.amount <= 0) {
      this.errorMessage = 'Please enter a valid deposit amount.';
      this.successMessage = '';
      return;
    }

    this.accountService.deposit(
      this.account.accountNumber,
      this.amount
    ).subscribe({
      next: (response: TransactionResponse) => {

        this.successMessage =
          `Deposit successful! Ref: ${response.transactionRef}`;

        this.errorMessage = '';
        this.amount = 0;

        // Reload account to update balance
        this.reloadAccount();
      },
      error: (err: any) => {
        console.error(err);
        this.errorMessage = 'Deposit failed. Please try again.';
        this.successMessage = '';
      }
    });
  }

  private reloadAccount(): void {
    if (!this.account) return;

    this.accountService
      .getAccountByNumber(this.account.accountNumber)
      .subscribe((data: Account) => {
        this.account = data;
      });
  }

  goBack() {
  this.location.back();
}
}
