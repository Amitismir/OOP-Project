package Model;

import Controller.Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AddCardsMenu implements Menu{
    @Override
    public void display() {
        System.out.println("==Add Cards===");
        System.out.println("Name :");
    }

    @Override
    public Menu handleInput(String input) {
        if(input.equals("Back"))
        {
            return new AdminMenu().handleInput("13831384");
        }
        Scanner scanner = new Scanner(System.in);
        String name = "";
        int Attack = 0;
        int duration = 100;
        int damage = 0;
        int upgradelevel = 0;
        int UpgradeCost = 0;
        int price = 0;
        String charChoice = "";
        int choice =0;
        String character = "";
        Controller.cardnames.add(input);
        System.out.println("Attack:");
        Attack = scanner.nextInt();
        while(Attack>100 || Attack < 10)
        {
            System.out.println("Attack should be between 10 and 100");
            Attack = scanner.nextInt();
        }
        System.out.println("Duration: ");
        duration = scanner.nextInt();
        while(duration>5 || duration < 1)
        {
            System.out.println("Duration should be between 1 and 5");
            duration = scanner.nextInt();
        }
        System.out.println("Damage: ");
        damage = scanner.nextInt();
        while(damage>50 || damage < 10)
        {
            System.out.println("Damage should be between 10 and 50");
            damage = scanner.nextInt();
        }
        System.out.println("Upgrade Level: ");
        upgradelevel = scanner.nextInt();
        System.out.println("Upgrade Cost: ");
        UpgradeCost = scanner.nextInt();
        System.out.println("Price: ");
        price = scanner.nextInt();
        System.out.println("Which character is this card for?");
        System.out.println("1. ALPHA LUPEX");
        System.out.println("2. TAG PUNKS");
        System.out.println("3. A.N.F.O.");
        System.out.println("4. HELIO CELON");
        //Potential Later Bug
        charChoice = scanner.nextLine();
        charChoice = scanner.nextLine();
        while(!charChoice.equals("1") && !charChoice.equals("2") && !charChoice.equals("3") && !charChoice.equals("4")){
            System.out.println("INVALID INPUT. Try again:");
            charChoice = scanner.nextLine();
        }
        String charcterChoice = "";
        switch(charChoice){
            case "1":
                charcterChoice = "ALPHA LUPEX";
                break;
            case "2":
                charcterChoice = "TAG PUNKS";
                break;
            case "3":
                charcterChoice = "A.N.F.O.";
                break;
            case "4":
                charcterChoice = "HELIO CELON";
                break;
        }
        Card card = new Card(input, Attack, duration,damage,upgradelevel,UpgradeCost,0,price, true, Controller.getCharacterByName(charcterChoice));
        Controller.ordinarycards.add(card);
        Controller.cardnames.add(input);
        try{
            final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
            final String USERNAME = "root";
            final String PASSWORD = "138387Amitis";
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String query ="INSERT INTO ordinary_cards (name, attack, duration, playerDMG, price, upgradeCost, upgradeLevel, timesUpgraded, type, playerCharacter)" +
                    " VALUES (?, ?, ?, ?, ?, ?, ?, 0, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, input);
            statement.setInt(2, Attack);
            statement.setInt(3, duration);
            statement.setInt(4, damage);
            statement.setInt(5, price);
            statement.setInt(6, UpgradeCost);
            statement.setInt(7, upgradelevel);
            statement.setString(8, "ORDINARY");
            statement.setString(9, charcterChoice);
            int rowsAffected = statement.executeUpdate();

            System.out.println("update successful, " + rowsAffected + " rows affected.");


        }catch(SQLException e){
            e.printStackTrace();
        }
        System.out.println("Card added successfully!");
        return new AdminMenu().handleInput("13831384");
    }
}
