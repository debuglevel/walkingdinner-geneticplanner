import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

import { OrganisationService }  from '../organisation.service';
import { Organisation } from '../organisation';

@Component({
  selector: 'app-organisation-detail',
  templateUrl: './organisation-detail.component.html',
  styleUrls: ['./organisation-detail.component.css']
})
export class OrganisationDetailComponent implements OnInit {

  //@Input() organisation: Organisation;
  organisation: Organisation;

  constructor(
    private route: ActivatedRoute,
    private organisationService: OrganisationService,
    private location: Location
  ) { }

  ngOnInit() {
    this.getOrganisation();
  }

  getOrganisation(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.organisationService.getOrganisation(id)
      .subscribe(organisation => this.organisation = organisation);
  }

  save(): void {
    this.organisationService.updateOrganisation(this.organisation)
  }
}
