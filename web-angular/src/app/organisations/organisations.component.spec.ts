import { async, ComponentFixture, TestBed } from "@angular/core/testing";
import { RouterTestingModule } from "@angular/router/testing";
import { OrganisationsComponent } from "./organisations.component";
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe("OrganisationsComponent", () => {
  let component: OrganisationsComponent;
  let fixture: ComponentFixture<OrganisationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [OrganisationsComponent],
      imports: [RouterTestingModule.withRoutes([]), HttpClientTestingModule],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OrganisationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it("should be created", () => {
    expect(component).toBeTruthy();
  });
});
