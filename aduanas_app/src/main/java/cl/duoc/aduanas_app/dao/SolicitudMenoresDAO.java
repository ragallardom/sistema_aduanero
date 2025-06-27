package cl.duoc.aduanas_app.dao;

import cl.duoc.aduanas_app.model.SolicitudViajeMenoresRequest;
import cl.duoc.aduanas_app.model.SolicitudViajeMenoresResponse;
import cl.duoc.aduanas_app.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitudMenoresDAO {

    public List<SolicitudViajeMenoresResponse> findAll() throws SQLException {
        List<SolicitudViajeMenoresResponse> solicitudes = new ArrayList<>();

        String sql = "SELECT * FROM solicitudes_viaje_menores";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SolicitudViajeMenoresResponse solicitud = new SolicitudViajeMenoresResponse();
                solicitud.setId(rs.getLong("id"));
                solicitud.setNombreMenor(rs.getString("nombre_menor"));
                solicitud.setNumeroDocumentoMenor(rs.getString("documento_menor"));
                solicitud.setEstado(rs.getString("estado"));
                solicitud.setFechaViaje(rs.getDate("fecha_viaje").toLocalDate());
                solicitud.setPaisDestino(rs.getString("pais_destino"));
                solicitud.setMotivoViaje(rs.getString("motivo_viaje"));
                // Puedes mapear más campos si lo necesitas

                solicitudes.add(solicitud);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return solicitudes;
    }

    public void updateEstado(long idSolicitud, String nuevoEstado) throws SQLException {
        String sql = "UPDATE solicitudes_viaje_menores SET estado = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoEstado);
            stmt.setLong(2, idSolicitud);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("No se encontró la solicitud con ID " + idSolicitud);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public long insertSolicitud(SolicitudViajeMenoresRequest solicitud) throws SQLException {
        String sql = """
    INSERT INTO solicitudes_viaje_menores (
        documento_menor, documento_padre, email_padre, estado, fecha_creacion,
        fecha_nacimiento_menor, fecha_viaje, motivo_viaje, nacionalidad_menor,
        nombre_menor, nombre_padre_madre, numero_documento_menor, numero_documento_padre,
        numero_transporte, pais_destino, pais_origen, relacion_menor, telefono_padre, tipo_solicitud_menor
    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    RETURNING id;
""";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, solicitud.getTipoDocumentoMenor()); // tipo en documento_menor
            stmt.setString(2, solicitud.getTipoDocumentoPadre()); // tipo en documento_padre
            stmt.setString(3, solicitud.getEmailPadre());
            stmt.setString(4, solicitud.getEstado());
            stmt.setTimestamp(5, Timestamp.valueOf(solicitud.getFechaCreacion().atStartOfDay()));
            stmt.setDate(6, Date.valueOf(solicitud.getFechaNacimientoMenor()));
            stmt.setDate(7, Date.valueOf(solicitud.getFechaViaje()));
            stmt.setString(8, solicitud.getMotivoViaje());
            stmt.setString(9, solicitud.getNacionalidadMenor());
            stmt.setString(10, solicitud.getNombreMenor());
            stmt.setString(11, solicitud.getNombrePadreMadre());
            stmt.setString(12, solicitud.getNumeroDocumentoMenor()); // número en numero_documento_menor
            stmt.setString(13, solicitud.getDocumentoPadre()); // número en numero_documento_padre
            stmt.setString(14, solicitud.getNumeroTransporte());
            stmt.setString(15, solicitud.getPaisDestino());
            stmt.setString(16, solicitud.getPaisOrigen());
            stmt.setString(17, solicitud.getRelacionMenor());
            stmt.setString(18, solicitud.getTelefonoPadre());
            stmt.setString(19, solicitud.getTipoSolicitudMenor());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new SQLException("No se pudo obtener el ID del trámite.");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }



    }

    public SolicitudViajeMenoresResponse obtenerSolicitudPorId(int idSolicitud) {
        SolicitudViajeMenoresResponse solicitud = null;

        String sql = "SELECT id, estado FROM solicitudes_viaje_menores WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSolicitud);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                solicitud = new SolicitudViajeMenoresResponse();
                solicitud.setId(rs.getLong("id"));
                solicitud.setEstado(rs.getString("estado"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return solicitud;
    }
}
