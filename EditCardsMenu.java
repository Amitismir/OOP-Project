package Model;

import Controller.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.Scanner;

public class EditCardsMenu implements Menu{
    public final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
    public final String USERNAME = "root";
    public final String PASSWORD = "138387Amitis";
    @Override
    public void display() {
        System.out.println("===Edit Cards===");
        for(int i = 0; i < Controller.ordinarycards.size(); i++)
        {
            System.out.println(i + 1 +"." + Controller.ordinarycards.get(i).name);
        }

    }

    @Override
    public Menu handleInput(String input) {
        if(input.equals("Back"))
        {
            return new AdminMenu().handleInput("13831384");
        }
        String what = "";
        Scanner scanner = new Scanner(System.in);
        String name =Controller.ordinarycards.get(Integer.parseInt(input)-1).name ;
        int Attack = Controller.ordinarycards.get(Integer.parseInt(input)-1).strength;
        int duration = Controller.ordinarycards.get(Integer.parseInt(input)-1).duration;
        int damage = Controller.ordinarycards.get(Integer.parseInt(input)-1).playerdamage;
        int upgradelevel = Controller.ordinarycards.get(Integer.parseInt(input)-1).upgradelevel;
        int UpgradeCost = Controller.ordinarycards.get(Integer.parseInt(input)-1).upgradecost;
        int price = Controller.ordinarycards.get(Integer.parseInt(input)-1).price;
        String charctername = Controller.ordinarycards.get(Integer.parseInt(input)-1).playerCharacter.getName();
        System.out.println("Choose the property you want to change:");
        System.out.println("1.Name: " + name);
        System.out.println("2.Attack: " + Attack);
        System.out.println("3.Duration: " + duration);
        System.out.println("4.Player Damage: "+damage);
        System.out.println("5.Upgrade Level: "+upgradelevel);
        System.out.println("6.Upgrade Cost: "+UpgradeCost);
        System.out.println("7.Price: "+price);
        System.out.println("8.Character: "+charctername);
        System.out.println("9.Finalize the changes I have made.");
        what =scanner.nextLine();
        if(what.equals("Back"))
        {
            return this;
        }
        while(!what.equals("9"))
        {

            if(what.equals("1"))
            {

                System.out.println("Name: ");
                what = scanner.nextLine();
                if(!what.equals("back")) {
                    name = what;
                    while (Controller.cardnames.contains(what)) {
                        System.out.println("This name is already used!");
                        what = scanner.nextLine();
                        name = what;
                    }
                }
                System.out.println("Choose the property you want to change:");
                System.out.println("1.Name: " + name);
                System.out.println("2.Attack: " + Attack);
                System.out.println("3.Duration: " + duration);
                System.out.println("4.Player Damage: "+damage);
                System.out.println("5.Upgrade Level: "+upgradelevel);
                System.out.println("6.Upgrade Cost: "+UpgradeCost);
                System.out.println("7.Price: "+price);
                System.out.println("8.Character: "+charctername);
                System.out.println("9.Finalize the changes I have made.");
                what=scanner.nextLine();
                continue;
            }
            if(what.equals("2"))
            {
                System.out.println("Attack: ");
                what = scanner.nextLine();
                if(!what.equals("back")){
                    Attack = Integer.parseInt(what);
                while(Integer.parseInt(what) >100 || Integer.parseInt(what) < 10)
                {
                    System.out.println("Attack should be between 10 and 100");
                    what = scanner.nextLine();
                    Attack = Integer.parseInt(what);
                }
                }
                System.out.println("Choose the property you want to change:");
                System.out.println("1.Name: " + name);
                System.out.println("2.Attack: " + Attack);
                System.out.println("3.Duration: " + duration);
                System.out.println("4.Player Damage: "+damage);
                System.out.println("5.Upgrade Level: "+upgradelevel);
                System.out.println("6.Upgrade Cost: "+UpgradeCost);
                System.out.println("7.Price: "+price);
                System.out.println("8.Character: "+charctername);
                System.out.println("9.Finalize the changes I have made.");
                what = scanner.nextLine();
                continue;

            }
            if(what.equals("3"))
            {
                System.out.println("Duration: ");
                what = scanner.nextLine();
                duration = Integer.parseInt(what);
                if(!what.equals("back")){
                    duration = Integer.parseInt(what);

                while(Integer.parseInt(what)> 5|| Integer.parseInt(what) < 1)
                {
                    System.out.println("Duration should be between 1 and 5");
                    what = scanner.nextLine();
                    duration = Integer.parseInt(what);
                }
                }
                System.out.println("Choose the property you want to change:");
                System.out.println("1.Name: " + name);
                System.out.println("2.Attack: " + Attack);
                System.out.println("3.Duration: " + duration);
                System.out.println("4.Player Damage: "+damage);
                System.out.println("5.Upgrade Level: "+upgradelevel);
                System.out.println("6.Upgrade Cost: "+UpgradeCost);
                System.out.println("7.Price: "+price);
                System.out.println("8.Character: "+charctername);
                System.out.println("9.Finalize the changes I have made.");
                what = scanner.nextLine();
                continue;

            }
            if(what.equals("4"))
            {
                System.out.println("Damage :");
                what = scanner.nextLine();
                if(!what.equals("back")){
                damage = Integer.parseInt(what);
                    while(Integer.parseInt(what)>50 || Integer.parseInt(what) < 10)
                    {
                        System.out.println("Damage should be between 10 and 50");
                        what = scanner.nextLine();
                        damage = Integer.parseInt(what);
                    }
                }
                System.out.println("Choose the property you want to change:");
                System.out.println("1.Name: " + name);
                System.out.println("2.Attack: " + Attack);
                System.out.println("3.Duration: " + duration);
                System.out.println("4.Player Damage: "+damage);
                System.out.println("5.Upgrade Level: "+upgradelevel);
                System.out.println("6.Upgrade Cost: "+UpgradeCost);
                System.out.println("7.Price: "+price);
                System.out.println("8.Character: "+charctername);
                System.out.println("9.Finalize the changes I have made.");
                what=scanner.nextLine();
                continue;

            }
            if(what.equals("5"))
            {
                System.out.println("Upgrade level: ");
                what = scanner.nextLine();
                if(!what.equals("back")){
                    upgradelevel = Integer.parseInt(what);
                }
                System.out.println("Choose the property you want to change:");
                System.out.println("1.Name: " + name);
                System.out.println("2.Attack: " + Attack);
                System.out.println("3.Duration: " + duration);
                System.out.println("4.Player Damage: "+damage);
                System.out.println("5.Upgrade Level: "+upgradelevel);
                System.out.println("6.Upgrade Cost: "+UpgradeCost);
                System.out.println("7.Price: "+price);
                System.out.println("8.Character: "+charctername);
                System.out.println("9.Finalize the changes I have made.");
                what=scanner.nextLine();
                continue;
            }
            if(what.equals("6"))
            {
                System.out.println("Upgrade cost: ");
                what = scanner.nextLine();
                if(!what.equals("back")){
                    UpgradeCost = Integer.parseInt(what);
                }
                System.out.println("Choose the property you want to change:");
                System.out.println("1.Name: " + name);
                System.out.println("2.Attack: " + Attack);
                System.out.println("3.Duration: " + duration);
                System.out.println("4.Player Damage: "+damage);
                System.out.println("5.Upgrade Level: "+upgradelevel);
                System.out.println("6.Upgrade Cost: "+UpgradeCost);
                System.out.println("7.Price: "+price);
                System.out.println("8.Character: "+charctername);
                System.out.println("9.Finalize the changes I have made.");
                what=scanner.nextLine();
                continue;
            }
            if(what.equals("7"))
            {
                System.out.println("Price: ");
                what = scanner.nextLine();
                if(!what.equals("back")){
                    price = Integer.parseInt(what);
                }
                System.out.println("Choose the property you want to change:");
                System.out.println("1.Name: " + name);
                System.out.println("2.Attack: " + Attack);
                System.out.println("3.Duration: " + duration);
                System.out.println("4.Player Damage: "+damage);
                System.out.println("5.Upgrade Level: "+upgradelevel);
                System.out.println("6.Upgrade Cost: "+UpgradeCost);
                System.out.println("7.Price: "+price);
                System.out.println("8.Character: "+charctername);
                System.out.println("9.Finalize the changes I have made.");
                what=scanner.nextLine();
                continue;
            }
            if(what.equals("8"))
            {
                System.out.println("Character: ");
                what = scanner.nextLine();
                if(!what.equals("back")){
                    charctername = what;
                }
                System.out.println("Choose the property you want to change:");
                System.out.println("1.Name: " + name);
                System.out.println("2.Attack: " + Attack);
                System.out.println("3.Duration: " + duration);
                System.out.println("4.Player Damage: "+damage);
                System.out.println("5.Upgarde Level: "+upgradelevel);
                System.out.println("6.Upgarde Cost: "+UpgradeCost);
                System.out.println("7.Price: "+price);
                System.out.println("8.Character: "+charctername);
                System.out.println("9.Finalize the changes I have made.");
                what=scanner.nextLine();
                continue;
            }



        }
        if(what.equals("9"))
        {
            System.out.println("Are you sure you want to change the cards?");
            System.out.println("1.Yes");
            System.out.println("2.No");
            what = scanner.nextLine();
            if(what.equals("1"))
            {
                changeName(name, Integer.parseInt(input)-1);
                changeStrength(Attack, Integer.parseInt(input)-1);
                changePlayerDMG(damage, Integer.parseInt(input)-1);
                changePrice(price, Integer.parseInt(input)-1);
                changeDuration(duration, Integer.parseInt(input)-1);
                changeUpgradeCost(UpgradeCost, Integer.parseInt(input)-1);
                changeUpgradeLevel(upgradelevel, Integer.parseInt(input)-1);
                changeCharacter(charctername,Integer.parseInt(input)-1);
                return new AdminMenu().handleInput("13831384");
            }
            if(what.equals("2"))
            {
                 return new AdminMenu().handleInput("13831384");
            }

        }
        return new AdminMenu().handleInput("13831384");

    }
    private void changeName(String newName, int in){
        if(!Objects.equals(Controller.ordinarycards.get(in).getName(), newName)){
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String query = "update ordinary_cards set name = \"" + newName + "\" where name = \"" + Controller.ordinarycards.get(in).getName() + "\";";
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
            }catch (SQLException e){
                e.printStackTrace();
            }
            Controller.ordinarycards.get(in).setName(newName);
            System.out.println("NAME EDITION SUCCESSFUL");
        }
    }
    private void changeStrength(int newStr, int in){
        if(!Objects.equals(Controller.ordinarycards.get(in).getstrength(), newStr)){
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String query = "update ordinary_cards set attack = \"" + newStr + "\" where name = \"" + Controller.ordinarycards.get(in).getName() + "\";";
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
            }catch(SQLException e){
                e.printStackTrace();
            }
            Controller.ordinarycards.get(in).setstrength(newStr);
            System.out.println("ATTACK EDITION SUCCESSFUL");
        }
    }
    private void changePlayerDMG(int newDMG, int in){
        if(!Objects.equals(Controller.ordinarycards.get(in).getPlayerdamage(), newDMG)) {
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String query = "update ordinary_cards set playerDMG = \"" + newDMG + "\" where name = \"" + Controller.ordinarycards.get(in).getName() + "\";";
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Controller.ordinarycards.get(in).setPlayerdamage(newDMG);
            System.out.println("DMG EDITION SUCCESSFUL");
        }
    }
    private void changeDuration(int newDUR, int in){
        if(!Objects.equals(Controller.ordinarycards.get(in).getDuration(), newDUR)){
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String query = "update ordinary_cards set duration = \"" + newDUR + "\" where name = \"" + Controller.ordinarycards.get(in).getName() + "\";";
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
            }catch(SQLException e){
                e.printStackTrace();
            }
            Controller.ordinarycards.get(in).setDuration(newDUR);
            System.out.println("DUR EDITION SUCCESSFUL");
        }
    }
    private void changeUpgradeCost(int newUpCost, int in){
        if(!Objects.equals(Controller.ordinarycards.get(in).getUpgradecost(), newUpCost)){
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String query = "update ordinary_cards set upgradeCost = \"" + newUpCost + "\" where name = \"" + Controller.ordinarycards.get(in).getName() + "\";";
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
            }catch(SQLException e){
                e.printStackTrace();
            }
            Controller.ordinarycards.get(in).setUpgradecost(newUpCost);
            System.out.println("UPGRADE COST EDITION SUCCESSFUL");
        }
    }
    private void changeUpgradeLevel(int newUpLevel, int in){
        if(!Objects.equals(Controller.ordinarycards.get(in).getUpgradelevel(), newUpLevel)){
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String query = "update ordinary_cards set upgradeLevel = \"" + newUpLevel + "\" where name = \"" + Controller.ordinarycards.get(in).getName() + "\";";
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
            }catch(SQLException e){
                e.printStackTrace();
            }
            Controller.ordinarycards.get(in).setUpgradelevel(newUpLevel);
            System.out.println("UPGRADE LEVEL EDITION SUCCESSFUL");
        }
    }
    private void changePrice(int newPrice, int in){
        if(!Objects.equals(Controller.ordinarycards.get(in).getPrice(), newPrice)){
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String query = "UPDATE ordinary_cards SET price = \"" + newPrice + "\" WHERE name = \"" + Controller.ordinarycards.get(in).getName() + "\"";
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
            }catch(SQLException e){
                e.printStackTrace();
            }
            Controller.ordinarycards.get(in).setPrice(newPrice);
            System.out.println("PRICE EDITION SUCCESSFUL");
        }
    }
    private void changeCharacter(String newChar, int in){
        if(!Objects.equals(Controller.ordinarycards.get(in).getPlayerCharacter(), newChar)){
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String query = "UPDATE ordinary_cards SET playerCharacter = \"" + newChar + "\" WHERE name = \"" + Controller.ordinarycards.get(in).getName() + "\"";
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
            }catch (SQLException e){
                e.printStackTrace();
            }
            Controller.ordinarycards.get(in).setPlayerCharacter(Controller.getCharacterByName(newChar));
            System.out.println("CHARACTER EDITION SUCCESSFUL");
        }
    }
}
