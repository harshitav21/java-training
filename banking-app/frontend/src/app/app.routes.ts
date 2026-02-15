import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { roleGuard } from './core/guards/role.guard';

export const routes: Routes = [

  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full'
  },

  {
    path: 'login',
    loadComponent: () =>
      import('./features/auth/login/login.component')
        .then(m => m.LoginComponent)
  },

  {
    path: 'manager',
    canActivate: [authGuard, roleGuard],
    data: { role: 'MANAGER' },
    loadChildren: () =>
      import('./features/manager/manager.routes')
        .then(m => m.MANAGER_ROUTES)
  },

  {
    path: 'clerk',
    canActivate: [authGuard, roleGuard],
    data: { role: 'CLERK' },
    loadChildren: () =>
      import('./features/clerk/clerk.routes')
        .then(m => m.clerkRoutes)
  },

  {
    path: '**',
    redirectTo: 'login'
  }
];
