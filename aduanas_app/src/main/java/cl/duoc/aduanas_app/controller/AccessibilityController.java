package cl.duoc.aduanas_app.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AccessibilityController {

    @FXML private ToggleButton toggleContraste;
    @FXML private ToggleButton toggleLectura;
    @FXML private ToggleButton toggleFuente;
    @FXML private ToggleButton toggleTeclado;
    @FXML private ComboBox<String> comboIdioma;

    @FXML
    public void initialize() {
        comboIdioma.getItems().addAll("Español", "Inglés");
        comboIdioma.getSelectionModel().selectFirst(); // Valor por defecto
    }

    @FXML
    private void handleGuardar() {
        System.out.println("Preferencias guardadas:");
        System.out.println("Contraste: " + toggleContraste.isSelected());
        System.out.println("Lectura: " + toggleLectura.isSelected());
        System.out.println("Fuente grande: " + toggleFuente.isSelected());
        System.out.println("Teclado: " + toggleTeclado.isSelected());
        System.out.println("Idioma: " + comboIdioma.getValue());

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Preferencias guardadas correctamente", ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void handleBack() {
        Stage stage = (Stage) toggleContraste.getScene().getWindow();
        stage.close();
    }
}
