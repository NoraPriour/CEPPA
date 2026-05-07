import Keycloak from 'keycloak-js';

export const keycloak = new Keycloak({
  url: 'http://localhost:3456',
  realm: 'Ceppa',
  clientId: 'ceppa-angular',
});