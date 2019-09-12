import { Component, OnInit } from "@angular/core";

import { Plan } from "../plan";
import { PlanService } from "../plan.service";
import { Base64Service } from "../base64.service";

@Component({
  selector: "app-plans",
  templateUrl: "./plans.component.html",
  styleUrls: ["./plans.component.css"],
})
export class PlansComponent implements OnInit {
  plans: Plan[];

  fileToUploadFile: File = null;
  fileToUploadBase64: String = null;
  handleFileInput(files: FileList) {
    this.fileToUploadFile = files.item(0);
    this.base64Service
      .getBase64(this.fileToUploadFile)
      .then(res => {
        //console.log("Converted file to Base64:", res);
        this.fileToUploadBase64 = res;
      })
      .catch(error => {
        console.log("ERROR:", error.message);
      });
  }

  constructor(
    private planService: PlanService,
    private base64Service: Base64Service
  ) {}

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

    const surveyfile = this.fileToUploadBase64;

    this.planService.addPlan({ name, surveyfile } as Plan).subscribe(plan => {
      //console.log("added plan:", plan);
      this.plans.push(plan);
    });
  }

  delete(plan: Plan): void {
    this.plans = this.plans.filter(h => h !== plan);
    this.planService.deletePlan(plan).subscribe();
  }
}
