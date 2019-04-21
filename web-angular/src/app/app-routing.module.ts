import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { OrganisationsComponent }      from './organisations/organisations.component';
import { DashboardComponent }   from './dashboard/dashboard.component';
import { OrganisationDetailComponent }      from './organisation-detail/organisation-detail.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'organisations', component: OrganisationsComponent },
  { path: 'detail/:id', component: OrganisationDetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
