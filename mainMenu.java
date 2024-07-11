package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class mainMenu extends Application {
    private static Stage stage;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        debutantMenu.stage = stage;
        URL url= debutantMenu.class.getResource("/FXML/main.fxml");
        AnchorPane root= FXMLLoader.load(url);
        Scene scene =new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.show();

    }
}
