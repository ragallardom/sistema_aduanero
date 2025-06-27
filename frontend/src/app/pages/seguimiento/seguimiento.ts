import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { SolicitudAduanaService } from '../../services/solicitudes-aduana/solicitud-aduana';
import { SolicitudViajeMenor } from '../../models/solicitud-viaje-menor';

@Component({
  selector: 'app-seguimiento',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './seguimiento.html',
  styleUrls: ['./seguimiento.scss'],
})
export class SeguimientoComponent {
  protected rut = '';
  protected resultados: SolicitudViajeMenor[] = [];
  protected errorMsg = '';

  constructor(private servicio: SolicitudAduanaService) {}

  protected buscar(): void {
    this.errorMsg = '';
    this.servicio.obtenerPorRutMenor(this.rut).subscribe({
      next: (data) => (this.resultados = data),
      error: () => (this.errorMsg = 'No se pudo obtener la información'),
    });
  }
}
