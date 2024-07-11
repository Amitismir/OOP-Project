package view;

import Controller.Controller;
import Model.MainMenu;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

public class debutantMenu extends Application {
    public static Stage stage;
    public static void main(String[] args){

        Controller controller = new Controller();
        controller.run();
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("City Wars");
       Image icon=new Image(String.valueOf(getClass().getResource("/Media/icon.png")));
        stage.getIcons().add(icon);
        debutantMenu.stage=stage;
        URL url= MainMenu.class.getResource("/FXML/debutantmenu.fxml");
        AnchorPane root= FXMLLoader.load(url);
        Scene scene =new Scene(root);
        scene.getRoot().requestFocus();
        stage.setScene(scene);
        stage.show();
    }
}
