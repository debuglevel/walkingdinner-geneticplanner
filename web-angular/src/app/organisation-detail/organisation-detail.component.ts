import { Component, OnInit, Input } from '@angular/core';
import { Organisation } from '../organisation';

@Component({
  selector: 'app-organisation-detail',
  templateUrl: './organisation-detail.component.html',
  styleUrls: ['./organisation-detail.component.css']
})
export class OrganisationDetailComponent implements OnInit {

  @Input() organisation: Organisation;

  constructor() { }

  ngOnInit() {
  }

}
