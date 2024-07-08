package Model;

import Controller.Controller;

public class MainMenu implements Menu{
    @Override
    public void display() {
        System.out.println("===Welcome to the Main Menu===");
        System.out.println("1.Profile Menu");
        System.out.println("2.Play");
        System.out.println("3.My deck");
        System.out.println("4.Game History");
        System.out.println("5.Shop");
        System.out.println("6.log out");
    }

    @Override
    public Menu handleInput(String input) {
        switch (input)
        {
            case "1":
                return new ProfileMenu();
                case "2":
                return new Play();
                case "3":
                    Card card;
                    System.out.println(Controller.currentuser.getUsername());
                    System.out.println(Controller.currentuser.cardsInCardType.size());
                    for(int i = 0; i < Controller.currentuser.cardsInCardType.size() ; i++)
                    {
                        card = Controller.currentuser.cardsInCardType.get(i);
                        System.out.println((i+1) + ". NAME: " + card.getName() + "  STRENGTH(ATTACK): " + card.getstrength() + "  DUR: " + card.getDuration());
                        System.out.println("    DMG: " + card.getPlayerdamage() + "  TIMES UPGRADED: " + card.getTimesupgraded() + "  TYPE: " + card.getType());
                        System.out.println("  UPGRADE LEVEL: " + card.getUpgradelevel() + " UPGRADE COST: " + card.getUpgradecost() + "  PRICE: " + card.getPrice());
                    }
                    return this;
            case "4":
                return new gameHistory();
            case"5":
                return new ShopMenu();
            case "6":
                return new DebutantMenu();
            default:
                System.out.println("Invalid Option");
                return this;
        }
    }
}
