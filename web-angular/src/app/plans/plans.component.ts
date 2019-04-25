import { Component, OnInit } from "@angular/core";

import { Plan } from "../plan";
import { PlanService } from "../plan.service";

@Component({
  selector: "app-plans",
  templateUrl: "./plans.component.html",
  styleUrls: ["./plans.component.css"],
})
export class PlansComponent implements OnInit {
  plans: Plan[];

  constructor(private planService: PlanService) {}

  ngOnInit() {
    this.getPlans();
  }

  getPlans(): void {
    this.planService.getPlans().subscribe(plans => (this.plans = plans));
  }

  add(name: string): void {
    name = name.trim();
    if (!name) {
      return;
    }
    this.planService.addPlan({ name } as Plan).subscribe(plan => {
      this.plans.push(plan);
    });
  }

  delete(plan: Plan): void {
    this.plans = this.plans.filter(h => h !== plan);
    this.planService.deletePlan(plan).subscribe();
  }
}
