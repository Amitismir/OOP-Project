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
    public static ArrayList<ASCIIART> asciiList = new ArrayList();
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
              System.out.println("-Coins: " + Controller.currentuser.coins);
              System.out.println("-XP: " + Controller.currentuser.XP);
              System.out.println("-Level: " + Controller.currentuser.level);
              System.out.println("-MaxHP: " + Controller.currentuser.MaxHP);
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
                      Controller.currentuser.setUsername(username);
                      Controller.usernames.add(username);
                    //  System.out.println("Username chnanged successfully!");
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
                    //  System.out.println("Nickname changed successfully!");
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
                      assign();
                      String str = "lala";
                      String Ans = "";
                      while (!Objects.equals(str, Ans)){
                          str = "lala";
                          Ans = "";
                          ArrayList<ASCIIART> selectedMembers = new ArrayList<>();
                          Collections.shuffle(asciiList);
                          for (int j = 0; j < 4; j++) {
                              selectedMembers.add(asciiList.get(j));
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
                    //  System.out.println("Password changed successfully!");
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
    private Matcher getCommandMatcher(String input, String regex)
    {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(input);
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
    private void changeAttrInDB_General(String newAttr, String Username, String changeType){
        try{
            final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
            final String USERNAME = "root";
            final String PASSWORD = "138387Amitis";
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String query = "";
            switch (changeType){
                case "USERNAME":
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
    private void changeTableName(String prevUsername, String newUsername){
        try{
            final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
            final String USERNAME = "root";
            final String PASSWORD = "Dorsa_Akbari@4518";
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
