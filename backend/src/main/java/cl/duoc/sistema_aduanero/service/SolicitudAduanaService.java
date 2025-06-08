package cl.duoc.sistema_aduanero.service;

import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import cl.duoc.sistema_aduanero.repository.SolicitudAduanaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class SolicitudAduanaService {

  private final SolicitudAduanaRepository repository;

  public SolicitudAduanaService(SolicitudAduanaRepository repository) {
    this.repository = repository;
  }

  public SolicitudViajeMenores crearSolicitud(SolicitudViajeMenores solicitud) {
    return repository.save(solicitud);
  }

  public List<SolicitudViajeMenores> obtenerTodas() {
    return repository.findAll();
  }

  public List<SolicitudViajeMenores> obtenerTodasConDocumentos() {
    return repository.findAllWithDocumentos();
  }

  public SolicitudViajeMenores actualizarEstado(Long id, String nuevoEstado) {
    SolicitudViajeMenores solicitud = repository.findById(id).orElseThrow(
        () -> new RuntimeException("Solicitud no encontrada"));
    solicitud.setEstado(nuevoEstado);
    return repository.save(solicitud);
  }

  public Optional<SolicitudViajeMenores> obtenerPorId(Long id) {
    return repository.findById(id);
  }

  public Optional<SolicitudViajeMenores> obtenerPorIdConDocumentos(Long id) {
    return repository.findByIdWithDocumentos(id);
  }
}
