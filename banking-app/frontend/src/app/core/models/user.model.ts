export interface CreateClerkRequest {
  username: string;
  password: string;
}

export interface UserResponse {
  id: number;
  username: string;
  role: string;
  active: boolean;
  createdAt: string;
}
