import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { DashboardComponent } from "./dashboard.component";
import { OrganisationSearchComponent } from "../organisation-search/organisation-search.component";

import { RouterTestingModule } from "@angular/router/testing";
import { of } from "rxjs";
import { HEROES } from "../mock-organisations";
import { OrganisationService } from "../organisation.service";

describe("DashboardComponent", () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;
  let organisationService;
  let getOrganisationsSpy;

  beforeEach(async(() => {
    organisationService = jasmine.createSpyObj("OrganisationService", [
      "getOrganisations",
    ]);
    getOrganisationsSpy = organisationService.getOrganisations.and.returnValue(
      of(HEROES)
    );
    TestBed.configureTestingModule({
      declarations: [DashboardComponent, OrganisationSearchComponent],
      imports: [RouterTestingModule.withRoutes([])],
      providers: [
        { provide: OrganisationService, useValue: organisationService },
      ],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should be created", () => {
    expect(component).toBeTruthy();
  });

  it('should display "Top Organisations" as headline', () => {
    expect(fixture.nativeElement.querySelector("h3").textContent).toEqual(
      "Top Organisations"
    );
  });

  it("should call organisationService", async(() => {
    expect(getOrganisationsSpy.calls.any()).toBe(true);
  }));

  it("should display 4 links", async(() => {
    expect(fixture.nativeElement.querySelectorAll("a").length).toEqual(4);
  }));
});
