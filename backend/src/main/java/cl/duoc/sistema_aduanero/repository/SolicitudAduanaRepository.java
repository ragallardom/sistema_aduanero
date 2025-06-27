package cl.duoc.sistema_aduanero.repository;

import cl.duoc.sistema_aduanero.model.SolicitudViajeMenores;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SolicitudAduanaRepository
    extends JpaRepository<SolicitudViajeMenores, Long> {

  @Query("SELECT DISTINCT s FROM SolicitudViajeMenores s LEFT JOIN FETCH s.documentos")
  List<SolicitudViajeMenores> findAllWithDocumentos();

  @Query(
      "SELECT s FROM SolicitudViajeMenores s LEFT JOIN FETCH s.documentos WHERE s.id = :id")
  Optional<SolicitudViajeMenores> findByIdWithDocumentos(@Param("id") Long id);

  List<SolicitudViajeMenores> findByNumeroDocumentoMenor(String numeroDocumentoMenor);
}
