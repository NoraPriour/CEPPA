import { Component, inject, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ApiService } from './services/api';
import type { Observable } from 'rxjs/internal/Observable';
import { AsyncPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, AsyncPipe, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  message = signal('');

  private readonly api = inject(ApiService);

  users$: Observable<{id: number, userName: string}[]> = this.api.getUsers();

  users = signal<{id: number, userName: string}[]>([]);

  deleteUser(id: number) {
    this.api.deleteUser(id).subscribe(() => {
      this.users.set(this.users().filter(u => u.id !== id));
    });
  }

  addUser(userName: string, email: string) {
    this.api.addUser({ userName, email }).subscribe(newUser => {
      this.users.set([...this.users(), newUser]);
    });
  }
}

//fn toSignal