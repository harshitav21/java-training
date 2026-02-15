import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AccountService, Account } from '../../../core/services/account.service';
import { TransactionService, Transaction } from '../../../core/services/transaction.service';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  accounts: Account[] = [];
  transactions: Transaction[] = [];
  searchText: string = '';
  greeting: string = '';
  username: string = '';

  activeCount: number = 0;
  pendingCount: number = 0;

  // ✅ Pagination State
  currentPage: number = 0;
  pageSize: number = 10;
  totalPages: number = 0;
  totalElements: number = 0;

  constructor(
    private accountService: AccountService,
    private transactionService: TransactionService,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadAccounts();
    this.loadPendingTransactions();
    this.setGreeting();
    this.username = this.authService.getUsername();
  }

  private setGreeting(): void {
    const hour = new Date().getHours();

    if (hour < 12) {
      this.greeting = 'Good Morning';
    } else if (hour < 18) {
      this.greeting = 'Good Afternoon';
    } else {
      this.greeting = 'Good Evening';
    }
  }

  // ✅ Load Accounts with Pagination
  loadAccounts(): void {
    this.accountService
      .getAllAccounts(this.currentPage, this.pageSize)
      .subscribe({
        next: (response) => {
          this.accounts = response.content;
          this.totalPages = response.totalPages;
          this.totalElements = response.totalElements;
          this.currentPage = response.number;

          // ⚠️ Counts active only on current page
          this.activeCount = this.accounts.filter(acc => acc.active).length;
        },
        error: (err) => {
          console.error('Error loading accounts', err);
        }
      });
  }

  filteredAccounts() {
    if (!this.searchText) return this.accounts;

    return this.accounts.filter(account =>
      account.accountNumber.toLowerCase().includes(this.searchText.toLowerCase()) ||
      account.clientName.toLowerCase().includes(this.searchText.toLowerCase())
    );
  }

  goToPage(page: number): void {
    if (page >= 0 && page < this.totalPages) {
      this.currentPage = page;
      this.loadAccounts();
    }
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.loadAccounts();
    }
  }

  prevPage(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.loadAccounts();
    }
  }

  loadPendingTransactions(): void {
    this.transactionService.getPendingTransactions()
      .subscribe({
        next: (response: Transaction[]) => {
          this.transactions = response;
          this.pendingCount = response.length;
        },
        error: (err) => {
          console.error('Error loading transactions', err);
        }
      });
  }

  approve(ref: string): void {
    this.transactionService.approveTransaction(ref).subscribe(() => {
      this.loadPendingTransactions();
      this.loadAccounts();
    });
  }

  reject(ref: string): void {
    this.transactionService.rejectTransaction(ref).subscribe(() => {
      this.loadPendingTransactions();
      this.loadAccounts();
    });
  }

  editAccount(accountNumber: string) {
    const account = this.accounts.find(acc => acc.accountNumber === accountNumber);

    if (account && !account.active) {
      return;
    }

    this.router.navigate(['/manager/update-account', accountNumber]);
  }

  deactivate(accountNumber: string) {

    if (!confirm('Are you sure you want to deactivate this account?')) {
      return;
    }

    this.accountService.deactivate(accountNumber)
      .subscribe({
        next: () => {
          alert('Account deactivated successfully.');
          this.loadAccounts();
        },
        error: (err) => {

          // Extract backend message safely
          const message =
            err?.error?.message ||
            'Account deactivation failed. Please try again.';

          alert(message);

          console.error('Deactivate error:', err);
        }
      });
  }



  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  // ✅ Helper for pagination buttons in HTML
  get pageNumbers(): number[] {
    return Array.from({ length: this.totalPages }, (_, i) => i);
  }
}
