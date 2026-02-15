export interface Account {
  accountNumber: string;
  clientName: string;
  balance: number;
  active: boolean;
  createdAt: string;
}

export interface CreateAccountRequest {
  clientName: string;
  initialDeposit: number;
}
