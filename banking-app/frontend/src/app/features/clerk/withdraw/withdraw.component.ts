import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { AccountService, Account, TransactionResponse } from '../../../core/services/account.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-withdraw',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './withdraw.component.html',
  styleUrl: './withdraw.component.css'
})
export class WithdrawComponent implements OnInit {

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

 withdraw(): void {
  if (!this.account) return;

  if (this.amount <= 0) {
    this.errorMessage = 'Please enter a valid withdrawal amount.';
    this.successMessage = '';
    return;
  }

  this.accountService.withdraw(
    this.account.accountNumber,
    this.amount
  ).subscribe({
    next: (response: TransactionResponse) => {

      const status = response.status?.toUpperCase();

      if (status === 'COMPLETED') {

        this.successMessage =
          `Withdrawal successful! Ref: ${response.transactionRef}`;

        this.reloadAccount();

      } else if (status === 'PENDING_APPROVAL') {

        this.successMessage =
          `Withdrawal request submitted. Ref: ${response.transactionRef}.
           Waiting for Manager Approval.`;
      }

      this.errorMessage = '';
      this.amount = 0;
    },
    error: (err: any) => {

      if (err.status === 400) {
        this.errorMessage = 'Insufficient balance.';
      } else {
        this.errorMessage = 'Withdrawal failed.';
      }

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
