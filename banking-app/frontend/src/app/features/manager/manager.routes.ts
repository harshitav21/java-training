import { Routes } from '@angular/router';
import { CreateClerkComponent } from './create-clerk/create-clerk.component';
import { PendingApprovalsComponent } from './pending-approvals/pending-approvals.component';
import { DashboardComponent } from '../manager/dashboard/dashboard.component';
import { CreateAccountComponent } from './create-account/create-account.component';

import { authGuard } from '../../core/guards/auth.guard';
import { roleGuard } from '../../core/guards/role.guard';

export const MANAGER_ROUTES: Routes = [

  {
    path: '',
    component: DashboardComponent,
    canActivate: [authGuard, roleGuard],
    data: { role: 'MANAGER' }
  },

  {
    path: 'create-account',
    component: CreateAccountComponent,
    canActivate: [authGuard, roleGuard],
    data: { role: 'MANAGER' }
  },

  {
    path: 'create-clerk',
    component: CreateClerkComponent,
    canActivate: [authGuard, roleGuard],
    data: { role: 'MANAGER' }
  },

  {
    path: 'approvals',
    component: PendingApprovalsComponent,
    canActivate: [authGuard, roleGuard],
    data: { role: 'MANAGER' }
  }
];

// export const MANAGER_ROUTES: Routes = [

//   {
//     path: '',
//     component: DashboardComponent,
//     canActivate: [authGuard, roleGuard],
//     data: { role: 'ROLE_MANAGER' }
//   },

//   {
//     path: 'create-account',
//     component: CreateAccountComponent,
//     canActivate: [authGuard, roleGuard],
//     data: { role: 'ROLE_MANAGER' }
//   },

//   {
//     path: 'create-clerk',
//     component: CreateClerkComponent,
//     canActivate: [authGuard, roleGuard],
//     data: { role: 'ROLE_MANAGER' }
//   },

//   {
//     path: 'approvals',
//     component: PendingApprovalsComponent,
//     canActivate: [authGuard, roleGuard],
//     data: { role: 'ROLE_MANAGER' }
//   }
// ];
