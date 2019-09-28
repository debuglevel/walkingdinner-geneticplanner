import { Settings } from './settings';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class SettingsService {
    public settings: Settings;

    constructor() {
        this.settings = new Settings();
    }

      getUri(uri: string): string {
        return this.settings.apiUrl + uri;
      }
}
