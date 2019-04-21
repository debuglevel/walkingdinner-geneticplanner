import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

import { DashboardComponent } from "./dashboard/dashboard.component";
import { OrganisationsComponent } from "./organisations/organisations.component";
import { OrganisationDetailComponent } from "./organisation-detail/organisation-detail.component";
import { DinnersComponent } from "./dinners/dinners.component";
import { DinnerDetailComponent } from "./dinner-detail/dinner-detail.component";

const routes: Routes = [
  { path: "", redirectTo: "/dashboard", pathMatch: "full" },
  { path: "dashboard", component: DashboardComponent },
  { path: "organisations/:id", component: OrganisationDetailComponent },
  { path: "organisations", component: OrganisationsComponent },
  { path: "dinners", component: DinnersComponent },
  { path: "dinners/:id", component: DinnerDetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
