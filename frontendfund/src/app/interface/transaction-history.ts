export interface TransactionHistory {
  id: number;
  senderId: number;
  receiverId: number;
  amount: number;
  status: string;
  timestamp: string;
}
