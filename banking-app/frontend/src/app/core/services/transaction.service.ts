import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Transaction {
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
export class TransactionService {

  // ✅ FIXED BASE URL
  private baseUrl = 'http://localhost:8080/api/approvals';

  constructor(private http: HttpClient) { }

  // ✅ Backend returns List<PendingApprovalResponseDto>
  getPendingTransactions(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(
      `${this.baseUrl}/pending`
    );
  }

  approveTransaction(ref: string): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${ref}`,
      { approve: true }   // ✅ CORRECT
    );
  }

  rejectTransaction(ref: string): Observable<any> {
    return this.http.post(
      `${this.baseUrl}/${ref}`,
      { approve: false }  // ✅ CORRECT
    );
  }

  getTransactionsByAccount(accountNumber: string) {
  return this.http.get<Transaction[]>(
    `${this.baseUrl}/account/${accountNumber}`
  );
}


}
