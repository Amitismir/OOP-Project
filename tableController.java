package Controller;

import Model.HISTORY;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import view.debutantMenu;
import view.mainMenu;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class tableController implements Initializable {
    public final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
    public final String USERNAME = "root";
    public final String PASSWORD = "138387Amitis";
    String table_name = Controller.currentuser.getUsername().toLowerCase() + "_gamehistory";
    public int pageLimit = Model.gameHistory.assignPageLimit(table_name);
    public int rowLimit;
    public ArrayList<HISTORY> history = gameHistoryController.history;
    int currentPage;
    @FXML
    private TableColumn<HISTORY, Integer> no;
    @FXML
    private TableColumn<HISTORY, String> opp;
    @FXML
    private TableColumn<HISTORY, Integer> oppLevel;
    @FXML
    private TableColumn<HISTORY, String> outcome;
    @FXML
    private TableColumn<HISTORY, String> state;
    @FXML
    private TableView<HISTORY> table;
    @FXML
    private TableColumn<HISTORY, String> time;
    @FXML
    private TextField goToPage;
    @FXML
    private Button prev;
    @FXML
    private Button next;
    @FXML
    private Button main;
    @FXML
    private CheckBox checkBox;
    private ObservableList<HISTORY> list = FXCollections.observableArrayList();
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        assignPageLimit(table_name);
        currentPage = 1;
        assignFirstPage(history);
        no.setCellValueFactory(new PropertyValueFactory<HISTORY, Integer>("number"));
        opp.setCellValueFactory(new PropertyValueFactory<HISTORY, String>("opponent"));
        oppLevel.setCellValueFactory(new PropertyValueFactory<HISTORY, Integer>("opponentLevel"));
        outcome.setCellValueFactory(new PropertyValueFactory<HISTORY, String >("outcome"));
        state.setCellValueFactory(new PropertyValueFactory<HISTORY, String>("state"));
        time.setCellValueFactory(new PropertyValueFactory<HISTORY, String>("time"));
        table.setItems(list);
    }
    @FXML
    private void onNextButtonAction(MouseEvent mouseEvent){
        if(currentPage + 1 > pageLimit) {
            showAlert(Alert.AlertType.ERROR, "ERROR", "There are no more pages left...");
            return;
        }
        currentPage++;
        assignTable(currentPage, history);
    }
    @FXML
    private void onPrevButtonAction(MouseEvent mouseEvent){
        if(currentPage-1 < 1){
            showAlert(Alert.AlertType.ERROR, "ERROR", "There is no previous page...");
            return;
        }
        currentPage--;
        assignTable(currentPage, history);
    }
    @FXML
    private void onMainButtonAction(MouseEvent mouseEvent){
        view.mainMenu mainMenu = new mainMenu();
        try {
            debutantMenu.stage.close();
            mainMenu.start(debutantMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void onCheckBoxAction(MouseEvent mouseEvent){
        if(goToPage.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, "ERROR", "Please fill the page number field first.");
            return;
        }
        if(!goToPage.getText().matches("\\d+")){
            showAlert(Alert.AlertType.ERROR, "ERROR", "Page number cannot be other than a number.");
            return;
        }
        int entered = Integer.parseInt(goToPage.getText());
        if(entered<1 || entered>pageLimit) {
            showAlert(Alert.AlertType.ERROR, "ERROR", "This page does not exist...");
            return;
        }
        currentPage = entered;
        assignTable(currentPage, history);
    }
    public void assignTable(int page, ArrayList<HISTORY> history){
        int idThresh;
        if(page*10 > rowLimit)
            idThresh = rowLimit;
        else
            idThresh = page*10;
        list.clear();
        for(int i=((page-1)*10); i<idThresh; i++){
            list.add(history.get(i));
        }
        table.setItems(list);
    }
    private void assignFirstPage(ArrayList<HISTORY> history){
        int idThreshold = 0;
        if(rowLimit<10)
            idThreshold = rowLimit;
        else
            idThreshold = 10;
        System.out.println(idThreshold);
        for(int i=0; i<idThreshold; i++){
            System.out.println(history.get(i) + "heyyy");
            list.add(history.get(i));
        }
        table.setItems(list);
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public int assignPageLimit(String tableName){
        int rowCount = 0;
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //System.out.println(table_name);
            String query = "select count(*) as total_rows from " + tableName + ";";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                rowCount = resultSet.getInt("total_rows");
            }
            rowLimit = rowCount;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return (int) Math.ceil((double) rowCount / 10);
    }
}
