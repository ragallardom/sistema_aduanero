package cl.duoc.sistema_aduanero.controller;

import cl.duoc.sistema_aduanero.dto.AdjuntoViajeMenoresResponse;
import cl.duoc.sistema_aduanero.dto.SolicitudViajeMenoresRequest;
import cl.duoc.sistema_aduanero.dto.SolicitudViajeMenoresResponse;
import cl.duoc.sistema_aduanero.dto.SolicitudSeguimientoResponse;
import cl.duoc.sistema_aduanero.model.AdjuntoViajeMenores;
import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import cl.duoc.sistema_aduanero.model.EstadoSolicitud;
import cl.duoc.sistema_aduanero.service.DocumentoAdjuntoService;
import cl.duoc.sistema_aduanero.service.SolicitudAduanaService;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudAduanaController {

  private final SolicitudAduanaService solicitudService;
  private final DocumentoAdjuntoService adjuntoService;

  public SolicitudAduanaController(SolicitudAduanaService solicitudService,
                                   DocumentoAdjuntoService adjuntoService) {
    this.solicitudService = solicitudService;
    this.adjuntoService = adjuntoService;
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<SolicitudViajeMenoresResponse> crearSolicitud(
      @ModelAttribute SolicitudViajeMenoresRequest request,
      @RequestPart(value = "archivos", required = false) List<MultipartFile> archivos,
      @RequestParam(value = "tiposDocumento", required = false) List<String> tiposDoc) {
    try {
      SolicitudViajeMenores solicitud = new SolicitudViajeMenores();
      String estado = request.getEstado();
      if (estado == null || estado.trim().isEmpty()) {
        estado = "PENDIENTE";
      }
      solicitud.setEstado(estado);
      solicitud.setTipoSolicitudMenor(request.getTipoSolicitudMenor());
      solicitud.setNombreMenor(request.getNombreMenor());
      solicitud.setFechaNacimientoMenor(request.getFechaNacimientoMenor());
      solicitud.setDocumentoMenor(request.getDocumentoMenor());
      solicitud.setNumeroDocumentoMenor(request.getNumeroDocumentoMenor());
      solicitud.setNacionalidadMenor(request.getNacionalidadMenor());
      solicitud.setNombrePadreMadre(request.getNombrePadreMadre());
      solicitud.setRelacionMenor(request.getRelacionMenor());
      solicitud.setDocumentoPadre(request.getDocumentoPadre());
      solicitud.setNumeroDocumentoPadre(request.getNumeroDocumentoPadre());
      solicitud.setTelefonoPadre(request.getTelefonoPadre());
      solicitud.setEmailPadre(request.getEmailPadre());
      solicitud.setFechaViaje(request.getFechaViaje());
      solicitud.setNumeroTransporte(request.getNumeroTransporte());

      String tipo = request.getTipoSolicitudMenor();
      if ("Entrada".equalsIgnoreCase(tipo)) {
        solicitud.setPaisOrigen(request.getPaisOrigen());
        solicitud.setPaisDestino("Chile");
      } else if ("Salida".equalsIgnoreCase(tipo)) {
        solicitud.setPaisOrigen("Chile");
        solicitud.setPaisDestino(request.getPaisDestino());
      } else {
        solicitud.setPaisOrigen(request.getPaisOrigen());
        solicitud.setPaisDestino(request.getPaisDestino());
      }

      solicitud.setMotivoViaje(request.getMotivoViaje());

      SolicitudViajeMenores creada = solicitudService.crearSolicitud(solicitud);

      List<MultipartFile> archivosRecibidos =
          (archivos != null) ? archivos : request.getArchivos();
      List<String> tiposRecibidos =
          (tiposDoc != null) ? tiposDoc : request.getTiposDocumento();

      if (archivosRecibidos != null && !archivosRecibidos.isEmpty()) {
        adjuntoService.guardarAdjuntos(creada, tiposRecibidos, archivosRecibidos);
      }

      return ResponseEntity.ok(mapearSolicitud(creada));

    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping("/descargar/{id}")
  public ResponseEntity<InputStreamResource>
  descargarTodosLosDocumentos(@PathVariable Long id) {
    try {
      Optional<SolicitudViajeMenores> solicitudOpt =
          solicitudService.obtenerPorId(id);
      if (solicitudOpt.isEmpty()) {
        return ResponseEntity.notFound().build();
      }
      SolicitudViajeMenores solicitud = solicitudOpt.get();

      String nombreZip = "solicitud_" + id + "_documentos.zip";

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ZipOutputStream zos = new ZipOutputStream(baos);

      for (AdjuntoViajeMenores doc : solicitud.getDocumentos()) {
        String rutaArchivo = doc.getRuta();
        Path pathDoc = Paths.get(rutaArchivo);

        if (!Files.exists(pathDoc) || Files.isDirectory(pathDoc)) {
          continue;
        }

        String nombreDentroZip = doc.getNombreArchivo();

        zos.putNextEntry(new ZipEntry(nombreDentroZip));

        try (InputStream is = Files.newInputStream(pathDoc)) {
          byte[] buffer = new byte[4096];
          int len;
          while ((len = is.read(buffer)) > 0) {
            zos.write(buffer, 0, len);
          }
        }

        zos.closeEntry();
      }

      zos.finish();
      zos.close();

      byte[] zipBytes = baos.toByteArray();
      ByteArrayInputStream bis = new ByteArrayInputStream(zipBytes);
      InputStreamResource resource = new InputStreamResource(bis);

      return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION,
                  "attachment; filename=\"" + nombreZip + "\"")
          .contentLength(zipBytes.length)
          .contentType(MediaType.APPLICATION_OCTET_STREAM)
          .body(resource);

    } catch (IOException e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().build();
    }
  }

  @GetMapping
  public ResponseEntity<List<SolicitudViajeMenoresResponse>> obtenerTodas() {
    List<SolicitudViajeMenores> solicitudes =
        solicitudService.obtenerTodasConDocumentos();
    List<SolicitudViajeMenoresResponse> respuesta =
        solicitudes.stream().map(this::mapearSolicitud).toList();
    return ResponseEntity.ok(respuesta);
  }

  @GetMapping("/menor")
  public ResponseEntity<List<SolicitudSeguimientoResponse>> obtenerPorRutMenor(
      @RequestParam String rut) {
    List<SolicitudViajeMenores> solicitudes =
        solicitudService.obtenerPorRutMenor(rut);
    List<SolicitudSeguimientoResponse> respuesta =
        solicitudes.stream().map(this::mapearSeguimiento).toList();
    return ResponseEntity.ok(respuesta);
  }

  @GetMapping("/{id:\\d+}")
  public ResponseEntity<SolicitudViajeMenoresResponse> obtenerPorId(
      @PathVariable Long id) {
    Optional<SolicitudViajeMenores> opt =
        solicitudService.obtenerPorIdConDocumentos(id);
    if (opt.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(mapearSolicitud(opt.get()));
  }

  @ PutMapping("/{id}/estado")
  public ResponseEntity<Void> actualizarEstado(
      @PathVariable Long id,
      @RequestParam String estado) {
    try {
      EstadoSolicitud nuevo = EstadoSolicitud.valueOf(estado.toUpperCase());
      solicitudService.actualizarEstado(id, nuevo.name());
      return ResponseEntity.ok().build();
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().build();
    }
  }

  private SolicitudViajeMenoresResponse mapearSolicitud(SolicitudViajeMenores s) {
    SolicitudViajeMenoresResponse r = new SolicitudViajeMenoresResponse();
    r.setId(s.getId());
    r.setEstado(s.getEstado());
    r.setFechaCreacion(s.getFechaCreacion());
    r.setTipoSolicitudMenor(s.getTipoSolicitudMenor());
    r.setNombreMenor(s.getNombreMenor());
    r.setFechaNacimientoMenor(s.getFechaNacimientoMenor());
    r.setDocumentoMenor(s.getDocumentoMenor());
    r.setNumeroDocumentoMenor(s.getNumeroDocumentoMenor());
    r.setNacionalidadMenor(s.getNacionalidadMenor());
    r.setNombrePadreMadre(s.getNombrePadreMadre());
    r.setRelacionMenor(s.getRelacionMenor());
    r.setDocumentoPadre(s.getDocumentoPadre());
    r.setNumeroDocumentoPadre(s.getNumeroDocumentoPadre());
    r.setTelefonoPadre(s.getTelefonoPadre());
    r.setEmailPadre(s.getEmailPadre());
    r.setFechaViaje(s.getFechaViaje());
    r.setNumeroTransporte(s.getNumeroTransporte());
    r.setPaisOrigen(s.getPaisOrigen());
    r.setPaisDestino(s.getPaisDestino());
    r.setMotivoViaje(s.getMotivoViaje());
    List<AdjuntoViajeMenoresResponse> docs =
        s.getDocumentos().stream().map(this::mapearAdjunto).toList();
    r.setDocumentos(docs);
    return r;
  }

  private SolicitudSeguimientoResponse mapearSeguimiento(SolicitudViajeMenores s) {
    SolicitudSeguimientoResponse r = new SolicitudSeguimientoResponse();
    r.setId(s.getId());
    r.setFechaCreacion(s.getFechaCreacion());
    r.setNumeroDocumentoMenor(s.getNumeroDocumentoMenor());
    r.setEstado(s.getEstado());
    return r;
  }

  private AdjuntoViajeMenoresResponse mapearAdjunto(AdjuntoViajeMenores a) {
    AdjuntoViajeMenoresResponse r = new AdjuntoViajeMenoresResponse();
    r.setId(a.getId());
    r.setNombreOriginal(a.getNombreOriginal());
    r.setNombreArchivo(a.getNombreArchivo());
    r.setRuta(a.getRuta());
    return r;
  }

}

