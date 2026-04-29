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
    return this.http.get<{ id: number, auteur: string, titre: string, texte: string }[]>('http://localhost:8080/api/articles');
  }
  newArticle(article: { title: string, author: string, text: string }) {
    return this.http.post('http://localhost:8080/api/articles', article);
  }
}


/* Note:
Pour refaire la base de données de 0 (réexecution des scripts de création) :
Dans pgAdmin : DROP SCHEMA public CASCADE; puis CREATE SCHEMA public;
Dans IntelliJ : flyway:migrate
*/