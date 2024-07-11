package Controller;

import Model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import view.debutantMenu;
import view.loginMenu;
import view.mainMenu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class loginMenuController {
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button loginbutton;
    @FXML
    public Label countdownLabel;
    public static String username;
    public static User currentuser;


    private static final Map<String, UserAttempt> userAttempts = new HashMap<>();
    private Timeline countdownTimeline;

    @FXML
    public void Forgotmypass(MouseEvent mouseEvent){

        if(usernameField.getText().isEmpty()){
            showAlert(Alert.AlertType.ERROR, "ERROR", "You haven't entered your username yet.");

        }
        else if(!Controller.usernames.contains(usernameField.getText())){
            //System.out.println(usernameField.getText());
            System.out.println(Controller.usernames);
            showAlert(Alert.AlertType.ERROR, "ERROR", "This username does not exist.");
        }
        else {
            username = usernameField.getText();
            showForgotPasswordDialog();
        }
    }


    @FXML
    public void handleloginbuttonaction(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        UserAttempt attempt = userAttempts.getOrDefault(username, new UserAttempt());

        if (System.currentTimeMillis() < attempt.getNextAllowedAttemptTime()) {
            long waitTime = (attempt.getNextAllowedAttemptTime() - System.currentTimeMillis()) / 1000;
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please wait " + waitTime + " more seconds before trying again.");
            return;
        }

        if (!Controller.usernames.contains(username)) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "This username doesn't exist!");
            attempt.incrementAttempts();
            userAttempts.put(username, attempt);
            startCountdown(attempt.getWaitTime());
            return;
        }
        String correctpass = Controller.getUserByUsername(username).password;
        if (!password.equals(correctpass)) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "This username and password don't match!");
            attempt.incrementAttempts();
            userAttempts.put(username, attempt);
            startCountdown(attempt.getWaitTime());
            return;
        }
        Controller.currentuser = Controller.getUserByUsername(username);
        currentuser = Controller.getUserByUsername(username);
        attempt.resetAttempts();
        userAttempts.put(username, attempt);
        showAlert(Alert.AlertType.INFORMATION, "Login in progress", "Dear user " + username + " ! You will be logged in in a second.");
        mainMenu main=new mainMenu();
        try {
            main.start(debutantMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void startCountdown(long waitTime) {
        loginbutton.setDisable(true);
        countdownLabel.setVisible(true);
        countdownLabel.setText("Please wait: " + waitTime + " more seconds before trying again!");

        countdownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            long remainingTime = Long.parseLong(countdownLabel.getText().replaceAll("[^0-9]", "")) - 1;
            countdownLabel.setText("Please wait: " + remainingTime + " more seconds before trying again!");

            if (remainingTime <= 0) {
                countdownTimeline.stop();
                loginbutton.setDisable(false);
                countdownLabel.setVisible(false);
            }
        }));
        countdownTimeline.setCycleCount((int) waitTime);
        countdownTimeline.play();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showForgotPasswordDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/forgotPass.fxml"));
            Stage captchaStage = new Stage();
            captchaStage.setScene(new Scene(loader.load()));
            captchaStage.initModality(Modality.APPLICATION_MODAL);
            captchaStage.setTitle("Forgot Password");
            captchaStage.setResizable(false);
            captchaStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load FORGOT PASS window.");
        }
    }
    private static class UserAttempt {
        private int attempts;
        private long nextAllowedAttemptTime;

        public UserAttempt() {
            this.attempts = 0;
            this.nextAllowedAttemptTime = 0;
        }

        public void incrementAttempts() {
            attempts++;
            nextAllowedAttemptTime = System.currentTimeMillis() + (5 * attempts * 1000);
        }

        public void resetAttempts() {
            attempts = 0;
            nextAllowedAttemptTime = 0;
        }

        public long getNextAllowedAttemptTime() {
            return nextAllowedAttemptTime;
        }

        public long getWaitTime() {
            return (nextAllowedAttemptTime - System.currentTimeMillis()) / 1000;
        }
    }
}
