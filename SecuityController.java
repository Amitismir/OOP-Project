package Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.debutantMenu;
import view.security;
import view.signupMenu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class SecuityController {

    @FXML
    public  TextField securityqField;
    @FXML
    public  TextField securityaField;
    @FXML
    private void handlesecuritybutton()
    {
        String securityq = securityqField.getText();
        String securitya = securityaField.getText();
        if(!securityq.matches("1") && !securityq.matches("2") && !securityq.matches("3"))
        {
            showAlert(AlertType.ERROR, "Form Error!", "You should choose a number between 1 to 3");
            return;

        }

        showCaptchaDialog();
        try{
            final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
            final String USERNAME = "root";
            final String PASSWORD = "138387Amitis";
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String query = "update users set securityQ = \"" + securityqField.getText() + "\" where username = \"" + signupMenuController.user.username + "\"";
            Statement statement1 = conn.createStatement();
            int rowsAffected = statement1.executeUpdate(query);
            query = "update users set securityANS = \"" + securityaField.getText() + "\" where username = \"" + signupMenuController.user.username + "\"";
            statement1 = conn.createStatement();
            rowsAffected = statement1.executeUpdate(query);


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void showCaptchaDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/captcha.fxml"));
            Stage captchaStage = new Stage();
            captchaStage.setScene(new Scene(loader.load()));
            captchaStage.initModality(Modality.APPLICATION_MODAL);
            captchaStage.setTitle("CAPTCHA Verification");
            captchaStage.setResizable(false);
            captchaStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load CAPTCHA window.");
        }
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
