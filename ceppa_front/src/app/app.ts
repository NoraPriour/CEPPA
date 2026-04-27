import { Component, OnInit, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ApiService } from './services/api';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App implements OnInit {
  message = signal('');

  constructor(private api: ApiService) {}


  users = signal<{id: number, userName: string}[]>([]);

  ngOnInit() {
    this.api.getUsers().subscribe(response => {
      this.users.set(response);
    });
  }
  
  deleteUser(id: number) {
    this.api.deleteUser(id).subscribe(() => {
      this.users.set(this.users().filter(u => u.id !== id));
    });
  }
}