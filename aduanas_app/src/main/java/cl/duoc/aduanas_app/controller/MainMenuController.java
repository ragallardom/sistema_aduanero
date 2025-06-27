package cl.duoc.aduanas_app.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class MainMenuController {

    @FXML private ImageView profileImageView;
    @FXML private Label lblUserName;
    @FXML private Label lblTramitesCount;
    @FXML private Label lblLastStatus;
    @FXML private Button btnNuevoTramite;
    @FXML  private Label lblMesaje;

    public void setLblUserName(String lblUserName) {
        this.lblUserName.setText(lblUserName);
    }

    // Método que recibe datos de usuario al cargar la vista
    public void setUserData(String user, int totalTramites, String ultimoEstado, String perfilImagePath) {
        lblUserName.setText("hola" + user);
        lblTramitesCount.setText("Has iniciado " + totalTramites + " trámites");
        lblLastStatus.setText(ultimoEstado);

        // Cargar imagen de perfil
        Image img = new Image(getClass().getResourceAsStream(perfilImagePath));
        profileImageView.setImage(img);
    }

    @FXML
    private void handleNuevoTramite(ActionEvent event) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Nuevo Trámite");
        alert.setHeaderText(null);
        alert.setContentText("Funcionalidad de iniciar nuevo trámite aún no implementada.");
        alert.showAndWait();
    }


    @FXML
    private void handlecolaboradores(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/CollaboratorView.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());

            // Obtener el Stage actual y cambiar la Scene
            Stage newStage = new Stage();
            newStage.setTitle("Colaboradores");
            newStage.setScene(scene);
            newStage.show();

        } catch (Exception e) {
            lblMesaje.setText("No se pudo cargar la vista de Colaborador.");
            e.printStackTrace();
        }
    }


    @FXML
    private void HandleNuevoTramite(ActionEvent event) {
        try {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/NewProcedures.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root, 400, 500);

                // Obtener el Stage actual y cambiar la Scene
                Stage newStage = new Stage();
                newStage.setTitle("Nuevo procedimiento");
                newStage.setScene(scene);
                newStage.show();

                scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());



            } catch (Exception e) {
                lblMesaje.setText("No se pudo cargar la vista de Colaborador.");
                e.printStackTrace();
            }

        } catch (Exception e) {
            // Ahora lblMesaje ya no será null
            lblMesaje.setText("No se pudo abrir la ventana de Nuevo Trámite.");
            e.printStackTrace();
        }
    }
    @FXML
    private void onSeguimiento() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SeguimientoTramite.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Seguimiento de Trámite");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 400, 500);
            scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle("Sistema Aduanero - Cliente");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
