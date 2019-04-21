import { Component, OnInit, Input } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Location } from "@angular/common";

import { Dinner } from "../dinner";
import { DinnerService } from "../dinner.service";

@Component({
  selector: "app-dinner-detail",
  templateUrl: "./dinner-detail.component.html",
  styleUrls: ["./dinner-detail.component.css"],
})
export class DinnerDetailComponent implements OnInit {
  @Input() dinner: Dinner;

  constructor(
    private route: ActivatedRoute,
    private dinnerService: DinnerService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getDinner();
  }

  getDinner(): void {
    const id = +this.route.snapshot.paramMap.get("id");
    this.dinnerService
      .getDinner(id)
      .subscribe(dinner => (this.dinner = dinner));
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.dinnerService.updateDinner(this.dinner).subscribe(() => this.goBack());
  }
}
