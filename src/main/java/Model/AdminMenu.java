package Model;

import Controller.Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class AdminMenu implements Menu {

    @Override
    public void display() {
        System.out.println("Please Enter your password:");
    }

    @Override
    public Menu handleInput(String input) {
        boolean once = false;
        Scanner scanner = new Scanner(System.in);
        if(input.matches("13831384"))
        {
           String answer = "";
           String what = "";
            System.out.println("Welcome!");
            System.out.println("1.Add cards");
            System.out.println("2.Edit cards");
            System.out.println("3.Remove cards");
            System.out.println("4.List of Players");
            System.out.println("5.Logout");
            input = scanner.nextLine();
            switch (input){
                case "1":
                    return new AddCardsMenu();
                case "2":
                  return new EditCardsMenu();
                case "3":
                    for(int i = 0; i < Controller.ordinarycards.size(); i++)
                    {
                        System.out.println(i + 1 +"." + Controller.ordinarycards.get(i).name);
                    }
                    answer = scanner.nextLine();
                    if(Integer.parseInt(answer)-1 > Controller.ordinarycards.size())
                    {
                        System.out.println("Invalid input");
                    }
                    else{
                        System.out.println("Are you sure you want to delete this card?");
                        System.out.println("1.Yes");
                        System.out.println("2.NO");
                        what = scanner.nextLine();
                        if(what.equals("1")){
                            String deletingCardName = Controller.ordinarycards.get(Integer.parseInt(answer)-1).getName();


                            //the card must be deleted from the card list of every user that has it

                            for(int i=0; i<Controller.users.size(); i++){
                                // 1. delete from cards in String type
                                int index = Controller.users.get(i).cardNamesInString.indexOf(deletingCardName);
                                if(index != -1){
                                    Controller.users.get(i).cardNamesInString.remove(index);
                                }
                                // 2. delete from cards in Card type
                                for(int j=0; j<Controller.users.get(i).cardsInCardType.size(); j++){
                                    if(Objects.equals(Controller.users.get(i).cardsInCardType.get(j).getName(), deletingCardName)){
                                        Controller.users.get(i).cardsInCardType.remove(j);
                                        // I put a break here because there are no repeated cards in each user's deck
                                        break;
                                    }
                                }
                            }

                            // 3. delete from the user's card table in DB
                            final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
                            final String USERNAME = "root";
                            final String PASSWORD = "138387Amitis";
                            for(int i=0; i<Controller.users.size(); i++) {
                                String tableName = Controller.users.get(i).getUsername() + "_CARDTABLE";
                                try {
                                    Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                    Statement statement = conn.createStatement();
                                    String query = "delete from " + tableName + " where name = \"" + deletingCardName + "\"";
                                    int rowsAffected = statement.executeUpdate(query);
                                    System.out.println("Card deleted from the card list of users who have it in database.");
                                    System.out.println(rowsAffected + "row(s) affected.");
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            }
                            //delete the card from DB
                            try{
                                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                                String query = "DELETE FROM ordinary_cards WHERE name = \""+ Controller.ordinarycards.get(Integer.parseInt(answer)-1).getName() +"\"";
                                Statement statement = conn.createStatement();
                                int rowsAffected = statement.executeUpdate(query);
                                System.out.println("Card deleted from database successfully.");
                                System.out.println(rowsAffected + " row(s) affected.");

                            }catch(SQLException e){
                                e.printStackTrace();
                            }
                            Controller.cardnames.remove(Controller.ordinarycards.get(Integer.parseInt(answer)-1).name);
                            Controller.ordinarycards.remove(Controller.ordinarycards.get(Integer.parseInt(answer)-1));
                        }
                    }
                    return this.handleInput("13831384");
                case "4":
                    once = true;
                    for(int i = 0; i < Controller.users.size();i++)
                    {
                        //level and xp are left
                        System.out.println(i+1 + "." + Controller.users.get(i).getUsername() + ": xp = " + Controller.users.get(i).getXP()
                                + " / level = " + Controller.users.get(i).getLevel() + " / coins = " + Controller.users.get(i).getCoins());                    }
                    if(Controller.users.isEmpty())
                    {
                        System.out.println("There are no users registered yet!");
                    }
                    return this.handleInput("13831384");

                case "5":
                    once = false;
                    return new DebutantMenu();
                default:
                    System.out.println("Invalid input!");
                    return this.handleInput("13831384");


            }

        }
        else if(input.equals("Back"))
        {
            return new DebutantMenu();
        }
        else{
            System.out.println("Your password was incorrect!");
            return new DebutantMenu();
        }

    }
}
