import { Component, OnInit, Input } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Location } from "@angular/common";

import { Organisation } from "../organisation";
import { OrganisationService } from "../organisation.service";

@Component({
  selector: "app-organisation-detail",
  templateUrl: "./organisation-detail.component.html",
  styleUrls: ["./organisation-detail.component.css"],
})
export class OrganisationDetailComponent implements OnInit {
  @Input() organisation: Organisation;

  constructor(
    private route: ActivatedRoute,
    private organisationService: OrganisationService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getOrganisation();
  }

  getOrganisation(): void {
    const id = this.route.snapshot.paramMap.get("id");
    this.organisationService
      .getOrganisation(id)
      .subscribe(organisation => (this.organisation = organisation));
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.organisationService
      .updateOrganisation(this.organisation)
      .subscribe(() => this.goBack());
  }
}
