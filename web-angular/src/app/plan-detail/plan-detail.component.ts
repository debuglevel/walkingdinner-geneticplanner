import { Component, OnInit, Input } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { Location } from "@angular/common";

import { Plan } from "../plan";
import { PlanService } from "../plan.service";
import { ReportService } from "../report.service";

@Component({
  selector: "app-plan-detail",
  templateUrl: "./plan-detail.component.html",
  styleUrls: ["./plan-detail.component.css"],
})
export class PlanDetailComponent implements OnInit {
  @Input() plan: Plan;

  constructor(
    private route: ActivatedRoute,
    private planService: PlanService,
    private reportService: ReportService,
    private location: Location
  ) {}

  ngOnInit(): void {
    this.getPlan();
  }

  getPlan(): void {
    const id = this.route.snapshot.paramMap.get("id");
    this.planService.getPlan(id).subscribe(plan => (this.plan = plan));
  }

  gotoSummaryReport(): void {
    const url = this.reportService.getSummaryReportUrl(this.plan.id);
    window.location.href = url;
  }

  gotoMailFileReport(): void {
    const url = this.reportService.getMailFileReportUrl(this.plan.id);
    window.location.href = url;
  }

  createGmailDraftsReport(): void {
    console.info("createGmailDraftsReport " + this.plan.id);
    this.reportService
      .postGmailDraftsReport(this.plan.id)
      .subscribe(report => {});
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.planService.updatePlan(this.plan).subscribe(() => this.goBack());
  }
}
