export interface DepositRequest {
  accountNumber: string;
  amount: number;
}

export interface WithdrawRequest {
  accountNumber: string;
  amount: number;
}

export interface ApprovalRequest {
  approve: boolean;
}

export interface PendingApproval {
  transactionRef: string;
  accountNumber: string;
  amount: number;
  performedBy: string;
  requestedAt: string;
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

export interface TransactionHistory {
  transactionRef: string;
  transactionType: string;
  amount: number;
  status: string;
  performedBy: string;
  approvedBy: string;
  createdAt: string;
}
