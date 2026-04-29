import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ApiService } from './services/api';
import { JsonPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { toSignal } from '@angular/core/rxjs-interop';
import { Subject, switchMap, startWith, tap } from 'rxjs';
import { FormatUserPipe } from './format-user-pipe';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, JsonPipe, FormsModule, FormatUserPipe],
  templateUrl: './app.html',
  styleUrl: './app.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class App {
  private readonly api = inject(ApiService);
  private refreshUsers$ = new Subject<void>();
  private refreshArticles$ = new Subject<void>();

  users = toSignal(
    this.refreshUsers$.pipe(
      startWith(null),
      switchMap(() => this.api.getUsers())
    )
  );

  deleteUser(id: number) {
    this.api.deleteUser(id).subscribe(() => {
      this.refreshUsers$.next();
    });
  }

  addUser(userName: string, email: string) {
    this.api.addUser({ userName, email }).subscribe(newUser => {
      this.refreshUsers$.next();
    });
  }

  articles = toSignal(this.refreshArticles$.pipe(
    startWith(null),
    switchMap(() => this.api.getArticles()),

    tap(data => console.log('articles reçus:', data))
  ));

  addArticle(articleTitle: string, articleAuthor: string, articleText: string) {
    this.api.addArticle({ title: articleTitle, author: articleAuthor, text: articleText }).subscribe(newArticle => {
      this.refreshArticles$.next();
    });
  }
}

// TODO
// essayer de supprimer les subscribe
// comprendre pourquoi il y a un fetch à intervale régulier