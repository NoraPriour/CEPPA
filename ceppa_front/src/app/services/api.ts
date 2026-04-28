import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class ApiService {
  constructor(private http: HttpClient) {}

  getHello() {
    return this.http.get('http://localhost:8080/api/hello', { responseType: 'text' });
  }
  getUsers() {
  return this.http.get<{id: number, userName: string}[]>('http://localhost:8080/api/users');
  }
  deleteUser(id: number) {
    return this.http.delete(`http://localhost:8080/api/users/${id}`);
  }
}