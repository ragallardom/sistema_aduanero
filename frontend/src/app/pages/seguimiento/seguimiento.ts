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
  protected id = '';
  protected resultados: SolicitudViajeMenor[] = [];
  protected errorMsg = '';

  constructor(private servicio: SolicitudAduanaService) {}

  protected buscar(): void {
    this.errorMsg = '';
    const idNum = parseInt(this.id, 10);
    if (isNaN(idNum) || idNum <= 0) {
      this.resultados = [];
      this.errorMsg = 'Ingresa un ID válido.';
      return;
    }
    this.servicio.obtenerPorId(idNum).subscribe({
      next: (data) => {
        this.resultados = [data];
      },
      error: (err) => {
        this.resultados = [];
        if (err.status === 404) {
          this.errorMsg = 'No existe una solicitud con el ID ingresado.';
        } else {
          this.errorMsg = 'No se pudo obtener la información';
        }
      },
    });
  }
}
