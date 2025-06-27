package cl.duoc.aduanas_app.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 400, 500); // Tamaño inicial directamente en Scene
        scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
        stage.setTitle("Sistema Aduanero - Cliente");
        stage.setScene(scene);
        stage.setResizable(false); // Si no quieres que el usuario agrande la ventana
        stage.centerOnScreen();
        stage.show();



    }

    public static void main(String[] args) {
        launch(args);
    }
}
