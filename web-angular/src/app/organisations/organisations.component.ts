import { Component, OnInit } from '@angular/core';
import { Organisation } from '../organisation';
import { OrganisationService } from '../organisation.service';

@Component({
  selector: 'app-organisations',
  templateUrl: './organisations.component.html',
  styleUrls: ['./organisations.component.css']
})
export class OrganisationsComponent implements OnInit {

  organisations: Organisation[]

  constructor(private organisationService: OrganisationService) { }

  ngOnInit() {
    this.getOrganisations();
  }

  getOrganisations(): void {
    this.organisationService.getOrganisations()
      .subscribe(organisations => this.organisations = organisations)
  }

}
