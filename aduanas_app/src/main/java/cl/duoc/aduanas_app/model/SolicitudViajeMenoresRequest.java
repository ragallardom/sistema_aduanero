// SolicitudViajeMenoresRequest.java
package cl.duoc.aduanas_app.model;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolicitudViajeMenoresRequest {
    private Long id;
    private String nombreMenor;
    private String apellidoMenor;
    private LocalDate fechaNacimientoMenor;
    private String sexo;
    private String nacionalidadMenor;
    private String numeroDocumentoMenor;
    private String tipoDocumentoPadre;

    private String tipoDocumentoMenor;

    private String nombrePadreMadre;
    private String documentoPadre;
    private String emailPadre;
    private String telefonoPadre;
    private String relacionMenor;

    private LocalDate fechaViaje;
    private String motivoViaje;
    private String tipoSolicitudMenor;
    private String numeroTransporte;
    private String paisOrigen;
    private String paisDestino;

    private String estado;
    private LocalDate fechaCreacion;

    private List<File> archivos = new ArrayList<>();

    // Getters y setters

    public String getTipoDocumentoMenor() { return tipoDocumentoMenor; }
    public void setTipoDocumentoMenor(String tipoDocumentoMenor) { this.tipoDocumentoMenor = tipoDocumentoMenor; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreMenor() { return nombreMenor; }
    public void setNombreMenor(String nombreMenor) { this.nombreMenor = nombreMenor; }

    public String getApellidoMenor() { return apellidoMenor; }
    public void setApellidoMenor(String apellidoMenor) { this.apellidoMenor = apellidoMenor; }

    public LocalDate getFechaNacimientoMenor() { return fechaNacimientoMenor; }
    public void setFechaNacimientoMenor(LocalDate fechaNacimientoMenor) { this.fechaNacimientoMenor = fechaNacimientoMenor; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getNacionalidadMenor() { return nacionalidadMenor; }
    public void setNacionalidadMenor(String nacionalidadMenor) { this.nacionalidadMenor = nacionalidadMenor; }

    public String getNumeroDocumentoMenor() { return numeroDocumentoMenor; }
    public void setNumeroDocumentoMenor(String numeroDocumentoMenor) { this.numeroDocumentoMenor = numeroDocumentoMenor; }

    public String getNombrePadreMadre() { return nombrePadreMadre; }
    public void setNombrePadreMadre(String nombrePadreMadre) { this.nombrePadreMadre = nombrePadreMadre; }

    public String getDocumentoPadre() { return documentoPadre; }
    public void setDocumentoPadre(String documentoPadre) { this.documentoPadre = documentoPadre; }

    public String getEmailPadre() { return emailPadre; }
    public void setEmailPadre(String emailPadre) { this.emailPadre = emailPadre; }

    public String getTelefonoPadre() { return telefonoPadre; }
    public void setTelefonoPadre(String telefonoPadre) { this.telefonoPadre = telefonoPadre; }

    public String getRelacionMenor() { return relacionMenor; }
    public void setRelacionMenor(String relacionMenor) { this.relacionMenor = relacionMenor; }

    public LocalDate getFechaViaje() { return fechaViaje; }
    public void setFechaViaje(LocalDate fechaViaje) { this.fechaViaje = fechaViaje; }

    public String getMotivoViaje() { return motivoViaje; }
    public void setMotivoViaje(String motivoViaje) { this.motivoViaje = motivoViaje; }

    public String getTipoSolicitudMenor() { return tipoSolicitudMenor; }
    public void setTipoSolicitudMenor(String tipoSolicitudMenor) { this.tipoSolicitudMenor = tipoSolicitudMenor; }

    public String getNumeroTransporte() { return numeroTransporte; }
    public void setNumeroTransporte(String numeroTransporte) { this.numeroTransporte = numeroTransporte; }

    public String getPaisOrigen() { return paisOrigen; }
    public void setPaisOrigen(String paisOrigen) { this.paisOrigen = paisOrigen; }

    public String getPaisDestino() { return paisDestino; }
    public void setPaisDestino(String paisDestino) { this.paisDestino = paisDestino; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public List<File> getArchivos() { return archivos; }
    public void setArchivos(List<File> archivos) { this.archivos = archivos; }

    public String getTipoDocumentoPadre() { return tipoDocumentoPadre; }
    public void setTipoDocumentoPadre(String tipoDocumentoPadre) {this.tipoDocumentoPadre = tipoDocumentoPadre; }
}