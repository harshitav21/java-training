import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { AccountService, Account } from '../../../core/services/account.service';
import { Location } from '@angular/common';


@Component({
  selector: 'app-account-details',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './account-details.component.html',
})
export class AccountDetailsComponent implements OnInit {

  account?: Account;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private accountService: AccountService,
    private location: Location
  ) {}

  ngOnInit(): void {
    const accountNumber =
      this.route.snapshot.paramMap.get('accountNumber');

    if (accountNumber) {
      this.accountService.getAccountByNumber(accountNumber)
        .subscribe({
          next: (data: Account) => {
            this.account = data;
            this.loading = false;
          },
          error: (err: any) => {
            console.error(err);
            this.loading = false;
          }
        });
    }
  }

  goBack() {
  this.location.back();
}

}
