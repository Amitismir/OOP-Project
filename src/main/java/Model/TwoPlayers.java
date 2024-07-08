package Model;

import Controller.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;

public class TwoPlayers implements Menu{
    @Override
    public void display() {
        System.out.println("Welcome!");
        System.out.println("The second player should login first!");
        System.out.println("Username :");
    }

    @Override
    public Menu handleInput(String input) {

        Scanner scanner = new Scanner(System.in);
        User Player1user = Controller.currentuser;
        boolean valid = false;
        int answercard = 0;
        int answercell = 0;
        String username = "";
        String character1 = "";
        String character2 = "";
        String correct = "";
        username = input;
        boolean hider1 = false;
        boolean hider2 = false;
        boolean buffWithPossibility50 = true;
        while (!Controller.usernames.contains(input)) {
            System.out.println("This username doesn't exist! Try Again!");
            input = scanner.nextLine();
            username = input;
        }
        while (input.equals(Player1user.username)) {
            System.out.println("This player is has already entered the game as player 1.");
            System.out.println("Enter another username:");
            input = scanner.nextLine();
            username = input;
        }
        String pass = "";
        correct = Controller.getUserByUsername(input).getPassword();
        System.out.println("Password: ");
        input = scanner.nextLine();
        pass = input;
        while (!correct.equals(input)) {
            System.out.println("Your password doesn't match! Please try again");
            input = scanner.nextLine();
            pass = input;
        }
        Controller.guest = Controller.getUserByUsername(username);
        User Player2user = Controller.guest;
        Player1user.HP = Player1user.MaxHP;
        Player2user.HP = Player2user.MaxHP;
        Player1user.ROUNDS = 4;
        Player2user.ROUNDS = 4;
        Player1user.DAMAGE = 0;
        Player2user.DAMAGE = 0;
        System.out.println("Choose your characters:");
        System.out.println("1. ALPHA LUPEX");
        System.out.println("2. TAG PUNKS");
        System.out.println("3. A.N.F.O.");
        System.out.println("4. HELIO CELON");
        System.out.println("Player1: ");
        character1 = scanner.nextLine();
        switch (character1) {
            case "1":
                Player1user.setCharactername("ALPHA LUPEX");
                break;
            case "2":
                Player1user.setCharactername("TAG PUNKS");
                break;
            case "3":
                Player1user.setCharactername("A.N.F.O.");
                break;
            case "4":
                Player1user.setCharactername("HELIO CELON");
                break;

        }
        // Controller.currentuser.setCharactername(character);
        System.out.println("Player2: ");
        character2 = scanner.nextLine();
        switch (character2) {
            case "1":
                Player2user.setCharactername("ALPHA LUPEX");
                break;
            case "2":
                Player2user.setCharactername("TAG PUNKS");
                break;
            case "3":
                Player2user.setCharactername("A.N.F.O.");
                break;
            case "4":
                Player2user.setCharactername("HELIO CELON");
                break;

        }
        int Final = 0;
        for (int i = 0; i < Player1user.cardsInCardType.size(); i++) {
            if (Player1user.cardsInCardType.get(i).getPlayerCharacter().equals(Controller.getCharacterByName(character1))) {
                Final = Player1user.cardsInCardType.get(i).getStrength();
                Player1user.cardsInCardType.get(i).setStrength(Final + Controller.getCharacterByName(character1).getIncrement());
            }
        }
        System.out.println("Cards of player 1 buffed according to their character choice successfully.");
        Final = 0;
        for (int i = 0; i < Player2user.cardsInCardType.size(); i++) {
            if (Player2user.cardsInCardType.get(i).getPlayerCharacter().equals(Controller.getCharacterByName(character2))) {
                Final = Player2user.cardsInCardType.get(i).getStrength();
                Player2user.cardsInCardType.get(i).setStrength(Final + Controller.getCharacterByName(character2).getIncrement());
            }
        }
        System.out.println("Cards of player 2 buffed according to their character choice successfully.");
        ArrayList<Card> deck1 = new ArrayList<>(5);
        ArrayList<Card> deck2 = new ArrayList<>(5);
        ArrayList<Cell> board1 = new ArrayList<>();
        ArrayList<Cell> board2 = new ArrayList<>();
        while (Player1user.HP > 0 && Player2user.HP > 0) {
            board1.clear();
            board2.clear();
            deck1.clear();
            deck2.clear();
           // System.out.println("Do I come back ? ");
            Player1user.DAMAGE = 0;
            Player2user.DAMAGE = 0;
            Player1user.ROUNDS = 4;
            Player2user.ROUNDS = 4;
            int hole1 = Controller.random();
            int hole2 = Controller.random();
            for (int i = 0; i < Controller.randomgenerator(Player1user.cardsInCardType.size()).size(); i++) {
                deck1.add(Player1user.cardsInCardType.get(Controller.randomgenerator(Player1user.cardsInCardType.size()).get(i)));
            }
            for (int i = 0; i < Controller.randomgenerator(Player2user.cardsInCardType.size()).size(); i++) {
                deck2.add(Player2user.cardsInCardType.get(Controller.randomgenerator(Player2user.cardsInCardType.size()).get(i)));
            }
            for (int i = 0; i < 21; i++) {
                if (i != hole1)
                    board1.add(new Cell(i, Player1user, Cell.State.empty, 0, 0));
                else
                    board1.add(new Cell(i, Player1user, Cell.State.hole, 0, 0));

            }
            for (int i = 0; i < 21; i++) {
                if (i != hole2)
                    board2.add(new Cell(i, Player2user, Cell.State.empty, 0, 0));
                else
                    board2.add(new Cell(i, Player2user, Cell.State.hole, 0, 0));

            }
            //System.out.println(Player1user.HP);
            //System.out.println(Player2user.HP);
            if (Player1user.HP > 0 && Player2user.HP > 0){
                for (int i = 0; i < 4; i++) {
                    ArrayList<Card> boomed = new ArrayList<>();
                    Card card;
                    int neww = 0;
                    if (Player1user.ROUNDS > 0) {
                        System.out.println("------------PLayer 1 should choose--------------------");
                        System.out.println("Player1: ");
                        for (int j = 0; j < deck1.size(); j++) {
                            System.out.print(j + 1 + ".");
                            if (!hider2)
                                System.out.print("Name: " + deck1.get(j).name + " Type: " + deck1.get(j).getType() + " Character: " + deck1.get(j).getPlayerCharacter().getName() + " Duration: " + deck1.get(j).duration + " Damage: " + deck1.get(j).playerdamage + " Attack: " + deck1.get(j).strength);
                            System.out.println();
                        }
                        System.out.println("Player2: ");
                        for (int j = 0; j < deck2.size(); j++) {
                            System.out.println(j + 1 + "." + "Name: " + deck2.get(j).name + " Type:  " + deck2.get(j).type + " Character: " + deck2.get(j).getPlayerCharacter().getName() + " Duration: " + deck2.get(j).duration + " Damage: " + deck2.get(j).playerdamage + " Attack: " + deck2.get(j).strength);
                        }
                        System.out.println("Damage1: " + Player1user.getDAMAGE() + "  Damage2: " + Player2user.getDAMAGE());
                        System.out.println("Rounds1: " + Player1user.ROUNDS + "  Rounds2: " + Player2user.ROUNDS);
                        System.out.println("HP1: " + Player1user.HP + "  HP2: " + Player2user.HP);
                        System.out.println("Character1: " + Player1user.getCharactername() + " Character2: " + Player2user.getCharactername());
                        for (int k = 0; k < 21; k++) {
                            if (!board1.get(k).state.equals(Cell.State.full))
                                System.out.print(k + 1 + ".[ " + Cell.toStringg(board1.get(k).state) + "] ");

                            else {
                                System.out.print(k + 1 + ".[N: " + board1.get(k).card.name + "/" + " - S: " + board1.get(k).card.strength + "/" + " - D: " + board1.get(k).card.divideddamage + "] ");
                            }
                            if (!board2.get(k).state.equals(Cell.State.full))
                                System.out.print(" [ " + Cell.toStringg(board2.get(k).state) + "] ");
                            else {
                                System.out.print(" [N: " + board2.get(k).card.name + "/" + " - S: " + board2.get(k).card.strength + "/" + " - D: " + board2.get(k).card.divideddamage + "] ");
                            }
                            System.out.println();
                        }
                        //SELECTING CARD FOR PLAYER1
                        System.out.println("PLAYER 1 STARTS");
                        // Card card;
                        System.out.println("Select the card you want to place: ");
                        answercard = scanner.nextInt();
                        card = deck1.get(answercard - 1);
                        //-------------//

                        //Card = card +1 =>array
                        if (card.getType().equals("ORDINARY") || card.getName().equals("heal") || card.getName().equals("shield")) {
                            System.out.println("Select the cell you want to place the card in: ");
                            answercell = scanner.nextInt();
                            //Block = cell + 1 => array;
                            valid = false;
                            while (!valid) {
                                for (int l = answercell - 1; l < answercell - 1 + card.duration; l++) {
                                    if (board1.get(l).state.equals(Cell.State.full) || board1.get(l).state.equals(Cell.State.boom) || board1.get(l).state.equals(Cell.State.hole) || answercell - 1 + card.duration > 20) {
                                        valid = false;
                                        System.out.println("Please try again!");
                                        System.out.println("Select the card you want to place: ");
                                        answercard = scanner.nextInt();
                                        System.out.println("Select the cell you want to place the card in: ");
                                        answercell = scanner.nextInt();
                                        card = deck1.get(answercard - 1);
                                        //Block = cell + 1 => array;
                                        continue;
                                        // public Cell(int index, User user, Cell.State state, int celldamage, int cellstrength) {
                                        //each cell has this constructor
                                    }
                                    valid = true;
                                }
                            }

                            for (int j = answercell - 1; j < answercell - 1 + card.duration; j++) {
                                board1.get(j).setState(Cell.State.full);
                                board1.get(j).setCelldamage(card.divideddamage);
                                board1.get(j).setCellstrength(card.strength);
                                board1.get(j).card = card;
                            }
                        }
                        neww = Controller.random();
                        deck1.set(answercard - 1, Player1user.cardsInCardType.get(neww));
                        if (buffWithPossibility50) {
                            if (deck1.size() == 5)
                                if (deck1.get(2).getPlayerCharacter().equals(Player1user.getCharactername())) {
                                    int prevoiusStr = deck1.get(2).getStrength();
                                    deck1.get(2).setstrength(prevoiusStr + 1);
                                    System.out.println("Player 1's middle card was buffed because being of the same type with your character.");
                                }
                        }
                        if (deck1.size() == 4) {
                            int addIndex = Controller.randomFromDeck(Player1user.cardsInCardType.size());
                            deck1.add(Player1user.cardsInCardType.get(addIndex));
                        }
                        //Creating boomed arraylist
                        // ArrayList<Card> boomed = new ArrayList<>();
                        if (card.getType().equals("ORDINARY")) {
                            boomed.clear();
                            for (int j = 0; j < 21; j++) {
                                if (board1.get(j).getState() == Cell.State.full && board2.get(j).getState() == Cell.State.full) {
                                    if ((board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("heal"))
                                            || (board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("shield"))
                                            || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("heal"))
                                            || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("shield"))) {
                                        board2.get(j).cellstrength = 0;
                                        board2.get(j).celldamage = 0;
                                        board2.get(j).state = Cell.State.boom;
                                        boomed.add(board2.get(j).card);
                                        board1.get(j).cellstrength = 0;
                                        board1.get(j).celldamage = 0;
                                        board1.get(j).state = Cell.State.boom;
                                        boomed.add(board1.get(j).card);
                                    } else if (board1.get(j).card.getName().equals("heal") || board1.get(j).card.getName().equals("shield")) {
                                        board2.get(j).cellstrength = 0;
                                        board2.get(j).celldamage = 0;
                                        board2.get(j).state = Cell.State.boom;
                                        boomed.add(board2.get(j).card);
                                    } else if (board2.get(j).card.getName().equals("heal") || board2.get(j).card.getName().equals("shield")) {
                                        board1.get(j).cellstrength = 0;
                                        board1.get(j).celldamage = 0;
                                        board1.get(j).state = Cell.State.boom;
                                        boomed.add(board1.get(j).card);
                                    } else {
                                        if (board1.get(j).cellstrength > board2.get(j).cellstrength) {
                                            board2.get(j).cellstrength = 0;
                                            board2.get(j).celldamage = 0;
                                            board2.get(j).state = Cell.State.boom;
                                            boomed.add(board2.get(j).card);

                                        }
                                        //equal situation
                                        else if (board1.get(j).celldamage == board2.get(j).celldamage) {
                                            board2.get(j).cellstrength = 0;
                                            board2.get(j).celldamage = 0;
                                            board2.get(j).state = Cell.State.empty;
                                            board1.get(j).cellstrength = 0;
                                            board1.get(j).celldamage = 0;
                                            board1.get(j).state = Cell.State.empty;

                                        } else {
                                            board1.get(j).cellstrength = 0;
                                            board1.get(j).celldamage = 0;
                                            board1.get(j).state = Cell.State.boom;
                                        }
                                    }
                                }
                            }
                            Player1user.DAMAGE = 0;
                            Player2user.DAMAGE = 0;
                            for (int j = 0; j < 21; j++) {
                                Player1user.DAMAGE += board1.get(j).celldamage;
                                Player2user.DAMAGE += board2.get(j).celldamage;
                            }
                            Player1user.ROUNDS -= 1;


                        } else {
                            int copyCardAnswer = 0;
                            switch (card.name) {
                                case "copyCard": {
                                    if (hider1) {
                                        System.out.println("Error: A hider is currently activated.");
                                        break;
                                    }
                                    System.out.println("Please select a card out of your deck: ");
                                    copyCardAnswer = scanner.nextInt();
                                    while (copyCardAnswer < 1 || copyCardAnswer > deck1.size() || Objects.equals(deck1.get(copyCardAnswer - 1).getName(), "copyCard")) {
                                        System.out.println("INVALID NUMBER. TRY AGAIN:");
                                        copyCardAnswer = scanner.nextInt();
                                    }
                                    Card cardcopy = new Card(deck1.get(copyCardAnswer - 1));
                                    deck1.add(cardcopy);
                                    System.out.println("Copied card added to deck successfully.");
                                    System.out.println("Player 1:");
                                    for (int j = 0; j < deck1.size(); j++) {
                                        System.out.println(j + 1 + "." + "Name: " + deck1.get(j).name + " Type: " + deck1.get(j).getType() + " Character: " + deck1.get(j).getPlayerCharacter().getName() + " Duration: " + deck1.get(j).duration + " Damage: " + deck1.get(j).playerdamage + " Attack: " + deck1.get(j).strength);
                                    }
                                    System.out.println("Player 2:");
                                    for (int j = 0; j < deck2.size(); j++) {
                                        System.out.println(j + 1 + "." + "Name: " + deck2.get(j).name + " Type: " + deck2.get(j).getType() + " Character: " + deck2.get(j).getPlayerCharacter().getName() + " Duration: " + deck2.get(j).duration + " Damage: " + deck2.get(j).playerdamage + " Attack: " + deck2.get(j).strength);
                                    }
                                    System.out.println("Now choose a card:");
                                    copyCardAnswer = scanner.nextInt();
                                    while (copyCardAnswer < 1 || copyCardAnswer > deck1.size() || Objects.equals(deck1.get(copyCardAnswer - 1).getName(), "copyCard") || deck1.get(copyCardAnswer - 1).type.equals("SPECIAL")) {
                                        System.out.println("INVALID INPUT/SPECIAL CARD WAS CHOSEN. TRY AGAIN.");
                                        copyCardAnswer = scanner.nextInt();
                                    }
                                    card = deck1.get(copyCardAnswer - 1);
                                    deck1.remove(copyCardAnswer - 1);

                                    System.out.println("Select the cell you want to place the card in: ");
                                    answercell = scanner.nextInt();
                                    //Block = cell + 1 => array;
                                    valid = false;
                                    while (!valid) {
                                        for (int l = answercell - 1; l < answercell - 1 + card.duration; l++) {
                                            if (board1.get(l).state.equals(Cell.State.full) || board1.get(l).state.equals(Cell.State.boom) || board1.get(l).state.equals(Cell.State.hole) || answercell - 1 + card.duration > 20) {
                                                valid = false;
                                                System.out.println("Please try again!");
                                                System.out.println("Select the card you want to place: ");
                                                answercard = scanner.nextInt();
                                                //Card = card +1 =>array
                                                System.out.println("Select the cell you want to place the card in: ");
                                                answercell = scanner.nextInt();
                                                card = deck1.get(answercard - 1);
                                                //Block = cell + 1 => array;
                                                continue;
                                                // public Cell(int index, User user, Cell.State state, int celldamage, int cellstrength) {
                                                //each cell has this constructor
                                            }
                                            valid = true;
                                        }

                                    }
                                    for (int j = answercell - 1; j < answercell - 1 + card.duration; j++) {
                                        board1.get(j).setState(Cell.State.full);
                                        board1.get(j).setCelldamage(card.divideddamage);
                                        board1.get(j).setCellstrength(card.strength);
                                        board1.get(j).card = card;
                                    }
                                    boomed.clear();
                                    for (int j = 0; j < 21; j++) {
                                        if (board1.get(j).getState().equals(Cell.State.full) && board2.get(j).getState().equals(Cell.State.full)) {
                                            if ((board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("shield"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("shield"))) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else if (board1.get(j).card.getName().equals("heal") || board1.get(j).card.getName().equals("shield")) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                            } else if (board2.get(j).card.getName().equals("heal") || board2.get(j).card.getName().equals("shield")) {
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else {
                                                if (board1.get(j).cellstrength > board2.get(j).cellstrength) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.boom;
                                                    boomed.add(board2.get(j).card);

                                                }
                                                //equal situation
                                                else if (board1.get(j).celldamage == board2.get(j).celldamage) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.empty;
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.empty;

                                                } else {
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.boom;
                                                }
                                            }
                                        }
                                    }
                                    Player1user.DAMAGE = 0;
                                    Player2user.DAMAGE = 0;
                                    for (int j = 0; j < 21; j++) {
                                        Player1user.DAMAGE += board1.get(j).celldamage;
                                        Player2user.DAMAGE += board2.get(j).celldamage;
                                    }
                                    Player1user.ROUNDS -= 1;
                                    break;
                                }
                                case "heal": {
                                    int currentHp = Player1user.getHP();
                                    Player1user.setHP(currentHp + 50);
                                    boomed.clear();
                                    for (int j = 0; j < 21 && j != answercell - 1; j++) {
                                        if (board1.get(j).getState().equals(Cell.State.full) && board2.get(j).getState().equals(Cell.State.full)) {
                                            if ((board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("shield"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("shield"))) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else if (board1.get(j).card.getName().equals("heal") || board1.get(j).card.getName().equals("shield")) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                            } else if (board2.get(j).card.getName().equals("heal") || board2.get(j).card.getName().equals("shield")) {
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else {
                                                if (board1.get(j).cellstrength > board2.get(j).cellstrength) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.boom;
                                                    boomed.add(board2.get(j).card);

                                                }
                                                //equal situation
                                                else if (board1.get(j).celldamage == board2.get(j).celldamage) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.empty;
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.empty;

                                                } else {
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.boom;
                                                }
                                            }
                                        }
                                    }

                                    Player1user.DAMAGE = 0;
                                    Player2user.DAMAGE = 0;

                                    //Player1user.DAMAGE += card.getPlayerdamage();
                                    if (board2.get(answercell - 1).getState().equals(Cell.State.full)) {
                                        // Player2user.DAMAGE -= board2.get(answercell - 1).celldamage;
                                        board2.get(answercell - 1).setState(Cell.State.boom);
                                        board2.get(answercell - 1).setCelldamage(0);
                                        board1.get(answercell - 1).setCelldamage(card.divideddamage);
                                        board2.get(answercell - 1).setCellstrength(0);
                                        boomed.add(board2.get(answercell - 1).card);
                                    }
                                    for (int j = 0; j < 21; j++) {
                                        Player1user.DAMAGE += board1.get(j).celldamage;
                                        Player2user.DAMAGE += board2.get(j).celldamage;
                                    }
                                    Player1user.ROUNDS -= 1;
                                    break;
                                }
                                case "hider": {
                                    if (hider2) {
                                        System.out.println("Another hider is currently activated.");
                                        break;
                                    }
                                    hider1 = true;
                                    Collections.shuffle(deck2);
                                    System.out.println("Hider activated!");
                                    Player1user.ROUNDS -= 1;
                                    break;
                                }
                                case "holeDisplace": {
                                    int holeIndex1 = 0;
                                    int holeIndex2 = 0;
                                    boolean holeExist1 = false;
                                    boolean holeExist2 = false;
                                    for (int j = 0; j < board1.size(); j++) {
                                        if (board1.get(j).getState() == Cell.State.hole) {
                                            holeIndex1 = j;
                                            holeExist1 = true;
                                            break;
                                        }
                                    }
                                    for (int j = 0; j < board2.size(); j++) {
                                        if (board2.get(j).getState() == Cell.State.hole) {
                                            holeIndex2 = j;
                                            holeExist2 = true;
                                            break;
                                        }
                                    }
                                    if (!holeExist1) {
                                        System.out.println("No hole exists in board of player 1.");
                                        break;
                                    }
                                    if (!holeExist2) {
                                        System.out.println("No hole exists in board of player 2.");
                                        break;
                                    }
                                    int random1 = Controller.randomFromBoard();
                                    int random2 = Controller.randomFromBoard();
                                    while (board1.get(random1).getState() != Cell.State.empty && random1 != holeIndex1) {
                                        random1 = Controller.random();
                                    }
                                    while (board2.get(random2).getState() != Cell.State.empty && random2 != holeIndex2) {
                                        random2 = Controller.random();
                                    }
                                    board1.get(random1).setState(Cell.State.hole);
                                    board1.get(holeIndex1).setState(Cell.State.empty);
                                    board2.get(random2).setState(Cell.State.hole);
                                    board2.get(holeIndex2).setState(Cell.State.empty);
                                    System.out.println("Hole displaced successfully!");
                                    Player1user.ROUNDS -= 1;
                                    break;
                                }
                                case "holeFixer": {
                                    boolean holeExists = false;
                                    for (int j = 0; j < board1.size(); j++) {
                                        if (board1.get(j).getState() == Cell.State.hole) {
                                            board1.get(j).setState(Cell.State.empty);
                                            System.out.println("Hole fixed successfully!");
                                            holeExists = true;
                                            break;
                                        }
                                    }
                                    if (!holeExists) {
                                        System.out.println("No hole exists in your board.");
                                        break;
                                    }
                                    Player1user.ROUNDS -= 1;
                                    break;
                                }
                                case "roundDecrease": {
                                    Player1user.ROUNDS -= 1;
                                    Player2user.ROUNDS -= 1;
                                    System.out.println("Your opponent skips a turn.");
                                    break;
                                }
                                case "shield": {
                                    boomed.clear();
                                    for (int j = 0; j < 21 && j != answercell - 1; j++) {
                                        if (board1.get(j).getState().equals(Cell.State.full) && board2.get(j).getState().equals(Cell.State.full)) {
                                            if ((board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("shield"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("shield"))) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else if (board1.get(j).card.getName().equals("heal") || board1.get(j).card.getName().equals("shield")) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                            } else if (board2.get(j).card.getName().equals("heal") || board2.get(j).card.getName().equals("shield")) {
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else {
                                                if (board1.get(j).cellstrength > board2.get(j).cellstrength) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.boom;
                                                    boomed.add(board2.get(j).card);

                                                }
                                                //equal situation
                                                else if (board1.get(j).celldamage == board2.get(j).celldamage) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.empty;
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.empty;

                                                } else {
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.boom;
                                                }
                                            }
                                        }
                                    }

                                    Player1user.DAMAGE = 0;
                                    Player2user.DAMAGE = 0;
                                    // Player1user.DAMAGE += card.getPlayerdamage();
                                    if (board2.get(answercell - 1).getState().equals(Cell.State.full)) {
                                        //  Player2user.DAMAGE -= board1.get(answercell-1).celldamage;
                                        board2.get(answercell - 1).setState(Cell.State.boom);
                                        board2.get(answercell - 1).setCelldamage(0);
                                        board1.get(answercell - 1).setCelldamage(card.divideddamage);
                                        board2.get(answercell - 1).setCellstrength(0);
                                        boomed.add(board2.get(answercell - 1).card);
                                    }
                                    for (int j = 0; j < 21; j++) {
                                        Player1user.DAMAGE += board1.get(j).celldamage;
                                        Player2user.DAMAGE += board2.get(j).celldamage;
                                    }
                                    Player1user.ROUNDS -= 1;
                                    break;
                                }
                                case "strengthBooster": {
                                    int tobeBuffed = Controller.randomFromBoard();
                                    while (!board1.get(tobeBuffed).getState().equals(Cell.State.full))
                                        tobeBuffed = Controller.randomFromBoard();
                                    String tobeBuffedCardName = board1.get(tobeBuffed).card.getName();
                                    ArrayList<Integer> tobeBuffedArrayList = new ArrayList<>();
                                    for (int j = 0; j < 21; j++) {
                                        if (board1.get(j).state.equals(Cell.State.full))
                                            if (board1.get(j).card.getName().equals(tobeBuffedCardName))
                                                tobeBuffedArrayList.add(j);
                                    }
                                    for (int j = 0; j < tobeBuffedArrayList.size(); j++) {
                                        int previous1 = board1.get(tobeBuffedArrayList.get(j)).card.getStrength();
                                        board1.get(tobeBuffedArrayList.get(j)).card.setstrength(previous1 + 1);
                                    }
                                    System.out.println("A card buffed from the deck successfully.");

                                    for (int j = 0; j < 21; j++) {
                                        if (board1.get(j).getState() == Cell.State.full && board2.get(j).getState() == Cell.State.full) {
                                            if ((board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("shield"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("shield"))) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else if (board1.get(j).card.getName().equals("heal") || board1.get(j).card.getName().equals("shield")) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                            } else if (board2.get(j).card.getName().equals("heal") || board2.get(j).card.getName().equals("shield")) {
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else {
                                                if (board1.get(j).cellstrength > board2.get(j).cellstrength) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.boom;
                                                    boomed.add(board2.get(j).card);

                                                }
                                                //equal situation
                                                else if (board1.get(j).celldamage == board2.get(j).celldamage) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.empty;
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.empty;

                                                } else {
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.boom;
                                                }
                                            }
                                        }
                                    }
                                    System.out.println("Buffing affected the gameboard successfully.");
                                    Player1user.ROUNDS -= 1;
                                    break;
                                }
                                case "takeCardFromOpp": {
                                    if (hider1) {
                                        System.out.println("Error: A hider is currently activated.");
                                        break;
                                    }
                                    int randomCard = Controller.randomFromDeck(deck2.size());
                                    System.out.println(deck2.get(randomCard).getName() + " was deleted from deck of player2.");
                                    deck1.add(deck2.get(randomCard));
                                    deck2.remove(randomCard);
                                    //-------------------------------------------------
                                    System.out.println("Player 1:");
                                    for (int j = 0; j < deck1.size(); j++) {
                                        System.out.println(j + 1 + "." + "Name: " + deck1.get(j).name + " Type: " + deck1.get(j).getType() + " Character: " + deck1.get(j).getPlayerCharacter().getName() + " Duration: " + deck1.get(j).duration + " Damage: " + deck1.get(j).playerdamage + " Attack: " + deck1.get(j).strength);
                                    }
                                    System.out.println("Player 2:");
                                    for (int j = 0; j < deck2.size(); j++) {
                                        System.out.println(j + 1 + "." + "Name: " + deck2.get(j).name + " Type: " + deck2.get(j).getType() + " Character: " + deck2.get(j).getPlayerCharacter().getName() + " Duration: " + deck2.get(j).duration + " Damage: " + deck2.get(j).playerdamage + " Attack: " + deck2.get(j).strength);
                                    }

                                    System.out.println("Now choose a card:");
                                    copyCardAnswer = scanner.nextInt();
                                    while (Objects.equals(deck1.get(copyCardAnswer - 1).getName(), "takeCardFromOpp") || copyCardAnswer < 1 || copyCardAnswer > deck1.size() || deck1.get(copyCardAnswer - 1).getType().equals("SPECIAL")) {
                                        System.out.println("INVALID NUMBER. TRY AGAIN:");
                                        copyCardAnswer = scanner.nextInt();
                                    }
                                    card = deck1.get(copyCardAnswer - 1);
                                    deck1.remove(copyCardAnswer - 1);

                                    //Card = card +1 => array
                                    System.out.println("Select the cell you want to place the card in: ");
                                    answercell = scanner.nextInt();
                                    //Block = cell + 1 => array;
                                    valid = false;
                                    while (!valid) {
                                        for (int l = answercell - 1; l < answercell - 1 + card.duration; l++) {
                                            if (board1.get(l).state.equals(Cell.State.full) || board1.get(l).state.equals(Cell.State.boom) || board1.get(l).state.equals(Cell.State.hole) || answercell - 1 + card.duration > 20) {
                                                valid = false;
                                                System.out.println("Please try again!");
                                                System.out.println("Select the card you want to place: ");
                                                answercard = scanner.nextInt();
                                                //Card = card +1 =>array
                                                System.out.println("Select the cell you want to place the card in: ");
                                                answercell = scanner.nextInt();
                                                card = deck1.get(answercard - 1);
                                                //Block = cell + 1 => array;
                                                continue;
                                                // public Cell(int index, User user, Cell.State state, int celldamage, int cellstrength) {
                                                //each cell has this constructor
                                            }

                                            valid = true;

                                        }

                                    }
                                    for (int j = answercell - 1; j < answercell - 1 + card.duration; j++) {
                                        board1.get(j).setState(Cell.State.full);
                                        board1.get(j).setCelldamage(card.divideddamage);
                                        board1.get(j).setCellstrength(card.strength);
                                        board1.get(j).card = card;
                                    }

                                    boomed.clear();
                                    for (int j = 0; j < 21; j++) {
                                        if (board1.get(j).getState().equals(Cell.State.full) && board2.get(j).getState().equals(Cell.State.full)) {
                                            if ((board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("shield"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("shield"))) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else if (board1.get(j).card.getName().equals("heal") || board1.get(j).card.getName().equals("shield")) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                            } else if (board2.get(j).card.getName().equals("heal") || board2.get(j).card.getName().equals("shield")) {
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else {
                                                if (board1.get(j).cellstrength > board2.get(j).cellstrength) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.boom;
                                                    boomed.add(board2.get(j).card);

                                                }
                                                //equal situation
                                                else if (board1.get(j).celldamage == board2.get(j).celldamage) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.empty;
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.empty;

                                                } else {
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.boom;
                                                }
                                            }
                                        }
                                    }
                                    Player1user.DAMAGE = 0;
                                    Player2user.DAMAGE = 0;
                                    for (int j = 0; j < 21; j++) {
                                        Player1user.DAMAGE += board1.get(j).celldamage;
                                        Player2user.DAMAGE += board2.get(j).celldamage;
                                    }
                                    Player2user.ROUNDS -= 1;
                                    break;
                                }
                                case "weakenCardFromOpp": {
                                    int random1 = Controller.randomFromDeck(deck1.size());
                                    int random2 = Controller.randomFromDeck(deck1.size());
                                    while (random1 == random2) {
                                        random2 = Controller.randomFromDeck(deck1.size());
                                    }
                                    int currentStr = deck2.get(random1).getstrength();
                                    deck2.get(random1).setStrength(currentStr - 1);
                                    int currentDamgage = deck2.get(random2).getPlayerdamage();
                                    deck2.get(random2).setPlayerdamage(currentDamgage - deck2.get(random2).getDuration());
                                    Player1user.ROUNDS -= 1;
                                    break;
                                }
                            }
                        }
                        System.out.println("-------ResultPlayer1-------");
                        System.out.println("Player1: ");
                        for (int j = 0; j < deck1.size(); j++) {
                            System.out.println(j + 1 + "." + "Name: " + deck1.get(j).name + " Type: " + deck1.get(j).type + " Character: " + deck1.get(j).getPlayerCharacter().getName() + " Duration: " + deck1.get(j).duration + " Damage: " + deck1.get(j).playerdamage + " Attack: " + deck1.get(j).strength);
                            if (hider2)
                                hider2 = false;
                        }
                        System.out.println("Player2: ");
                        for (int j = 0; j < deck2.size(); j++) {
                            System.out.print(j + 1 + ".");
                            if (!hider1)
                                System.out.print("Name: " + deck2.get(j).name + " Type: " + deck2.get(j).getType() + " Character: " + deck2.get(j).getPlayerCharacter().getName() + " Duration: " + deck2.get(j).duration + " Damage: " + deck2.get(j).playerdamage + " Attack: " + deck2.get(j).strength);
                            System.out.println();
                        }
                        System.out.println("Damage1: " + Player1user.getDAMAGE() + "  Damage2: " + Player2user.getDAMAGE());
                        System.out.println("Rounds1: " + Player1user.ROUNDS + "  Rounds2: " + Player2user.ROUNDS);
                        System.out.println("HP1: " + Player1user.HP + "  HP2: " + Player2user.HP);
                        System.out.println("Character1: " + Player1user.getCharactername() + " Character2: " + Player2user.getCharactername());
                        for (int k = 0; k < 21; k++) {
                            if (!board1.get(k).state.equals(Cell.State.full))
                                System.out.print(k + 1 + ".[ " + Cell.toStringg(board1.get(k).state) + "] ");

                            else {
                                System.out.print(k + 1 + ".[N: " + board1.get(k).card.name + " - S: " + board1.get(k).card.strength + " - D: " + board1.get(k).card.divideddamage + "] ");
                            }
                            if (!board2.get(k).state.equals(Cell.State.full))
                                System.out.print(" [ " + Cell.toStringg(board2.get(k).state) + "] ");
                            else {
                                System.out.print(" [N: " + board2.get(k).card.name + " - S: " + board2.get(k).card.strength + " - D: " + board2.get(k).card.divideddamage + "] ");
                            }
                            System.out.println();
                        }
                    }
                    //Player2 starts
                    if (Player2user.ROUNDS > 0) {
                        System.out.println("------------------Player2 should choose now--------------");
                        System.out.println("Player1: ");
                        for (int j = 0; j < deck1.size(); j++) {
                            System.out.println(j + 1 + "." + "Name: " + deck1.get(j).name + " Type: " + deck1.get(j).type + " Character: " + deck1.get(j).getPlayerCharacter().getName() + " Duration: " + deck1.get(j).duration + " Damage: " + deck1.get(j).playerdamage + " Attack: " + deck1.get(j).strength);
                        }
                        System.out.println("Player2: ");
                        for (int j = 0; j < deck2.size(); j++) {
                            System.out.print(j + 1 + ".");
                            if (!hider1)
                                System.out.print("Name: " + deck2.get(j).name + " Type: " + deck2.get(j).getType() + " Character: " + deck2.get(j).getPlayerCharacter().getName() + " Duration: " + deck2.get(j).duration + " Damage: " + deck2.get(j).playerdamage + " Attack: " + deck2.get(j).strength);
                            System.out.println();
                        }
                        System.out.println("Damage1: " + Player1user.getDAMAGE() + "  Damage2: " + Player2user.getDAMAGE());
                        System.out.println("Rounds1: " + Player1user.ROUNDS + "  Rounds2: " + Player2user.ROUNDS);
                        System.out.println("HP1: " + Player1user.HP + "  HP2: " + Player2user.HP);
                        System.out.println("Character1: " + Player1user.getCharactername() + " Character2: " + Player2user.getCharactername());
                        for (int k = 0; k < 21; k++) {
                            if (!board1.get(k).state.equals(Cell.State.full))
                                System.out.print(k + 1 + ".[" + Cell.toStringg(board1.get(k).state) + "] ");

                            else {
                                System.out.print(k + 1 + ".[N: " + board1.get(k).card.name + " - " + "S: " + board1.get(k).card.strength + " - " + "D: " + board1.get(k).card.divideddamage + "] ");
                            }
                            if (!board2.get(k).state.equals(Cell.State.full))
                                System.out.print(" [" + Cell.toStringg(board2.get(k).state) + "] ");
                            else {
                                System.out.print(" [N: " + board2.get(k).card.name + " - " + "S: " + board2.get(k).card.strength + " - " + "D: " + board2.get(k).card.divideddamage + "] ");
                            }
                            System.out.println();
                        }
                        //SELECTING CARD FOR PLAYER2
                        System.out.println("PLAYER 2 STARTS");
                        System.out.println("Select the card you want to place: ");
                        answercard = scanner.nextInt();
                        card = deck2.get(answercard - 1);
                        //Card = card +1 =>array
                        if (card.getType().equals("ORDINARY") || card.getName().equals("heal") || card.getName().equals("shield")) {

                            System.out.println("Select the cell you want to place the card in: ");
                            answercell = scanner.nextInt();
                            //Block = cell + 1 => array;

                            while (!valid) {
                                for (int l = answercell - 1; l < answercell - 1 + card.duration; l++) {
                                    if (board2.get(l).state.equals(Cell.State.full) || board2.get(l).state.equals(Cell.State.boom) || board2.get(l).state.equals(Cell.State.hole) || answercell - 1 + card.duration > 20) {
                                        valid = false;
                                        System.out.println("Please try again!");
                                        System.out.println("Select the card you want to place: ");
                                        answercard = scanner.nextInt();
                                        //Card = card +1 =>array
                                        System.out.println("Select the cell you want to place the card in: ");
                                        answercell = scanner.nextInt();
                                        card = deck2.get(answercard - 1);
                                        //Block = cell + 1 => array;
                                        continue;
                                        // public Cell(int index, User user, Cell.State state, int celldamage, int cellstrength) {
                                        //each cell has this constructor
                                    }

                                    valid = true;
                                }

                            }

                            if (deck2.size() == 4) {
                                int addIndex = Controller.randomFromDeck(Player2user.cardsInCardType.size());
                                deck2.add(Player1user.cardsInCardType.get(addIndex));
                            }

                            for (int j = answercell - 1; j < answercell - 1 + card.duration; j++) {
                                board2.get(j).setState(Cell.State.full);
                                board2.get(j).setCelldamage(card.divideddamage);
                                board2.get(j).setCellstrength(card.strength);
                                board2.get(j).card = card;
                            }
                        }
                        neww = Controller.random();
                        deck2.set(answercard - 1, Player2user.cardsInCardType.get(neww));
                        if (buffWithPossibility50) {
                            if (deck1.size() == 5)
                                if (deck1.get(2).getPlayerCharacter().equals(Player1user.getCharactername())) {
                                    int prevoiusStr = deck1.get(2).getStrength();
                                    deck1.get(2).setstrength(prevoiusStr + 1);
                                    System.out.println("Player 2's middle card was buffed because being of the same type with your character.");
                                }
                        }
                        //Creating boomed arraylist
                        if (card.getType().equals("ORDINARY")) {
                            boomed.clear();
                            for (int j = 0; j < 21; j++) {
                                if (board1.get(j).getState().equals(Cell.State.full) && board2.get(j).getState().equals(Cell.State.full)) {
                                    if ((board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("heal"))
                                            || (board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("shield"))
                                            || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("heal"))
                                            || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("shield"))) {
                                        board2.get(j).cellstrength = 0;
                                        board2.get(j).celldamage = 0;
                                        board2.get(j).state = Cell.State.boom;
                                        boomed.add(board2.get(j).card);
                                        board1.get(j).cellstrength = 0;
                                        board1.get(j).celldamage = 0;
                                        board1.get(j).state = Cell.State.boom;
                                        boomed.add(board1.get(j).card);
                                    } else if (board1.get(j).card.getName().equals("heal") || board1.get(j).card.getName().equals("shield")) {
                                        board2.get(j).cellstrength = 0;
                                        board2.get(j).celldamage = 0;
                                        board2.get(j).state = Cell.State.boom;
                                        boomed.add(board2.get(j).card);
                                    } else if (board2.get(j).card.getName().equals("heal") || board2.get(j).card.getName().equals("shield")) {
                                        board1.get(j).cellstrength = 0;
                                        board1.get(j).celldamage = 0;
                                        board1.get(j).state = Cell.State.boom;
                                        boomed.add(board1.get(j).card);
                                    } else {
                                        if (board1.get(j).cellstrength > board2.get(j).cellstrength) {
                                            board2.get(j).cellstrength = 0;
                                            board2.get(j).celldamage = 0;
                                            board2.get(j).state = Cell.State.boom;
                                            boomed.add(board2.get(j).card);

                                        }
                                        //equal situation
                                        else if (board1.get(j).celldamage == board2.get(j).celldamage) {
                                            board2.get(j).cellstrength = 0;
                                            board2.get(j).celldamage = 0;
                                            board2.get(j).state = Cell.State.empty;
                                            board1.get(j).cellstrength = 0;
                                            board1.get(j).celldamage = 0;
                                            board1.get(j).state = Cell.State.empty;

                                        } else {
                                            board1.get(j).cellstrength = 0;
                                            board1.get(j).celldamage = 0;
                                            board1.get(j).state = Cell.State.boom;
                                        }
                                    }
                                }
                            }
                            Player1user.DAMAGE = 0;
                            Player2user.DAMAGE = 0;
                            for (int j = 0; j < 21; j++) {
                                Player1user.DAMAGE += board1.get(j).celldamage;
                                Player2user.DAMAGE += board2.get(j).celldamage;
                            }
                            Player2user.ROUNDS -= 1;


                        } else {
                            int copyCardAnswer = 0;
                            switch (card.name) {
                                case "copyCard": {
                                    if (hider2) {
                                        System.out.println("Error: A hider is currently activated.");
                                        break;
                                    }
                                    System.out.println("Please select a card out of your deck: ");
                                    copyCardAnswer = scanner.nextInt();
                                    while (copyCardAnswer < 1 || copyCardAnswer > deck2.size() || Objects.equals(deck2.get(copyCardAnswer - 1).getName(), "copyCard")) {
                                        System.out.println("INVALID NUMBER. TRY AGAIN:");
                                        copyCardAnswer = scanner.nextInt();
                                    }
                                    Card cardCopy = new Card(deck2.get(copyCardAnswer - 1));
                                    deck2.add(cardCopy);
                                    System.out.println("Copied card added to deck successfully.");
                                    System.out.println("Now choose a card:");
                                    copyCardAnswer = scanner.nextInt();
                                    while (Objects.equals(deck2.get(copyCardAnswer - 1).getName(), "copyCard") || copyCardAnswer < 1 || copyCardAnswer > deck1.size() || deck2.get(copyCardAnswer - 1).getType().equals("SPECIAL")) {
                                        System.out.println("INVALID NUMBER/SPECIAL CARD WAS CHOSEN. TRY AGAIN:");
                                        copyCardAnswer = scanner.nextInt();
                                    }
                                    card = deck2.get(copyCardAnswer - 1);
                                    neww = Controller.random();
                                    deck2.set(answercard - 1, Player1user.cardsInCardType.get(neww));
                                    deck2.remove(copyCardAnswer - 1);
                                    if (buffWithPossibility50) {
                                        if (deck1.size() == 5)
                                            if (deck1.get(2).getPlayerCharacter().equals(Player1user.getCharactername())) {
                                                int prevoiusStr = deck1.get(2).getStrength();
                                                deck1.get(2).setstrength(prevoiusStr + 1);
                                                System.out.println("Your middle card was buffed because being of the same type with your character.");
                                            }
                                    }


                                    //Card = card +1 =>array
                                    System.out.println("Select the cell you want to place the card in: ");
                                    answercell = scanner.nextInt();
                                    //Block = cell + 1 => array;
                                    valid = false;
                                    while (!valid) {
                                        for (int l = answercell - 1; l < answercell - 1 + card.duration; l++) {
                                            if (board2.get(l).state.equals(Cell.State.full) || board2.get(l).state.equals(Cell.State.boom) || board2.get(l).state.equals(Cell.State.hole) || answercell - 1 + card.duration > 20) {
                                                valid = false;
                                                System.out.println("Please try again!");
                                                System.out.println("Select the card you want to place: ");
                                                answercard = scanner.nextInt();
                                                //Card = card +1 =>array
                                                System.out.println("Select the cell you want to place the card in: ");
                                                answercell = scanner.nextInt();
                                                card = deck2.get(answercard - 1);
                                                //Block = cell + 1 => array;
                                                continue;
                                                // public Cell(int index, User user, Cell.State state, int celldamage, int cellstrength) {
                                                //each cell has this constructor
                                            }
                                            valid = true;
                                        }

                                    }
                                    for (int j = answercell - 1; j < answercell - 1 + card.duration; j++) {
                                        board2.get(j).setState(Cell.State.full);
                                        board2.get(j).setCelldamage(card.divideddamage);
                                        board2.get(j).setCellstrength(card.strength);
                                        board2.get(j).card = card;
                                    }

                                    boomed.clear();
                                    for (int j = 0; j < 21; j++) {
                                        if (board1.get(j).getState().equals(Cell.State.full) && board2.get(j).getState().equals(Cell.State.full)) {
                                            if ((board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("shield"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("shield"))) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else if (board1.get(j).card.getName().equals("heal") || board1.get(j).card.getName().equals("shield")) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                            } else if (board2.get(j).card.getName().equals("heal") || board2.get(j).card.getName().equals("shield")) {
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                            } else {
                                                if (board1.get(j).cellstrength > board2.get(j).cellstrength) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.boom;
                                                    boomed.add(board2.get(j).card);

                                                }
                                                //equal situation
                                                else if (board1.get(j).celldamage == board2.get(j).celldamage) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.empty;
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.empty;

                                                } else {
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.boom;
                                                }
                                            }
                                        }
                                    }
                                    Player1user.DAMAGE = 0;
                                    Player2user.DAMAGE = 0;
                                    for (int j = 0; j < 21; j++) {
                                        Player1user.DAMAGE += board1.get(j).celldamage;
                                        Player2user.DAMAGE += board2.get(j).celldamage;
                                    }
                                    Player2user.ROUNDS -= 1;
                                    break;
                                }
                                case "heal": {
                                    int currentHp = Player2user.getHP();
                                    Player2user.setHP(currentHp + 50);
                                    boomed.clear();
                                    for (int j = 0; j < 21 && j != answercell - 1; j++) {
                                        if (board1.get(j).getState().equals(Cell.State.full) && board2.get(j).getState().equals(Cell.State.full)) {
                                            if ((board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("shield"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("shield"))) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else if (board1.get(j).card.getName().equals("heal") || board1.get(j).card.getName().equals("shield")) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                            } else if (board2.get(j).card.getName().equals("heal") || board2.get(j).card.getName().equals("shield")) {
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else {
                                                if (board1.get(j).cellstrength > board2.get(j).cellstrength) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.boom;
                                                    boomed.add(board2.get(j).card);

                                                }
                                                //equal situation
                                                else if (board1.get(j).celldamage == board2.get(j).celldamage) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.empty;
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.empty;

                                                } else {
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.boom;
                                                }
                                            }
                                        }
                                    }

                                    Player1user.DAMAGE = 0;
                                    Player2user.DAMAGE = 0;
                                    if (board1.get(answercell - 1).getState().equals(Cell.State.full)) {
                                        //Player1user.DAMAGE -= board1.get(answercell-1).celldamage;
                                        board1.get(answercell - 1).setState(Cell.State.boom);
                                        board1.get(answercell - 1).setCelldamage(0);
                                        board2.get(answercell - 1).setCelldamage(card.divideddamage);
                                        board1.get(answercell - 1).setCellstrength(0);
                                        boomed.add(board1.get(answercell - 1).card);
                                    }
                                    for (int j = 0; j < 21; j++) {
                                        Player1user.DAMAGE += board1.get(j).celldamage;
                                        Player2user.DAMAGE += board2.get(j).celldamage;
                                    }
                                    Player2user.ROUNDS -= 1;
                                    break;
                                }
                                case "hider": {
                                    if (hider1) {
                                        System.out.println("Another hider is currently activated.");
                                        break;
                                    }
                                    hider2 = true;
                                    Collections.shuffle(deck1);
                                    System.out.println("Hider activated!");
                                    Player2user.ROUNDS -= 1;
                                    break;
                                }
                                case "holeDisplace": {
                                    int holeIndex1 = 0;
                                    int holeIndex2 = 0;
                                    boolean holeExist1 = false;
                                    boolean holeExist2 = false;
                                    for (int j = 0; j < board1.size(); j++) {
                                        if (board1.get(j).getState() == Cell.State.hole) {
                                            holeIndex1 = j;
                                            holeExist1 = true;
                                            break;
                                        }
                                    }
                                    for (int j = 0; j < board2.size(); j++) {
                                        if (board2.get(j).getState() == Cell.State.hole) {
                                            holeIndex2 = j;
                                            holeExist2 = true;
                                            break;
                                        }
                                    }
                                    if (!holeExist1) {
                                        System.out.println("No hole exists in board of player 1.");
                                        break;
                                    }
                                    if (!holeExist2) {
                                        System.out.println("No hole exists in board of player 2.");
                                        break;
                                    }
                                    int random1 = Controller.randomFromBoard();
                                    int random2 = Controller.randomFromBoard();
                                    while (board1.get(random1).getState() != Cell.State.empty && random1 != holeIndex1) {
                                        random1 = Controller.random();
                                    }
                                    while (board2.get(random2).getState() != Cell.State.empty && random2 != holeIndex2) {
                                        random2 = Controller.random();
                                    }
                                    board1.get(random1).setState(Cell.State.hole);
                                    board1.get(holeIndex1).setState(Cell.State.empty);
                                    board2.get(random2).setState(Cell.State.hole);
                                    board2.get(holeIndex2).setState(Cell.State.empty);
                                    System.out.println("Hole displaced successfully!");
                                    Player2user.ROUNDS -= 1;
                                    break;
                                }
                                case "holeFixer": {
                                    boolean holeExists = false;
                                    for (int j = 0; j < board1.size(); j++) {
                                        if (board2.get(j).getState() == Cell.State.hole) {
                                            board2.get(j).setState(Cell.State.empty);
                                            System.out.println("Hole fixed successfully!");
                                            System.out.println("Hole fixed successfully!");
                                            holeExists = true;
                                            break;
                                        }
                                    }
                                    if (!holeExists) {
                                        System.out.println("No hole exists in your board.");
                                        break;
                                    }
                                    Player2user.ROUNDS -= 1;
                                    break;
                                }
                                case "roundDecrease": {
                                    Player1user.ROUNDS -= 1;
                                    Player2user.ROUNDS -= 1;
                                    System.out.println("Your opponent skips a turn.");
                                    break;
                                }
                                case "shield": {
                                    boomed.clear();
                                    for (int j = 0; j < 21 && j != answercell - 1; j++) {
                                        //Player1user.DAMAGE =0;
                                        if (board1.get(j).state.equals(Cell.State.full) && board2.get(j).state.equals(Cell.State.full)) {
                                            if ((board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("shield"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("shield"))) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else if (board1.get(j).card.getName().equals("heal") || board1.get(j).card.getName().equals("shield")) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                            } else if (board2.get(j).card.getName().equals("heal") || board2.get(j).card.getName().equals("shield")) {
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else {
                                                if (board1.get(j).cellstrength > board2.get(j).cellstrength) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.boom;
                                                    boomed.add(board2.get(j).card);

                                                }
                                                //equal situation
                                                else if (board1.get(j).celldamage == board2.get(j).celldamage) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.empty;
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.empty;

                                                } else {
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.boom;
                                                }
                                            }
                                        }
                                    }

                                    Player1user.DAMAGE = 0;
                                    Player2user.DAMAGE = 0;

                                    // Player2user.DAMAGE += card.getPlayerdamage();
                                    if (board1.get(answercell - 1).getState().equals(Cell.State.full)) {
                                        //     Player1user.DAMAGE -= board1.get(answercell-1).celldamage;
                                        board1.get(answercell - 1).setState(Cell.State.boom);
                                        board1.get(answercell - 1).setCelldamage(0);
                                        board2.get(answercell - 1).setCelldamage(card.divideddamage);
                                        board1.get(answercell - 1).setCellstrength(0);
                                        boomed.add(board1.get(answercell - 1).card);
                                    }
                                    for (int j = 0; j < 21; j++) {
                                        Player1user.DAMAGE += board1.get(j).celldamage;
                                        Player2user.DAMAGE += board2.get(j).celldamage;
                                    }
                                    Player2user.ROUNDS -= 1;
                                    break;
                                }
                                case "strengthBooster": {
                                    int tobeBuffed = Controller.randomFromBoard();
                                    while (!board2.get(tobeBuffed).getState().equals(Cell.State.full))
                                        tobeBuffed = Controller.randomFromBoard();
                                    String tobeBuffedCardName = board2.get(tobeBuffed).card.getName();
                                    ArrayList<Integer> tobeBuffedArrayList = new ArrayList<>();
                                    for (int j = 0; j < 21; j++) {
                                        if (board2.get(j).state.equals(Cell.State.full))
                                            if (board2.get(j).card.getName().equals(tobeBuffedCardName))
                                                tobeBuffedArrayList.add(j);
                                    }
                                    for (int j = 0; j < tobeBuffedArrayList.size(); j++) {
                                        //int increment = 1;
                                        int previous1 = board2.get(tobeBuffedArrayList.get(j)).card.getStrength();
                                        board2.get(tobeBuffedArrayList.get(j)).card.setstrength(previous1 + 1);
                                        //  board2.get(tobeBuffedArrayList.get(j)).cellstrength += increment;
                                    }
                                    System.out.println("A card buffed from the deck successfully.");

                                    for (int j = 0; j < 21; j++) {
                                        if (board1.get(j).getState() == Cell.State.full && board2.get(j).getState() == Cell.State.full) {
                                            if ((board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("shield"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("shield"))) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else if (board1.get(j).card.getName().equals("heal") || board1.get(j).card.getName().equals("shield")) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                            } else if (board2.get(j).card.getName().equals("heal") || board2.get(j).card.getName().equals("shield")) {
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else {
                                                if (board1.get(j).cellstrength > board2.get(j).cellstrength) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.boom;
                                                    boomed.add(board2.get(j).card);

                                                }
                                                //equal situation
                                                else if (board1.get(j).celldamage == board2.get(j).celldamage) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.empty;
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.empty;

                                                } else {
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.boom;
                                                }
                                            }
                                        }
                                    }
                                    System.out.println("Buffing affected the gameboard successfully.");
                                    Player2user.ROUNDS -= 1;
                                    break;
                                }
                                case "takeCardFromOpp": {
                                    if (hider2) {
                                        System.out.println("Error: A hider is currently activated.");
                                        break;
                                    }
                                    int randomCard = Controller.randomFromDeck(deck1.size());
                                    System.out.println(deck1.get(randomCard).getName() + " was deleted from deck of player1.");
                                    deck2.add(deck1.get(randomCard));
                                    deck1.remove(randomCard);
                                    //-------------------------------------------------
                                    System.out.println("Player 1:");
                                    for (int j = 0; j < deck1.size(); j++) {
                                        System.out.println(j + 1 + "." + "Name: " + deck1.get(j).name + " Type: " + deck1.get(j).getType() + " Character: " + deck1.get(j).getPlayerCharacter().getName() + " Duration: " + deck1.get(j).duration + " Damage: " + deck1.get(j).playerdamage + " Attack: " + deck1.get(j).strength);
                                    }
                                    System.out.println("Player 2:");
                                    for (int j = 0; j < deck2.size(); j++) {
                                        System.out.println(j + 1 + "." + "Name: " + deck2.get(j).name + " Type: " + deck2.get(j).getType() + " Character: " + deck2.get(j).getPlayerCharacter().getName() + " Duration: " + deck2.get(j).duration + " Damage: " + deck2.get(j).playerdamage + " Attack: " + deck2.get(j).strength);
                                    }

                                    System.out.println("Now choose a card:");
                                    copyCardAnswer = scanner.nextInt();
                                    while (Objects.equals(deck2.get(copyCardAnswer - 1).getName(), "takeCardFromOpp") || copyCardAnswer < 1 || copyCardAnswer > deck2.size() || deck2.get(copyCardAnswer - 1).getType().equals("SPECIAL")) {
                                        System.out.println("INVALID NUMBER. TRY AGAIN:");
                                        copyCardAnswer = scanner.nextInt();
                                    }
                                    card = deck2.get(copyCardAnswer - 1);
                                    deck2.remove(copyCardAnswer - 1);

                                    //Card = card +1 => array
                                    System.out.println("Select the cell you want to place the card in: ");
                                    answercell = scanner.nextInt();
                                    //Block = cell + 1 => array;
                                    valid = false;
                                    while (!valid) {
                                        for (int l = answercell - 1; l < answercell - 1 + card.duration; l++) {
                                            if (board2.get(l).state.equals(Cell.State.full) || board2.get(l).state.equals(Cell.State.boom) || board2.get(l).state.equals(Cell.State.hole) || answercell - 1 + card.duration > 20) {
                                                valid = false;
                                                System.out.println("Please try again!");
                                                System.out.println("Select the card you want to place: ");
                                                answercard = scanner.nextInt();
                                                //Card = card +1 =>array
                                                System.out.println("Select the cell you want to place the card in: ");
                                                answercell = scanner.nextInt();
                                                card = deck2.get(answercard - 1);
                                                //Block = cell + 1 => array;
                                                continue;
                                                // public Cell(int index, User user, Cell.State state, int celldamage, int cellstrength) {
                                                //each cell has this constructor
                                            }

                                            valid = true;
                                        }

                                    }
                                    for (int j = answercell - 1; j < answercell - 1 + card.duration; j++) {
                                        board2.get(j).setState(Cell.State.full);
                                        board2.get(j).setCelldamage(card.divideddamage);
                                        board2.get(j).setCellstrength(card.strength);
                                        board2.get(j).card = card;
                                    }

                                    boomed.clear();
                                    for (int j = 0; j < 21; j++) {
                                        if (board1.get(j).getState().equals(Cell.State.full) && board2.get(j).getState().equals(Cell.State.full)) {
                                            if ((board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("heal") && board1.get(j).card.getName().equals("shield"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("heal"))
                                                    || (board1.get(j).card.getName().equals("shield") && board1.get(j).card.getName().equals("shield"))) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else if (board1.get(j).card.getName().equals("heal") || board1.get(j).card.getName().equals("shield")) {
                                                board2.get(j).cellstrength = 0;
                                                board2.get(j).celldamage = 0;
                                                board2.get(j).state = Cell.State.boom;
                                                boomed.add(board2.get(j).card);
                                            } else if (board2.get(j).card.getName().equals("heal") || board2.get(j).card.getName().equals("shield")) {
                                                board1.get(j).cellstrength = 0;
                                                board1.get(j).celldamage = 0;
                                                board1.get(j).state = Cell.State.boom;
                                                boomed.add(board1.get(j).card);
                                            } else {
                                                if (board1.get(j).cellstrength > board2.get(j).cellstrength) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.boom;
                                                    boomed.add(board2.get(j).card);

                                                }
                                                //equal situation
                                                else if (board1.get(j).celldamage == board2.get(j).celldamage) {
                                                    board2.get(j).cellstrength = 0;
                                                    board2.get(j).celldamage = 0;
                                                    board2.get(j).state = Cell.State.empty;
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.empty;

                                                } else {
                                                    board1.get(j).cellstrength = 0;
                                                    board1.get(j).celldamage = 0;
                                                    board1.get(j).state = Cell.State.boom;
                                                }
                                            }
                                        }
                                    }
                                    Player1user.DAMAGE = 0;
                                    Player2user.DAMAGE = 0;
                                    for (int j = 0; j < 21; j++) {
                                        Player1user.DAMAGE += board1.get(j).celldamage;
                                        Player2user.DAMAGE += board2.get(j).celldamage;
                                    }
                                    Player2user.ROUNDS -= 1;

                                    break;
                                }
                                case "weakenCardFromOpp": {
                                    int random1 = Controller.randomFromDeck(deck2.size());
                                    int random2 = Controller.randomFromDeck(deck2.size());
                                    while (random1 == random2) {
                                        random2 = Controller.randomFromDeck(deck2.size());
                                    }
                                    int currentStr = deck2.get(random1).getstrength();
                                    deck2.get(random1).setStrength(currentStr - 1);
                                    int currentDamgage = deck2.get(random2).getPlayerdamage();
                                    deck2.get(random2).setPlayerdamage(currentDamgage - deck2.get(random2).getDuration());
                                    Player2user.ROUNDS -= 1;
                                    break;
                                }
                            }
                        }
                        System.out.println("-------ResultPlayer2-------");
                        System.out.println("Player1: ");
                        for (int j = 0; j < deck1.size(); j++) {
                            System.out.print(j + 1 + ".");
                            if (!hider2)
                                System.out.print("Name: " + deck1.get(j).name + " Type: " + deck1.get(j).getType() + " Character: " + deck1.get(j).getPlayerCharacter().getName() + " Duration: " + deck1.get(j).duration + " Damage: " + deck1.get(j).playerdamage + " Attack: " + deck1.get(j).strength);
                            System.out.println();
                        }
                        System.out.println("Player2: ");
                        for (int j = 0; j < deck2.size(); j++) {
                            System.out.println(j + 1 + "." + "Name: " + deck2.get(j).name + " Type: " + deck2.get(j).getType() + " Character: " + deck2.get(j).getPlayerCharacter().getName() + " Duration: " + deck2.get(j).duration + " Damage: " + deck2.get(j).playerdamage + " Attack: " + deck2.get(j).strength);
                            if (hider1)
                                hider1 = false;
                        }
                        System.out.println("Damage1: " + Player1user.getDAMAGE() + "  Damage2: " + Player2user.getDAMAGE());
                        System.out.println("Rounds1: " + Player1user.ROUNDS + "  Rounds2: " + Player2user.ROUNDS);
                        System.out.println("HP1: " + Player1user.HP + "  HP2: " + Player2user.HP);
                        System.out.println("Character1: " + Player1user.getCharactername() + " Character2: " + Player2user.getCharactername());
                        for (int k = 0; k < 21; k++) {
                            if (!board1.get(k).state.equals(Cell.State.full))
                                System.out.print(k + 1 + ".[ " + Cell.toStringg(board1.get(k).state) + "] ");

                            else {
                                System.out.print(k + 1 + ".[N: " + board1.get(k).card.name + " - S: " + board1.get(k).card.strength + " - D: " + board1.get(k).card.divideddamage + "] ");
                            }
                            if (!board2.get(k).state.equals(Cell.State.full))
                                System.out.print(" [ " + Cell.toStringg(board2.get(k).state) + "] ");
                            else {
                                System.out.print(" [N: " + board2.get(k).card.name + "/" + " - S: " + board2.get(k).card.strength + "/" + " - D: " + board2.get(k).card.divideddamage + "] ");
                            }
                            System.out.println();
                        }
                    }
                }
        }
            //STARTING THE COMBAT PHASE
            Player1user.DAMAGE = 0;
            Player2user.DAMAGE = 0;
            for(int i=0; i<21; i++){
                if(board1.get(i).getState().equals(Cell.State.full)){
                    Player1user.DAMAGE += board1.get(i).celldamage;
                    Player2user.HP -= board1.get(i).celldamage;
                }
                if(board2.get(i).getState().equals(Cell.State.full)){
                    Player2user.DAMAGE += board2.get(i).celldamage;
                    Player1user.HP -= board2.get(i).celldamage;
                }
                if (!board1.get(i).state.equals(Cell.State.full))
                    System.out.print(i + 1 + ".[" + Cell.toStringg(board1.get(i).state) + "] ");

                else {
                    System.out.print(i + 1 + ".[N: " + board1.get(i).card.name + " - " + "S: " + board1.get(i).card.strength + " - " + "D: " + board1.get(i).card.divideddamage + "] ");
                }
                if (!board2.get(i).state.equals(Cell.State.full))
                    System.out.print(" [" + Cell.toStringg(board2.get(i).state) + "] ");
                else {
                    System.out.print(" [N: " + board2.get(i).card.name + " - " + "S: " + board2.get(i).card.strength + " - " + "D: " + board2.get(i).card.divideddamage + "] ");
                }
                System.out.println("Damage1: " + Player1user.getDAMAGE() + "  Damage2: " + Player2user.getDAMAGE());
                int printHP1, printHP2;
                if(Player1user.HP < 0){
                    Player1user.HP = 0;
                    printHP1 = 0;}
                else
                    printHP1 = Player1user.HP;
                if(Player2user.HP < 0){
                    Player2user.HP = 0;
                    printHP2 = 0;}
                else
                    printHP2 = Player2user.HP;

                System.out.println("HP1: " + printHP1 + "  HP2: " + printHP2);

                System.out.println("Cell number " + (i+1) + " processed!");
                System.out.println();
                try{
                Thread.sleep(1000); // 1 second delay
                } catch (InterruptedException e){
                System.err.println(e.getMessage());
                }
                if(Player1user.HP <= 0 || Player2user.HP <= 0 )
                    break;

            }
        }
        //WINNER AND LOSER
        String player1_State = "", player2_State = "";
        String player1_Outcome = "", player2_Outcome = "";
        int prevoius_coins = 0;
        try {
            final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
            final String USERNAME = "root";
            final String PASSWORD = "138387Amitis";
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String query = "";
            User Winner, Loser;
            if (Player1user.HP == 0) {
                Winner = Player2user;
                Loser = Player1user;
                player1_State = "LOSE";
                player2_State = "WIN";
                prevoius_coins = Player2user.getCoins();
                Player2user.setCoins(calculateCoins(Player1user.DAMAGE, Player2user.DAMAGE, Player2user.HP));
                player2_Outcome = "Coins+" + (Player2user.getCoins()-prevoius_coins);
                Player1user.setCoins(Player1user.getCoins()-2);
                player1_Outcome = "Coins-2";
                Player1user.setXP(calculateXP(Player1user.DAMAGE, Player2user.DAMAGE, Player2user.HP, false));
                Player2user.setXP(calculateXP(Player1user.DAMAGE, Player2user.DAMAGE, Player2user.HP, true));
                System.out.println("Congrats! User 2 has won!");
            }
            if (Player2user.HP == 0) {
                Winner = Player1user;
                Loser = Player2user;
                player1_State = "WIN";
                player2_State = "LOSE";
                prevoius_coins = Player1user.getCoins();
                Player1user.setCoins(calculateCoins(Player1user.DAMAGE, Player2user.DAMAGE, Player1user.HP));
                player1_Outcome = "Coins+" + (Player1user.getCoins()-prevoius_coins);
                Player2user.setCoins(Player2user.getCoins()-2);
                player2_Outcome = "Coins-2";
                Player1user.setXP(calculateXP(Player1user.DAMAGE, Player2user.DAMAGE, Player1user.HP, true));
                Player2user.setXP(calculateXP(Player1user.DAMAGE, Player2user.DAMAGE, Player1user.HP, false));
                System.out.println("Congrats! User 1 has won!");
            }
            System.out.println("Player 1 Result: ");
            System.out.println("Coins: " + Player1user.getCoins());
            System.out.println("XP: " + Player1user.getXP());
            System.out.println("Player 2 Result: ");
            System.out.println("Coins: "+ Player2user.getCoins());
            System.out.println("XP: " + Player2user.getXP());

            int player1_previousLevel = Player1user.getLevel();
            int player2_previousLevel = Player2user.getLevel();

            //CHECKS FOR LEVELUP
            levelup(Player1user);
            levelup(Player2user);

            //UPDATING
            Statement statement = conn.createStatement();
            query = "update users set coins = " +Player1user.getCoins()  + " where username = \"" + Player1user.username + "\";";
            int rowsAffected = statement.executeUpdate(query);
            System.out.println("Coins of player1 updated in DB.");
            System.out.println(rowsAffected + " row(s) affected.");
            query = "update users set coins = " +Player2user.getCoins()  + " where username = \"" + Player2user.username + "\";";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Coins of player2 updated in DB.");
            System.out.println(rowsAffected + " row(s) affected.");

            query = "update users set XP = " +Player1user.getXP()  + " where username = \"" + Player1user.username + "\";";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("XP of player1 updated in DB.");
            System.out.println(rowsAffected + " row(s) affected.");

            query = "update users set XP = " +Player2user.getXP()  + " where username = \"" + Player2user.username + "\";";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("XP of player2 updated in DB.");
            System.out.println(rowsAffected + " row(s) affected.");

            query = "update users set level = " +Player1user.getLevel()  + " where username = \"" + Player1user.username + "\";";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Level of player1 updated in DB");
            System.out.println(rowsAffected + " row(s) affected.");

            query = "update users set level = " +Player2user.getLevel()  + " where username = \"" + Player2user.username + "\";";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Level of player2 updated in DB");
            System.out.println(rowsAffected + " row(s) affected.");

            query = "update users set MaxHP = " +Player1user.getMaxHP()  + " where username = \"" + Player1user.username + "\";";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("MaxHP of player1 updated in DB.");
            System.out.println(rowsAffected + " row(s) affected.");

            query = "update users set MaxHP = " +Player2user.getMaxHP()  + " where username = \"" + Player2user.username + "\";";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("MaxHP of player2 updated in DB.");
            System.out.println(rowsAffected + " row(s) affected.");


            //ADD TO GAME HISTORY
            //we need: state, outcome, opponent, opponent level, time

            //GETTING CURRENT TIME:
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String time = currentDateTime.format(formatter);

            //ADD TO HISTORY OF PLAYER 1
            query = "insert into " + Player1user.getUsername() + "_gamehistory " + "(state, outcome, opponent, oppLevel, time)" +
                    " values (\"" + player1_State +"\", \"" + player1_Outcome + "\", \"" + Player2user.getUsername() + "\", " + player2_previousLevel + ", \"" + time + "\");";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Data was added to the games history of player1 in DB.");
            System.out.println(rowsAffected + "row(s) affected.");

            //ADD TO HISTORY OF PLAYER 2
            query = "insert into " + Player2user.getUsername() + "_gamehistory " + "(state, outcome, opponent, oppLevel, time)" +
                    " values (\"" + player2_State +"\", \"" + player2_Outcome + "\", \"" + Player1user.getUsername() + "\", " + player1_previousLevel + ", \"" + time + "\");";
            rowsAffected = statement.executeUpdate(query);
            System.out.println("Data was added to the games history of player2 in DB.");
            System.out.println(rowsAffected + "row(s) affected.");

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        //ADD TO GAME HISTORY

        System.out.println("Payer 2 Logged out successfully!");
        return new Play();

    }
    int calculateCoins(int damagePlayer1, int damagePlayer2, int healthPowerWinner) {
        if(healthPowerWinner < 40)
        return (damagePlayer1 + damagePlayer2) * healthPowerWinner;
        else
            return abs(damagePlayer1 - damagePlayer2) * healthPowerWinner;

    }
    int calculateXP(int damagePlayer1, int damagePlayer2, int healthPowerWinner, boolean isWinner) {
        if (isWinner) {
            return (damagePlayer1 + damagePlayer2) * healthPowerWinner * 2;
        } else {
            return (int) ceil((damagePlayer1 + damagePlayer2) * healthPowerWinner * 0.5);
        }
    }
    public void levelup(User user) {
        if (user.getXP() >= 500 * user.getLevel()) {
            user.level++;
            user.XP = 0;
            System.out.println("Congrats! You have been leveled up " + user.getUsername() + "!");
            user.setMaxHP(100 + (user.level - 1) * 50);
        }
    }

}