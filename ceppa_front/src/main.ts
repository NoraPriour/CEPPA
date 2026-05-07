import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';
import { keycloak } from './keycloak';

keycloak.init({
  onLoad: 'login-required',
  checkLoginIframe: false,
}).then(() => {
  bootstrapApplication(App, appConfig)
    .catch((err) => console.error(err));
});