import { Routes } from '@angular/router';
// import { DashboardComponent } from './dashboard/dashboard.component';
import { AccountsComponent } from './accounts/accounts.component';
import { AccountDetailsComponent } from './account-details/account-details.component';
import { DepositComponent } from './deposit/deposit.component';
import { WithdrawComponent } from './withdraw/withdraw.component';

export const clerkRoutes: Routes = [
  {
    path: '',
    children: [
    //  { path: 'dashboard', component: DashboardComponent },
      { path: 'accounts', component: AccountsComponent },
      { path: 'account/:accountNumber', component: AccountDetailsComponent },
      { path: 'deposit/:accountNumber', component: DepositComponent },
      { path: 'withdraw/:accountNumber', component: WithdrawComponent },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  }
];
