import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";

import { Observable, of } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";

import { Plan } from "./plan";
import { MessageService } from "./message.service";
import { SettingsService } from "./settings.service";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
};

@Injectable({ providedIn: "root" })
export class PlanService {
  private plansUrl = "/plans/"; // URL to web api

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private settingsService: SettingsService
  ) {
    this.plansUrl = settingsService.getUri(this.plansUrl);
  }

  /** GET plans from the server */
  getPlans(): Observable<Plan[]> {
    return this.http.get<Plan[]>(this.plansUrl).pipe(
      tap(_ => this.log("fetched plans")),
      catchError(this.handleError<Plan[]>("getPlans", []))
    );
  }

  /** GET plan by id. Return `undefined` when id not found */
  getPlanNo404<Data>(id: string): Observable<Plan> {
    const url = `${this.plansUrl}?id=${id}`;
    return this.http.get<Plan[]>(url).pipe(
      map(plans => plans[0]), // returns a {0|1} element array
      tap(h => {
        const outcome = h ? `fetched` : `did not find`;
        this.log(`${outcome} plan id=${id}`);
      }),
      catchError(this.handleError<Plan>(`getPlan id=${id}`))
    );
  }

  /** GET plan by id. Will 404 if id not found */
  getPlan(id: string): Observable<Plan> {
    const url = `${this.plansUrl}${id}`;
    return this.http.get<Plan>(url).pipe(
      tap(_ => this.log(`fetched plan id=${id}`)),
      catchError(this.handleError<Plan>(`getPlan id=${id}`))
    );
  }

  /* GET plans whose name contains search term */
  searchPlans(term: string): Observable<Plan[]> {
    if (!term.trim()) {
      // if not search term, return empty plan array.
      return of([]);
    }
    return this.http.get<Plan[]>(`${this.plansUrl}?name=${term}`).pipe(
      tap(_ => this.log(`searched plans matching "${term}"`)),
      catchError(this.handleError<Plan[]>("searchPlans", []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new plan to the server */
  addPlan(plan: Plan): Observable<Plan> {
    return this.http.post<Plan>(this.plansUrl, plan, httpOptions).pipe(
      tap((newPlan: Plan) => this.log(`added plan w/ id=${newPlan.id}`)),
      catchError(this.handleError<Plan>("addPlan"))
    );
  }

  /** DELETE: delete the plan from the server */
  deletePlan(plan: Plan | string): Observable<Plan> {
    const id = typeof plan === "string" ? plan : plan.id;
    const url = `${this.plansUrl}${id}`;

    return this.http.delete<Plan>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted plan id=${id}`)),
      catchError(this.handleError<Plan>("deletePlan"))
    );
  }

  /** PUT: update the plan on the server */
  updatePlan(plan: Plan): Observable<any> {
    return this.http.put(this.plansUrl, plan, httpOptions).pipe(
      tap(_ => this.log(`updated plan id=${plan.id}`)),
      catchError(this.handleError<any>("updatePlan"))
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

  /** Log a PlanService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`PlanService: ${message}`);
  }
}
