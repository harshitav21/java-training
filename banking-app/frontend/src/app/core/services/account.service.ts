import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Account {
  accountNumber: string;
  clientName: string;
  balance: number;
  active: boolean;
  createdAt: string;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number;
  size: number;
}

export interface TransactionResponse {
  transactionRef: string;
  accountNumber: string;
  transactionType: string;
  amount: number;
  status: string;
  performedBy: string;
  approvedBy: string;
  createdAt: string;
  approvedAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private baseUrl = 'http://localhost:8080/api/accounts';
  private transactionUrl = 'http://localhost:8080/api/transactions';

  constructor(private http: HttpClient) {}

  createAccount(data: { clientName: string; initialDeposit: number }) {
    return this.http.post<Account>(this.baseUrl, data);
  }

  getAllAccounts(page: number = 0, size: number = 10): Observable<PageResponse<Account>> {
    return this.http.get<PageResponse<Account>>(
      `${this.baseUrl}?page=${page}&size=${size}`
    );
  }

  getActiveAccounts(): Observable<PageResponse<Account>> {
    return this.http.get<PageResponse<Account>>(`${this.baseUrl}/active`);
  }

  getAccountByNumber(accountNumber: string): Observable<Account> {
    return this.http.get<Account>(
      `${this.baseUrl}/${accountNumber}`
    );
  }

  deposit(accountNumber: string, amount: number): Observable<TransactionResponse> {
    return this.http.post<TransactionResponse>(
      `${this.transactionUrl}/deposit`,
      {
        accountNumber: accountNumber,
        amount: amount
      }
    );
  }

  withdraw(accountNumber: string, amount: number): Observable<TransactionResponse> {
    return this.http.post<TransactionResponse>(
      `${this.transactionUrl}/withdraw`,
      {
        accountNumber: accountNumber,
        amount: amount
      }
    );
  }

  deactivate(accountNumber: string) {
  return this.http.delete(
    `${this.baseUrl}/${accountNumber}`,
    { responseType: 'text'}
  );
}

}
