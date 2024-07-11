package Model;

import Controller.Controller;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu implements Menu {
    private static Map<String, UserAttempt> userAttempts = new HashMap<>();
    final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
    final String USERNAME = "root";
    final String PASSWORD = "138387Amitis";
    @Override
    public void display() {
        System.out.println("===Login Menu===");
        System.out.println("use the syntax: user login -u <username> -p <password>");
    }

    @Override
    public Menu handleInput(String input) {
        boolean stop = false;
        boolean ok = false;
        Scanner scanner = new Scanner(System.in);
        while(!stop)
        {
            //System.out.println(input);
           // System.out.println(Controller.users.get(0).password);
            if(input.matches("user login -u (?<username>.*) -p (?<pass>.*)") && !stop)
            {
                Matcher matcher = getCommandMatcher(input, "user login -u (?<username>.*) -p (?<pass>.*)");
                matcher.find();
                String pass = matcher.group("pass");
                String username = matcher.group("username");
                //System.out.println(Controller.getUserByUsername(username).securityquestion);
                //System.out.println(Controller.getUserByUsername(username).username);
                if(!Controller.usernames.contains(username))
                {
                    System.out.println("Username doesn't exist!");
                    input = scanner.nextLine();
                    continue;
                }
                UserAttempt attempt = userAttempts.getOrDefault(username, new UserAttempt());
                if (System.currentTimeMillis() < attempt.getNextAllowedAttemptTime()) {
                    long waitTime = (attempt.getNextAllowedAttemptTime() - System.currentTimeMillis()) / 1000;
                    System.out.println("Please wait " + waitTime + " more seconds before trying again.");
                    input = scanner.nextLine();
                    continue;
                }
                if (!pass.equals(Controller.getUserByUsername(username).password)) {
                    attempt.incrementAttempts();
                    userAttempts.put(username, attempt);
                    System.out.println("Password and Username don't match! Please wait " + attempt.getWaitTime() + " seconds before trying again.");
                    input = scanner.nextLine();
                    continue;
                }

                attempt.resetAttempts();
                userAttempts.put(username, attempt);
                stop = true;
                Controller.currentuser = Controller.getUserByUsername(username);
                System.out.println("User logged in successfully!");

                String tableName = username + "_challengerequest";
                try{
                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    Statement statement = conn.createStatement();
                    ResultSet resultSet = statement.executeQuery("select * from " + tableName + ";");
                    if(!resultSet.next())
                        System.out.println("There were no challenge requests submitted for you while you were logged out.");
                    else{
                        System.out.println("While you were logged out, the following users have challenged you:");
                        while(resultSet.next()){
                            System.out.println(resultSet.getString("username"));
                        }
                    }

                }catch(SQLException sqlException){
                    sqlException.printStackTrace();
                }

            }
            else if(input.matches("Forgot my password -u (?<username>.*)") && !stop)
            {
                String answer = "";
                String neww = "";
                Matcher matcher = getCommandMatcher(input,"Forgot my password -u (?<username>.*)" );
                matcher.find();
                String username = matcher.group("username");
                User user = Controller.getUserByUsername(username);
                if(!Controller.usernames.contains(username))
                {
                    System.out.println("Username doesn't exist!");
                    input = scanner.nextLine();
                    continue;
                }
                if(Objects.requireNonNull(Controller.getUserByUsername(username)).securityQ.equals("1"))
                {
                    System.out.println("What is your father's name?");
                    answer =scanner.nextLine();
                    assert user != null;
                    if(answer.equals(user.securityANS))
                    {
                        System.out.println("Please Enter Your new Password:");
                        neww = scanner.nextLine();
                        while(!ok) {
                            if (neww.length() < 8 && !neww.matches("random")) {
                                System.out.println("Your password is less than 8 characters!");
                                neww = scanner.nextLine();

                            }
                            else if (!neww.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])(?!.*\\s)(?!\\d).{8,20}$")) {
                                System.out.println("Your password should contain at least a small and Capital letter and a special character!");
                                neww = scanner.nextLine();
                            }
                            else {
                                ok = true;
                            }
                        }
                        changePassword(neww, user);
                        input = scanner.nextLine();
                        continue;
                    }
                    else
                    {
                        System.out.println("Your answer doesn't match!");
                        input = scanner.nextLine();
                        continue;
                    }

                }
                if(Objects.requireNonNull(Controller.getUserByUsername(username)).securityQ.equals("2"))
                {
                    System.out.println("What is your favourite color?");
                    answer =scanner.nextLine();
                    assert user != null;
                    if(answer.equals(user.securityANS))
                    {
                        System.out.println("Please Enter Your new Password:");
                        neww = scanner.nextLine();
                        while(!ok) {
                            if (neww.length() < 8 && !neww.matches("random")) {
                                System.out.println("Your password is less than 8 characters!");
                                neww = scanner.nextLine();

                            }
                            else if (!neww.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])(?!.*\\s)(?!\\d).{8,20}$")) {
                                System.out.println("Your password should contain at least a small and Capital letter and a special character!");
                                neww = scanner.nextLine();

                            }
                            else {
                                ok = true;
                            }
                        }
                        changePassword(neww, user);
                        input = scanner.nextLine();
                        continue;}
                    else
                    {
                        System.out.println("Your answer doesn't match!");
                        input = scanner.nextLine();
                        continue;
                    }
                }
                if(Objects.requireNonNull(Controller.getUserByUsername(username)).securityQ.equals("3"))
                {
                    System.out.println("What was the name of your first pet?");
                    answer =scanner.nextLine();
                    assert user != null;
                    if(answer.equals(user.securityANS))
                    {
                        System.out.println("Please Enter Your new Password:");
                        neww = scanner.nextLine();
                        while(!ok) {
                            if (neww.length() < 8 && !neww.matches("random")) {
                                System.out.println("Your password is less than 8 characters!");
                                neww = scanner.nextLine();

                            }
                            else if (!neww.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])(?!.*\\s)(?!\\d).{8,20}$")) {
                                System.out.println("Your password should contain at least a small and Capital letter and a special character!");
                                neww = scanner.nextLine();

                            }
                            else {
                                ok = true;
                            }
                        }
                        changePassword(neww, user);
                        input = scanner.nextLine();
                        continue;
                    }
                    else
                    {
                        System.out.println("Your answer doesn't match!");
                        input = scanner.nextLine();
                        continue;
                    }
                }

            }
            else{
                System.out.println("Invalid command!");
                input = scanner.nextLine();
                continue;
            }
        }

        return new MainMenu();

    }
    private Matcher getCommandMatcher(String input, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
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
    private void changePassword(String newPass, User user){
        //changing password in arrayList
        user.setPassword(newPass);
        //changing password in database
        try{
            final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
            final String USERNAME = "root";
            final String PASSWORD = "138387Amitis";
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String query = "update users set password = \"" + newPass + "\" where username = \"" + user.getUsername() + "\";";
            Statement statement = conn.createStatement();
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Password Changed successfully!");
            System.out.println(rowsAffected + " row(s) affected.");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
