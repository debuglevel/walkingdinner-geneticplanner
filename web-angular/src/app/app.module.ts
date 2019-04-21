import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { OrganisationsComponent } from './organisations/organisations.component';
import { OrganisationDetailComponent } from './organisation-detail/organisation-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    OrganisationsComponent,
    OrganisationDetailComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
