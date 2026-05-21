import { Pipe, type PipeTransform } from '@angular/core';

@Pipe({
  name: 'appFormatUser',
})
export class FormatUserPipe implements PipeTransform {

  transform(user: unknown): string {
    const typedUser = user as { userName: string; email: string; keycloakId: string | null };
    const linkStatus = typedUser.keycloakId ? 'compte Keycloak lie' : 'profil non lie a Keycloak';

    return `${typedUser.userName} - ${typedUser.email} (${linkStatus})`;
  }

}
