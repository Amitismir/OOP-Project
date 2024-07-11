package Controller;

import Model.Card;
import Model.SignupMenu;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import view.debutantMenu;
import view.security;
import view.signupMenu;

import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Model.SignupMenu.randomgenerator;


public class signupMenuController {
    final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
    final String USERNAME = "root";
    final String PASSWORD = "138387Amitis";
    public static User user;
    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;
    public Button signupButton;
    public Button random;

    @FXML
    private PasswordField confirmField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nicknameField;

    private List<String> existingUsernames = new ArrayList<>();

    @FXML
    private void handleSignupButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPass = confirmField.getText();
        String email = emailField.getText();
        String nickname = nicknameField.getText();

        if (!validateInput(username, password, confirmPass, email, nickname)) {
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oop_proj", "root", "138387Amitis");
             Statement stmt = conn.createStatement()) {
            String query = "SELECT COUNT(*) FROM users WHERE username = '" + username + "'";
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    showAlert(AlertType.ERROR, "Form Error!", "The username is already taken!");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (existingUsernames.contains(username)) {
            showAlert(AlertType.ERROR, "Form Error!", "The username is already taken!");
            return;
        }

        List<String> usernameInLowerCase = new ArrayList<>();
        for (String existingUsername : existingUsernames) {
            usernameInLowerCase.add(existingUsername.toLowerCase());
        }
        if (usernameInLowerCase.contains(username.toLowerCase())) {
            showAlert(AlertType.ERROR, "Form Error!", "There is at least one player in the game with whom your username only differs in the case of the letters.");
            return;
        }
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //Statement statement = conn.createStatement();
            String query ="INSERT INTO USERS (username, password, nickname, securityQ, securityANS, email, level, MaxHP, XP, coins)" +
                    " VALUES (?, ?, ?, ?, ?, ?, 1, 100, 0, 20)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, nickname);
            statement.setString(4, "1");
            statement.setString(5, "");
            statement.setString(6, email);
            int rowsAffected = statement.executeUpdate();

            System.out.println("Username creation in DB successful, " + rowsAffected + " rows affected.");
             user = new User(username, password, nickname,"1","",email,1,100,0,20);
            Controller.users.add(user);
            Controller.usernames.add(username);
            Controller.nicknames.add(nickname);


            //ASSIGNING A STARTER PACK:
            ArrayList<Card> StarterPack = new ArrayList<>();
            Collections.shuffle(Controller.ordinarycards);
            for(int i=0; i<15; i++){
                StarterPack.add(Controller.ordinarycards.get(i));
            }
            Collections.shuffle(Controller.specialcards);
            for(int i=0; i<5; i++){
                StarterPack.add(Controller.specialcards.get(i));
            }

            user.cardsInCardType = StarterPack;

            for(int i=0; i<StarterPack.size(); i++){
                user.cardNamesInString.add(StarterPack.get(i).getName());
            }

            String tableName = user.getUsername().toLowerCase() + "_cardtable";
            query = "create table " + tableName + "(name char(25), strength int, duration int, playerDMG int, upgradeLevel int," +
                    " upgradeCost int, timesUpgraded int, price int, type char(10), playerCharacter char(20));";
            Statement statement1 = conn.createStatement();
            rowsAffected = statement1.executeUpdate(query);
            System.out.println("Personal card table created successfully in DB.");

            Card card;

            for(int i=0; i<StarterPack.size(); i++){
                card = StarterPack.get(i);
                query = "insert into " + tableName + " (name, strength, duration, playerDMG, upgradeLevel, upgradeCost, timesUpgraded, price, type, playerCharacter)"
                        + " values " + "(\"" + card.getName() + "\", " + card.getstrength() + ", " + card.getDuration() + ", " + card.getPlayerdamage()
                        + ", "+ card.getUpgradelevel() + ", " + card.getUpgradecost() + ", " + card.getTimesupgraded() + ", " + card.getPrice()
                        + ", \"" + card.getType() + "\", \"" + card.getPlayerCharacter().getName() +"\");";
                statement1 = conn.createStatement();
                rowsAffected = statement1.executeUpdate(query);
                System.out.println("Table creation and starter pack assigning successful in DB.");
                System.out.println(rowsAffected + " row(s) affected.");
            }

            //ASSIGNING A GAME HISTORY TABLE
            tableName = user.getUsername().toLowerCase() + "_gamehistory";
            query = "create table " + tableName +
                    "(id int auto_increment primary key, state char(4), outcome char(15), opponent char(16), oppLevel int, time char(40));";
            Statement statement2 = conn.createStatement();
            rowsAffected = statement2.executeUpdate(query);
            System.out.println("Personal game history table created successfully in DB.");

            //ASSIGNING A CHALLENGE REQUEST TABLE
            tableName = user.getUsername().toLowerCase() + "_challengerequest";
            query = "create table " + tableName + "(username char(20));";
            Statement statement3 = conn.createStatement();
            rowsAffected = statement3.executeUpdate(query);
            System.out.println("Challenge requests table created in DB successfully.");

        }catch(Exception e){
            e.printStackTrace();
        }

        showAlert(AlertType.INFORMATION, "Signup in Progress", "Dear user " + username + "! You have to answer the security questions now");
        security securityq = new security();
        try {
            securityq.start(debutantMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    private boolean validateInput(String username, String password, String confirmPass, String email, String nickname) {
        if (username.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Username field is empty!");
            return false;
        }
        if (password.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Password field is empty!");
            return false;
        }
        if (confirmPass.isEmpty() && !password.equals("random")) {
            showAlert(AlertType.ERROR, "Form Error!", "Password Confirmation field is empty!");
            return false;
        }
        if (email.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Email field is empty!");
            return false;
        }
        if (nickname.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Nickname field is empty!");
            return false;
        }
        if (!username.matches("[a-zA-Z0-9_]+")) {
            showAlert(AlertType.ERROR, "Form Error!", "Incorrect format for username!");
            return false;
        }
        if (password.length() < 8 && !password.equals("random")) {
            showAlert(AlertType.ERROR, "Form Error!", "Your password is less than 8 characters!");
            return false;
        }
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])(?!.*\\s).{8,20}$") && !password.equals("random")) {
            showAlert(AlertType.ERROR, "Form Error!", "Your password should contain at least a small and Capital letter and a special character!");
            return false;
        }
        if (!password.equals(confirmPass) && !password.equals("random")) {
            showAlert(AlertType.ERROR, "Form Error!", "Your confirm password differs from the original one!");
            return false;
        }
        if (!email.matches("(?<name>.*)@gmail.com")) {
            showAlert(AlertType.ERROR, "Form Error!", "Your email address is invalid!");
            return false;
        }
        return true;
    }

    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleRandomButtonAction() {
        String randomPassword = SignupMenu.randomgenerator();
        //System.out.println(randomPassword);
        passwordField.setText(randomPassword);
        confirmField.setText(randomPassword);
        showAlert(AlertType.INFORMATION, "Random Password Generated", "Your random password is: " + randomPassword);
    }
}
