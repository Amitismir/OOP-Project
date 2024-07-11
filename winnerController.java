package Controller;

import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.debutantMenu;
import view.levelup;
import view.mainMenu;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

//All of the tasks of changing coins, xp, check for level up and assignments of new game histories are handled in this menu
//What we need from the game menu: check whose HP is zero and return them as loser, and the other user as winner

public class winnerController implements Initializable {
    final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
    final String USERNAME = "root";
    final String PASSWORD = "138387Amitis";
    String query;   int rowsAffected;
    public static User winner;
    public static User loser;
    @FXML
    private Label loserUser;
    @FXML
    private Label winnerUser;
    @FXML
    private Label winnerCoin;   private int winnerCoinDiff;
    @FXML
    private Label winnerXP;     private int winnerXPDiff;
    @FXML
    private Label loserCoin;    private int loserCoinDiff;
    @FXML
    private Label loserXP;      private int loserXPdiff;
    @FXML
    private ImageView bg;
    public static ArrayList<User> toBeLeveledUp = new ArrayList<>();
    @Override
    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle){
        winnerUser.setText(winner.getUsername());
        loserUser.setText(loser.getUsername());
        changeWinnerInfo(winner);
        changeLoserInfo(loser);
    }
    private void changeWinnerInfo(User user){
        //assign differences and labels + change in user object
        int oldCoins = user.getCoins();
        int oldXP = user.getXP();
        user.setCoins(oldCoins + calculateCoins(winner.getDAMAGE(), loser.getDAMAGE(), winner.getHP()));
        winnerCoinDiff = user.getCoins()-oldCoins;
        String outcome = "coins+" + winnerCoinDiff;
        user.setXP(calculateXP(oldXP + winner.getDAMAGE(), loser.getDAMAGE(), winner.getHP(), true));
        winnerXPDiff = user.getXP()-oldXP;
        winnerCoin.setText(String.valueOf(winnerCoinDiff));
        winnerXP.setText(String.valueOf(winnerXPDiff));

        //change in coins ang xp in db
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();

            query = "update users set coins = " + user.getCoins()  + " where username = \"" + user.username + "\";";
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Coins of winner updated in DB.");
            System.out.println(rowsAffected + " row(s) affected.");

            query = "update users set XP = " + user.getXP()  + " where username = \"" + user.username + "\";";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("XP of winner updated in DB.");
            System.out.println(rowsAffected + " row(s) affected.");

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        //ADD A GAME HISTORY
        //getting current time:
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = currentDateTime.format(formatter);

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            query = "insert into " + user.getUsername() + "_gamehistory " + "(state, outcome, opponent, oppLevel, time)" +
                    " values (\"WIN\", \"" + outcome + "\", \"" + loser.getUsername() + "\", " + loser.getLevel() + ", \"" + time + "\");";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Data was added to the games history of winner in DB.");
            System.out.println(rowsAffected + "row(s) affected.");
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    private void changeLoserInfo(User user){
        //assign differences ang labels + change user object
        user.setCoins(user.getCoins()-2);
        loserCoinDiff = 2;
        String outcome = "coin-" + loserCoinDiff;
        int oldXP = user.getXP();
        user.setXP(oldXP+calculateXP(winner.getDAMAGE(), loser.getDAMAGE(), winner.getHP(), false));
        loserXPdiff = user.getXP()-oldXP;
        loserXP.setText(String.valueOf(loserXPdiff));
        loserCoin.setText(String.valueOf(loserCoinDiff));

        //change in coins and xp db
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();

            query = "update users set coins = " + user.getCoins()  + " where username = \"" + user.username + "\";";
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Coins of loser updated in DB.");
            System.out.println(rowsAffected + " row(s) affected.");

            query = "update users set XP = " + user.getXP()  + " where username = \"" + user.username + "\";";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("XP of loser updated in DB.");
            System.out.println(rowsAffected + " row(s) affected.");

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }

        //ADD A GAME HISTORY
        //getting current time:
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = currentDateTime.format(formatter);

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            query = "insert into " + user.getUsername() + "_gamehistory " + "(state, outcome, opponent, oppLevel, time)" +
                    " values (\"LOSE\", \"" + outcome + "\", \"" + winner.getUsername() + "\", " + winner.getLevel() + ", \"" + time + "\");";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Data was added to the games history of loser in DB.");
            System.out.println(rowsAffected + "row(s) affected.");
        }catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    @FXML
    private void checkLevelUp(MouseEvent mouseEvent){
        toBeLeveledUp.clear();
        if (winner.getXP() >= 500 * winner.getLevel()) {
            winner.setLevel(winner.getLevel()+1);
            winner.setXP(0);
            winner.setMaxHP(100 + (winner.getLevel() - 1) * 50);
            winner.setCoins(winner.getCoins()+50);
            toBeLeveledUp.add(winner);

            //update level and coins in database
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement statement = conn.createStatement();
                query = "update users set coins = " + winner.getCoins() + " where username = \"" + winner.getUsername() + "\";";
                int rowsAffected = statement.executeUpdate(query);
                System.out.println("Coins assigned as award in DB");
                System.out.println(rowsAffected + " row(s) affected.");

                query = "update users set level = " + winner.getLevel() + " where username = \"" + winner.getUsername() + "\"";
                rowsAffected = statement.executeUpdate(query);
                System.out.println("Level updated in DB");
                System.out.println(rowsAffected + " row(s) affected.");

                query = "update users set MaxHP = " + winner.getMaxHP() + " where username = \"" + winner.getUsername() + "\"";
                rowsAffected = statement.executeUpdate(query);
                System.out.println("MaxHP updated in DB");
                System.out.println(rowsAffected + " row(s) affected.");
            }catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
        if(loser.getXP() >= 500 * loser.getLevel()){
            loser.setLevel(loser.getLevel()+1);
            loser.setXP(0);
            loser.setMaxHP(100 + (loser.getLevel() - 1) * 50);
            loser.setCoins(loser.getCoins()+50);
            toBeLeveledUp.add(loser);

            //update level and coins in database
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement statement = conn.createStatement();
                query = "update users set coins = " + loser.getCoins() + " where username = \"" + loser.getUsername() + "\";";
                int rowsAffected = statement.executeUpdate(query);
                System.out.println("Coins assigned as award in DB");
                System.out.println(rowsAffected + " row(s) affected.");

                query = "update users set level = " + winner.getLevel() + " where username = \"" + loser.getUsername() + "\"";
                rowsAffected = statement.executeUpdate(query);
                System.out.println("Level updated in DB");
                System.out.println(rowsAffected + " row(s) affected.");

                query = "update users set MaxHP = " + loser.getMaxHP() + " where username = \"" + loser.getUsername() + "\"";
                rowsAffected = statement.executeUpdate(query);
                System.out.println("MaxHP updated in DB");
                System.out.println(rowsAffected + " row(s) affected.");
            }catch(SQLException sqlException){
                sqlException.printStackTrace();
            }
        }
        if(toBeLeveledUp.isEmpty()){
            view.mainMenu mainMenu = new mainMenu();
            try{
                mainMenu.start(debutantMenu.stage);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else{
            view.levelup levelup = new levelup();
            try{
                levelup.start(debutantMenu.stage);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    int calculateCoins(int damagePlayer1, int damagePlayer2, int healthPowerWinner) {
        if(healthPowerWinner < 40)
            return (damagePlayer1 + damagePlayer2) * healthPowerWinner;
        else
            return Math.abs(damagePlayer1 - damagePlayer2) * healthPowerWinner;

    }
    int calculateXP(int damagePlayer1, int damagePlayer2, int healthPowerWinner, boolean isWinner) {
        System.out.println(damagePlayer1);
        System.out.println(damagePlayer2);
        System.out.println(healthPowerWinner);
        System.out.println(isWinner);
        if (isWinner) {
            return (damagePlayer1 + damagePlayer2) * healthPowerWinner * 2;
        } else {
            return (int) Math.ceil((damagePlayer1 + damagePlayer2) * healthPowerWinner);
        }
    }
}
