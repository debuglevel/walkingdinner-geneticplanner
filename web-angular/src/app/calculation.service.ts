import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";

import { Observable, of } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";

import { Calculation } from "./calculation";
import { MessageService } from "./message.service";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
};

@Injectable({ providedIn: "root" })
export class CalculationService {
  private calculationsUrl = "api/calculations"; // URL to web api

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) {}

  /** GET calculations from the server */
  getCalculations(): Observable<Calculation[]> {
    return this.http.get<Calculation[]>(this.calculationsUrl).pipe(
      tap(_ => this.log("fetched calculations")),
      catchError(this.handleError<Calculation[]>("getCalculations", []))
    );
  }

  /** GET calculation by id. Return `undefined` when id not found */
  getCalculationNo404<Data>(id: string): Observable<Calculation> {
    const url = `${this.calculationsUrl}/?id=${id}`;
    return this.http.get<Calculation[]>(url).pipe(
      map(calculations => calculations[0]), // returns a {0|1} element array
      tap(h => {
        const outcome = h ? `fetched` : `did not find`;
        this.log(`${outcome} calculation id=${id}`);
      }),
      catchError(this.handleError<Calculation>(`getCalculation id=${id}`))
    );
  }

  /** GET calculation by id. Will 404 if id not found */
  getCalculation(id: string): Observable<Calculation> {
    const url = `${this.calculationsUrl}/${id}`;
    return this.http.get<Calculation>(url).pipe(
      tap(_ => this.log(`fetched calculation id=${id}`)),
      catchError(this.handleError<Calculation>(`getCalculation id=${id}`))
    );
  }

  /* GET calculations whose name contains search term */
  searchCalculations(term: string): Observable<Calculation[]> {
    if (!term.trim()) {
      // if not search term, return empty calculation array.
      return of([]);
    }
    return this.http
      .get<Calculation[]>(`${this.calculationsUrl}/?name=${term}`)
      .pipe(
        tap(_ => this.log(`searched calculations matching "${term}"`)),
        catchError(this.handleError<Calculation[]>("searchCalculations", []))
      );
  }

  //////// Save methods //////////

  /** POST: add a new calculation to the server */
  addCalculation(calculation: Calculation): Observable<Calculation> {
    return this.http
      .post<Calculation>(this.calculationsUrl, calculation, httpOptions)
      .pipe(
        tap((newCalculation: Calculation) =>
          this.log(`added calculation w/ id=${newCalculation.id}`)
        ),
        catchError(this.handleError<Calculation>("addCalculation"))
      );
  }

  /** DELETE: delete the calculation from the server */
  deleteCalculation(
    calculation: Calculation | string
  ): Observable<Calculation> {
    const id = typeof calculation === "string" ? calculation : calculation.id;
    const url = `${this.calculationsUrl}/${id}`;

    return this.http.delete<Calculation>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted calculation id=${id}`)),
      catchError(this.handleError<Calculation>("deleteCalculation"))
    );
  }

  /** PUT: update the calculation on the server */
  updateCalculation(calculation: Calculation): Observable<any> {
    return this.http.put(this.calculationsUrl, calculation, httpOptions).pipe(
      tap(_ => this.log(`updated calculation id=${calculation.id}`)),
      catchError(this.handleError<any>("updateCalculation"))
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

  /** Log a CalculationService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`CalculationService: ${message}`);
  }
}
