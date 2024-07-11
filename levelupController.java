package Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import view.debutantMenu;
import view.mainMenu;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class levelupController implements Initializable {
    @FXML
    private Button ok;
    @FXML
    private Label username;
    @FXML
    private Label level;
    boolean secondShown = false;
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        //levelUpSE();
        username.setText(winnerController.toBeLeveledUp.get(0).getUsername());
        level.setText(String.valueOf(winnerController.toBeLeveledUp.get(0).getLevel()));
    }
    @FXML
    private void okClick(MouseEvent mouseEvent){
        if(winnerController.toBeLeveledUp.size() == 2){
          //  levelUpSE();
            username.setText(winnerController.toBeLeveledUp.get(1).getUsername());
            level.setText(String.valueOf(winnerController.toBeLeveledUp.get(1).getLevel()));
            secondShown = true;
        }
        if(winnerController.toBeLeveledUp.size() == 1 || secondShown){
            try{
                view.mainMenu mainMenu = new mainMenu();
                mainMenu.start(debutantMenu.stage);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
  /*  private void levelUpSE(){
        String path = "D:\\University\\Term2\\OOP\\Project Phase 1\\Proj_Ph1_Merged\\src\\main\\resources\\Media\\levelUp.mp3";
        Media sound = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.setVolume(1.0);
        mediaPlayer.play();
    }*/
}
