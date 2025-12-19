import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {

  // Base URL for authentication APIs
  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {
    console.log('[AuthService] Service initialized');
  }

  /**
   * Sends login request to backend
   * @param payload { username, password }
   */
  login(payload: any): Observable<any> {
    console.log('[AuthService] Login request payload:', payload);
    return this.http.post(`${this.baseUrl}/login`, payload);
  }

  /**
   * Sends signup request to backend
   * @param payload { username, password }
   */
  signup(payload: any): Observable<any> {
    console.log('[AuthService] Signup request payload:', payload);
    return this.http.post(`${this.baseUrl}/signup`, payload);
  }

  /**
   * Stores authentication details in localStorage
   * @param token JWT token
   * @param userId Logged-in user ID
   */
  saveAuth(token: string, userId: number) {
    console.log('[AuthService] Saving auth data | userId=', userId);
    localStorage.setItem('token', token);
    localStorage.setItem('userId', userId.toString());
  }

  /**
   * Checks whether user is logged in
   * @returns true if token exists
   */
  isLoggedIn(): boolean {
    const loggedIn = !!localStorage.getItem('token');
    console.log('[AuthService] isLoggedIn =', loggedIn);
    return loggedIn;
  }

  /**
   * Clears authentication data and logs out user
   */
  logout() {
    console.log('[AuthService] Logging out user');
    localStorage.clear();
  }
}
