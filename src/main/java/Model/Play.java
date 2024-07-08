package Model;

import Controller.Controller;

public class Play implements Menu{
    @Override
    public void display() {
        System.out.println("===Choose the Game Mode===");
        System.out.println("1.Two Players mode");
        System.out.println("2.Betting mode");
        System.out.println("3.Back to Main Menu");

    }

    @Override
    public Menu handleInput(String input) {

        switch (input)
        {
            case "1":
                return new TwoPlayers();
            case "2":
                return new Betting();
            case "3":
                return new MainMenu();
            default:
                return this;
        }
    }
}
