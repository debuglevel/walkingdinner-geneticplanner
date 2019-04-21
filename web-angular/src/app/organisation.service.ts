import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Organisation } from './organisation';
import { ORGANISATIONS } from './mock-organisations';

@Injectable({
  providedIn: 'root'
})
export class OrganisationService {

  constructor() { }

  getOrganisations(): Observable<Organisation[]> {
    return of(ORGANISATIONS);
  }
}
