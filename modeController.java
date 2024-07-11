package Controller;

import Model.User;
import Model.character;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import view.*;

import java.util.Optional;

public class modeController {
    @FXML
    private Label gamemode;
    public static User guest;

    @FXML
    public void initialize() {
        gamemode.setEffect(new Glow(0.8));
        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(javafx.scene.paint.Color.web("#cb63ab"));
        borderGlow.setWidth(30);
        borderGlow.setHeight(30);
        borderGlow.setSpread(0.5);
        gamemode.setEffect(borderGlow);
        System.out.println("Initializing MainMenuController...");
    }

    public void Main(MouseEvent mouseEvent) {

        mainMenu main = new mainMenu();
        try {
            main.start(debutantMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    public void Twoplayers(MouseEvent mouseEvent) {
        // Create the custom dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Enter Username and Password");
        dialog.setHeaderText("Please enter the username and password for the second player:");

        // Set the button types
        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(passwordField, 1, 1);

        // Enable/Disable login button depending on whether a username was entered
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using Java 8 lambda syntax)
        usernameField.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default
        Platform.runLater(usernameField::requestFocus);

        // Convert the result to a username-password pair when the login button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(usernameField.getText(), passwordField.getText());
            }
            return null;
        });

        // Show the dialog and capture the result
        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {
            String username = usernamePassword.getKey();
            String password = usernamePassword.getValue();

            // Validate the username and password (dummy validation)
            if (isValid(username, password)) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Login successful!");
                guest = Controller.getUserByUsername(username);
                charcater signupmenu = new charcater();
                try {
                    signupmenu.start(debutantMenu.stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password. Please try again.");
            }
        });
    }

    // Dummy validation method (replace with your actual validation logic)
    private boolean isValid(String username, String password) {
        if(!Controller.usernames.contains(username))
            return false;
        else {
            User user = Controller.getUserByUsername(username);
            if(user.password.matches(password))
            {
                return true;
            }
        }
        return false;
    }



    public void   Betting(MouseEvent mouseEvent)
    {
        Platform.exit();

    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
