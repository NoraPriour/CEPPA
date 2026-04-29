import { Pipe, type PipeTransform } from '@angular/core';

@Pipe({
  name: 'appFormatUser',
})
export class FormatUserPipe implements PipeTransform {

  transform(user: unknown): string {

    return "⭐ " + (user as { userName: string }).userName + " (id " + (user as { id: number }).id + ")";
  }

}
