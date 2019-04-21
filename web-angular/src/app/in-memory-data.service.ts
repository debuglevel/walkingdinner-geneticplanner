import { InMemoryDbService } from "angular-in-memory-web-api";
import { Organisation } from "./organisation";
import { Injectable } from "@angular/core";

@Injectable({
  providedIn: "root",
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const organisations = [
      { id: 11, name: "Umbrella Corp." },
      { id: 12, name: "Tyrell Corp." },
      { id: 13, name: "Stark Industries" },
      { id: 14, name: "Gringotts" },
      { id: 15, name: "MomCorp" },
      { id: 16, name: "Duff Beer" },
      { id: 17, name: "Octan" },
    ];
    return { organisations };
  }

  // Overrides the genId method to ensure that a organisation always has an id.
  // If the organisations array is empty,
  // the method below returns the initial number (11).
  // if the organisations array is not empty, the method below returns the highest
  // organisation id + 1.
  genId(organisations: Organisation[]): number {
    return organisations.length > 0
      ? Math.max(...organisations.map(organisation => organisation.id)) + 1
      : 11;
  }
}
