import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User, CreateUser, UserId } from '../user-type';

@Injectable({ providedIn: 'root' })
export class ApiService {

  private readonly http = inject(HttpClient);
  
  getHello() {
    return this.http.get('http://localhost:8080/api/hello', { responseType: 'text' });
  }
  getUsers() {
  return this.http.get<User[]>('http://localhost:8080/api/users');
  }
  deleteUser(id: UserId) {
    return this.http.delete(`http://localhost:8080/api/users/${id}`);
  }
  addUser(user: CreateUser) {
    return this.http.post<CreateUser>('http://localhost:8080/api/users', user);
  }
  getArticles() {
    return this.http.get<{ id: number, auteur_id: number, titre: string, texte: string }[]>('http://localhost:8080/api/articles');
  }
}