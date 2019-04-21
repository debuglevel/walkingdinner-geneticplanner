import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Organisation } from './organisation';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const organisations: Organisation[] = [
      { id: 11, name: 'Umbrella Corp' },
      { id: 12, name: 'Google' },
      { id: 13, name: 'NASA' },
      { id: 14, name: 'Microsoft' },
      { id: 15, name: 'Lenovo' },
      { id: 16, name: 'Huawei' },
      { id: 17, name: 'Schweppes' },
    ];

    return {organisations};
  }

  // Overrides the genId method to ensure that a hero always has an id.
  // If the heroes array is empty,
  // the method below returns the initial number (11).
  // if the heroes array is not empty, the method below returns the highest
  // hero id + 1.
  genId(organisations: Organisation[]): number {
    return organisations.length > 0 ? Math.max(...organisations.map(organisation => organisation.id)) + 1 : 11;
  }
}