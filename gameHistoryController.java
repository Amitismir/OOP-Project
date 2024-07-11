package Controller;
import Model.*;

import Model.HISTORY;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.debutantMenu;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;

public class gameHistoryController implements Initializable {
    final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
    final String USERNAME = "root";
    final String PASSWORD = "138387Amitis";
    String table_name = Controller.currentuser.getUsername().toLowerCase() + "_gamehistory";
    public static ArrayList<HISTORY> history = new ArrayList<>();
    @FXML
    private ChoiceBox<String> sorting;
    @FXML
    private ChoiceBox<String> ac_dc;
    public static String SortingChoice;
    public static String a_dChoice;
    private final String[] sortingMethods = {"By time", "By opponent name (alphabetical sort)", "By opponent level", "By game result (win/lose)"};
    private final String[] acdc = {"Ascending", "Descending"};
    private final String[] wl = {"WIN first", "LOSE first"};
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        sorting.getItems().addAll(sortingMethods);
        ac_dc.getItems().addAll(acdc);
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from " + table_name + ";");
            HISTORY historyToAdd;
            while (resultSet.next()) {
                historyToAdd = new HISTORY(resultSet.getInt("id"), resultSet.getString("state"),
                        resultSet.getString("outcome"), resultSet.getString("opponent"),
                        resultSet.getInt("oppLevel"), resultSet.getString("time"));
                history.add(historyToAdd);
            }
            System.out.println("History arrayList filled from database successfully.");
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        sorting.setOnAction(this::getSorting);
        ac_dc.setOnAction(this::getAcDc);
    }
    boolean SortingChosen;
    boolean ADChosen;
    public void getSorting(ActionEvent actionEvent){
        String mySorting = sorting.getValue();
        if(mySorting.isEmpty()){
            //show alert
            SortingChosen = false;
            return;
        }
        switch(mySorting){
            case "By time":{
                SortingChoice = "time";
                SortingChosen = true;
                showTableDialog();
                break;
            }
            case "By opponent name (alphabetical sort)":{
                SortingChoice = "opp";
                SortingChosen = true;
                showTableDialog();
                break;
            }
            case "By opponent level":{
                SortingChoice = "level";
                SortingChosen = true;
                showTableDialog();
                break;
            }
            case "By game result (win/lose)":{
                SortingChoice = "result";
                ac_dc.getItems().removeAll(acdc);
                ac_dc.getItems().addAll(wl);
                SortingChosen = true;
                showTableDialog();
                break;
            }
        }
    }
    public void getAcDc(ActionEvent actionEvent){
        String MyAcDc = ac_dc.getValue();
        switch (MyAcDc){
            case "Ascending":
                a_dChoice = "a";
                ADChosen = true;
                showTableDialog();
                break;
            case "Descending":
                a_dChoice = "d";
                ADChosen = true;
                showTableDialog();
                break;
            case "WIN first":
                a_dChoice = "w";
                ADChosen = true;
                showTableDialog();
                break;
            case "LOSE first":
                a_dChoice = "l";
                ADChosen = true;
                showTableDialog();
                break;
            default:
                //show alert
                ADChosen = false;
        }
    }
    public void showTableDialog(){
        if(ADChosen && SortingChosen){
            if(SortingChoice.equals("time")){
                if(a_dChoice.equals("a"))
                    Collections.sort(history, Comparator.comparingInt(HISTORY::getNumber));
                if(a_dChoice.equals("d"))
                    Collections.sort(history, Comparator.comparingInt(HISTORY::getNumber).reversed());
            }
            else if(SortingChoice.equals("opp")){
                if(a_dChoice.equals("a"))
                    Collections.sort(history, Comparator.comparing(HISTORY::getOpponent));
                if(a_dChoice.equals("d"))
                    Collections.sort(history, Comparator.comparing(HISTORY::getOpponent).reversed());
            }
            else if(SortingChoice.equals("level")){
                if(a_dChoice.equals("a"))
                    Collections.sort(history, Comparator.comparingInt(HISTORY::getOpponentLevel));
                if(a_dChoice.equals("d"))
                    Collections.sort(history, Comparator.comparingInt(HISTORY::getOpponentLevel).reversed());
            }
            else if(SortingChoice.equals("result")){
                if(a_dChoice.equals("w"))
                    Collections.sort(history, Comparator.comparing(HISTORY::getState).reversed());
                if(a_dChoice.equals("l"))
                    Collections.sort(history, Comparator.comparing(HISTORY::getState));
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/table.fxml"));
                Stage tableStage = new Stage();
                tableStage.setScene(new Scene(loader.load()));
                tableStage.initModality(Modality.APPLICATION_MODAL);
                tableStage.setTitle("GAME HISTORY TABLE");
                tableStage.setResizable(false);
                tableStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load HISTORY TABLE window.");
            }
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
