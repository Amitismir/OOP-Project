package Controller;

import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.debutantMenu;
import view.loginMenu;
import view.mainMenu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class editprofilecontroller {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField nicknameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;

    public User currentUser = loginMenuController.currentuser;
    private String oldusename = currentUser.username;

    public void initialize() {
        if (currentUser != null) {
            usernameField.setText(currentUser.getUsername());
            nicknameField.setText(currentUser.getNickname());
            emailField.setText(currentUser.getEmail());
        }
    }
    String newUsername = currentUser.getUsername();
    String newNickname = currentUser.getNickname();
    String newPassword = currentUser.password;
    String newEmail = currentUser.email;
    @FXML
    private void handleSave() {
        newUsername = usernameField.getText().trim();
        newNickname = nicknameField.getText().trim();
        newPassword = passwordField.getText().trim();
        newEmail = emailField.getText().trim();

        boolean changesMade = false;

        if (!newUsername.equals(currentUser.getUsername())) {
            if (!validateUsername(newUsername)) {
                return;
            }
            currentUser.setUsername(newUsername);
            changesMade = true;
        }

        if (!newNickname.equals(currentUser.getNickname())) {
            if (!validateNickname(newNickname)) {
                return;
            }
            currentUser.setNickname(newNickname);
            changesMade = true;
        }
        if (!newPassword.isEmpty()) {
            if (!validatePassword(newPassword)) {
                return;
            }
            currentUser.setPassword(newPassword);
            changesMade = true;
        }

        if (!newEmail.equals(currentUser.getEmail())) {
            if (!validateEmail(newEmail)) {
                return;
            }
            currentUser.setEmail(newEmail);
            changesMade = true;
        }

        if (changesMade) {
            updateDatabase(currentUser);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Profile Updated");
            alert.setHeaderText(null);
            alert.setContentText("Changes have been saved successfully.");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                mainMenu main=new mainMenu();
                try {
                    main.start(debutantMenu.stage);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } else {
            mainMenu main=new mainMenu();
            try {
                main.start(debutantMenu.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void handleCancel() {
        mainMenu main=new mainMenu();
        try {
            main.start(debutantMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateUsername(String username) {
        if(Controller.usernames.contains(username))
        {
            showAlert("Invalid Username", "This username already exists!");
            return false;
        }
        if (!username.matches("[a-zA-Z0-9_]+")) {
            showAlert("Invalid Username", "Username must contain only letters, numbers, or underscores.");
            return false;
        }
        return true;
    }

    private boolean validateNickname(String nickname) {
        if(Controller.nicknames.contains(nickname))
        {
            showAlert("Invalid Nickname", "This nickname already exists!");
            return false;
        }
        if (!nickname.matches("[a-zA-Z0-9_]+")) {
            showAlert("Invalid Nickname", "Nickname must contain only letters, numbers, or underscores.");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.length() < 8) {
            showAlert("Invalid Password", "Password must be at least 8 characters long.");
            return false;
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])(?!.*\\s)(?!\\d).{8,20}$")) {
            showAlert("Invalid Password", "Your password should contain at least a small and Capital letter and a special character!");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        if (!email.matches("\\b[A-Za-z0-9._%+-]+@gmail\\.com\\b")) {
            showAlert("Invalid Email", "Email address must be in valid format (e.g., example@gmail.com).");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void updateDatabase(User user) {
        System.out.println(currentUser.username + "name");
        System.out.println(currentUser.nickname + " nick");
        System.out.println(currentUser.password + " pass");
        System.out.println(currentUser.email + " email");

        try{
            final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
            final String USERNAME = "root";
            final String PASSWORD = "138387Amitis";
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String query = "";
            query = "update users set username = \"" + currentUser.username + "\" where username = \"" + oldusename + "\";";
            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Username changed successfully!");
            System.out.println(rowsAffected + " row(s) affected.");
            query = "update users set nickname = \"" + currentUser.nickname + "\" where username = \"" + oldusename + "\";";
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Nickname changed successfully!");
            System.out.println(rowsAffected + " row(s) affected.");
            query = "update users set password = \"" + currentUser.password + "\" where username = \"" + oldusename + "\";";
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Password changed successfully!");
            System.out.println(rowsAffected + " row(s) affected.");
            query = "update users set email = \"" + currentUser.email + "\" where username = \"" + oldusename + "\";";
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Email changed successfully!");
            System.out.println(rowsAffected + " row(s) affected.");
            query = "alter table " + oldusename.toLowerCase() + "_cardtable" + " rename to " + currentUser.getUsername() + "_cardtable;";
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Card table name modified successfully!");
            System.out.println(rowsAffected + " row(s) affected.");
            query = "alter table " + oldusename.toLowerCase() + "_gamehistory" + " rename to " + currentUser.getUsername() + "_gamehistory;";
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Game history table name modified successfully!");
            System.out.println(rowsAffected + " row(s) affected.");
            query = "alter table " + oldusename.toLowerCase() + "_challengerequest" + " rename to " + currentUser.getUsername() + "_challengerequest;";
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Challenge request table name modified successfully!");
            System.out.println(rowsAffected + " row(s) affected.");
        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Updating database for user: " + user.getUsername());
    }

}
