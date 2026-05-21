import { Injectable } from '@angular/core';
import Keycloak, { KeycloakProfile } from 'keycloak-js';
import { keycloakConfig } from './keycloak.config';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly keycloak = new Keycloak(keycloakConfig);
  private initPromise?: Promise<boolean>;

  init() {
    this.initPromise ??= this.keycloak.init({
      checkLoginIframe: false,
      pkceMethod: 'S256'
    });

    return this.initPromise;
  }

  async login(redirectUri = window.location.href) {
    await this.init();
    await this.keycloak.login({ redirectUri });
  }

  async getToken() {
    await this.init();

    if (!this.keycloak.authenticated) {
      return null;
    }

    await this.keycloak.updateToken(30);
    return this.keycloak.token ?? null;
  }

  async isAuthenticated() {
    await this.init();
    return this.keycloak.authenticated === true;
  }

  async getDisplayName() {
    await this.init();

    const tokenName = this.keycloak.tokenParsed?.['preferred_username'];

    if (typeof tokenName === 'string' && tokenName.trim().length > 0) {
      return tokenName;
    }

    const profile: KeycloakProfile = await this.keycloak.loadUserProfile();
    return profile.username ?? profile.email ?? 'Membre';
  }

  async logout(redirectUri = window.location.origin) {
    await this.init();
    await this.keycloak.logout({ redirectUri });
  }
}
