import { Routes } from '@angular/router';
import { authGuard } from './auth/auth.guard';
import { ArticleDetail } from './pages/article-detail/article-detail';
import { Home } from './pages/home/home';
import { MemberSpace } from './pages/member-space/member-space';

export const routes: Routes = [
  {
    path: '',
    component: Home
  },
  {
    path: 'espace-membre',
    component: MemberSpace,
    canActivate: [authGuard]
  },
  {
    path: 'articles/:id',
    component: ArticleDetail
  },
  {
    path: '**',
    redirectTo: ''
  }
];
