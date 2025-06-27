package cl.duoc.aduanas_app.model;

import java.time.LocalDate;

public class SolicitudViajeMenoresResponse {
    private Long id;
    private String nombreMenor;
    private String numeroDocumentoMenor;
    private String documentoPadre;
    private String emailPadre;
    private String telefonoPadre;
    private String paisOrigen;
    private String paisDestino;
    private LocalDate fechaNacimientoMenor;
    private LocalDate fechaViaje;
    private LocalDate fechaCreacion;
    private String estado;
    private String motivoViaje;
    private String nacionalidadMenor;
    private String tipoDocumentoMenor;

    // Getters
    public Long getId() { return id; }
    public String getNombreMenor() { return nombreMenor; }
    public String getNumeroDocumentoMenor() { return numeroDocumentoMenor; }
    public String getDocumentoPadre() { return documentoPadre; }
    public String getEmailPadre() { return emailPadre; }
    public String getTelefonoPadre() { return telefonoPadre; }
    public String getPaisOrigen() { return paisOrigen; }
    public String getPaisDestino() { return paisDestino; }
    public LocalDate getFechaNacimientoMenor() { return fechaNacimientoMenor; }
    public LocalDate getFechaViaje() { return fechaViaje; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public String getEstado() { return estado; }
    public String getMotivoViaje() { return motivoViaje; }
    public String getNacionalidadMenor() { return nacionalidadMenor; }
    public String getTipoDocumentoMenor() { return tipoDocumentoMenor; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNombreMenor(String nombreMenor) { this.nombreMenor = nombreMenor; }
    public void setNumeroDocumentoMenor(String numeroDocumentoMenor) { this.numeroDocumentoMenor = numeroDocumentoMenor; }
    public void setDocumentoPadre(String documentoPadre) { this.documentoPadre = documentoPadre; }
    public void setEmailPadre(String emailPadre) { this.emailPadre = emailPadre; }
    public void setTelefonoPadre(String telefonoPadre) { this.telefonoPadre = telefonoPadre; }
    public void setPaisOrigen(String paisOrigen) { this.paisOrigen = paisOrigen; }
    public void setPaisDestino(String paisDestino) { this.paisDestino = paisDestino; }
    public void setFechaNacimientoMenor(LocalDate fechaNacimientoMenor) { this.fechaNacimientoMenor = fechaNacimientoMenor; }
    public void setFechaViaje(LocalDate fechaViaje) { this.fechaViaje = fechaViaje; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public void setEstado(String estado) { this.estado = estado; }
    public void setMotivoViaje(String motivoViaje) { this.motivoViaje = motivoViaje; }
    public void setNacionalidadMenor(String nacionalidadMenor) { this.nacionalidadMenor = nacionalidadMenor; }
    public void setTipoDocumentoMenor(String tipoDocumentoMenor) {this.tipoDocumentoMenor = tipoDocumentoMenor; }
}