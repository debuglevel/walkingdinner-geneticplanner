import { Injectable } from "@angular/core";
import { HttpClient, HttpHeaders } from "@angular/common/http";

import { Observable, of } from "rxjs";
import { catchError, map, tap } from "rxjs/operators";

import { Organisation } from "./organisation";
import { MessageService } from "./message.service";

const httpOptions = {
  headers: new HttpHeaders({ "Content-Type": "application/json" }),
};

@Injectable({ providedIn: "root" })
export class OrganisationService {
  private organisationsUrl = "api/organisations"; // URL to web api

  constructor(
    private http: HttpClient,
    private messageService: MessageService
  ) {}

  /** GET organisations from the server */
  getOrganisations(): Observable<Organisation[]> {
    return this.http.get<Organisation[]>(this.organisationsUrl).pipe(
      tap(_ => this.log("fetched organisations")),
      catchError(this.handleError<Organisation[]>("getOrganisations", []))
    );
  }

  /** GET organisation by id. Return `undefined` when id not found */
  getOrganisationNo404<Data>(id: number): Observable<Organisation> {
    const url = `${this.organisationsUrl}/?id=${id}`;
    return this.http.get<Organisation[]>(url).pipe(
      map(organisations => organisations[0]), // returns a {0|1} element array
      tap(h => {
        const outcome = h ? `fetched` : `did not find`;
        this.log(`${outcome} organisation id=${id}`);
      }),
      catchError(this.handleError<Organisation>(`getOrganisation id=${id}`))
    );
  }

  /** GET organisation by id. Will 404 if id not found */
  getOrganisation(id: number): Observable<Organisation> {
    const url = `${this.organisationsUrl}/${id}`;
    return this.http.get<Organisation>(url).pipe(
      tap(_ => this.log(`fetched organisation id=${id}`)),
      catchError(this.handleError<Organisation>(`getOrganisation id=${id}`))
    );
  }

  /* GET organisations whose name contains search term */
  searchOrganisations(term: string): Observable<Organisation[]> {
    if (!term.trim()) {
      // if not search term, return empty organisation array.
      return of([]);
    }
    return this.http
      .get<Organisation[]>(`${this.organisationsUrl}/?name=${term}`)
      .pipe(
        tap(_ => this.log(`searched organisations matching "${term}"`)),
        catchError(this.handleError<Organisation[]>("searchOrganisations", []))
      );
  }

  //////// Save methods //////////

  /** POST: add a new organisation to the server */
  addOrganisation(organisation: Organisation): Observable<Organisation> {
    return this.http
      .post<Organisation>(this.organisationsUrl, organisation, httpOptions)
      .pipe(
        tap((newOrganisation: Organisation) =>
          this.log(`added organisation w/ id=${newOrganisation.id}`)
        ),
        catchError(this.handleError<Organisation>("addOrganisation"))
      );
  }

  /** DELETE: delete the organisation from the server */
  deleteOrganisation(
    organisation: Organisation | number
  ): Observable<Organisation> {
    const id =
      typeof organisation === "number" ? organisation : organisation.id;
    const url = `${this.organisationsUrl}/${id}`;

    return this.http.delete<Organisation>(url, httpOptions).pipe(
      tap(_ => this.log(`deleted organisation id=${id}`)),
      catchError(this.handleError<Organisation>("deleteOrganisation"))
    );
  }

  /** PUT: update the organisation on the server */
  updateOrganisation(organisation: Organisation): Observable<any> {
    return this.http.put(this.organisationsUrl, organisation, httpOptions).pipe(
      tap(_ => this.log(`updated organisation id=${organisation.id}`)),
      catchError(this.handleError<any>("updateOrganisation"))
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

  /** Log a OrganisationService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`OrganisationService: ${message}`);
  }
}
