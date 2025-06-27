package cl.duoc.aduanas_app.controller;

import cl.duoc.aduanas_app.dao.SolicitudMenoresDAO;
import cl.duoc.aduanas_app.model.SolicitudViajeMenoresResponse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CollaboratorViewController {

    private SolicitudMenoresDAO dao = new SolicitudMenoresDAO();

    @FXML private TableView<SolicitudViajeMenoresResponse> tableSolicitudes;
    @FXML private Label lblMensaje;


    @FXML private TableColumn<SolicitudViajeMenoresResponse, Long>   colId;
    @FXML private TableColumn<SolicitudViajeMenoresResponse, String> colNombre;
    @FXML private TableColumn<SolicitudViajeMenoresResponse, String> colRut;
    @FXML private TableColumn<SolicitudViajeMenoresResponse, String> colDestino;
    @FXML private TableColumn<SolicitudViajeMenoresResponse, LocalDate> colFechaSolicitud;
    @FXML private TableColumn<SolicitudViajeMenoresResponse, LocalDate> colFechaSalida;
    @FXML private TableColumn<SolicitudViajeMenoresResponse, String> colEstado;

    @FXML
    private void initialize() {
        // 1) vincular columnas a propiedades del bean
        colId.setCellValueFactory  (new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreMenor"));
        colRut.setCellValueFactory   (new PropertyValueFactory<>("numeroDocumentoMenor"));
        colDestino.setCellValueFactory(new PropertyValueFactory<>("paisDestino"));
        colFechaSolicitud.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        colFechaSalida.setCellValueFactory   (new PropertyValueFactory<>("fechaViaje"));
        colEstado.setCellValueFactory        (new PropertyValueFactory<>("estado"));
        // 2) carga inicial de datos
        cargarSolicitudes();
    }

    private void cargarSolicitudes() {
        try {
            List<SolicitudViajeMenoresResponse> lista = dao.findAll();
            tableSolicitudes.getItems().setAll(lista);
        } catch (Exception e) {
            e.printStackTrace();
            lblMensaje.setText("Error al cargar solicitudes.");
        }
    }

    @FXML
    private void verDetalle(ActionEvent event) {
        SolicitudViajeMenoresResponse sel = tableSolicitudes.getSelectionModel().getSelectedItem();
        if (sel != null) {
            // Aquí abres un diálogo o nueva ventana con los datos de ‘sel’
            System.out.println("Detalle de solicitud: " + sel);
            // por ejemplo: new DetalleSolicitudWindow(sel).show();
        } else {
            // muestra mensaje de “seleccione primero”
        }
    }

    @FXML
    private void refrescarTabla(ActionEvent event) {
        cargarSolicitudes();
        lblMensaje.setText("");
    }
    

    @FXML
    private void aprobar(ActionEvent event) {
        SolicitudViajeMenoresResponse sel = tableSolicitudes.getSelectionModel().getSelectedItem();
        if (sel != null) {
            try {
                dao.updateEstado(sel.getId(), "APROBADA");
                lblMensaje.setText("Solicitud #" + sel.getId() + " aprobada.");
                cargarSolicitudes();
            } catch (Exception e) {
                e.printStackTrace();
                lblMensaje.setText("Error al aprobar solicitud.");
            }
        } else {
            lblMensaje.setText("Seleccione una solicitud primero.");
        }
    }

    @FXML
    private void rechazar(ActionEvent event) {
        SolicitudViajeMenoresResponse sel = tableSolicitudes.getSelectionModel().getSelectedItem();
        if (sel != null) {
            try {
                dao.updateEstado(sel.getId(), "RECHAZADA");
                lblMensaje.setText("Solicitud #" + sel.getId() + " rechazada.");
                cargarSolicitudes();
            } catch (Exception e) {
                e.printStackTrace();
                lblMensaje.setText("Error al rechazar solicitud.");
            }
        } else {
            lblMensaje.setText("Seleccione una solicitud primero.");
        }
    }




    @FXML
    private void handleBack(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }



    public AnchorPane getView() throws Exception {
        return FXMLLoader.load(getClass().getResource("/fxml/CollaboratorView.fxml"));

    }
}