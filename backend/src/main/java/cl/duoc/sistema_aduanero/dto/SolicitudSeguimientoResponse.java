package cl.duoc.sistema_aduanero.dto;

import java.time.LocalDateTime;

public class SolicitudSeguimientoResponse {
  private Long id;
  private LocalDateTime fechaCreacion;
  private String numeroDocumentoMenor;
  private String estado;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public LocalDateTime getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(LocalDateTime fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getNumeroDocumentoMenor() {
    return numeroDocumentoMenor;
  }

  public void setNumeroDocumentoMenor(String numeroDocumentoMenor) {
    this.numeroDocumentoMenor = numeroDocumentoMenor;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }
}
