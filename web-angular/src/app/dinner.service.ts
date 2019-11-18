import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";

import { Observable, of } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";

import { Dinner } from "./dinner";
import { MessageService } from "./message.service";
import { SettingsService } from "./settings.service";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
};

@Injectable({ providedIn: "root" })
export class DinnerService {
  private dinnersUrl = "/dinners/"; // URL to web api

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private settingsService: SettingsService
  ) {
    this.dinnersUrl = settingsService.getUri(this.dinnersUrl);
  }

  /** GET dinners from the server */
  getDinners(): Observable<Dinner[]> {
    return this.http.get<Dinner[]>(this.dinnersUrl).pipe(
      tap(_ => this.log("fetched dinners")),
      catchError(this.handleError<Dinner[]>("getDinners", []))
    );
  }

  /** GET dinner by id. Return `undefined` when id not found */
  getDinnerNo404<Data>(id: string): Observable<Dinner> {
    const url = `${this.dinnersUrl}?id=${id}`;
    return this.http.get<Dinner[]>(url).pipe(
      map(dinners => dinners[0]), // returns a {0|1} element array
      tap(h => {
        const outcome = h ? `fetched` : `did not find`;
        this.log(`${outcome} dinner id=${id}`);
      }),
      catchError(this.handleError<Dinner>(`getDinner id=${id}`))
    );
  }

  /** GET dinner by id. Will 404 if id not found */
  getDinner(id: string): Observable<Dinner> {
    const url = `${this.dinnersUrl}${id}`;
    return this.http.get<Dinner>(url).pipe(
      tap(_ => this.log(`fetched dinner id=${id}`)),
      catchError(this.handleError<Dinner>(`getDinner id=${id}`))
    );
  }

  /* GET dinners whose name contains search term */
  searchDinners(term: string): Observable<Dinner[]> {
    if (!term.trim()) {
      // if not search term, return empty dinner array.
      return of([]);
    }
    return this.http.get<Dinner[]>(`${this.dinnersUrl}?name=${term}`).pipe(
      tap(_ => this.log(`searched dinners matching "${term}"`)),
      catchError(this.handleError<Dinner[]>("searchDinners", []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new dinner to the server */
  addDinner(dinner: Dinner): Observable<Dinner> {
    return this.http.post<Dinner>(this.dinnersUrl, dinner, httpOptions).pipe(
      tap((newDinner: Dinner) =>
        this.log(`added dinner w/ id=${newDinner.id}`)
      ),
      catchError(this.handleError<Dinner>("addDinner"))
    );
  }

  /** DELETE: delete the dinner from the server */
  deleteDinner(dinner: Dinner | string): Observable<Dinner> {
    const id = typeof dinner === "string" ? dinner : dinner.id;
    const url = `${this.dinnersUrl}${id}`;

    return this.http.delete<Dinner>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted dinner id=${id}`)),
      catchError(this.handleError<Dinner>("deleteDinner"))
    );
  }

  /** PUT: update the dinner on the server */
  updateDinner(dinner: Dinner): Observable<any> {
    return this.http.put(this.dinnersUrl, dinner, httpOptions).pipe(
      tap(_ => this.log(`updated dinner id=${dinner.id}`)),
      catchError(this.handleError<any>("updateDinner"))
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

  /** Log a DinnerService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`DinnerService: ${message}`);
  }
}
