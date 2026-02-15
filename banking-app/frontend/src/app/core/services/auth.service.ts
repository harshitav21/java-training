import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private authBaseUrl = 'http://localhost:8080/api/auth';
  private userBaseUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient) { }

  // ✅ LOGIN
  login(data: any): Observable<any> {
    return this.http.post(`${this.authBaseUrl}/login`, data);
  }

  // ✅ CREATE CLERK (Manager Only)
  createClerk(data: any): Observable<any> {
    return this.http.post(`${this.userBaseUrl}/clerks`, data);
  }

  // ✅ SAVE AUTH DATA
  saveAuthData(response: any) {
    localStorage.setItem('token', response.token);
    localStorage.setItem('role', response.role);
    localStorage.setItem('username', response.username);
  }

  // ✅ GET TOKEN
  getToken(): string | null {
    return localStorage.getItem('token');
  }

  // ✅ GET ROLE
  getRole(): string | null {
    return localStorage.getItem('role');
  }

  // ✅ CHECK LOGIN
  isLoggedIn(): boolean {
    return !!localStorage.getItem('token');
  }

  getUsername(): string {
  return localStorage.getItem('username') || '';
}


  // ✅ LOGOUT
  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
  }
}
