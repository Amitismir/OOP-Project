package Model;

import Controller.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProfileMenu implements Menu{
    @Override
    public void display() {
        System.out.println("===Profile Menu===");
        System.out.println("1.Show information");
        System.out.println("2.Profile change");
        System.out.println("3.Return to Main Menu");

    }

    @Override
    public Menu handleInput(String input) {
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        switch (input) {
            case "1":
                //Probably cards and game things should be shown as well
                System.out.println("-Username: " + Controller.currentuser.username);
                System.out.println("-Password: " + Controller.currentuser.password);
                System.out.println("-Email: " + Controller.currentuser.email);
                System.out.println("-Nickname: " + Controller.currentuser.nickname);
                System.out.println("-Coins: " + Controller.currentuser.getCoins());
                System.out.println("-XP: " + Controller.currentuser.getXP());
                System.out.println("-Level: " + Controller.currentuser.getLevel());
                System.out.println("-MaxHP: " +  Controller.currentuser.getMaxHP());
                return new ProfileMenu();
            case "2":
                input = scanner.nextLine();
                stop = false;
                while (!stop)
                {
                    if (input.matches("Profile change -u (?<username>.*)")&&!stop) {
                        Matcher matcher = getCommandMatcher(input, "Profile change -u (?<username>.*)");
                        matcher.find();
                        String username = matcher.group("username");
                        if (Controller.usernames.contains(username)) {
                            System.out.println("This username already exists!");
                            input = scanner.nextLine();
                            continue;
                        }
                        if(!username.matches("[a-zA-Z0-9_]+")&&!stop)
                        {
                            System.out.println("Incorrect format for username!");
                            input = scanner.nextLine();
                            continue;
                        }
                        for(int i = 0; i < Controller.usernames.size(); i++)
                        {
                            if(Controller.usernames.get(i).equals(Controller.currentuser.username))
                            {
                                Controller.usernames.remove(i);
                            }
                        }
                        changeAttrInDB_General(username, Controller.currentuser.getUsername(), "USERNAME");
                        //System.out.println("Username changed successfully!");
                        Controller.currentuser.setUsername(username);
                        Controller.usernames.add(username);
                        stop = true;
                        return new ProfileMenu();


                    }
                    else if (input.matches("Profile change -n (?<nickname>.*)")&&!stop) {
                        Matcher matcher = getCommandMatcher(input, "Profile change -n (?<nickname>.*)");
                        matcher.find();
                        String nickname = matcher.group("nickname");
                        if (Controller.nicknames.contains(nickname)) {
                            System.out.println("This nickname already exists!");
                            input = scanner.nextLine();
                            continue;
                        }
                        if(!nickname.matches("[a-zA-Z0-9_]+")&&!stop)
                        {
                            System.out.println("Incorrect format for nickname!");
                            input = scanner.nextLine();
                            continue;
                        }
                        for(int i = 0; i < Controller.nicknames.size(); i++)
                        {
                            if(Controller.nicknames.get(i).equals(Controller.currentuser.nickname))
                            {
                                Controller.nicknames.remove(i);
                            }
                        }
                        changeAttrInDB_General(nickname, Controller.currentuser.getUsername(), "NICKNAME");
                        //System.out.println("Nickname changed successfully!");
                        Controller.currentuser.setNickname(nickname);
                        Controller.nicknames.add(nickname);
                        stop = true;
                        return new ProfileMenu();

                    }
                    else if (input.matches("Profile change password -o (?<oldpassword>.*) -n (?<newpass>.*)")&&!stop) {
                        Matcher matcher = getCommandMatcher(input, "Profile change password -o (?<oldpassword>.*) -n (?<newpass>.*)");
                        matcher.find();
                        String neww = matcher.group("newpass");
                        String oldd = matcher.group("oldpassword");
                        if(!oldd.equals(Controller.currentuser.getPassword()))
                        {
                            System.out.println("Current password is incorrect!");
                            input = scanner.nextLine();
                            continue;
                        }
                        if(neww.length() < 8 )
                        {
                            System.out.println("Your password is less than 8 characters!");
                            input = scanner.nextLine();
                            continue;
                        }
                        if (!neww.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])(?!.*\\s)(?!\\d).{8,20}$")) {
                            System.out.println("Your password should contain at least a small and Capital letter and a special character!");
                            input = scanner.nextLine();
                            continue;
                        }
                        if(neww.equals(oldd))
                        {
                            System.out.println("Please enter a new password!");
                            input = scanner.nextLine();
                            continue;
                        }
                        Controller.assign();
                        String str = "lala";
                        String Ans = "";
                        while (!Objects.equals(str, Ans)){
                            str = "lala";
                            Ans = "";
                            ArrayList<ASCIIART> selectedMembers = new ArrayList<>();
                            Collections.shuffle(ASCIIART.asciiList);
                            for (int j = 0; j < 4; j++) {
                                selectedMembers.add(ASCIIART.asciiList.get(j));
                            }
                            for(int j=0; j<9; j++){
                                for(int k=0; k<4; k++){
                                    System.out.print(selectedMembers.get(k).lines[j]);
                                }
                                System.out.println();
                            }

                            for(int j=0; j<4; j++){
                                Ans+= selectedMembers.get(j).value;
                            }

                            Scanner Sc = new Scanner(System.in);
                            str = Sc.nextLine();


                        }
                        System.out.println("Checked Successfully!");
                        System.out.println("Please enter your new password again:");
                        String answer = "";
                        answer = scanner.nextLine();
                        if(!answer.equals(neww))
                        {
                            System.out.println("You have entered your new password wrongly!");
                            input = scanner.nextLine();
                            continue;
                        }
                        changeAttrInDB_General(neww, Controller.currentuser.getUsername(), "PASSWORD");
                        Controller.currentuser.setPassword(neww);
                        //System.out.println("Password changed successfully!");
                        stop = true;
                        return new ProfileMenu();

                    }
                   else if(input.matches("Profile change -e (?<email>.*)") && !stop)
                    {
                        Matcher matcher = getCommandMatcher(input,"Profile change -e (?<email>.*)");
                        matcher.find();
                        String email = matcher.group("email");
                        if(!email.matches("(?<name>.*)@gmail.com"))
                        {
                            System.out.println("Your email address is invalid!");
                            input = scanner.nextLine();
                            continue;

                        }
                        changeAttrInDB_General(email, Controller.currentuser.getUsername(), "EMAIL");
                        //System.out.println("Email changed successfully!");
                        Controller.currentuser.setEmail(email);
                        stop = true;


                    }
                   else {
                        System.out.println("invalid command!");
                        input = scanner.nextLine();
                        continue;
                    }
                }
            case "3":
                return new MainMenu();
            default:
                System.out.println("Invalid Option");
                return this;
        }

    }
    private void changeAttrInDB_General(String newAttr, String Username, String changeType){
        try{
            final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
            final String USERNAME = "root";
            final String PASSWORD = "138387Amitis";
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String query = "";
            switch (changeType){
                case "USERNAME":
                    changeTableName(Username, newAttr);
                    query = "update users set username = \"" + newAttr + "\" where username = \"" + Username + "\";";
                    break;
                case "NICKNAME":
                    query = "update users set nickname = \"" + newAttr + "\" where username = \"" + Username + "\";";
                    break;
                case "PASSWORD":
                    query = "update users set password = \"" + newAttr + "\" where username = \"" + Username + "\";";
                    break;
                case "EMAIL":
                    query = "update users set email = \"" + newAttr + "\" where username = \"" + Username + "\";";
                    break;
            }
            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            switch (changeType){
                case "USERNAME":
                    System.out.println("Username changed successfully!");

                    break;
                case "NICKNAME":
                    System.out.println("Nickname changed successfully!");
                    break;
                case "PASSWORD":
                    System.out.println("Password changed successfully!");
                    break;
                case "EMAIL":
                    System.out.println("Email changed successfully!");
                    break;
            }
            System.out.println(rowsAffected + " row(s) affected.");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private Matcher getCommandMatcher(String input, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }
    private void changeTableName(String prevUsername, String newUsername){
        try{
            final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
            final String USERNAME = "root";
            final String PASSWORD = "138387Amitis";
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String query = "";
            Statement statement;
            int rowsAffected;
            query = "alter table " + prevUsername.toLowerCase() + "_cardtable" + " rename to " + newUsername + "_cardtable;";
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Card table name modified successfully!");
            System.out.println(rowsAffected + " row(s) affected.");
            query = "alter table " + prevUsername.toLowerCase() + "_gamehistory" + " rename to " + newUsername + "_gamehistory;";
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Game history table name modified successfully!");
            System.out.println(rowsAffected + " row(s) affected.");
            query = "alter table " + prevUsername.toLowerCase() + "_challengerequest" + " rename to " + newUsername + "_challengerequest;";
            statement = conn.createStatement();
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Challenge request table name modified successfully!");
            System.out.println(rowsAffected + " row(s) affected.");
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}
