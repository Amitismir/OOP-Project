package Model;

import Controller.Controller;

import java.security.SecureRandom;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupMenu implements Menu {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*()_+";
    public static String randomgenerator() {
        List<Character> charList = new ArrayList<>();
        for (char c : CHARACTERS.toCharArray()) {
            charList.add(c);
        }
        List<Character> specialCharList = new ArrayList<>();
        for(char c: SPECIAL_CHARACTERS.toCharArray()){
            specialCharList.add(c);
        }
        SecureRandom random = new SecureRandom();
        Collections.shuffle(charList, random);

        StringBuilder password = new StringBuilder();
        password.append(charList.get(random.nextInt(25))); // at least one uppercase
        password.append(charList.get(random.nextInt(25) + 26)); // at least one lowercase
        password.append(charList.get(random.nextInt(9) + 52)); // at least one number
        password.append(specialCharList.get(random.nextInt(specialCharList.size()-1))); // at least one special character

        while (password.length() < 8) {
            password.append(charList.get(random.nextInt(CHARACTERS.length())));
        }

        Collections.shuffle(charList, random);
        return password.toString();
    }



    @Override
    public void display() {
        System.out.println("===Signup Menu===");
        System.out.println("use the syntax: user create username <username> password <password> confirm <confirm> email <email> nickname <nickname>");
    }

    @Override
    public Menu handleInput(String input) {
        boolean stop = false;
        Scanner scanner = new Scanner(System.in);
        while(!stop) {
            String questionchoice = "";
            if (input.matches("user create username ?(?<username>.*) password ?(?<password>.*) confirm ?(?<confirm>.*) email ?(?<email>.*) nickname ?(?<nickname>.*)") && !stop) {
                Matcher matcher = getCommandMatcher(input, "user create username ?(?<username>.*) password ?(?<password>.*) confirm ?(?<confirm>.*) email ?(?<email>.*) nickname ?(?<nickname>.*)");
                matcher.find();
               // System.out.println(matcher.group("password"));
                String pass = matcher.group("password");
                String confirm = matcher.group("confirm");
                String username = matcher.group("username");
                String email = matcher.group("email");
                String nickname = matcher.group("nickname");

                if(username.matches(""))
                {
                    System.out.println("Username field is empty!");
                    input = scanner.nextLine();
                    continue;

                }
                if(pass.matches(""))
                {
                    System.out.println("Password field is empty!");
                    input = scanner.nextLine();
                    continue;
                }
                if(confirm.matches("") && !pass.matches("random"))
                {
                    System.out.println("Password Confirmation field is empty!");
                    input = scanner.nextLine();
                    continue;
                }
                if(email.matches(""))
                {
                    System.out.println("Email field is empty!");
                    input = scanner.nextLine();
                    continue;
                }
                if(nickname.matches(""))
                {
                    System.out.println("Nickname field is empty!");
                    input = scanner.nextLine();
                    continue;
                }
                if(!username.matches("[a-zA-Z0-9_]+"))
                {
                    System.out.println("Incorrect format for username!");
                    input = scanner.nextLine();
                    continue;
                }
                try(Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/oop_proj", "root", "138387Amitis");
                    Statement stmt = conn.createStatement()) {
                        String query = "select count(*) from users where username = '" + username + "';";
                        ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        if (count > 0) {
                            System.out.println("The username is already taken!");
                            input = scanner.nextLine();
                            continue;
                        }
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
                if(Controller.usernames.contains(username))
                {
                    System.out.println("The username is already taken!");
                    input = scanner.nextLine();
                    continue;
                }
                ArrayList<String> usernameInLowerCase = new ArrayList<>();
                for(int i=0; i<Controller.usernames.size(); i++){
                    usernameInLowerCase.add(Controller.usernames.get(i).toLowerCase());
                }
                if(usernameInLowerCase.contains(username)){
                    System.out.println("There is at least one player in the game with whom your username only differs in the case of the letters.");
                    input = scanner.nextLine();
                    continue;
                }
                if(pass.length() < 8 && !pass.matches("random"))
                {
                    System.out.println("Your password is less than 8 characters!");
                    input = scanner.nextLine();
                    continue;
                }
                if (!pass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])(?!.*\\s)(?!\\d).{8,20}$") && !pass.matches("random")) {
                    System.out.println("Your password should contain at least a small and Capital letter and a special character. Also, the password must not start with a number.");
                    input = scanner.nextLine();
                    continue;
                }
                if(!pass.equals(confirm) && !pass.matches("random"))
                {
                    System.out.println("Your confirm password differs from the original one!");
                    input = scanner.nextLine();
                    continue;
                }
                if(!email.matches("(?<name>.*)@gmail.com"))
                {
                    System.out.println("Your email address is invalid!");
                    input = scanner.nextLine();
                    continue;

                }
                if(pass.matches("random"))
                {
                    if(confirm.matches(""))
                    {
                        String ok = "";
                        String RandomPassword = randomgenerator();
                        System.out.println("Your random password: " + RandomPassword);
                        System.out.println("Please enter your password:");
                        //potential bug
                        while(!ok.equals(RandomPassword)){
                            ok = scanner.nextLine();
                            if(ok.equals(RandomPassword))
                            {
                                System.out.println("You can Sign up now!");
                                input = scanner.nextLine();
                            }
                            else {
                                System.out.println("You have entered the random password incorrectly!");
                            }

                        }
                    }
                    continue;

                }
                System.out.println("User created successfully. Please choose a security question:");
                System.out.println("Use syntax: question pick q- <questionNumber> a- (answer) c- (answerConfirm)");
                Controller.usernames.add(username);
                Controller.nicknames.add(nickname);
                System.out.println("1.What is your father's name?");
                System.out.println("2.What is your favourite color?");
                System.out.println("3.What was the name of your first pet?");
                //questionchoice = "";
                String answer = "";
                String number = "";

                questionchoice = scanner.nextLine();
                if(questionchoice.matches("question pick q- (?<questionnumber>.*) a- (?<answer>.*) c- (?<answerconfirm>.*)")){

                    Matcher matcher1 = getCommandMatcher(questionchoice, "question pick q- (?<questionnumber>.*) a- (?<answer>.*) c- (?<answerconfirm>.*)");
                    matcher1.find();
                    answer = matcher1.group("answer");
                    number = matcher1.group("questionnumber");
                    //System.out.println("number = " + number);
                    stop = true;
                }
                //User user = new User(username, pass, nickname,number,answer,email);
                //Controller.users.add(user);
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
                try{
                    final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
                    final String USERNAME = "root";
                    final String PASSWORD = "138387Amitis";
                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                    //Statement statement = conn.createStatement();
                    String query ="insert into USERS (username, password, nickname, securityQ, securityANS, email, level, MaxHP, XP, coins)" +
                            " values (?, ?, ?, ?, ?, ?, 1, 100, 0, 20);";
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.setString(1, username);
                    statement.setString(2, pass);
                    statement.setString(3, nickname);
                    statement.setString(4, number);
                    statement.setString(5, answer);
                    statement.setString(6, email);
                    int rowsAffected = statement.executeUpdate();

                    System.out.println("Username creation in DB successful, " + rowsAffected + " rows affected.");
                    User user = new User(username, pass, nickname,number,answer,email, 1, 100, 0, 20);
                    Controller.users.add(user);

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
                    System.out.println("Starter pack generated successfully.");

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
                    int ROWS_AFFECTED = 0;
                    for(int i=0; i<StarterPack.size(); i++){
                        card = StarterPack.get(i);
                        query = "insert into " + tableName + " (name, strength, duration, playerDMG, upgradeLevel, upgradeCost, timesUpgraded, price, type, playerCharacter)"
                                + " values " + "(\"" + card.getName() + "\", " + card.getstrength() + ", " + card.getDuration() + ", " + card.getPlayerdamage()
                                + ", "+ card.getUpgradelevel() + ", " + card.getUpgradecost() + ", " + card.getTimesupgraded() + ", " + card.getPrice()
                                + ", \"" + card.getType() + "\", \"" + card.getPlayerCharacter().getName() +"\");";
                        statement1 = conn.createStatement();
                        rowsAffected = statement1.executeUpdate(query);
                        ROWS_AFFECTED += rowsAffected;
                    }
                    System.out.println("Table creation and starter pack assigning successful in DB.");
                    System.out.println(ROWS_AFFECTED + " row(s) affected.");

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

                    break;

                }catch(Exception e){
                    e.printStackTrace();
                }

            }
            else{
                System.out.println("INVALID INPUT OR WRONG SYNTAX");
                System.out.println("Try again: ");
                input = scanner.nextLine();
            }
        }

        System.out.println("Signing up...");

        return new DebutantMenu();

    }
    private Matcher getCommandMatcher(String input, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
    }

}
