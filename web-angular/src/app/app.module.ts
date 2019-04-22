import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule } from "@angular/forms";
import { HttpClientModule } from "@angular/common/http";

// Material
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import {
  MatButtonModule,
  MatCheckboxModule,
  MatIconModule,
  MatSidenavModule,
  MatToolbarModule,
  MatTooltipModule,
} from "@angular/material";
import { MatInputModule } from "@angular/material/input";
import { MatDividerModule } from "@angular/material/divider";
import { MatListModule } from "@angular/material/list";
import { MatCardModule } from "@angular/material/card";

import { HttpClientInMemoryWebApiModule } from "angular-in-memory-web-api";
import { InMemoryDataService } from "./in-memory-data.service";

import { AppRoutingModule } from "./app-routing.module";

import { AppComponent } from "./app.component";
import { DashboardComponent } from "./dashboard/dashboard.component";
import { OrganisationDetailComponent } from "./organisation-detail/organisation-detail.component";
import { OrganisationsComponent } from "./organisations/organisations.component";
import { OrganisationSearchComponent } from "./organisation-search/organisation-search.component";
import { MessagesComponent } from "./messages/messages.component";
import { DinnersComponent } from "./dinners/dinners.component";
import { DinnerDetailComponent } from "./dinner-detail/dinner-detail.component";
import { TeamsComponent } from "./teams/teams.component";
import { TeamDetailComponent } from "./team-detail/team-detail.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,

    // Material
    BrowserAnimationsModule,
    MatButtonModule,
    MatCheckboxModule,
    MatInputModule,
    MatDividerModule,
    MatListModule,
    MatCardModule,
    MatIconModule,
    MatSidenavModule,
    MatToolbarModule,
    MatTooltipModule,

    // The HttpClientInMemoryWebApiModule module intercepts HTTP requests
    // and returns simulated server responses.
    // Remove it when a real server is ready to receive requests.
    HttpClientInMemoryWebApiModule.forRoot(InMemoryDataService, {
      dataEncapsulation: false,
    }),
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    OrganisationsComponent,
    OrganisationDetailComponent,
    MessagesComponent,
    OrganisationSearchComponent,
    DinnersComponent,
    DinnerDetailComponent,
    TeamsComponent,
    TeamDetailComponent,
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}