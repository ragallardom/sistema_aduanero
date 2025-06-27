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

  protected formatearRut(event: Event): void {
    const input = event.target as HTMLInputElement;
    let value = input.value.replace(/[^0-9kK]/g, '').toUpperCase();
    if (value.length > 1) {
      const cuerpo = value.slice(0, -1);
      const dv = value.slice(-1);
      let formatted = '';
      for (let i = cuerpo.length - 1, j = 1; i >= 0; i--, j++) {
        formatted = cuerpo.charAt(i) + formatted;
        if (j % 3 === 0 && i !== 0) {
          formatted = '.' + formatted;
        }
      }
      formatted += '-' + dv;
      input.value = formatted;
    } else {
      input.value = value;
    }
    this.rut = input.value;
  }

  protected buscar(): void {
    this.errorMsg = '';
    if (!esRutValido(this.rut)) {
      this.resultados = [];
      this.errorMsg = 'Ingresa un RUT válido.';
      return;
    }
    this.servicio.obtenerPorRutMenor(this.rut).subscribe({
      next: (data) => {
        this.resultados = data;
        if (!data.length) {
          this.errorMsg = 'No existen solicitudes para el RUT ingresado.';
        }
      },
      error: () => (this.errorMsg = 'No se pudo obtener la información'),
    });
  }
}

function esRutValido(value: string): boolean {
  if (!value) {
    return false;
  }
  const clean = value.replace(/\./g, '');
  const rutPattern = /^[0-9]+-[0-9kK]{1}$/;
  if (!rutPattern.test(clean)) {
    return false;
  }
  const [num, dv] = clean.split('-');
  let sum = 0;
  let multiplier = 2;
  for (let i = num.length - 1; i >= 0; i--) {
    sum += parseInt(num.charAt(i), 10) * multiplier;
    multiplier = multiplier === 7 ? 2 : multiplier + 1;
  }
  const expected = 11 - (sum % 11);
  const dvCalc = expected === 11 ? '0' : expected === 10 ? 'K' : expected.toString();
  return dvCalc.toUpperCase() === dv.toUpperCase();
}
