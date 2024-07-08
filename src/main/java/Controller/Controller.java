package Controller;

import Model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Controller {
    private Menu currentMenu;
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList <String> usernames = new ArrayList<>();
    public static ArrayList<String> nicknames = new ArrayList<>();
    public static ArrayList<Card> ordinarycards = new ArrayList<>();
    public static ArrayList<Card> specialcards = new ArrayList<>();
    public static  ArrayList<String> cardnames = new ArrayList<>();
    public static User currentuser;
    public static User guest;
    public static User getUserByUsername(String username)
    {
        for(User user : users)
        {
            if(user.getUsername().equals(username))
            {
                return user;
            }
        }
        return null;
    }
    public static character getCharacterByName(String name)
    {
        switch (name){
            case "ALPHA LUPEX":
                return new AlPHALUPEX();
            case "A.N.F.O.":
                return new ANFO();
            case "TAG PUNKS":
                return new TAGPUNKS();
            case "HELIO CELON":
                return new HELIOCELON();
            default:
                return null;
        }
    }
    public static ArrayList<Integer> randomgenerator(int size)
    {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, size);
            numbers.add(randomNum);
        }
        return numbers;
    }
    public static int random()
    {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 20);
        return randomNum;
    }
    public static int randomFromBoard(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, 21);
        return randomNum;
    }
    public static int randomFromDeck(int input){
        int randomNum = ThreadLocalRandom.current().nextInt(0, input);
        return randomNum;
    }


    DebutantMenu debutantMenu = new DebutantMenu();
    SignupMenu signupMenu = new SignupMenu();
    LoginMenu loginMenu = new LoginMenu();
    public Controller()
    {
        this.currentMenu = debutantMenu;
    }

    public void run()
    {
        System.out.println("Program Starting...");
        Scanner scanner = new Scanner(System.in);
        User userToAdd;
        //INITAILIZING THE USERS ARRAYLIST
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oop_proj", "root", "138387Amitis");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
            while(resultSet.next()) {
                userToAdd = new User(resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("nickname"),
                        resultSet.getString("securityQ"), resultSet.getString("securityANS"),
                        resultSet.getString("email"), resultSet.getInt("level"), resultSet.getInt("MaxHP"), resultSet.getInt("XP"), resultSet.getInt("coins"));
                //INITIALIZING THE USER CARDS ARRAYLIST IN CARD TYPE AND IN STRING TYPE FROM ITS OWN TABLE
                String tableName = userToAdd.getUsername().toLowerCase() + "_cardtable";
                try{
                    Statement statement1  = connection.createStatement();
                    ResultSet resultSet1 = statement1.executeQuery("select * from " + tableName);
                    Boolean bool;
                    while(resultSet1.next()){
                        if(Objects.equals(resultSet1.getString("type"), "ORDINARY"))
                            bool = true;
                        else
                            bool = false;
                        userToAdd.cardsInCardType.add(new Card(resultSet1.getString("name"), resultSet1.getInt("strength"),
                                resultSet1.getInt("duration"), resultSet1.getInt("playerDMG"),
                                resultSet1.getInt("upgradeLevel"), resultSet1.getInt("upgradeCost"), resultSet1.getInt("timesUpgraded"),
                                resultSet1.getInt("price"), bool, getCharacterByName(resultSet1.getString("playerCharacter"))));
                        userToAdd.cardNamesInString.add(resultSet1.getString("name"));
                    }
                }catch(SQLException e){
                    e.printStackTrace();;
                }

                users.add(userToAdd);
                //INITIALIZING THE USERNAMES ARRAYLIST
                if(!usernames.contains(userToAdd.getUsername()))
                    usernames.add(userToAdd.getUsername());
                //INITAILIZING THE NICKNAMES ARRAYLIST
                if(!nicknames.contains(userToAdd.getNickname()))
                    nicknames.add(userToAdd.getNickname());
            }
            //TODO: TEST THE ARRAYLIST
            System.out.println("Database read successfully.");
        }catch(SQLException e){
            e.printStackTrace();
        }
        //INITAILIZING THE ORDINARY CARDS ARRAYLIST
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oop_proj", "root", "138387Amitis");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM ordinary_cards");
            while(resultSet.next()){
                Card cardToAdd = new Card(resultSet.getString("name"), resultSet.getInt("attack"), resultSet.getInt("duration"),
                        resultSet.getInt("playerDMG"), resultSet.getInt("upgradeLevel"), resultSet.getInt("upgradeCost"),
                        resultSet.getInt("timesUpgraded"), resultSet.getInt("price"),true,getCharacterByName(resultSet.getString("playerCharacter")));

                ordinarycards.add(cardToAdd);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        //INITIALIZING THE SPECIAL CARDS ARRAYLIST
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oop_proj", "root", "138387Amitis");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM special_cards");
            while(resultSet.next()){
                Card cardToAdd = new Card(resultSet.getString("name"), resultSet.getInt("strength"), resultSet.getInt("duration"),
                        resultSet.getInt("playerDMG"), resultSet.getInt("upgradeLevel"), resultSet.getInt("upgradeCost"),
                        resultSet.getInt("timesUpgraded"), resultSet.getInt("price"),false,getCharacterByName(resultSet.getString("playerCharacter")));
                specialcards.add(cardToAdd);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }




    }


}
