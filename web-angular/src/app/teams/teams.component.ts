import { Component, OnInit } from "@angular/core";

import { Team } from "../team";
import { TeamService } from "../team.service";

@Component({
  selector: "app-teams",
  templateUrl: "./teams.component.html",
  styleUrls: ["./teams.component.css"],
})
export class TeamsComponent implements OnInit {
  teams: Team[];

  constructor(private teamService: TeamService) {}

  ngOnInit() {
    this.getTeams();
  }

  getTeams(): void {
    this.teamService.getTeams().subscribe(teams => (this.teams = teams));
  }

  add(
    address: string,
    chef1: string,
    chef2: string,
    mail1: string,
    mail2: string,
    phone1: string,
    phone2: string,
    city: string,
    notes: string,
    veganAppetizer: boolean,
    veganMaindish: boolean,
    veganDessert: boolean,
    vegetarianAppetizer: boolean,
    vegetarianMaindish: boolean,
    vegetarianDessert: boolean,
    omnivoreAppetizer: boolean,
    omnivoreMaindish: boolean,
    omnivoreDessert: boolean,
    diet: string
  ): void {
    //name = name.trim();
    // if (!name) {
    //   return;
    // }

    console.log(veganAppetizer);

    this.teamService
      .addTeam({
        address,
        chef1,
        chef2,
        mail1,
        mail2,
        phone1,
        phone2,
        city,
        notes,
        veganAppetizer,
        veganMaindish,
        veganDessert,
        vegetarianAppetizer,
        vegetarianMaindish,
        vegetarianDessert,
        omnivoreAppetizer,
        omnivoreMaindish,
        omnivoreDessert,
        diet,
      } as Team)
      .subscribe(team => {
        this.teams.push(team);
      });
  }

  delete(team: Team): void {
    this.teams = this.teams.filter(h => h !== team);
    this.teamService.deleteTeam(team).subscribe();
  }
}
