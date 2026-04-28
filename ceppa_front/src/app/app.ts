import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ApiService } from './services/api';
import type { Observable } from 'rxjs/internal/Observable';
import { AsyncPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { toSignal } from '@angular/core/rxjs-interop';
import { Subject, switchMap, startWith } from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AsyncPipe, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class App {
  private readonly api = inject(ApiService);
  private refresh$ = new Subject<void>();

  users$: Observable<{ id: number, userName: string }[]> = this.api.getUsers();

  users = toSignal(
    this.refresh$.pipe(
      startWith(null),
      switchMap(() => this.api.getUsers())
    )
  );

  deleteUser(id: number) {
    this.api.deleteUser(id).subscribe(() => {
      this.refresh$.next();
    });
  }

  addUser(userName: string, email: string) {
    this.api.addUser({ userName, email }).subscribe(newUser => {
      this.refresh$.next();
    });
  }
}