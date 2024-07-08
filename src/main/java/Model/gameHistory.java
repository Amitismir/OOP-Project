package Model;

import Controller.Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class gameHistory implements Menu {
    final static String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
    final static String USERNAME = "root";
    final static String PASSWORD = "138387Amitis";
    int currentPage = 1;
    String table_name = Controller.currentuser.getUsername().toLowerCase() + "_gamehistory";
    public int pageLimit = assignPageLimit(table_name);
    public static int rowLimit;
    public ArrayList<HISTORY> history = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    public void display() {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            ResultSet resultSet;
            resultSet = statement.executeQuery("select * from " + table_name + ";");
            HISTORY historyToAdd;
            while (resultSet.next()) {
                historyToAdd = new HISTORY(resultSet.getInt("id"), resultSet.getString("state"),
                        resultSet.getString("outcome"), resultSet.getString("opponent"),
                        resultSet.getInt("oppLevel"), resultSet.getString("time"));
                history.add(historyToAdd);
            }
            System.out.println("History table filled from database successfully.");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        System.out.println("Welcome to game history menu!");
        System.out.println("Choose the sorting method: (or type 'exit' to return to the main menu.");
        System.out.println("1. By time");
        System.out.println("2. By opponent name (alphabetical sort)");
        System.out.println("3. By opponent level");
        System.out.println("4. By game result (win/lose)");
    }
    public Menu handleInput (String input){
        if(rowLimit == 0){
            System.out.println("ERROR: YOUR GAME HISTORY TABLE IS EMPTY");
            System.out.println("You will be returned to the main menu now. Play a game to add to your history.");
            return new MainMenu();
        }
        while(!input.matches("exit")){
            if(input.matches("1") || input.toLowerCase().matches("by time")){
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                input = sc.nextLine();
                while(true){
                    if(input.matches("1") || input.toLowerCase().matches("ascending")){
                        Collections.sort(history, Comparator.comparingInt(HISTORY::getNumber));
                        System.out.println("PAGE 1: ");
                        printFirstPage(history);
                        currentPage = 1;
                        System.out.println();
                        System.out.println("You can either choose one of the syntax below or enter 'exit' to go back to the main menu:");
                        System.out.println("1. next page");
                        System.out.println("2. previous page");
                        System.out.println("3. show page <pageNumber>");
                        System.out.println("4. have a challenge with <username>");
                        while(!input.toLowerCase().matches("exit")){
                            if(input.toLowerCase().matches("next page")){
                                if(currentPage + 1 > pageLimit){
                                    System.out.println("There are no more pages left...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage++;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("previous page")){
                                if(currentPage-1 < 1){
                                    System.out.println("There is no previous page...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage--;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("show page (?<number>\\d+)")){
                                Matcher Mt = getCommandMatcher(input, "show page ?(<number>\\d+)");
                                Mt.find();
                                //handle existence
                                int entered = Integer.parseInt(Mt.group("number"));
                                if(entered<1 || entered>pageLimit){
                                    System.out.println("This page does not exist...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage = entered;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("have a challenge with (?<username>.+)")){
                                Matcher Mt = getCommandMatcher(input, "have a challenge with (?<username>.+)");
                                Mt.find();
                                System.out.println(Mt.group("username"));
                                try{
                                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                    Statement statement = conn.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select * from " + table_name + " where opponent = \"" + Mt.group("username") + "\";");
                                    if(!resultSet.next()){
                                        System.out.println("There is no such username among your previous opponents.");
                                        continue;
                                    }
                                    String tableName = Mt.group("username") + "_challengerequest";
                                    Statement statement1 = conn.createStatement();
                                    String query = "insert into " + tableName + " (username) values (\"" + Controller.currentuser.getUsername() + "\");";
                                    int rowsAffected = statement1.executeUpdate(query);
                                    System.out.println("Request saved in DB successfully.");
                                    System.out.println(rowsAffected + " row(s) affected.");
                                    System.out.println("Whenever the user logs in, they will see your challenge request.");

                                }catch(SQLException sqlException){
                                    sqlException.printStackTrace();
                                }
                                input = sc.nextLine();
                            }
                            else{
                                System.out.println("INVALID INPUT. TRY AGAIN:");
                                input = sc.nextLine();
                            }
                        }
                        break;
                    }
                    else if(input.matches("2") || input.toLowerCase().matches("descending")){
                        Collections.sort(history, Comparator.comparingInt(HISTORY::getNumber).reversed());
                        System.out.println("PAGE 1: ");
                        printFirstPage(history);
                        currentPage = 1;
                        System.out.println();
                        System.out.println("You can either choose one of the syntax below or enter 'exit' to go back to the main menu:");
                        System.out.println("1. next page");
                        System.out.println("2. previous page");
                        System.out.println("3. show page <pageNumber>");
                        System.out.println("4. have a challenge with <username>");
                        while(!input.toLowerCase().matches("exit")){
                            if(input.toLowerCase().matches("next page")){
                                if(currentPage + 1 > pageLimit){
                                    System.out.println("There are no more pages left...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage++;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("previous page")){
                                if(currentPage-1 < 1){
                                    System.out.println("There is no previous page...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage--;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("show page (?<number>\\d+)")){
                                Matcher Mt = getCommandMatcher(input, "show page ?(<number>\\d+)");
                                Mt.find();
                                //handle existence
                                int entered = Integer.parseInt(Mt.group("number"));
                                if(entered<1 || entered>pageLimit){
                                    System.out.println("This page does not exist...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage = entered;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("have a challenge with (?<username>.+)")){
                                Matcher Mt = getCommandMatcher(input, "have a challenge with (?<username>.+)");
                                Mt.find();
                                System.out.println(Mt.group("username"));
                                try{
                                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                    Statement statement = conn.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select * from " + table_name + " where opponent = \"" + Mt.group("username") + "\";");
                                    if(!resultSet.next()){
                                        System.out.println("There is no such username among your previous opponents.");
                                        continue;
                                    }
                                    String tableName = Mt.group("username") + "_challengerequest";
                                    Statement statement1 = conn.createStatement();
                                    String query = "insert into " + tableName + " (username) values (\"" + Controller.currentuser.getUsername() + "\");";
                                    int rowsAffected = statement1.executeUpdate(query);
                                    System.out.println("Request saved in DB successfully.");
                                    System.out.println(rowsAffected + " row(s) affected.");
                                    System.out.println("Whenever the user logs in, they will see your challenge request.");

                                }catch(SQLException sqlException){
                                    sqlException.printStackTrace();
                                }
                                input = sc.nextLine();
                            }
                            else{
                                System.out.println("INVALID INPUT. TRY AGAIN:");
                                input = sc.nextLine();
                            }
                        }
                        break;
                    }
                    else{
                        System.out.println("INVALID INPUT");
                        System.out.println("Try again:");
                        input = sc.nextLine();
                    }
                }
                break;
            }
            else if(input.matches("2") || input.toLowerCase().matches("by opponent name")){
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                input = sc.nextLine();
                while(true){
                    if(input.matches("1") || input.toLowerCase().matches("ascending")){
                        Collections.sort(history, Comparator.comparing(HISTORY::getOpponent));
                        System.out.println("PAGE 1: ");
                        printFirstPage(history);
                        currentPage = 1;
                        System.out.println();
                        System.out.println("You can either choose one of the syntax below or enter 'exit' to go back to the main menu:");
                        System.out.println("1. next page");
                        System.out.println("2. previous page");
                        System.out.println("3. show page <pageNumber>");
                        System.out.println("4. have a challenge with <username>");
                        while(!input.toLowerCase().matches("exit")){
                            if(input.toLowerCase().matches("next page")){
                                if(currentPage + 1 > pageLimit){
                                    System.out.println("There are no more pages left...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage++;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("previous page")){
                                if(currentPage-1 < 1){
                                    System.out.println("There is no previous page...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage--;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("show page (?<number>\\d+)")){
                                Matcher Mt = getCommandMatcher(input, "show page ?(<number>\\d+)");
                                Mt.find();
                                //handle existence
                                int entered = Integer.parseInt(Mt.group("number"));
                                if(entered<1 || entered>pageLimit){
                                    System.out.println("This page does not exist...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage = entered;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("have a challenge with (?<username>.+)")){
                                Matcher Mt = getCommandMatcher(input, "have a challenge with (?<username>.+)");
                                Mt.find();
                                System.out.println(Mt.group("username"));
                                try{
                                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                    Statement statement = conn.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select * from " + table_name + " where opponent = \"" + Mt.group("username") + "\";");
                                    if(!resultSet.next()){
                                        System.out.println("There is no such username among your previous opponents.");
                                        continue;
                                    }
                                    String tableName = Mt.group("username") + "_challengerequest";
                                    Statement statement1 = conn.createStatement();
                                    String query = "insert into " + tableName + " (username) values (\"" + Controller.currentuser.getUsername() + "\");";
                                    int rowsAffected = statement1.executeUpdate(query);
                                    System.out.println("Request saved in DB successfully.");
                                    System.out.println(rowsAffected + " row(s) affected.");
                                    System.out.println("Whenever the user logs in, they will see your challenge request.");

                                }catch(SQLException sqlException){
                                    sqlException.printStackTrace();
                                }
                                input = sc.nextLine();
                            }
                            else{
                                System.out.println("INVALID INPUT. TRY AGAIN:");
                                input = sc.nextLine();
                            }
                        }
                        break;
                    }
                    else if(input.matches("2") || input.toLowerCase().matches("descending")){
                        Collections.sort(history, Comparator.comparing(HISTORY::getOpponent).reversed());
                        System.out.println("PAGE 1: ");
                        printFirstPage(history);
                        currentPage = 1;
                        System.out.println();
                        System.out.println("You can either choose one of the syntax below or enter 'exit' to go back to the main menu:");
                        System.out.println("1. next page");
                        System.out.println("2. previous page");
                        System.out.println("3. show page <pageNumber>");
                        System.out.println("4. have a challenge with <username>");
                        while(!input.toLowerCase().matches("exit")){
                            if(input.toLowerCase().matches("next page")){
                                if(currentPage + 1 > pageLimit){
                                    System.out.println("There are no more pages left...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage++;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("previous page")){
                                if(currentPage-1 < 1){
                                    System.out.println("There is no previous page...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage--;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("show page (?<number>\\d+)")){
                                Matcher Mt = getCommandMatcher(input, "show page ?(<number>\\d+)");
                                Mt.find();
                                //handle existence
                                int entered = Integer.parseInt(Mt.group("number"));
                                if(entered<1 || entered>pageLimit){
                                    System.out.println("This page does not exist...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage = entered;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("have a challenge with (?<username>.+)")){
                                Matcher Mt = getCommandMatcher(input, "have a challenge with (?<username>.+)");
                                Mt.find();
                                System.out.println(Mt.group("username"));
                                try{
                                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                    Statement statement = conn.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select * from " + table_name + " where opponent = \"" + Mt.group("username") + "\";");
                                    if(!resultSet.next()){
                                        System.out.println("There is no such username among your previous opponents.");
                                        continue;
                                    }
                                    String tableName = Mt.group("username") + "_challengerequest";
                                    Statement statement1 = conn.createStatement();
                                    String query = "insert into " + tableName + "(username) values (\"" + Controller.currentuser.getUsername() + "\");";
                                    int rowsAffected = statement1.executeUpdate(query);
                                    System.out.println("Request saved in DB successfully.");
                                    System.out.println(rowsAffected + " row(s) affected.");
                                    System.out.println("Whenever the user logs in, they will see your challenge request.");

                                }catch(SQLException sqlException){
                                    sqlException.printStackTrace();
                                }
                                input = sc.nextLine();
                            }
                            else{
                                System.out.println("INVALID INPUT. TRY AGAIN:");
                                input = sc.nextLine();
                            }
                        }
                        break;
                    }
                    else{
                        System.out.println("INVALID INPUT");
                        System.out.println("Try again:");
                        input = sc.nextLine();
                    }
                }
                break;
            }
            else if(input.matches("3") || input.toLowerCase().matches("by opponent level")){
                System.out.println("1. Ascending");
                System.out.println("2. Descending");
                input = sc.nextLine();
                while(true){
                    if(input.matches("1") || input.toLowerCase().matches("ascending")){
                        Collections.sort(history, Comparator.comparingInt(HISTORY::getOpponentLevel));
                        System.out.println("PAGE 1: ");
                        printFirstPage(history);
                        currentPage = 1;
                        System.out.println();
                        System.out.println("You can either choose one of the syntax below (do not enter a number) or enter 'exit' to go back to the main menu:");
                        System.out.println("1. next page");
                        System.out.println("2. previous page");
                        System.out.println("3. show page <pageNumber>");
                        System.out.println("4. have a challenge with <username>");
                        while(!input.toLowerCase().matches("exit")){
                            if(input.toLowerCase().matches("next page")){
                                if(currentPage + 1 > pageLimit){
                                    System.out.println("There are no more pages left...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage++;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("previous page")){
                                if(currentPage-1 < 1){
                                    System.out.println("There is no previous page...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage--;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("show page (?<number>\\d+)")){
                                Matcher Mt = getCommandMatcher(input, "show page ?(<number>\\d+)");
                                Mt.find();
                                //handle existence
                                int entered = Integer.parseInt(Mt.group("number"));
                                if(entered<1 || entered>pageLimit){
                                    System.out.println("This page does not exist...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage = entered;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("have a challenge with (?<username>.+)")){
                                Matcher Mt = getCommandMatcher(input, "have a challenge with (?<username>.+)");
                                Mt.find();
                                System.out.println(Mt.group("username"));
                                try{
                                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                    Statement statement = conn.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select * from " + table_name + " where opponent = \"" + Mt.group("username") + "\";");
                                    if(!resultSet.next()){
                                        System.out.println("There is no such username among your previous opponents.");
                                        continue;
                                    }
                                    String tableName = Mt.group("username") + "_challengerequest";
                                    Statement statement1 = conn.createStatement();
                                    String query = "insert into " + tableName + "(username) values (\"" + Controller.currentuser.getUsername() + "\");";
                                    int rowsAffected = statement1.executeUpdate(query);
                                    System.out.println("Request saved in DB successfully.");
                                    System.out.println(rowsAffected + " row(s) affected.");
                                    System.out.println("Whenever the user logs in, they will see your challenge request.");

                                }catch(SQLException sqlException){
                                    sqlException.printStackTrace();
                                }
                                input = sc.nextLine();
                            }
                            else{
                                System.out.println("INVALID INPUT. TRY AGAIN:");
                                input = sc.nextLine();
                            }
                        }
                        break;
                    }
                    else if(input.matches("2") || input.toLowerCase().matches("descending")){
                        Collections.sort(history, Comparator.comparingInt(HISTORY::getOpponentLevel).reversed());
                        System.out.println("PAGE 1: ");
                        printFirstPage(history);
                        currentPage = 1;
                        System.out.println();
                        System.out.println("You can either choose one of the syntax below or enter 'exit' to go back to the main menu:");
                        System.out.println("1. next page");
                        System.out.println("2. previous page");
                        System.out.println("3. show page <pageNumber>");
                        System.out.println("4. have a challenge with <username>");
                        while(!input.toLowerCase().matches("exit")){
                            if(input.toLowerCase().matches("next page")){
                                if(currentPage + 1 > pageLimit){
                                    System.out.println("There are no more pages left...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage++;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("previous page")){
                                if(currentPage-1 < 1){
                                    System.out.println("There is no previous page...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage--;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("show page (?<number>\\d+)")){
                                Matcher Mt = getCommandMatcher(input, "show page ?(<number>\\d+)");
                                Mt.find();
                                //handle existence
                                int entered = Integer.parseInt(Mt.group("number"));
                                if(entered<1 || entered>pageLimit){
                                    System.out.println("This page does not exist...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage = entered;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("have a challenge with (?<username>.+)")){
                                Matcher Mt = getCommandMatcher(input, "have a challenge with (?<username>.+)");
                                Mt.find();
                                System.out.println(Mt.group("username"));
                                try{
                                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                    Statement statement = conn.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select * from " + table_name + " where opponent = \"" + Mt.group("username") + "\";");
                                    if(!resultSet.next()){
                                        System.out.println("There is no such username among your previous opponents.");
                                        continue;
                                    }
                                    String tableName = Mt.group("username") + "_challengerequest";
                                    Statement statement1 = conn.createStatement();
                                    String query = "insert into " + tableName + " (username) values (\"" + Controller.currentuser.getUsername() + "\");";
                                    int rowsAffected = statement1.executeUpdate(query);
                                    System.out.println("Request saved in DB successfully.");
                                    System.out.println(rowsAffected + " row(s) affected.");
                                    System.out.println("Whenever the user logs in, they will see your challenge request.");

                                }catch(SQLException sqlException){
                                    sqlException.printStackTrace();
                                }
                                input = sc.nextLine();
                            }
                            else{
                                System.out.println("INVALID INPUT. TRY AGAIN:");
                                input = sc.nextLine();
                            }
                        }
                        break;
                    }
                    else{
                        System.out.println("INVALID INPUT");
                        System.out.println("Try again:");
                        input = sc.nextLine();
                    }
                }
                break;
            }
            else if(input.matches("4") || input.toLowerCase().matches("by game result")){
                System.out.println("1. WIN first");
                System.out.println("2. LOSE first");
                input = sc.nextLine();
                while(true){
                    if(input.matches("1") || input.toLowerCase().matches("win first")){
                        Collections.sort(history, Comparator.comparing(HISTORY::getState).reversed());
                        System.out.println("PAGE 1: ");
                        printFirstPage(history);
                        currentPage = 1;
                        System.out.println();
                        System.out.println("You can either choose one of the syntax below or enter 'exit' to go back to the main menu:");
                        System.out.println("1. next page");
                        System.out.println("2. previous page");
                        System.out.println("3. show page <pageNumber>");
                        System.out.println("4. have a challenge with <username>");
                        while(!input.toLowerCase().matches("exit")){
                            if(input.toLowerCase().matches("next page")){
                                if(currentPage + 1 > pageLimit){
                                    System.out.println("There are no more pages left...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage++;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("previous page")){
                                if(currentPage-1 < 1){
                                    System.out.println("There is no previous page...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage--;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("show page (?<number>\\d+)")){
                                Matcher Mt = getCommandMatcher(input, "show page ?(<number>\\d+)");
                                Mt.find();
                                //handle existence
                                int entered = Integer.parseInt(Mt.group("number"));
                                if(entered<1 || entered>pageLimit){
                                    System.out.println("This page does not exist...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage = entered;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("have a challenge with (?<username>.+)")){
                                Matcher Mt = getCommandMatcher(input, "have a challenge with (?<username>.+)");
                                Mt.find();
                                System.out.println(Mt.group("username"));
                                try{
                                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                    Statement statement = conn.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select * from " + table_name + " where opponent = \"" + Mt.group("username") + "\";");
                                    if(!resultSet.next()){
                                        System.out.println("There is no such username among your previous opponents.");
                                        continue;
                                    }
                                    String tableName = Mt.group("username") + "_challengerequest";
                                    Statement statement1 = conn.createStatement();
                                    String query = "insert into " + tableName + " (username) values (\"" + Controller.currentuser.getUsername() + "\");";
                                    int rowsAffected = statement1.executeUpdate(query);
                                    System.out.println("Request saved in DB successfully.");
                                    System.out.println(rowsAffected + " row(s) affected.");
                                    System.out.println("Whenever the user logs in, they will see your challenge request.");

                                }catch(SQLException sqlException){
                                    sqlException.printStackTrace();
                                }
                                input = sc.nextLine();
                            }
                            else{
                                System.out.println("INVALID INPUT. TRY AGAIN:");
                                input = sc.nextLine();
                            }
                        }
                        break;
                    }
                    else if(input.matches("2") || input.toLowerCase().matches("lose first")){
                        Collections.sort(history, Comparator.comparing(HISTORY::getState));
                        System.out.println("PAGE 1: ");
                        printFirstPage(history);
                        currentPage = 1;
                        System.out.println();
                        System.out.println("You can either choose one of the syntax below or enter 'exit' to go back to the main menu:");
                        System.out.println("1. next page");
                        System.out.println("2. previous page");
                        System.out.println("3. show page <pageNumber>");
                        System.out.println("4. have a challenge with <username>");
                        while(!input.toLowerCase().matches("exit")){
                            if(input.toLowerCase().matches("next page")){
                                if(currentPage + 1 > pageLimit){
                                    System.out.println("There are no more pages left...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage++;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("previous page")){
                                if(currentPage-1 < 1){
                                    System.out.println("There is no previous page...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage--;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("show page (?<number>\\d+)")){
                                Matcher Mt = getCommandMatcher(input, "show page ?(<number>\\d+)");
                                Mt.find();
                                //handle existence
                                int entered = Integer.parseInt(Mt.group("number"));
                                if(entered<1 || entered>pageLimit){
                                    System.out.println("This page does not exist...");
                                    System.out.println("Try again:");
                                    input = sc.nextLine();
                                    continue;
                                }
                                currentPage = entered;
                                printTable(currentPage, history);
                                input =  sc.nextLine();
                            }
                            else if(input.toLowerCase().matches("have a challenge with (?<username>.+)")){
                                Matcher Mt = getCommandMatcher(input, "have a challenge with (?<username>.+)");
                                Mt.find();
                                System.out.println(Mt.group("username"));
                                try{
                                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                    Statement statement = conn.createStatement();
                                    ResultSet resultSet = statement.executeQuery("select * from " + table_name + " where opponent = \"" + Mt.group("username") + "\";");
                                    if(!resultSet.next()){
                                        System.out.println("There is no such username among your previous opponents.");
                                        continue;
                                    }
                                    String tableName = Mt.group("username") + "_challengerequest";
                                    Statement statement1 = conn.createStatement();
                                    String query = "insert into " + tableName + "(username) values (\"" + Controller.currentuser.getUsername() + "\");";
                                    int rowsAffected = statement1.executeUpdate(query);
                                    System.out.println("Request saved in DB successfully.");
                                    System.out.println(rowsAffected + " row(s) affected.");
                                    System.out.println("Whenever the user logs in, they will see your challenge request.");

                                }catch(SQLException sqlException){
                                    sqlException.printStackTrace();
                                }
                                input = sc.nextLine();
                            }
                            else{
                                System.out.println("INVALID INPUT. TRY AGAIN:");
                                input = sc.nextLine();
                            }
                        }
                        break;
                    }
                    else{
                        System.out.println("INVALID INPUT");
                        System.out.println("Try again:");
                        input = sc.nextLine();
                    }
                }
                break;
            }
            else{
                System.out.println("INVALID INPUT");
                System.out.println("Try again:");
                input = sc.nextLine();
            }
        }
        return new MainMenu();
    }



    public void printFirstPage(ArrayList<HISTORY> history){
        System.out.println("+-----+-------+-----------------+------------------+----------+----------------------------------------+");
        System.out.println("| no. | state |     outcome     |     opponent     | oppLevel |                  time                  |");
        System.out.println("+-----+-------+-----------------+------------------+----------+----------------------------------------+");
        int idThreshold = 0;
        if(rowLimit<10)
            idThreshold = rowLimit;
        else
            idThreshold = 10;
        for(int i=0; i<idThreshold; i++){
            System.out.println(String.format("| %-3s | %-5s | %-15s | %-16s | %-8s | %-38s |", history.get(i).getNumber(),
                    history.get(i).getState(), history.get(i).getOutcome(),history.get(i).getOpponent(),
                    history.get(i).getOpponentLevel(), history.get(i).getTime()));
            System.out.println("+-----+-------+-----------------+------------------+----------+----------------------------------------+");
        }
    }
    public static int assignPageLimit(String tableName){
        int rowCount = 0;
        try{
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            //System.out.println(table_name);
            String query = "select count(*) as total_rows from " + tableName + ";";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                rowCount = resultSet.getInt("total_rows");
            }
            rowLimit = rowCount;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return (int) Math.ceil((double) rowCount / 10);
    }
    public void printTable(int page, ArrayList<HISTORY> history){
        //do not say 50 to 60 handle limit
        int idThresh;
        if(page*10 > rowLimit)
            idThresh = rowLimit;
        else
            idThresh = page*10;
        System.out.println("PAGE " + page + ":");
        System.out.println("+-----+-------+-----------------+------------------+----------+----------------------------------------+");
        System.out.println("| no. | state |     outcome     |     opponent     | oppLevel |                  time                  |");
        System.out.println("+-----+-------+-----------------+------------------+----------+----------------------------------------+");
        for(int i=((page-1)*10); i<idThresh; i++){
            System.out.println(String.format("| %-3s | %-5s | %-15s | %-16s | %-8s | %-38s |", history.get(i).getNumber(),
                    history.get(i).getState(), history.get(i).getOutcome(), history.get(i).getOpponent(),
                    history.get(i).getOpponentLevel(), history.get(i).getTime()));
            System.out.println("+-----+-------+-----------------+------------------+----------+----------------------------------------+");
        }
    }
    private Matcher getCommandMatcher(String input, String regex){
        return Pattern.compile(regex).matcher(input);
    }
}
