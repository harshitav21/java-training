import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApprovalService {

  private baseUrl = 'http://localhost:8080/api/approvals';

  constructor(private http: HttpClient) {}

  // Get all pending withdrawals
  getPendingApprovals(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/pending`);
  }

  // Approve or reject
  approveOrReject(transactionRef: string, approve: boolean) {
    return this.http.post(
      `${this.baseUrl}/${transactionRef}`,
      { approve }
    );
  }
}
