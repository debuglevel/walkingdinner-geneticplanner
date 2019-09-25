import { Component, OnInit, Input } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Location } from "@angular/common";

import { Calculation } from "../calculation";
import { CalculationService } from "../calculation.service";

@Component({
  selector: "app-calculation-detail",
  templateUrl: "./calculation-detail.component.html",
  styleUrls: ["./calculation-detail.component.css"],
})
export class CalculationDetailComponent implements OnInit {
  @Input() calculation: Calculation;

  constructor(
    private route: ActivatedRoute,
    private calculationService: CalculationService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getCalculation();
  }

  getCalculation(): void {
    const id = this.route.snapshot.paramMap.get("id");
    this.calculationService
      .getCalculation(id)
      .subscribe(calculation => (this.calculation = calculation));
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.calculationService
      .updateCalculation(this.calculation)
      .subscribe(() => this.goBack());
  }
}
