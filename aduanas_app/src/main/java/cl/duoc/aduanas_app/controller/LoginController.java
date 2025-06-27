package cl.duoc.aduanas_app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class LoginController {

    @FXML  private TextField tfUsername;
    @FXML  private PasswordField tfPassword;
    @FXML  private Label lblMesaje;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = tfUsername.getText();
        String password = tfPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lblMesaje.setText("Debe de rellenar ambos campos");
            return;
        }
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/fxml/MainMenu.fxml")
            );

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            URL cssUrl = getClass().getResource("/css/Style.css");
            System.out.println("CSS URL = " + cssUrl);

            if (cssUrl == null) {
                System.err.println("ERROR: No encontré /css/Style.css");
            } else {
                scene.getStylesheets().add(cssUrl.toExternalForm());
                System.out.println("Añadido stylesheet: " + cssUrl.toExternalForm());
            }
            MainMenuController controller = fxmlLoader.getController();
            controller.setLblUserName(username);

            Stage stage = (Stage) tfUsername.getScene().getWindow();
            stage.setTitle("Menú Principal");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();

            stage.setWidth(400);
            stage.setHeight(500);
            stage.setMinWidth(400);
            stage.setMinHeight(500);

        } catch (Exception e) {
            lblMesaje.setText("No se pudo cargar la vitas principal.");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAccesibilidad() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/AccessibilityPreferences.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Accesibilidad");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
