package cl.duoc.sistema_aduanero.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
  @Override
  public void start(Stage stage) throws Exception {
    FXMLLoader loader =
        new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
    Scene scene = new Scene(loader.load());
    stage.setTitle("Sistema Aduanero - Cliente");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) { launch(args); }
}
