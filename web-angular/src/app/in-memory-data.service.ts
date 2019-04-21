import { InMemoryDbService } from "angular-in-memory-web-api";
import { Organisation } from "./organisation";
import { Dinner } from "./dinner";
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
    const dinners = [
      { id: 21, name: "1st Zombie dinner at Umbrella Corp." },
      { id: 22, name: "2nd Zombie dinner at Umbrella Corp." },
      { id: 23, name: "Tyrell's Replicant Dinner" },
      { id: 24, name: "Eating old fish (goblins invited only)" },
      { id: 25, name: "Tony destroys your meal" },
      { id: 26, name: "J.A.R.V.I.S. cooks better than you" },
      { id: 27, name: "Beer-gathering" },
    ];
    const teams = [
      { id: 31, name: "Luna and Neville" },
      { id: 32, name: "Kreacher and Dobby" },
      { id: 33, name: "Harry and Ron" },
      { id: 34, name: "Hermoine and Ginny" },
      { id: 35, name: "Dean and Seamus" },
      { id: 36, name: "Patil twins" },
      { id: 37, name: "Tom Riddle and some other parts of his soul" },
      { id: 38, name: "James and Lilly" },
      { id: 39, name: "Aberforth Dumbledore and his goat" },
    ];
    return { organisations, dinners, teams };
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
