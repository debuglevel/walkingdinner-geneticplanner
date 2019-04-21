import { Component, OnInit } from "@angular/core";

import { Organisation } from "../organisation";
import { OrganisationService } from "../organisation.service";

@Component({
  selector: "app-organisations",
  templateUrl: "./organisations.component.html",
  styleUrls: ["./organisations.component.css"],
})
export class OrganisationsComponent implements OnInit {
  organisations: Organisation[];

  constructor(private organisationService: OrganisationService) {}

  ngOnInit() {
    this.getOrganisations();
  }

  getOrganisations(): void {
    this.organisationService
      .getOrganisations()
      .subscribe(organisations => (this.organisations = organisations));
  }

  add(name: string): void {
    name = name.trim();
    if (!name) {
      return;
    }
    this.organisationService
      .addOrganisation({ name } as Organisation)
      .subscribe(organisation => {
        this.organisations.push(organisation);
      });
  }

  delete(organisation: Organisation): void {
    this.organisations = this.organisations.filter(h => h !== organisation);
    this.organisationService.deleteOrganisation(organisation).subscribe();
  }
}
