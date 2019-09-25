import { Component, OnInit } from "@angular/core";

import { Calculation } from "../calculation";
import { CalculationService } from "../calculation.service";
import { Base64Service } from "../base64.service";

@Component({
  selector: "app-calculations",
  templateUrl: "./calculations.component.html",
  styleUrls: ["./calculations.component.css"],
})
export class CalculationsComponent implements OnInit {
  calculations: Calculation[];

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
    private calculationService: CalculationService,
    private base64Service: Base64Service
  ) {}

  ngOnInit() {
    this.getCalculations();
  }

  getCalculations(): void {
    this.calculationService
      .getCalculations()
      .subscribe(calculations => (this.calculations = calculations));
  }

  add(): void {
    const surveyfile = this.fileToUploadBase64;

    // TODO: do not hardcode these values (especially the location :-))
    this.calculationService
      .addCalculation({
        finished: false,
        surveyfile,
        populationsSize: 200,
        fitnessThreshold: 0.001,
        steadyFitness: 100,
        location: "Bamberg",
      } as Calculation)
      .subscribe(calculation => {
        this.calculations.push(calculation);
      });
  }

  delete(calculation: Calculation): void {
    this.calculations = this.calculations.filter(h => h !== calculation);
    this.calculationService.deleteCalculation(calculation).subscribe();
  }
}
