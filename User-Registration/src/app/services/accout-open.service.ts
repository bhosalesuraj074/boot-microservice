import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AccoutOpenService {
  constructor(private http: HttpClient) {}
  // URL for the backend API, to Create the user account
  private apiUrl = 'http://localhost:8085/account/create';

  // URL for the backend API, to get the list of user accounts
  private accountListUrl = 'http://localhost:8085/account/get';

  //URL for the backend API, to delete the user account
  private deleteAccountUrl = 'http://localhost:8085/account/delete';

  // URL for the backend API, to update the user account
  private updateAccountUrl = 'http://localhost:8085/account/update';

  // Method to create a new user account
  createAccount(userData: any): Observable<any> {
    return this.http.post(this.apiUrl, userData);
  }

  getAccountList(): Observable<any> {
    return this.http.get(this.accountListUrl);
  }

  // method to get the user account by id
  getAccountById(id: number): Observable<any> {
    return this.http.get(`${this.accountListUrl}/${id}`);
  }

  // method to delete the user account by id
  deleteAccount(id: number): Observable<any> {
    return this.http.delete(`${this.deleteAccountUrl}/${id}`);
  }

  //method to update the details of the user account
  updateAccount(id: number, userData: any): Observable<any> {
    return this.http.patch(`${this.updateAccountUrl}/${id}`, userData);
  }
}
