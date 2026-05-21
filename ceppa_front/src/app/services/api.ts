import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { User, CreateUser, UserId } from '../user-type';
import { keycloak } from '../../keycloak';

export type Article = {
  id: number;
  auteur: string;
  titre: string;
  texte: string;
};

@Injectable({ providedIn: 'root' })
export class ApiService {

  private readonly http = inject(HttpClient);

  private getAuthHeaders() {
  return {
    headers: {
      Authorization: `Bearer ${keycloak.token}`
    }
  };
}
  
  getHello() {
    return this.http.get('http://localhost:8080/api/hello', { responseType: 'text' });
  }
  getUsers() {
  return this.http.get<User[]>('http://localhost:8080/api/users', this.getAuthHeaders());
  }
  deleteUser(id: UserId) {
    return this.http.delete(`http://localhost:8080/api/users/${id}`, this.getAuthHeaders());
  }
  addUser(user: CreateUser) {
<<<<<<< HEAD
    return this.http.post<CreateUser>('http://localhost:8080/api/users', user, this.getAuthHeaders());
  }
  getArticles() {
    return this.http.get<{ id: number, author: string, title: string, text: string }[]>('http://localhost:8080/api/articles', this.getAuthHeaders());
  }
  deleteArticle(id: number) {
    return this.http.delete(`http://localhost:8080/api/articles/${id}`, this.getAuthHeaders());
  }
  addArticle(article: { title: string, author: string, text: string }) {
    return this.http.post('http://localhost:8080/api/articles', article, this.getAuthHeaders());
  }
}


/* Note:
Pour refaire la base de données de 0 (réexecution des scripts de création) :
Dans pgAdmin : DROP SCHEMA public CASCADE; puis CREATE SCHEMA public;
Dans IntelliJ : flyway:migrate
*/
=======
    return this.http.post<User>('http://localhost:8080/api/users', user);
  }
  getArticles() {
    return this.http.get<Article[]>('http://localhost:8080/api/articles');
  }
  getArticle(id: number) {
    return this.http.get<Article>(`http://localhost:8080/api/articles/${id}`);
  }
}
>>>>>>> f266748 (feat: separate public site and member area)
