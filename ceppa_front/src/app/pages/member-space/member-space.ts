import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { toSignal } from '@angular/core/rxjs-interop';
import { from, startWith, Subject, switchMap } from 'rxjs';
import { FormatUserPipe } from '../../format-user-pipe';
import { ApiService } from '../../services/api';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-member-space',
  imports: [FormsModule, FormatUserPipe, RouterLink],
  templateUrl: './member-space.html',
  styleUrl: './member-space.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MemberSpace {
  private readonly api = inject(ApiService);
  private readonly auth = inject(AuthService);
  private refreshUsers$ = new Subject<void>();
  private refreshArticles$ = new Subject<void>();

  displayName = toSignal(from(this.auth.getDisplayName()), { initialValue: 'Membre' });

  users = toSignal(
    this.refreshUsers$.pipe(
      startWith(null),
      switchMap(() => this.api.getUsers())
    )
  );

  articles = toSignal(
    this.refreshArticles$.pipe(
      startWith(null),
      switchMap(() => this.api.getArticles())
    )
  );

  deleteUser(id: number) {
    this.api.deleteUser(id).subscribe(() => {
      this.refreshUsers$.next();
    });
  }

  addUser(userName: string, email: string, temporaryPassword: string) {
    this.api.addUser({ userName, email, temporaryPassword }).subscribe(() => {
      this.refreshUsers$.next();
    });
  }

  logout() {
    void this.auth.logout(window.location.origin);
  }
}
