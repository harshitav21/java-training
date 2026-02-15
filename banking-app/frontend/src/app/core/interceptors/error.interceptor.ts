import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {

  const router = inject(Router);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {

      if (error.status === 401) {
        // Token expired or invalid
        localStorage.removeItem('token');
        router.navigate(['/login']);
        alert('Session expired. Please login again.');
      }

      else if (error.status === 403) {
        alert('You do not have permission to perform this action.');
      }

      else if (error.status === 500) {
        alert('Server error. Please try again later.');
      }

      else if (error.error?.message) {
        alert(error.error.message);
      }

      return throwError(() => error);
    })
  );
};
