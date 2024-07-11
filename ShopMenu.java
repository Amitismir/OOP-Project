package Model;

import Controller.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class ShopMenu implements Menu{
    Scanner Sc = new Scanner(System.in);
    public final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
    public final String USERNAME = "root";
    public final String PASSWORD = "138387Amitis";
    @Override
    public void display(){
        System.out.println("Welcome to the shop! Please choose an option:");
        System.out.println("1. See all of the cards I don't have");
        System.out.println("2. Buy a card");
        System.out.println("3. Upgrade a card");
        System.out.println("4. See my current deck");
        System.out.println("5. Go back to the play menu");
    }
    @Override
    public Menu handleInput(String input){
        switch (input){
            case "1":
                printAllCards(Controller.currentuser);
                System.out.println();
                System.out.println("Now choose an option: 1. Go back to the shop / 2. Go back to main menu");
                switch (Sc.nextLine()) {
                    case "1":
                        System.out.println("Back to the shop...");
                        return this;
                    default:
                        System.out.println("Back to main menu...");
                        return new MainMenu();
                }
            case "2":
                System.out.println("Enter the card name: ");
                String inputCardName = Sc.nextLine();
                if(buyCard(inputCardName, Controller.currentuser))
                    System.out.println("CARD PURCHASE SUCCESSFUL");
                else
                    System.out.println("CARD PURCHASE UNSUCCESSFUL");
                System.out.println("Now choose an option: 1. Go back to the shop / 2. Go back to main menu");
                switch (Sc.nextLine()){
                    case "1":
                        System.out.println("Back to the shop...");
                        return this;
                    default:
                        System.out.println("Back to main menu...");
                        return new MainMenu();
                }
            case "3":
                System.out.println("Enter the card name: ");
                inputCardName = Sc.nextLine();
                if(upgradeCard(inputCardName, Controller.currentuser))
                    System.out.println("UPGRADE SUCCESSFUL");
                else
                    System.out.println("UPGRADE UNSUCCESSFUL");
                System.out.println("Now choose an option: 1. Go back to the shop / 2. Go back to main menu");
                switch (Sc.nextLine()){
                    case "1":
                        System.out.println("Back to the shop...");
                        return this;
                    default:
                        System.out.println("Back to main menu...");
                        return new MainMenu();
                }
            case "4":
                seeMyCurrentDeck(Controller.currentuser);
                System.out.println("Now choose an option: 1. Go back to the shop / 2. Go back to main menu");
                switch (Sc.nextLine()){
                    case "1":
                        System.out.println("Back to the shop...");
                        return this;
                    default:
                        System.out.println("Back to main menu...");
                        return new MainMenu();
                }
            case "5":
                System.out.println("Back to main menu...");
                return new MainMenu();
            default:
                System.out.println("INVALID INPUT");
                return new ShopMenu();
        }
    }
    public void printAllCards(User user){
        Card cForEasyIterate;
        for(int i=0; i<Controller.ordinarycards.size(); i++){
            cForEasyIterate = Controller.ordinarycards.get(i);
            if(!user.cardNamesInString.contains(cForEasyIterate.getName())) {

                System.out.println((i + 1) + ". Name: " + cForEasyIterate.getName() + "  Strength: " + cForEasyIterate.getstrength()
                        + "  DUR: " + cForEasyIterate.getDuration() + "  DMG: " + cForEasyIterate.getPlayerdamage()
                        + "  UpgradeLevel: " + cForEasyIterate.upgradelevel);
                System.out.println("    " + "  Upgrade Cost:" + cForEasyIterate.getUpgradecost() + "  TimesUpgraded: "
                        + cForEasyIterate.getTimesupgraded() + "  Price: " + cForEasyIterate.getPrice() + "  Type: " + cForEasyIterate.getType());

            }
        }
        for(int i=0; i<Controller.specialcards.size(); i++) {
            cForEasyIterate = Controller.specialcards.get(i);
            if (!user.cardNamesInString.contains(cForEasyIterate.getName())) {
                System.out.println((i + 1) + ". Name: " + cForEasyIterate.getName() + "  Strength: " + cForEasyIterate.getstrength()
                        + "  DUR: " + cForEasyIterate.getDuration() + "  DMG: " + cForEasyIterate.getPlayerdamage()
                        + "  UpgradeLevel: " + cForEasyIterate.upgradelevel);
                System.out.println("    " + "  Upgrade Cost:" + cForEasyIterate.getUpgradecost() + "  TimesUpgraded: "
                        + cForEasyIterate.getTimesupgraded() + "  Price: " + cForEasyIterate.getPrice() + "  Type: " + cForEasyIterate.getType());

            }
        }
    }
    public boolean buyCard(String inputCardName, User user){
        Card requested = new Card();
        boolean found = false;
        for(int i=0; i<Controller.ordinarycards.size(); i++){
            if(Objects.equals(Controller.ordinarycards.get(i).getName(), inputCardName)){
                requested = Controller.ordinarycards.get(i);
                found = true;
                break;
            }
        }
        for(int i=0; i<Controller.specialcards.size() && !found; i++){
            if(Objects.equals(Controller.specialcards.get(i).getName(), inputCardName)){
                requested = Controller.specialcards.get(i);
                found = true;
                break;
            }
        }
        if(!found){
            System.out.println("Such card name does not exist.");
            return false;
        }
        if(user.cardNamesInString.contains(requested.getName())){
            System.out.println("You already have this card.");
            return false;
        }
        if(requested.getPrice() > user.getCoins()){
            System.out.println("You do not have enough coins to buy the card.");
            return false;
        }
        //Now there is no problem with buying the card:
        //change the number of coins
            int currentCoins = user.getCoins();
            user.setCoins(currentCoins - requested.getPrice());
        //change the user's coins in DB
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String query = "update users set coins = " + user.getCoins() + " where username = \"" + user.getUsername() + "\";";
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
                System.out.println("User's coins changed in database.");
                System.out.println(rowsAffected + "(s) row affected.");
            }catch(SQLException e){
                e.printStackTrace();
            }
        //change the array lists of user cards : CardNames in String and in CardType
            user.cardsInCardType.add(requested);
            user.cardNamesInString.add(requested.getName());
        //insert into the table of user cards in DB
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String tableName = user.getUsername() + "_cardtable";
            String query = "insert into " + tableName + " (name, strength, duration, playerDMG, upgradeLevel, upgradeCost, timesUpgraded, price, type, playerCharacter)"
                    + " values " + "(\"" + requested.getName() + "\", " + requested.getstrength() + ", " + requested.getDuration() + ", " + requested.getPlayerdamage()
                    + ", "+ requested.getUpgradelevel() + ", " + requested.getUpgradecost() + ", " + requested.getTimesupgraded() + ", " + requested.getPrice()
                    + ", \"" + requested.getType() + "\", \"" + requested.getPlayerCharacter().getName() +"\");";
            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("User's card table in DB changed successfully.");
            System.out.println(rowsAffected + "(s) row affected.");
        }catch(SQLException e){
            e.printStackTrace();
        }
        //change the user's cards in DB
            String addedToDB = "";
            for(int i=0; i<user.cardNamesInString.size(); i++){
                addedToDB += user.cardNamesInString.get(i);
                if(i != user.cardNamesInString.size()-1)
                    addedToDB += ",";
            }
            /*
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String query = "update users set cards = \"" + addedToDB + "\" WHERE username = \"" + user.getUsername() + "\";";
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
                System.out.println("User's card list changed in database.");
                System.out.println(rowsAffected + "(s) row affected.");
            }catch(SQLException e){
                e.printStackTrace();
            }
             */
        return true;
    }
    public boolean upgradeCard(String inputCardName, User user){
        Card requested = new Card();
        boolean foundInGeneral = false;
        boolean specialCard = false;
        for(int i=0; i<Controller.ordinarycards.size(); i++){
            if(Objects.equals(Controller.ordinarycards.get(i).getName(), inputCardName)){
                foundInGeneral = true;
                break;
            }
        }
        for(int i=0; i<Controller.specialcards.size() && !foundInGeneral; i++){
            if(Objects.equals(Controller.specialcards.get(i).getName(), inputCardName)){
                specialCard = true;
                foundInGeneral = true;
                break;
            }
        }
        if(!foundInGeneral){
            System.out.println("This is not a valid card name.");
            return false;
        }
        boolean found = false;
        for(int i=0; i<user.cardsInCardType.size(); i++){
            if(Objects.equals(user.cardsInCardType.get(i).getName(), inputCardName)){
                requested = user.cardsInCardType.get(i);
                found = true;
                break;
            }
        }
        if(!found){
            System.out.println("This card does not exist in your deck.");
            return false;
        }
        if(specialCard){
            System.out.println("You cannot upgrade special cards.");
            return false;
        }
        if(requested.getUpgradecost() > user.getCoins()){
            System.out.println("You do not have enough coins to upgrade this card.");
            return false;
        }
        if(requested.getUpgradelevel() > user.getLevel()){
            System.out.println("Your level is not high enough to upgrade this card.");
            return false;
        }

        //The user can upgrade the card now
        //set the new upgradeCost and strength for the card
        int newUpgradeCost = (int)Math.ceil(1.25 * requested.getUpgradecost());
        int newStrength = requested.getstrength()+1;
        int newTimesUpgraded = requested.getTimesupgraded()+1;

        //Change the user coins both in arrayList and DB
        int newUserCoins = user.getCoins() - requested.getUpgradecost();
        user.setCoins(newUserCoins);
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String query = "update users set coins = " + newUserCoins + " where username = \"" + user.getUsername() + "\";";
            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("User's coins modified in DB successfully.");
            System.out.println(rowsAffected + "row(s) affected.");
        }catch(SQLException e){
            e.printStackTrace();
        }

        //Change the number of times the card was upgraded both in user's cards arrayList and cards table in DB
        //Change the cards strength and upgrade cost in 1. User's cards arrayList, 2. User's card table in DB
        requested.setTimesupgraded(newTimesUpgraded);
        requested.setStrength(newStrength);
        requested.setUpgradecost(newUpgradeCost);
        String tableName = user.getUsername() + "card_table";
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String query = "update " + tableName + " set strength = " + newStrength + " where name = \"" + requested.getName() + "\";";
            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Card's strength in user's cards table in DB was modified successfully.");
            System.out.println(rowsAffected + "row(s) affected.");

            query = "update " + tableName + " set upgradeCost " + newUpgradeCost + " where name = \"" + requested.getName() + "\";";
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Card's upgrade cost was modified successfully.");
            System.out.println(rowsAffected + "row(s) affected.");

            query = "update " + tableName + " set timesUpgraded " + newTimesUpgraded + " where name = \"" + requested.getName() + "\";";
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Card's timesUpgraded was modified successfully.");
            System.out.println(rowsAffected + "row(s) affected.");

        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }
    public void seeMyCurrentDeck(User user){
        Card card;
        for(int i=0; i<user.cardsInCardType.size(); i++){
            card = user.cardsInCardType.get(i);
            //System.out.println(card.getTimesupgraded());
            System.out.println((i+1) + ". NAME: " + card.getName() + "  STRENGTH(ATTACK): " + card.getstrength() + "  DUR: " + card.getDuration());
            System.out.println("    DMG: " + card.getPlayerdamage() + "  TIMES UPGRADED: " + card.getTimesupgraded() + " TYPE: " + card.getType());
            //" / UPGRADE LEVEL: " + card.getUpgradelevel() + " / UPGRADE COST: " + card.getUpgradecost() + " / PRICE: " + card.getPrice()
        }
    }
}
