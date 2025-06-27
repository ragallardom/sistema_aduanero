package cl.duoc.aduanas_app.dao;

import cl.duoc.aduanas_app.model.AdjuntoMenor;
import cl.duoc.aduanas_app.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdjuntoMenoresDAO {

    public void insertAdjunto(AdjuntoMenor adjunto) throws SQLException {
        String sql = """
                INSERT INTO adjuntos_viaje_menores (nombre_archivo, nombre_original, ruta, id_solicitud)
                VALUES (?, ?, ?, ?)
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, adjunto.getNombreArchivo());
            stmt.setString(2, adjunto.getNombreOriginal());
            stmt.setString(3, adjunto.getRuta());
            stmt.setLong(4, adjunto.getSolicitudId());

            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
