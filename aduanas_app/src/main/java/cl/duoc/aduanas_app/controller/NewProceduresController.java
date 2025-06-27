package cl.duoc.aduanas_app.controller;

import cl.duoc.aduanas_app.dao.SolicitudMenoresDAO;
import cl.duoc.aduanas_app.model.SolicitudViajeMenoresRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class NewProceduresController {
    private final SolicitudMenoresDAO dao = new SolicitudMenoresDAO();

    @FXML private Button btnVehiculo;
    @FXML private Button btnMenor;
    @FXML private Button btnMascota;
    @FXML private Button btnAlimentos;
    @FXML private Button btnDeclaracion;
    @FXML private Button btnOtro;
    @FXML private Button btnComenzar;

    // Campos del formulario de menor de edad
    @FXML private DatePicker fechaNacimientoMenor;
    @FXML private TextField txtDocumentoMenor;
    @FXML private TextField txtNombreMenor;
    @FXML private TextField txtDocumentoPadre;
    @FXML private TextField txtEmailPadre;
    @FXML private TextField txtTelefonoPadre;
    @FXML private TextField txtPaisOrigen;
    @FXML private DatePicker fechaViaje;
    @FXML private TextField txtMotivoViaje;
    // ...otros campos que correspondan

    @FXML
    private void onVehiculo(ActionEvent event) {
        System.out.println("Elegido Vehículo");
        // TODO: Cargar formulario Vehículo
    }

    @FXML
    private void onMenorEdad(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TramiteMenorEdad.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());

            Stage newStage = new Stage();
            newStage.setTitle("TramiteMenorEdad");
            newStage.setScene(scene);
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "No se pudo abrir el formulario de menor de edad").showAndWait();
        }
    }

    @FXML
    private void onMascota(ActionEvent event) {
        System.out.println("Elegido Mascota");
        // TODO: Cargar formulario Mascota
    }

    @FXML
    private void onAlimentos(ActionEvent event) {
        System.out.println("Elegido Alimentos");
        // TODO: Cargar formulario Alimentos
    }

    @FXML
    private void onDeclaracion(ActionEvent event) {
        System.out.println("Elegido Declaración");
        // TODO: Cargar formulario Declaración
    }

    @FXML
    private void onOtro(ActionEvent event) {
        System.out.println("Elegido Otro");
        // TODO: Cargar formulario Otro tipo
    }

    @FXML
    private void iniciarTramite(ActionEvent event) {
        // Leer datos del formulario de menores
        LocalDate fechaNac = fechaNacimientoMenor.getValue();
        String docMenor = txtDocumentoMenor.getText();
        String nombreMenor = txtNombreMenor.getText();
        String docPadre = txtDocumentoPadre.getText();
        String emailPadre = txtEmailPadre.getText();
        String telPadre = txtTelefonoPadre.getText();
        String paisOrig = txtPaisOrigen.getText();
        LocalDate fecViaje = fechaViaje.getValue();
        String motivo = txtMotivoViaje.getText();

        // Validaciones básicas
        if (fechaNac == null || docMenor.isBlank() || nombreMenor.isBlank()) {
            new Alert(Alert.AlertType.WARNING, "Complete todos los campos obligatorios").showAndWait();
            return;
        }

        // Construir request
        SolicitudViajeMenoresRequest req = new SolicitudViajeMenoresRequest();
        req.setFechaNacimientoMenor(fechaNac);
        req.setNumeroDocumentoMenor(docMenor);
        req.setNombreMenor(nombreMenor);
        req.setDocumentoPadre(docPadre);
        req.setEmailPadre(emailPadre);
        req.setTelefonoPadre(telPadre);
        req.setPaisOrigen(paisOrig);
        req.setFechaViaje(fecViaje);
        req.setMotivoViaje(motivo);
        req.setEstado("PENDIENTE");
        req.setFechaCreacion(LocalDate.now());

        try {
            dao.insertSolicitud(req);
            new Alert(Alert.AlertType.INFORMATION, "Solicitud creada con éxito").showAndWait();
            // Opcional: cerrar ventana
            Stage stage = (Stage) btnComenzar.getScene().getWindow();
            stage.close();
        } catch (Exception e) {e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Error al crear la solicitud").showAndWait();
        }
    }

    @FXML
    private void handleBack(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnComenzar.getScene().getWindow();
        stage.close();
    }
}