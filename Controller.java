package Controller;

import Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static Model.ASCIIART.asciiList;

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
        assign();
        User userToAdd;
        //INITAILIZING THE USERS ARRAYLIST
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oop_proj", "root", "138387Amitis");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while(resultSet.next()) {
                userToAdd = new User(resultSet.getString("username"),
                        resultSet.getString("password"), resultSet.getString("nickname"),
                        resultSet.getString("securityQ"), resultSet.getString("securityANS"),
                        resultSet.getString("email"), resultSet.getInt("level"), resultSet.getInt("MaxHP"), resultSet.getInt("XP"), resultSet.getInt("coins"));

                //INITIALIZING THE USER CARDS ARRAYLIST IN CARD TIME AND IN STRING TYPE FROM ITS OWN TABLE
                String tableName = userToAdd.getUsername().toLowerCase() + "_cardtable";
                try{
                    Statement statement1 = connection.createStatement();
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
            System.out.println("Database read successfully.");
        }catch(SQLException e){
            e.printStackTrace();
        }
        //INITAILIZING THE ORDINARY CARDS ARRAYLIST
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oop_proj", "root", "138387Amitis");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from ordinary_cards;");
            while(resultSet.next()){
                Card cardToAdd = new Card(resultSet.getString("name"), resultSet.getInt("attack"), resultSet.getInt("duration"),
                        resultSet.getInt("playerDMG"), resultSet.getInt("upgradeLevel"), resultSet.getInt("upgradeCost"),
                        resultSet.getInt("timesUpgraded"), resultSet.getInt("price"),true, getCharacterByName(resultSet.getString("playerCharacter")));
                ordinarycards.add(cardToAdd);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        //INITIALIZING THE SPECIAL CARDS ARRAYLIST
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oop_proj", "root", "138387Amitis");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from special_cards;");
            while(resultSet.next()){
                Card cardToAdd = new Card(resultSet.getString("name"), resultSet.getInt("strength"), resultSet.getInt("duration"),
                        resultSet.getInt("playerDMG"), resultSet.getInt("upgradeLevel"), resultSet.getInt("upgradeCost"),
                        resultSet.getInt("timesUpgraded"), resultSet.getInt("price"),false, getCharacterByName(resultSet.getString("playerCharacter")));
                specialcards.add(cardToAdd);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
        /*
        while (true)
        {
            currentMenu.display();
            String input = scanner.nextLine();
            currentMenu = currentMenu.handleInput(input);
        }
         */
    }

    public static void assign() {
        ASCIIART A = new ASCIIART(" _______ ", "(  ___  )", "| (   ) |", "| (___) |", "|  ___  |", "| (   ) |", "| )   ( |", "|/     \\|", "         ", 'A');
        asciiList.add(A);
        ASCIIART B = new ASCIIART(" ______  ", "(  ___ \\ ", "| (   ) )", "| (__/ / ", "|  __ (  ", "| (  \\ \\ ", "| )___) )", "|/ \\___/ ", "         ", 'B');
        asciiList.add(B);
        ASCIIART C = new ASCIIART(" _______ ", "(  ____ \\", "| (    \\/", "| |      ", "| |      ", "| |      ", "| (____/\\", "(_______/", "         ", 'C');
        asciiList.add(C);
        ASCIIART D = new ASCIIART(" ______  ", "(  __  \\ ", "| (  \\  )", "| |   ) |", "| |   | |", "| |   ) |", "| (__/  )", "(______/ ", "         ", 'D');
        asciiList.add(D);
        ASCIIART E = new ASCIIART(" _______ ", "(  ____ \\", "| (    \\/", "| (__    ", "|  __)   ", "| (      ", "| (____/\\", "(_______/", "         ", 'E');
        asciiList.add(E);
        ASCIIART F = new ASCIIART(" _______ ", "(  ____ \\", "| (    \\/", "| (__    ", "|  __)   ", "| (      ", "| )      ", "|/       ", "         ", 'F');
        asciiList.add(F);
        ASCIIART G = new ASCIIART(" _______ ", "(  ____ \\", "| (    \\/", "| |      ", "| | ____ ", "| | \\_  )", "| (___) |", "(_______)", "         ", 'G');
        asciiList.add(G);
        ASCIIART H = new ASCIIART("         ", "|\\     /|", "| )   ( |", "| (___) |", "|  ___  |", "| (   ) |", "| )   ( |", "|/     \\|", "         ", 'H');
        asciiList.add(H);
        ASCIIART I = new ASCIIART("_________", "\\__   __/", "   ) (   ", "   | |   ", "   | |   ", "   | |   ", "___) (___", "\\_______/", "         ", 'I');
        asciiList.add(I);
        ASCIIART J = new ASCIIART("_________", "\\__    _/", "   ) (   ", "   |  |  ", "   |  |  ", "   |  |  ", "|\\_)  )  ", "(____/   ", "         ", 'J');
        asciiList.add(J);
        ASCIIART K = new ASCIIART(" _       ", "| \\    /\\", "|  \\  / /", "|  (_/ / ", "|   _ (  ", "|  ( \\ \\ ", "|  /  \\ \\", "|_/    \\/", "         ", 'K');
        asciiList.add(K);
        ASCIIART L = new ASCIIART(" _       ", "( \\      ", "| (      ", "| |      ", "| |      ", "| |      ", "| (____/\\", "(_______/", "         ", 'L');
        asciiList.add(L);
        ASCIIART M = new ASCIIART(" _______ ", "(       )", "| () () |", "| || || |", "| |(_)| |", "| |   | |", "| )   ( |", "|/     \\|", "         ", 'M');
        asciiList.add(M);
        ASCIIART N = new ASCIIART(" _       ", "( (    /|", "|  \\  ( |", "|   \\ | |", "| (\\ \\) |", "| | \\   |", "| )  \\  |", "|/    )_)", "         ", 'N');
        asciiList.add(N);
        ASCIIART O = new ASCIIART(" _______ ", "(  ___  )", "| (   ) |", "| |   | |", "| |   | |", "| |   | |", "| (___) |", "(_______)", "         ", 'O');
        asciiList.add(O);
        ASCIIART P = new ASCIIART(" _______ ", "(  ____ )", "| (    )|", "| (____)|", "|  _____)", "| (      ", "| )      ", "|/       ", "         ", 'P');
        asciiList.add(P);
        ASCIIART Q = new ASCIIART(" _______ ", "(  ___  )", "| (   ) |", "| |   | |", "| |   | |", "| | /\\| |", "| (_\\ \\ |", "(____\\/_)", "         ", 'Q');
        asciiList.add(Q);
        ASCIIART R = new ASCIIART(" _______ ", "(  ____ )", "| (    )|", "| (____)|", "|     __)", "| (\\ (   ", "| ) \\ \\__", "|/   \\__/", "         ", 'R');
        asciiList.add(R);
        ASCIIART S = new ASCIIART(" _______ ", "(  ____ \\", "| (    \\/", "| (_____ ", "(_____  )", "      ) |", "/\\____) |", "\\_______)", "         ", 'S');
        asciiList.add(S);
        ASCIIART T = new ASCIIART("_________", "\\__   __/", "   ) (   ", "   | |   ", "   | |   ", "   | |   ", "   | |   ", "   )_(   ", "         ", 'T');
        asciiList.add(T);
        ASCIIART U = new ASCIIART("         ", "|\\     /|", "| )   ( |", "| |   | |", "| |   | |", "| |   | |", "| (___) |", "(_______)", "         ", 'U');
        asciiList.add(U);
        ASCIIART V = new ASCIIART("         ", "|\\     /|", "| )   ( |", "| |   | |", "( (   ) )", " \\ \\_/ / ", "  \\   /  ", "   \\_/   ", "         ", 'V');
        asciiList.add(V);
        ASCIIART W = new ASCIIART("         ", "|\\     /|", "| )   ( |", "| | _ | |", "| |( )| |", "| || || |", "| () () |", "(_______)", "         ", 'W');
        asciiList.add(W);
        ASCIIART X = new ASCIIART("         ", "|\\     /|", "( \\   / )", " \\ (_) / ", "  ) _ (  ", " / ( ) \\ ", "( /   \\ )", "|/     \\|", "         ", 'X');
        asciiList.add(X);
        ASCIIART Y = new ASCIIART("         ", "|\\     /|", "( \\   / )", " \\ (_) / ", "  \\   /  ", "   ) (   ", "   | |   ", "   \\_/   ", "         ", 'Y');
        asciiList.add(Y);
        ASCIIART Z = new ASCIIART(" _______ ", "/ ___   )", "\\/   )  |", "    /   )", "   /   / ", "  /   /  ", " /   (_/\\", "(_______/", "         ", 'Z');
        asciiList.add(Z);
        ASCIIART Num1 = new ASCIIART("  __   ", " /  \\  ", " \\/) ) ", "   | | ", "   | | ", "   | | ", " __) (_", " \\____/", "       ", '1');
        asciiList.add(Num1);
        ASCIIART Num2 = new ASCIIART(" _______ ", "/ ___   )", "\\/   )  |", "    /   )", "  _/   / ", " /   _/  ", "(   (__/\\", "\\_______/", "         ", '2');
        asciiList.add(Num2);
        ASCIIART Num3 = new ASCIIART(" ______  ", "/ ___  \\ ", "\\/   \\  \\", "   ___) /", "  (___ ( ", "      ) \\", "/\\___/  /", "\\______/ ", "         ", '3');
        asciiList.add(Num3);
        ASCIIART Num4 = new ASCIIART("    ___   ", "   /   ) ", "  / /) | ", " / (_) (_ ", "(____   _)", "     ) (  ", "     | |  ", "     (_)  ", "         ", '4');
        asciiList.add(Num4);
        ASCIIART Num5 = new ASCIIART(" _______ ", " (  ____ \\", "| (    \\/", "| (____  ", "(_____ \\ ", "      ) )", "/\\____) )", "\\______/ ", "         ", '5');
        asciiList.add(Num5);
        ASCIIART Num6 = new ASCIIART("  ______ ", " / ____ \\", "| (    \\/", "| (____  ", "|  ___ \\ ", "| (   ) )", "( (___) )", " \\_____/ ", "         ", '6');
        asciiList.add(Num6);
        ASCIIART Num7 = new ASCIIART(" ______  ", "/ ___  \\ ", "\\/   )  )", "    /  / ", "   /  /  ", "  /  /   ", " /  /    ", " \\_/     ", "         ", '7');
        asciiList.add(Num7);
        ASCIIART Num8 = new ASCIIART("  _____  ", " / ___ \\ ", "( (___) )", " \\     / ", " / ___ \\ ", "( (   ) )", "( (___) )", " \\_____/ ", "         ", '8');
        asciiList.add(Num8);
        ASCIIART Num9 = new ASCIIART("  _____  ", " / ___ \\ ", "( (   ) )", "( (___) |", " \\____  |", "      ) |", "/\\____) )", "\\______/ ", "         ", '9');
        asciiList.add(Num9);
        ASCIIART Num0 = new ASCIIART(" _______ ", "(  __   )", "| (  )  |", "| | /   |", "| (/ /) |", "|   / | |", "|  (__) |", "(_______)", "         ", '0');
        asciiList.add(Num0);
    }

}
