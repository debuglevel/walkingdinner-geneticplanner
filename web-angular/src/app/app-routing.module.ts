import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

import { DashboardComponent } from "./dashboard/dashboard.component";
import { OrganisationsComponent } from "./organisations/organisations.component";
import { OrganisationDetailComponent } from "./organisation-detail/organisation-detail.component";
import { DinnersComponent } from "./dinners/dinners.component";
import { DinnerDetailComponent } from "./dinner-detail/dinner-detail.component";
import { TeamsComponent } from "./teams/teams.component";
import { TeamDetailComponent } from "./team-detail/team-detail.component";

const routes: Routes = [
  { path: "", redirectTo: "/dashboard", pathMatch: "full" },
  {
    path: "dashboard",
    component: DashboardComponent,
    data: { title: "Dashboard" },
  },
  {
    path: "organisations/:id",
    component: OrganisationDetailComponent,
    data: { title: "Organisationen" },
  },
  {
    path: "organisations",
    component: OrganisationsComponent,
    data: { title: "Organisation" },
  },
  { path: "dinners", component: DinnersComponent, data: { title: "Dinners" } },
  {
    path: "dinners/:id",
    component: DinnerDetailComponent,
    data: { title: "Dinner" },
  },
  { path: "teams", component: TeamsComponent, data: { title: "Teams" } },
  {
    path: "teams/:id",
    component: TeamDetailComponent,
    data: { title: "Team" },
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
