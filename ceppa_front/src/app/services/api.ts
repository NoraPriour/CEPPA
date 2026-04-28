import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

type UserId = number
type UserResult = {id: UserId, userName: string}

@Injectable({ providedIn: 'root' })
export class ApiService {

  private readonly http = inject(HttpClient);
  
  getHello() {
    return this.http.get('http://localhost:8080/api/hello', { responseType: 'text' });
  }
  getUsers() {
  return this.http.get<UserResult[]>('http://localhost:8080/api/users');
  }
  deleteUser(id: UserId) {
    return this.http.delete(`http://localhost:8080/api/users/${id}`);
  }
  addUser(user: { userName: string, email: string }) {
    return this.http.post<UserResult>('http://localhost:8080/api/users', user);
  }
  getArticles() {
    return this.http.get<{ id: number, auteur: string, titre: string, texte: string }[]>('http://localhost:8080/api/articles');
  }
}