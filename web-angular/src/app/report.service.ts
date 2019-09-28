import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";

import { Observable, of } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";

import { Report } from "./report";
import { MessageService } from "./message.service";
import { SettingsService } from "./settings.service";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
};

@Injectable({ providedIn: "root" })
export class ReportService {
  private reportsUrl = "/plans/reports/"; // URL to web api

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private settingsService: SettingsService
  ) {
    this.reportsUrl = settingsService.getUri(this.reportsUrl);
  }

  getSummaryReportUrl(planId: string): string {
    const url = `${this.reportsUrl}summary/${planId}`;
    return url;
  }

  getMailFileReportUrl(planId: string): string {
    const url = `${this.reportsUrl}mails/${planId}`;
    return url;
  }

  getGmailDraftsReportUrl(planId: string): string {
    const url = `${this.reportsUrl}gmail/${planId}`;
    return url;
  }

  postGmailDraftsReport(planId: string): Observable<string> {
    const url = this.getGmailDraftsReportUrl(planId);
    return this.http.post<string>(url, null, httpOptions).pipe(
      tap((newReport: string) => this.log(`posted gmail drafts report w/`)),
      catchError(this.handleError<string>("postGmailDraftsReport"))
    );
  }

  /** GET summary report by plan id. Will 404 if id not found */
  getSummaryReport(planId: string): Observable<string> {
    const url = `${this.reportsUrl}${planId}`;
    return this.http.get<string>(url).pipe(
      tap(_ => this.log(`fetched summary report planId=${planId}`)),
      catchError(this.handleError<string>(`getSummaryReport planId=${planId}`))
    );
  }

  /** */

  /** GET reports from the server */
  getReports(): Observable<Report[]> {
    return this.http.get<Report[]>(this.reportsUrl).pipe(
      tap(_ => this.log("fetched reports")),
      catchError(this.handleError<Report[]>("getReports", []))
    );
  }

  /** GET report by id. Return `undefined` when id not found */
  getReportNo404<Data>(id: string): Observable<Report> {
    const url = `${this.reportsUrl}?id=${id}`;
    return this.http.get<Report[]>(url).pipe(
      map(reports => reports[0]), // returns a {0|1} element array
      tap(h => {
        const outcome = h ? `fetched` : `did not find`;
        this.log(`${outcome} report id=${id}`);
      }),
      catchError(this.handleError<Report>(`getReport id=${id}`))
    );
  }

  /** GET report by id. Will 404 if id not found */
  getReport(id: string): Observable<Report> {
    const url = `${this.reportsUrl}${id}`;
    return this.http.get<Report>(url).pipe(
      tap(_ => this.log(`fetched report id=${id}`)),
      catchError(this.handleError<Report>(`getReport id=${id}`))
    );
  }

  /* GET reports whose name contains search term */
  searchReports(term: string): Observable<Report[]> {
    if (!term.trim()) {
      // if not search term, return empty report array.
      return of([]);
    }
    return this.http.get<Report[]>(`${this.reportsUrl}?name=${term}`).pipe(
      tap(_ => this.log(`searched reports matching "${term}"`)),
      catchError(this.handleError<Report[]>("searchReports", []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new report to the server */
  addReport(report: Report): Observable<Report> {
    return this.http.post<Report>(this.reportsUrl, report, httpOptions).pipe(
      tap((newReport: Report) =>
        this.log(`added report w/ id=${newReport.id}`)
      ),
      catchError(this.handleError<Report>("addReport"))
    );
  }

  /** DELETE: delete the report from the server */
  deleteReport(report: Report | string): Observable<Report> {
    const id = typeof report === "string" ? report : report.id;
    const url = `${this.reportsUrl}${id}`;

    return this.http.delete<Report>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted report id=${id}`)),
      catchError(this.handleError<Report>("deleteReport"))
    );
  }

  /** PUT: update the report on the server */
  updateReport(report: Report): Observable<any> {
    return this.http.put(this.reportsUrl, report, httpOptions).pipe(
      tap(_ => this.log(`updated report id=${report.id}`)),
      catchError(this.handleError<any>("updateReport"))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = "operation", result?: T) {
    return (error: any): Observable<T> => {
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a ReportService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`ReportService: ${message}`);
  }
}
