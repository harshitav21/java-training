import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AccountService, Account, PageResponse } from '../../../core/services/account.service';
import { AuthService } from '../../../core/services/auth.service'; // ✅ ADD THIS

@Component({
  selector: 'app-accounts',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './accounts.component.html',
})
export class AccountsComponent implements OnInit {

  accounts: Account[] = [];
  currentPage = 0;
  totalPages = 0;
  pageSize = 10;

  username: string = '';   // ✅ Initialize
  greeting: string = '';   // ✅ Initialize

  constructor(
    private accountService: AccountService,
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadAccounts();

    // ✅ Get username safely
    this.username = this.authService.getUsername() || 'User';

    // ✅ Greeting logic
    const hour = new Date().getHours();
    if (hour < 12) {
      this.greeting = 'Good Morning';
    } else if (hour < 17) {
      this.greeting = 'Good Afternoon';
    } else {
      this.greeting = 'Good Evening';
    }
  }

  loadAccounts(page: number = 0): void {
    this.accountService.getAllAccounts(page, this.pageSize).subscribe({
      next: (response: PageResponse<Account>) => {
        this.accounts = response.content;
        this.totalPages = response.totalPages;
        this.currentPage = response.number;
      },
      error: (err) => {
        console.error('Error loading accounts:', err);
      }
    });
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages - 1) {
      this.loadAccounts(this.currentPage + 1);
    }
  }

  previousPage(): void {
    if (this.currentPage > 0) {
      this.loadAccounts(this.currentPage - 1);
    }
  }
  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    this.router.navigate(['/login']);
  }

}
