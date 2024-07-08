package Controller;

import Model.SignupMenu;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class forgotPassController {
    final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
    final String USERNAME = "root";
    final String PASSWORD = "138387Amitis";
    @FXML
    public Label question;
    @FXML
    public TextField answer;
    @FXML
    public PasswordField newPass;
    @FXML
    public PasswordField confirmPass;
    @FXML
    public Button random;
    @FXML
    public Button submit;
    @FXML
    public void randomGenerate(){
        String randomPassword = SignupMenu.randomgenerator();
        newPass.setText(randomPassword);
        confirmPass.setText(randomPassword);
        showAlert(Alert.AlertType.INFORMATION, "Random Password Generated", "Your random password is: " + randomPassword);
    }
    @FXML
    public void submitClicked(){
        if(!answer.getText().equals(Controller.getUserByUsername(loginMenuController.username).securityANS)){
            showAlert(Alert.AlertType.ERROR, "ERROR", "Security answer not correct");
            return;
        }
        if(validateInput(newPass.getText(), confirmPass.getText())){
            //change password in arrayList
            for(int i=0; i<Controller.users.size(); i++){
                if(Controller.users.get(i).getUsername().equals(loginMenuController.username)){
                    Controller.users.get(i).setPassword(newPass.getText());
                    break;
                }
            }
            //change password in DB
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement statement = conn.createStatement();
                String query = "update users set password = \"" + newPass.getText() + "\" where username = \"" + loginMenuController.username + "\";";
                int rowsAffected = statement.executeUpdate(query);
                showAlert(Alert.AlertType.INFORMATION, "Database Update", "Password updated in database successfully.\n" + rowsAffected + " row(s) affected.");
            }catch (SQLException sqlException){
                showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to update password in database.");
                sqlException.printStackTrace();
            }
        }
    }
    @FXML
    private void initialize() {
        switch(Controller.getUserByUsername(loginMenuController.username).securityQ){
            case "1":
                question.setText("What's your father's name?");
                break;
            case "2":
                question.setText("What's your favorite color?");
                break;
            case "3":
                question.setText("What was the name of your first pet?");
                break;
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean validateInput(String password, String confirmPass) {
        if (password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Password field is empty!");
            return false;
        }
        if (confirmPass.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Password Confirmation field is empty!");
            return false;
        }
        if (password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Your password is less than 8 characters!");
            return false;
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])(?!.*\\s).{8,20}$")) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Your password should contain at least a small and Capital letter and a special character!");
            return false;
        }
        if (!password.equals(confirmPass)) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Your confirm password differs from the original one!");
            return false;
        }
        return true;
    }
}
