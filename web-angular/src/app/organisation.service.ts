import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Organisation } from './organisation';
import { ORGANISATIONS } from './mock-organisations';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class OrganisationService {

  constructor(private messageService: MessageService) { }

  getOrganisations(): Observable<Organisation[]> {
    // TODO: send the message _after_ fetching the organisations
    this.messageService.add('OrganisationService: fetching organisations...');
    return of(ORGANISATIONS);
  }
}
