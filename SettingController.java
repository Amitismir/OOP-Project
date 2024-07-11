package Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import view.debutantMenu;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    //TODO: ADD THE DEMO OF THE VOLUMES
    //TODO: FIX THE LABELS ON THE IMAGES
    private final String[] musicNames = {"In Dreams", "Thunder"};
    private final String[] seNames = {"Bright Blip", "Retro Beep"};
    public static int bgIndex = 1; //default value is 1
    public static int seIndex = 1; //default value is 1
    public static int musicIndex = 1; //default value is 1
    public static int seVolumeIndex;
    public static int musicVolumeIndex;
    @FXML
    private ChoiceBox<String> musicChoice;
    @FXML
    private Slider musicSlider;
    @FXML
    private ChoiceBox<String> seChoice;
    @FXML
    private Slider seSlider;
    @FXML
    private Label musicVolumeLabel;
    @FXML
    private Label seVolumeLabel;
    @FXML
    private Label theme1LABEL;
    @FXML
    private Label theme2LABEL;
    @FXML
    private Label theme3LABEL;
    @FXML
    private Label theme4LABEL;
    @FXML
    public void theme1(MouseEvent event) {
        bgIndex = 1;
        theme2LABEL.setText("");
        theme3LABEL.setText("");
        theme4LABEL.setText("");
        theme1LABEL.setText("\"Magic Fluid\" chosen");
    }
    @FXML
    public void theme2(MouseEvent event) {
        bgIndex = 2;
        theme3LABEL.setText("");
        theme4LABEL.setText("");
        theme1LABEL.setText("");
        theme2LABEL.setText("\"Tokyo City\" chosen");
    }
    @FXML
    void theme3(MouseEvent event) {
        bgIndex = 3;
        theme4LABEL.setText("");
        theme1LABEL.setText("");
        theme2LABEL.setText("");
        theme3LABEL.setText("\"New Era\" chosen");
    }
    @FXML
    void theme4(MouseEvent event){
        bgIndex = 4;
        theme1LABEL.setText("");
        theme2LABEL.setText("");
        theme3LABEL.setText("");
        theme4LABEL.setText("\"Futuristic Sphere\" chosen");
    }
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        musicChoice.getItems().addAll(musicNames);
        seChoice.getItems().addAll(seNames);
        musicSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                musicVolumeIndex = (int)musicSlider.getValue();
                musicVolumeLabel.setText(Integer.toString(musicVolumeIndex));
            }
        });
        seSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                seVolumeIndex = (int)seSlider.getValue();
                seVolumeLabel.setText(Integer.toString(seVolumeIndex));
            }
        });
        musicChoice.setOnAction(this::getMusicChoice);
        seChoice.setOnAction(this::getSeChoice);
    }
    public void getMusicChoice(ActionEvent actionEvent){
        String myMusic = musicChoice.getValue();
        switch(myMusic){
            case "In Dreams":
                musicIndex = 1;
                inDreamsDemo();
                break;
            case "Thunder":
                musicIndex = 2;
                thunderDemo();
                break;
        }
    }
    public void getSeChoice(ActionEvent actionEvent){
        String mySE = seChoice.getValue();
        switch (mySE){
            case "Bright Blip":
                seIndex = 1;
                BrightBlipDemo();
                break;
            case "Retro Beep":
                musicIndex = 2;
                retroBeepDemo();
                break;
        }
    }
    private void BrightBlipDemo(){
        String path = "C:\\Users\\Notebook\\IdeaProjects\\Citywars\\src\\main\\resources\\Media\\se1.mp3";
        Media sound = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    private void retroBeepDemo(){
        String path = "C:\\Users\\Notebook\\IdeaProjects\\Citywars\\src\\main\\resources\\Media\\se2.mp3";
        Media sound = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    private void inDreamsDemo(){
        String path = "C:\\Users\\Notebook\\IdeaProjects\\Citywars\\src\\main\\resources\\Media\\InDreamsDemo.mp3";
        Media sound = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    private void thunderDemo(){
        String path = "C:\\Users\\Notebook\\IdeaProjects\\Citywars\\src\\main\\resources\\Media\\ThunderDemo.mp3";
        Media sound = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    @FXML
    public void setOnSave(MouseEvent mouseEvent){
        view.mainMenu mainMenu = new view.mainMenu();
        try{
            debutantMenu.stage.close();
            mainMenu.start(debutantMenu.stage);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
