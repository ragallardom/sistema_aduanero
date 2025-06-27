package cl.duoc.aduanas_app.controller;

import cl.duoc.aduanas_app.dao.SolicitudMenoresDAO;
import cl.duoc.aduanas_app.model.SolicitudViajeMenoresResponse;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SeguimientoTramiteController {

    @FXML
    private TextField txtIdTramite;
    @FXML
    private Label lblEstado;



    @FXML
    public void handleBuscar() {
        String idTexto = txtIdTramite.getText();

        if (idTexto.isEmpty()) {
            lblEstado.setText("Ingrese el ID del trámite.");
            actualizarColorEstado("rechazado");
            return;
        }

        try {
            int id = Integer.parseInt(idTexto);
            SolicitudMenoresDAO dao = new SolicitudMenoresDAO();
            SolicitudViajeMenoresResponse solicitud = dao.obtenerSolicitudPorId(id);

            if (solicitud != null) {
                lblEstado.setText("Estado: " + solicitud.getEstado());
                actualizarColorEstado(solicitud.getEstado().toLowerCase());
            } else {
                lblEstado.setText("No se encontró el trámite.");
                actualizarColorEstado("rechazado");
            }

        } catch (NumberFormatException e) {
            lblEstado.setText("ID inválido.");
            actualizarColorEstado("rechazado");
        }
    }
    private void actualizarColorEstado(String estado) {
        lblEstado.getStyleClass().removeAll("estado-aprobado", "estado-rechazado", "estado-pendiente");

        switch (estado) {
            case "aprobado":
                lblEstado.getStyleClass().add("estado-aprobado");
                break;
            case "rechazado":
                lblEstado.getStyleClass().add("estado-rechazado");
                break;
            default:
                lblEstado.getStyleClass().add("estado-pendiente");
                break;
        }

        // Forzar actualización visual
        lblEstado.applyCss();
        lblEstado.layout();
    }
}