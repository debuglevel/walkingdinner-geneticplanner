import { Component, OnInit } from '@angular/core';
import { Organisation } from '../organisation';
import { OrganisationService } from '../organisation.service';
 
@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: [ './dashboard.component.css' ]
})
export class DashboardComponent implements OnInit {
  organisations: Organisation[] = [];
 
  constructor(private organisationService: OrganisationService) { }
 
  ngOnInit() {
    this.getOrganisations();
  }
 
  getOrganisations(): void {
    this.organisationService.getOrganisations()
      .subscribe(organisations => this.organisations = organisations.slice(1, 4));
  }
}