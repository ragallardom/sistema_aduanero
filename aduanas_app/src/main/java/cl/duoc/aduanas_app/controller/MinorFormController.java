package cl.duoc.aduanas_app.controller;

import cl.duoc.aduanas_app.dao.SolicitudMenoresDAO;
import cl.duoc.aduanas_app.dao.AdjuntoMenoresDAO;
import cl.duoc.aduanas_app.model.AdjuntoMenor;
import cl.duoc.aduanas_app.model.SolicitudViajeMenoresRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.time.LocalDate;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class MinorFormController {

    @FXML private TextField txtNombreMenor;
    @FXML private TextField txtDocumentoMenor;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private ComboBox<String> cbSexo;
    @FXML private ComboBox<String> cbNacionalidad;
    @FXML private TextField txtNombrePadre;
    @FXML private TextField txtDocumentoPadre;
    @FXML private TextField txtEmailPadre;
    @FXML private TextField txtTelefonoPadre;
    @FXML private TextField txtRelacion;
    @FXML private DatePicker dpFechaViaje;
    @FXML private TextField txtMotivo;
    @FXML private TextField txtNumeroTransporte;
    @FXML private Label lblMensaje;

    @FXML private ComboBox<String> cbTipoDocumentoMenor;
    @FXML private ComboBox<String> cbTipoDocumentoPadre;
    @FXML private ComboBox<String> cbPaisOrigen;
    @FXML private ComboBox<String> cbPaisDestino;

    @FXML private Label lblErrorTipoDocMenor, lblErrorDocMenor, lblErrorNombreMenor, lblErrorFechaNacimiento;
    @FXML private Label lblErrorTipoDocPadre, lblErrorDocPadre, lblErrorNombrePadre, lblErrorEmailPadre, lblErrorTelefonoPadre;
    @FXML private Label lblErrorPaisOrigen, lblErrorPaisDestino, lblErrorRelacion, lblErrorFechaViaje, lblErrorMotivo, lblErrorNumeroTransporte;

    private File archivoSeleccionado;
    private final SolicitudMenoresDAO dao = new SolicitudMenoresDAO();
    private final AdjuntoMenoresDAO adjuntoDao = new AdjuntoMenoresDAO();

    @FXML
    public void initialize() {
        cbSexo.getItems().addAll("Masculino", "Femenino", "Otro");
        cbNacionalidad.getItems().addAll("Chilena", "Argentina", "Peruana", "Boliviana", "Otro");
        cbTipoDocumentoMenor.getItems().addAll("RUT", "Pasaporte");
        cbTipoDocumentoPadre.getItems().addAll("RUT", "Pasaporte");
        cbPaisOrigen.getItems().addAll("Chile", "Argentina", "Perú", "Bolivia", "Brasil", "Otro");
        cbPaisDestino.getItems().addAll("Chile", "Argentina", "Perú", "Bolivia", "Brasil", "Otro");
    }

    @FXML
    private void handleAdjuntar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar documento");
        archivoSeleccionado = fileChooser.showOpenDialog(((Stage) txtNombreMenor.getScene().getWindow()));

        lblMensaje.setText(archivoSeleccionado != null ? "Archivo seleccionado: " + archivoSeleccionado.getName() : "No se seleccionó archivo.");
    }

    @FXML
    private void handleGuardar(ActionEvent event) {
        if (!validarCampos()) return;

        try {
            SolicitudViajeMenoresRequest solicitud = new SolicitudViajeMenoresRequest();
            solicitud.setNombreMenor(txtNombreMenor.getText());
            solicitud.setTipoDocumentoMenor(cbTipoDocumentoMenor.getValue());
            solicitud.setNumeroDocumentoMenor(txtDocumentoMenor.getText());
            solicitud.setFechaNacimientoMenor(dpFechaNacimiento.getValue());
            solicitud.setSexo(cbSexo.getValue());
            solicitud.setNacionalidadMenor(cbNacionalidad.getValue());
            solicitud.setTipoDocumentoPadre(cbTipoDocumentoPadre.getValue());
            solicitud.setDocumentoPadre(txtDocumentoPadre.getText());
            solicitud.setNombrePadreMadre(txtNombrePadre.getText());
            solicitud.setEmailPadre(txtEmailPadre.getText());
            solicitud.setTelefonoPadre(txtTelefonoPadre.getText());
            solicitud.setRelacionMenor(txtRelacion.getText());
            solicitud.setPaisOrigen(cbPaisOrigen.getValue());
            solicitud.setPaisDestino(cbPaisDestino.getValue());
            solicitud.setFechaViaje(dpFechaViaje.getValue());
            solicitud.setMotivoViaje(txtMotivo.getText());
            solicitud.setNumeroTransporte(txtNumeroTransporte.getText());
            solicitud.setEstado("PENDIENTE");
            solicitud.setFechaCreacion(LocalDate.now());
            solicitud.setTipoSolicitudMenor("VIAJE");

            long idSolicitud = dao.insertSolicitud(solicitud);

            if (archivoSeleccionado != null) {
                String carpetaDestino = "adjuntos/solicitud_" + idSolicitud;
                new File(carpetaDestino).mkdirs();
                String rutaDestino = carpetaDestino + "/" + archivoSeleccionado.getName();
                Files.copy(archivoSeleccionado.toPath(), new File(rutaDestino).toPath(), StandardCopyOption.REPLACE_EXISTING);

                AdjuntoMenor adjunto = new AdjuntoMenor();
                adjunto.setNombreArchivo(archivoSeleccionado.getName());
                adjunto.setNombreOriginal(archivoSeleccionado.getName());
                adjunto.setRuta(rutaDestino);
                adjunto.setSolicitudId(idSolicitud);

                adjuntoDao.insertAdjunto(adjunto);
            }

            new Alert(Alert.AlertType.INFORMATION, "El trámite se guardó correctamente.").showAndWait();
            limpiarFormulario();
        } catch (Exception e) {
            e.printStackTrace();
            lblMensaje.setText("Error al guardar el trámite.");
        }
    }

    private boolean validarCampos() {
        boolean valido = true;
        lblErrorTipoDocMenor.setText(""); lblErrorDocMenor.setText(""); lblErrorNombreMenor.setText(""); lblErrorFechaNacimiento.setText("");
        lblErrorTipoDocPadre.setText(""); lblErrorDocPadre.setText(""); lblErrorNombrePadre.setText(""); lblErrorEmailPadre.setText("");
        lblErrorTelefonoPadre.setText(""); lblErrorPaisOrigen.setText(""); lblErrorPaisDestino.setText(""); lblErrorRelacion.setText("");
        lblErrorFechaViaje.setText(""); lblErrorMotivo.setText(""); lblErrorNumeroTransporte.setText(""); lblMensaje.setText("");

        if (cbTipoDocumentoMenor.getValue() == null) { lblErrorTipoDocMenor.setText("Selecciona tipo documento."); valido = false; }
        if (txtDocumentoMenor.getText().isBlank()) { lblErrorDocMenor.setText("Ingresa documento."); valido = false; }
        if (txtNombreMenor.getText().isBlank()) { lblErrorNombreMenor.setText("Ingresa nombre."); valido = false; }
        if (dpFechaNacimiento.getValue() == null) { lblErrorFechaNacimiento.setText("Selecciona fecha nacimiento."); valido = false; }

        if (cbTipoDocumentoPadre.getValue() == null) { lblErrorTipoDocPadre.setText("Selecciona tipo documento."); valido = false; }
        if (txtDocumentoPadre.getText().isBlank()) { lblErrorDocPadre.setText("Ingresa documento."); valido = false; }
        if (txtNombrePadre.getText().isBlank()) { lblErrorNombrePadre.setText("Ingresa nombre."); valido = false; }
        if (txtEmailPadre.getText().isBlank() || !txtEmailPadre.getText().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            lblErrorEmailPadre.setText("Email inválido."); valido = false;
        }
        if (txtTelefonoPadre.getText().isBlank() || !txtTelefonoPadre.getText().matches("^\\+?\\d{7,15}$")) {
            lblErrorTelefonoPadre.setText("Teléfono inválido."); valido = false;
        }

        if (cbPaisOrigen.getValue() == null) { lblErrorPaisOrigen.setText("Selecciona país origen."); valido = false; }
        if (cbPaisDestino.getValue() == null) { lblErrorPaisDestino.setText("Selecciona país destino."); valido = false; }
        if (txtRelacion.getText().isBlank()) { lblErrorRelacion.setText("Ingresa relación."); valido = false; }

        if (dpFechaViaje.getValue() == null) {
            lblErrorFechaViaje.setText("Selecciona fecha de viaje.");
            valido = false;
        } else if (dpFechaViaje.getValue().isBefore(LocalDate.now())) {
            lblErrorFechaViaje.setText("La fecha debe ser posterior a hoy.");
            valido = false;
        }

        if (txtMotivo.getText().isBlank()) { lblErrorMotivo.setText("Ingresa motivo de viaje."); valido = false; }
        if (txtNumeroTransporte.getText().isBlank()) { lblErrorNumeroTransporte.setText("Ingresa número transporte."); valido = false; }

        return valido;
    }

    private void limpiarFormulario() {
        txtDocumentoMenor.clear(); txtNombreMenor.clear(); cbTipoDocumentoMenor.setValue(null); dpFechaNacimiento.setValue(null);
        cbSexo.setValue(null); cbNacionalidad.setValue(null); txtNombrePadre.clear(); txtDocumentoPadre.clear();
        cbTipoDocumentoPadre.setValue(null); txtEmailPadre.clear(); txtTelefonoPadre.clear(); cbPaisOrigen.setValue(null);
        cbPaisDestino.setValue(null); txtRelacion.clear(); dpFechaViaje.setValue(null); txtMotivo.clear(); txtNumeroTransporte.clear();
        archivoSeleccionado = null; lblMensaje.setText("");

        lblErrorTipoDocMenor.setText(""); lblErrorDocMenor.setText(""); lblErrorNombreMenor.setText(""); lblErrorFechaNacimiento.setText("");
        lblErrorTipoDocPadre.setText(""); lblErrorDocPadre.setText(""); lblErrorNombrePadre.setText(""); lblErrorEmailPadre.setText("");
        lblErrorTelefonoPadre.setText(""); lblErrorPaisOrigen.setText(""); lblErrorPaisDestino.setText(""); lblErrorRelacion.setText("");
        lblErrorFechaViaje.setText(""); lblErrorMotivo.setText(""); lblErrorNumeroTransporte.setText("");
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }
}
