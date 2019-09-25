import { Injectable } from "@angular/core";

@Injectable({ providedIn: "root" })
export class ConfigurationService {
  private host = "http://localhost:8080";

  getUri(uri: string): string {
    return this.host + uri;
  }
}
