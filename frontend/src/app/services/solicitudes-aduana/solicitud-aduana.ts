// src/app/services/solicitudes-aduana/solicitud-aduana.service.ts

import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SolicitudViajeMenor } from '../../models/solicitud-viaje-menor';

@Injectable({ providedIn: 'root' })
export class SolicitudAduanaService {

  /**
   * Base URL para las solicitudes de la API.
   *
   * Se deja relativa para aprovechar el proxy de desarrollo
   * (proxy.conf.json) y evitar problemas de CORS en entornos
   * locales. Cuando se despliegue a producción puede apuntar a la
   * URL absoluta correspondiente.
   */
  private readonly baseUrl = '/api/solicitudes';

  constructor(private http: HttpClient) {}

  /**
   * Envía la solicitud utilizando `multipart/form-data`.
   *
   * Algunos backends no aceptan `application/json` para la carga de archivos,
   * por lo que adjuntamos todos los campos dentro de una instancia de
   * `FormData`, incluyendo el archivo si se proporciona.
   */
  crearConAdjunto(
    data: Omit<
      SolicitudViajeMenor,
      'id' | 'estado' | 'fechaCreacion' | 'documentos'
    >,
    tiposDocumento: string[] = [],
    archivos: File[] = []
  ): Observable<SolicitudViajeMenor> {
    const formData = new FormData();
    Object.entries(data).forEach(([key, value]) => {
      if (value !== undefined && value !== null) {
        formData.append(key, value as string);
      }
    });
    tiposDocumento.forEach((t) => formData.append('tiposDocumento', t));
    archivos.forEach((a) => formData.append('archivos', a));

    return this.http.post<SolicitudViajeMenor>(this.baseUrl, formData);
  }

  obtenerPorRutMenor(rut: string): Observable<SolicitudViajeMenor[]> {
    const params = new HttpParams().set('rut', rut);
    return this.http.get<SolicitudViajeMenor[]>(`${this.baseUrl}/menor`, { params });
  }

  obtenerPorId(id: number): Observable<SolicitudViajeMenor> {
    return this.http.get<SolicitudViajeMenor>(`${this.baseUrl}/${id}`);
  }
}
