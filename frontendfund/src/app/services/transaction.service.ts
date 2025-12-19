import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TransferPayload } from '../interface/transfer-payload';
import { TransactionHistory } from '../interface/transaction-history';

@Injectable({ providedIn: 'root' })
export class TransactionService {

  // Base URL for transaction APIs
  private baseUrl = 'http://localhost:8080/api/transactions';

  constructor(private http: HttpClient) {
    console.log('[TransactionService] Service initialized');
  }

  /**
   * Builds HTTP headers with JWT token
   * @returns HttpHeaders with Authorization token
   */
  private getHeaders() {
    const token = localStorage.getItem('token');
    console.log('[TransactionService] Fetching token from localStorage');

    return {
      headers: new HttpHeaders({
        Authorization: `Bearer ${token}`
      })
    };
  }

  /**
   * Sends money transfer request to backend
   * @param payload senderId, receiverId, amount
   */
  transfer(payload: TransferPayload): Observable<any> {
    console.log('[TransactionService] Transfer request payload:', payload);
    return this.http.post(
      `${this.baseUrl}/transfer`,
      payload,
      this.getHeaders()
    );
  }

  /**
   * Fetches transaction history for a user
   * @param userId Logged-in user ID
   */
  getHistory(userId: number): Observable<TransactionHistory[]> {
    console.log('[TransactionService] Fetching history for userId=', userId);

    return this.http.get<TransactionHistory[]>(
      `${this.baseUrl}/history/${userId}`,
      this.getHeaders()
    );
  }
}
