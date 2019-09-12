import { Injectable } from "@angular/core";

@Injectable({ providedIn: "root" })
export class Base64Service {
  // see: https://stackoverflow.com/a/52311051/4764279
  getBase64(file: File): Promise<String> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => {
        let encoded = reader.result.toString().replace(/^data:(.*,)?/, "");
        if (encoded.length % 4 > 0) {
          encoded += "=".repeat(4 - (encoded.length % 4));
        }
        resolve(encoded);
      };
      reader.onerror = error => reject(error);
    });
  }
}
