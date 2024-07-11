package Controller;

import Model.Card;
import Model.Cell;
import Model.Play;
import Model.User;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import view.debutantMenu;
import view.game;
import view.signupMenu;
import view.winner;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class gameController {


    @FXML
    Label infoLabel;
    @FXML
    private Label STR0, STR1, STR2, STR3, STR4, STR5, STR6, STR7, STR8, STR9, STR10, STR11, STR12, STR13, STR14;
    @FXML
    private Label str0, str1, str2, str3, str4, str5, str6, str7, str8, str9, str10, str11, str12, str13, str14;
    @FXML
    private Label dmg0, dmg1, dmg2, dmg3, dmg4, dmg5, dmg6, dmg7, dmg8, dmg9, dmg10, dmg11, dmg12, dmg13, dmg14;
    @FXML
    private Label DMG0, DMG1, DMG2, DMG3, DMG4, DMG5, DMG6, DMG7, DMG8, DMG9, DMG10, DMG11, DMG12, DMG13, DMG14;
    @FXML
    Label LABEL0, LABEL1, LABEL2, LABEL3, LABEL4, LABEL5, LABEL6, LABEL7, LABEL8, LABEL9, LABEL10, LABEL11, LABEL12, LABEL13, LABEL14;
    @FXML
    Label label0, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14;
    @FXML
        AnchorPane root;
    ArrayList<Label> labels = new ArrayList<>();
    ArrayList<Label> labels1 = new ArrayList<>();
    @FXML
    Label result;
    @FXML
    Label progressLabel;
    @FXML
    ProgressBar progressBar;
    @FXML
    Circle circle;
    int index;
    double previousx = 0;
    @FXML
    ArrayList<Label> Deck1str = new ArrayList<>();
    @FXML
    ArrayList<Label> Deck1dmg = new ArrayList<>();
    @FXML
    ArrayList<Label> Deck2STR = new ArrayList<>();
    @FXML
    ArrayList<Label> Deck2DMG = new ArrayList<>();
    int neww;
    ArrayList<Card> deck1 = new ArrayList<>(5);
    ArrayList<Card> deck2 = new ArrayList<>(5);
    ArrayList<Cell> board1 = new ArrayList<>(15);
    ArrayList<Cell> board2 = new ArrayList<>(15);
    @FXML
    GridPane BoardGridPane;
    @FXML
    GridPane Deck1;
    @FXML
            GridPane Deck2;

    public User Player1user = Controller.currentuser;
    public User Player2user = modeController.guest;
    @FXML
    private ImageView player1;
    @FXML
    private ImageView player2;
    @FXML
    private Label HP1;
    @FXML
    private Label HP2;
    @FXML
    private Label Damage1;
    @FXML
    private Label Damage2;
    @FXML
    private Label rounds1;
    @FXML
    private Label rounds2;


    public ArrayList<Card> hand1 = Player1user.cardsInCardType;
    public ArrayList<Card> hand2 = Player2user.cardsInCardType;

    int hole1 = random();
    int hole2 = random();
    boolean allowed1 = true;
    boolean allowed2 = false;
    private boolean showProgressBar;
    private boolean clicked = false;
    private long startTime;
    private double progress;
    private int currentRound = 1;
    private int maxRounds = 4;
    private boolean player1Turn = true;
    private final String[] imagePaths = {
            "/Media/bg1.jpg",
            "/Media/bg2.jpg",
            "/Media/bg3.jpg",
            "/Media/bg4.jpg"
    };
    private final String[] characters = {
      "/Media/A.N.F.Of.png",
            "/Media/ALPHA LUPEXf.png",
            "/Media/HELIO CELONf.png",
            "/Media/TAG PUNKSf.png"

    };

    @FXML
    private ImageView backgroundImageView;
    ArrayList<String> cellCodes = new ArrayList<>();



    @FXML
    private void initialize() {
        Player1user.ROUNDS = 4;
        Player2user.ROUNDS =4;
        Player1user.DAMAGE = 0;
        Player2user.DAMAGE = 0;
        labels.add(LABEL0);
        labels.add(LABEL1);
        labels.add(LABEL2);
        labels.add(LABEL3);
        labels.add(LABEL4);
        labels.add(LABEL5);
        labels.add(LABEL6);
        labels.add(LABEL7);
        labels.add(LABEL8);
        labels.add(LABEL9);
        labels.add(LABEL10);
        labels.add(LABEL11);
        labels.add(LABEL12);
        labels.add(LABEL13);
        labels.add(LABEL14);
        labels1.add(label0);
        labels1.add(label1);
        labels1.add(label2);
        labels1.add(label3);
        labels1.add(label4);
        labels1.add(label5);
        labels1.add(label6);
        labels1.add(label7);
        labels1.add(label8);
        labels1.add(label9);
        labels1.add(label10);
        labels1.add(label11);
        labels1.add(label12);
        labels1.add(label13);
        labels1.add(label14);
        progressBar.setVisible(false);
        circle.setVisible(true);
        progressLabel.setVisible(true);
        result.setVisible(true);
        Player1user.setHP(Player1user.getMaxHP());
        Player2user.setHP(Player2user.getMaxHP());
        for(int i = 0; i < 15; i++)
        {
            if(i != hole1)
                board1.add(new Cell(i,Player1user, Cell.State.empty,0,0));
            else
                board1.add(new Cell(i,Player1user, Cell.State.hole,0,0));

        }
        for(int i = 0; i < 15; i++)
        {
            if(i != hole2)
                board2.add(new Cell(i,Player2user, Cell.State.empty,0,0));
            else
                board2.add(new Cell(i,Player2user, Cell.State.hole,0,0));

        }
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 15; col++) {
                if (!(row == 0 && col == hole2) && !(row == 1 && col == hole1)) {
                    String code = String.format("%d%d", row, col);
                    cellCodes.add(code);
                }
            }
        }

        Image backgroundImage = new Image(getClass().getResourceAsStream(imagePaths[SettingController.bgIndex-1]));
        backgroundImageView.setImage(backgroundImage);
        backgroundImageView.setFitWidth(backgroundImageView.getFitWidth());
        backgroundImageView.setFitHeight(backgroundImageView.getFitHeight());
        for(int i = 0 ; i < Controller.randomgenerator(Player1user.cardsInCardType.size()).size();i++)
        {
            deck1.add(Player1user.cardsInCardType.get(Controller.randomgenerator(Player1user.cardsInCardType.size()).get(i)));
        }
        for(int i = 0 ; i < Controller.randomgenerator(Player2user.cardsInCardType.size()).size();i++)
        {
            deck2.add(Player2user.cardsInCardType.get(Controller.randomgenerator(Player2user.cardsInCardType.size()).get(i)));
        }
        setPlayerCharacterImage(player1, Player1user);
        setPlayerCharacterImage(player2, Player2user);
        displayHP();
        populatedecks();
        populateBoard();
        displayDMG();
        displayROUNDS();
        assignDeck1Labels();
        assignDeck2Labels();
        assignLabels();


    }
    public void populatedecks()
    {
        Deck1.getChildren().clear();
        Deck2.getChildren().clear();
        for (int i = 0; i < deck1.size(); i++) {
            Card card = deck1.get(i);
            String cardImagePath = "/Media/" + card.getName() + ".jpg"; // Assuming card.getName() gives the card name
            ImageView cardImageView = new ImageView(new Image(getClass().getResourceAsStream(cardImagePath)));
            cardImageView.setFitWidth(157.0);
            cardImageView.setFitHeight(146.0);
            cardImageView.setPreserveRatio(true);
            StackPane cell = new StackPane();
            cell.getChildren().add(cardImageView);
            cardImageView.setOnMouseEntered(event -> showCardInfo(card));
            cardImageView.setOnMouseExited(event -> clearCardInfo());
            Deck1.add(cardImageView, i, 0);
            if (player1Turn && Player1user.ROUNDS!= 0) {
                setDragHandlers(cardImageView);
            }
        }
        for (int i = 0; i < deck2.size(); i++) {
            Card card = deck2.get(i);
            String cardImagePath = "/Media/" + card.getName() + ".jpg";
            ImageView cardImageView = new ImageView(new Image(getClass().getResourceAsStream(cardImagePath)));
            cardImageView.setFitWidth(157.0);
            cardImageView.setFitHeight(146.0);
            cardImageView.setPreserveRatio(true);

            StackPane cell = new StackPane();
            cell.getChildren().add(cardImageView);
            cardImageView.setOnMouseEntered(event -> showCardInfo(card));
            cardImageView.setOnMouseExited(event -> clearCardInfo());
            Deck2.add(cardImageView, i, 0);
            if (!player1Turn && Player2user.ROUNDS != 0) {
                setDragHandlers(cardImageView);
            }
        }
    }
    private void setDragHandlers(ImageView imageView) {
        imageView.setOnDragDetected(event -> onDragDetected(event, imageView));
        imageView.setOnMouseClicked(event -> onmouseclicked(event,imageView));
    }
    private void setCellEventHandlers(Label label) {
        label.setOnDragDropped(event -> onCellDragDropped(event, label));
        label.setOnDragOver(event -> {
            if (event.getGestureSource() != label && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
    }
   private void onmouseclicked(MouseEvent event, ImageView imageView)
   {

       Node source = (Node) event.getSource();

       GridPane parentGrid = (GridPane) source.getParent();

       int columnIndex = GridPane.getColumnIndex(source);

       if (player1Turn && parentGrid == Deck1) {
           if(deck1.get(columnIndex).getName().equals("roundDecrease"))
           {
               showAlert("Message", "Opponent's Rounds was decreased by one!");
               Player2user.ROUNDS -= 1;
               player1Turn =! player1Turn;
           }
           if(deck1.get(columnIndex).getName().equals("strengthBooster"))
           {
               int random = random();
               for(int i = 0; i < 15 ; i++)
               {
                   if(board1.get(i).state.equals(Cell.State.full)){
                   board1.get(random).setCellstrength(board1.get(random).getCellstrength() + 5);
                   showAlert("Message", "Your" + random+1 + " card's strength was increased by 5!");}


               }
           }
           if(deck1.get(columnIndex).getName().equals("weakenCardFromOpp"))
           {
               int random1 = Controller.randomFromDeck(deck2.size());
               int random2 = Controller.randomFromDeck(deck2.size());
               while(random1== random2)
               {
                   random2 = Controller.randomFromDeck(deck2.size());
               }
               deck2.get(random1).setstrength(deck2.get(random2).getstrength() - 1);
               deck2.get(random2).setPlayerdamage(deck2.get(random2).getPlayerdamage() - deck2.get(random2).getDuration());
               showAlert("Message", "Player2 " + random1+1 + " card's strength was decreased by 1!");
               showAlert("Message", "Player2 " + random2+1 + " card's damage was decreased!");

           }
           if(deck1.get(columnIndex).getName().equals("holeFixer"))
           {
               for(int i = 0; i < 15; i ++)
               {
                   if(board1.get(i).state.equals(Cell.State.hole))
                   {
                       board1.get(i).setState(Cell.State.empty);
                       labels1.get(i).setText("EMPTY");
                       showAlert("Message", "Your existing hole in board1 was fixed!");
                   }
               }
           }
           if(deck1.get(columnIndex).getName().equals("holeDisplace"))
           {
               int holee1 = 0;
               int holee2 = 0;
               boolean found1 = false;
               boolean found2 = false;

               for(int i = 0; i < 15; i++)
               {
                   if(board1.get(i).state.equals(Cell.State.hole))
                   {
                       holee1 = i;
                       found1 = true;
                   }
                   if(board2.get(i).state.equals(Cell.State.hole))
                   {
                       holee2 = i;
                       found2 = true;
                   }
               }
               if(!found1)
               {
                   showAlert("Message", "There isn't any hole in Player1's board");

               }
               if(!found2)
               {
                   showAlert("Message", "There isn't any hole in Player2's board");
               }
               if(found1)
               {
                   int random1 = random();
                   while(random1 == holee1 && !board1.get(random1).state.equals(Cell.State.empty))
                       random1 = random();
                   System.out.println(random1 + " :random1");
                   board1.get(random1).setState(Cell.State.hole);
                   board1.get(holee1).setState(Cell.State.empty);
                   labels1.get(holee1).setText("EMPTY");
                   labels1.get(random1).setText("HOLE");
                   showAlert("Message", "Board1's existing hole was displaced!");

               }
               if(found2)
               {
                   int random2 = random();
                   while(random2== holee2 && !board2.get(random2).state.equals(Cell.State.empty))
                       random2 = random();
                   System.out.println(random2 +" :random2");
                   board2.get(random2).setState(Cell.State.hole);
                   board2.get(holee2).setState(Cell.State.empty);
                   labels.get(holee2).setText("EMPTY");
                   labels.get(random2).setText("HOLE");
                   showAlert("Message", "Board2's existing hole was displaced!");

               }

           }

           neww = Controller.random();
           deck1.set(columnIndex, Player1user.cardsInCardType.get(neww));
           Player1user.ROUNDS -= 1;
           player1Turn = !player1Turn;
           populatedecks();
           displayROUNDS();
           displayDMG();
           assignLabels();


       }
       if(!player1Turn && parentGrid == Deck2) {
           if(deck2.get(columnIndex).getName().equals("roundDecrease"))
           {
               showAlert("Message", "Opponent's Rounds was decreased by one!");
               Player1user.ROUNDS -= 1;
               player1Turn =! player1Turn;
           }
           if(deck2.get(columnIndex).getName().equals("strengthBooster"))
           {
               int random = random();
               for(int i = 0; i < 15 ; i++)
               {
                   if(board2.get(i).state.equals(Cell.State.full)){
                       board2.get(random).setCellstrength(board1.get(random).getCellstrength() + 5);
                       showAlert("Message", "Your" + random+1 + " card's strength was increased by 5!");}
               }
           }
           if(deck2.get(columnIndex).getName().equals("weakenCardFromOpp"))
           {
               int random1 = Controller.randomFromDeck(deck1.size());
               int random2 = Controller.randomFromDeck(deck1.size());
               while(random1== random2)
               {
                   random2 = Controller.randomFromDeck(deck1.size());
               }
               deck2.get(random1).setstrength(deck2.get(random2).getstrength() - 1);
               deck2.get(random2).setPlayerdamage(deck2.get(random2).getPlayerdamage() - deck2.get(random2).getDuration());
               showAlert("Message", "Player1 " + random1+1 + " card's strength was decreased by 1!");
               showAlert("Message", "Player1 " + random2+1 + " card's damage was decreased!");
           }
           if(deck2.get(columnIndex).getName().equals("holeFixer"))
           {
               for(int i = 0; i < 15; i ++)
               {
                   if(board2.get(i).state.equals(Cell.State.hole))
                   {
                       board2.get(i).setState(Cell.State.empty);
                       labels.get(i).setText("EMPTY");
                       showAlert("Message", "Your existing hole in board2 was fixed!");
                   }
               }
           }
           if(deck2.get(columnIndex).getName().equals("holeDisplace"))
           {
               int holee1 = 0;
               int holee2 = 0;
               boolean found1 = false;
               boolean found2 = false;

               for(int i = 0; i < 15; i++)
               {
                   if(board1.get(i).state.equals(Cell.State.hole))
                   {
                       holee1 = i;
                       found1 = true;
                   }
                   if(board2.get(i).state.equals(Cell.State.hole))
                   {
                       holee2 = i;
                       found2 = true;
                   }
               }
               if(!found1)
               {
                   showAlert("Message", "There isn't any hole in Player1's board");

               }
               if(!found2)
               {
                   showAlert("Message", "There isn't any hole in Player2's board");
               }
               if(found1)
               {
                   int random1 = random();
                   while(random1 == holee1 && !board1.get(random1).state.equals(Cell.State.empty))
                       random1 = random();
                   System.out.println(random1 +": random1");
                   board1.get(random1).setState(Cell.State.hole);
                   board1.get(holee1).setState(Cell.State.empty);
                   labels1.get(holee1).setText("EMPTY");
                   labels1.get(random1).setText("HOLE");
                   showAlert("Message", "Board1's existing hole was displaced!");

               }
               if(found2)
               {
                   int random2 = random();
                   while(random2== holee2 && !board2.get(random2).state.equals(Cell.State.empty))
                       random2 = random();
                   System.out.println(random2 +" :random2");
                   board2.get(random2).setState(Cell.State.hole);
                   board2.get(holee2).setState(Cell.State.empty);
                   labels.get(holee2).setText("EMPTY");
                   labels.get(random2).setText("HOLE");
                   showAlert("Message", "Board2's existing hole was displaced!");

               }

           }
           neww = Controller.random();
           deck2.set(columnIndex, Player2user.cardsInCardType.get(neww));
           Player2user.ROUNDS -= 1;
           player1Turn = !player1Turn;
           populatedecks();
           System.out.println(player1Turn + "in mouse");
           displayDMG();
           displayROUNDS();
           assignLabels();
           if(Player1user.ROUNDS == 0 && Player2user.ROUNDS ==0)
           {
               showAlert("Pending", "Card phase is over! Combat phase will start in a second...");
               Player2user.DAMAGE = 0;
               Player1user.DAMAGE = 0;
               displayDMG();
               starttimeline();

           }

       }
   }

    private void onDragDetected(MouseEvent event, ImageView imageView) {
        Dragboard dragboard = imageView.startDragAndDrop(TransferMode.ANY);
        ClipboardContent content = new ClipboardContent();
        content.putImage(imageView.getImage());
        dragboard.setContent(content);
        dragboard.setDragView(imageView.getImage(), 157, 146);
        dragboard.setDragViewOffsetX(event.getX());
        dragboard.setDragViewOffsetY(event.getY());
        event.consume();
    }
   private void onCellDragDropped(DragEvent event, Label label) {
       System.out.println(player1Turn + "in drop");
       System.out.println("I'm in");
       System.out.println("Which turn? " + player1Turn);
       Dragboard dragboard = event.getDragboard();
        boolean success = false;
        int columnIndex = 0;
        int rowIndex = 0;

       if (dragboard.hasImage()) {
            ImageView draggedImageView = (ImageView) event.getGestureSource();
            GridPane parentGrid = (GridPane) draggedImageView.getParent();
            int sourceColumnIndex = GridPane.getColumnIndex(draggedImageView);
            boolean isFromDeck1 = (parentGrid == Deck1);
            boolean isFromDeck2 = (parentGrid == Deck2);
           //Integer columnIndex = GridPane.getColumnIndex(label);
           //Integer rowIndex = GridPane.getRowIndex(label);

            if ((isFromDeck1 && !player1Turn) || (isFromDeck2 && player1Turn)) {
                showAlert("Invalid Move", "It's not your turn.");
                event.setDropCompleted(false);
                event.consume();
                return;
            }
           System.out.println("Did I reach here?");
           int[] cellIndices = getCellIndices(BoardGridPane, event);
           if (cellIndices != null) {
               columnIndex = cellIndices[0];
               rowIndex = cellIndices[1];}

           System.out.println("Dropped at column: " + columnIndex + ", row: " + rowIndex);
           System.out.println("I am nearly at the begin");
           ImageView newImageView = new ImageView(dragboard.getImage());
            newImageView.setFitWidth(70.0);
            newImageView.setFitHeight(146.0);
            newImageView.setPreserveRatio(true);
            ArrayList<ImageView> images = new ArrayList<>();
            ArrayList<ImageView> boomed = new ArrayList<>();
            ArrayList<ImageView> empty = new ArrayList<>();
           Bounds labelBounds = label.localToScene(label.getBoundsInLocal());
           if (!labelBounds.contains(event.getSceneX(), event.getSceneY())) {
               event.setDropCompleted(false);
               System.out.println("Here is the problem!");
               return;
           }
           for (Node node : BoardGridPane.getChildren()) {

                   System.out.println("I came till here!2");
                   //VBox cell = (VBox) node;
                    //int columnIndex = GridPane.getColumnIndex(cell);
                    //int rowIndex = GridPane.getRowIndex(cell);
                    ArrayList<String> codes = new ArrayList<>();
                    ArrayList<String> Opponent = new ArrayList<>();
                    //Bounds boundsInScene = cell.localToScene(cell.getBoundsInLocal());
                      //  if (boundsInScene.contains(event.getSceneX(), event.getSceneY())) {
                            int dur = 0;
                            if(isFromDeck1)
                            {
                                if(rowIndex == 0)
                                {
                                    showAlert("Invalid Move", "You can't place the card in your opponent's cell.");
                                    break;
                                }
                                dur = deck1.get(sourceColumnIndex).getDuration();
                                for(int i = 0; i < dur; i++)
                                {
                                    String answer = "1";
                                    answer += String.valueOf(columnIndex+i);
                                    codes.add(String.valueOf(answer));
                                }
                                for(int i = 0; i < dur; i++)
                                {
                                    String answer = "0";
                                    answer += String.valueOf(columnIndex+i);
                                    Opponent.add(String.valueOf(answer));
                                }

                            }
                            if(isFromDeck2)
                            {

                                if(rowIndex == 1)
                                {
                                    showAlert("Invalid Move", "You can't place the card in your opponent's cell.");
                                    break;
                                }

                                dur = deck2.get(sourceColumnIndex).getDuration();
                                for(int i = 0; i < dur; i++)
                                {
                                    String answer = "0";
                                    answer += String.valueOf(columnIndex+i);
                                    codes.add(String.valueOf(answer));
                                }
                                for(int i = 0; i < dur; i++)
                                {
                                    String answer = "1";
                                    answer += String.valueOf(columnIndex+i);
                                    Opponent.add(String.valueOf(answer));
                                }
                            }
                            for(int i = 0; i < dur; i++)
                            {
                                newImageView = new ImageView(dragboard.getImage());
                                newImageView.setFitWidth(70.0);
                                newImageView.setFitHeight(146.0);
                                newImageView.setPreserveRatio(true);
                                images.add(newImageView);
                                String cardImagePath = "/Media/boom.jpg";
                                ImageView cardImageView = new ImageView(new Image(getClass().getResourceAsStream(cardImagePath)));
                                boomed.add(cardImageView);


                            }
                            for(int i = 0; i < 2 * dur ; i++)
                            {
                                String ImagePath = "/Media/empty.tiff";
                                ImageView ImageView = new ImageView(new Image(getClass().getResourceAsStream(ImagePath)));
                                empty.add(ImageView);
                            }
                            System.out.println(images.size() + ":  images");
                            System.out.println(empty.size() + ":  empty");
                            System.out.println(boomed.size() + ":  boom");

                            boolean goodtogo = true;
                            for(int i = 0; i < codes.size(); i++)
                            {
                                if(!cellCodes.contains(codes.get(i)))
                                {
                                    goodtogo = false;
                                    continue;
                                }
                            }
                            System.out.println(goodtogo);
                            if(goodtogo)
                            {
                                draggedImageView = (ImageView) event.getGestureSource();
                                parentGrid = (GridPane) draggedImageView.getParent();
                                parentGrid.getChildren().remove(draggedImageView);
                                if(isFromDeck1)
                                {

                                    if(deck1.get(sourceColumnIndex).getType().equals("ORDINARY") || deck1.get(sourceColumnIndex).getName().equals("copyCard") || deck1.get(sourceColumnIndex).getName().equals("takeCardFromOpp") || deck1.get(sourceColumnIndex).getName().equals("hider"))
                                    {
                                        for(int i = 0; i < dur; i ++)
                                        {
                                            if(!cellCodes.contains(Opponent.get(i)) && board2.get(columnIndex + i).state.equals(Cell.State.full))
                                            {
                                                if(deck1.get(sourceColumnIndex).getstrength() > board2.get(columnIndex + i).cellstrength)
                                                {

                                                    //TODO: making the labels 0 as well
                                                    board2.get(columnIndex + i).celldamage = 0;
                                                    board2.get(columnIndex+i).state = Cell.State.boom;
                                                    board2.get(columnIndex + i).cellstrength = 0;
                                                    BoardGridPane.add(boomed.get(i), columnIndex + i, 0);
                                                    GridPane.setHalignment(boomed.get(i), HPos.CENTER);
                                                    GridPane.setValignment(boomed.get(i), VPos.CENTER);
                                                    board1.get(columnIndex + i).cellstrength = deck1.get(sourceColumnIndex).getStrength();
                                                    board1.get(columnIndex + i).celldamage = deck1.get(sourceColumnIndex).getDivideddamage();
                                                    board1.get(columnIndex + i).state = Cell.State.full;
                                                    board1.get(columnIndex + i).card = deck1.get(sourceColumnIndex);
                                                    cellCodes.remove(codes.get(i));
                                                    BoardGridPane.add(images.get(i), columnIndex + i, rowIndex);
                                                    GridPane.setHalignment(images.get(i), HPos.CENTER);
                                                    GridPane.setValignment(images.get(i), VPos.CENTER);
                                                }
                                                else
                                                {
                                                    board1.get(columnIndex + i).celldamage = 0;
                                                    board1.get(columnIndex + i).cellstrength = 0;
                                                    board1.get(columnIndex+i).state = Cell.State.boom;
                                                    cellCodes.remove(codes.get(i));
                                                    BoardGridPane.add(boomed.get(i), columnIndex + i, 1);
                                                    GridPane.setHalignment(boomed.get(i), HPos.CENTER);
                                                    GridPane.setValignment(boomed.get(i), VPos.CENTER);
                                                }
                                            }
                                            else
                                            {
                                                System.out.println("is the opponent empty?" + i);
                                                board1.get(columnIndex + i).cellstrength = deck1.get(sourceColumnIndex).getStrength();
                                                board1.get(columnIndex + i).celldamage = deck1.get(sourceColumnIndex).getDivideddamage();
                                                board1.get(columnIndex + i).state = Cell.State.full;
                                                board1.get(columnIndex + i).card = deck1.get(sourceColumnIndex);
                                                cellCodes.remove(codes.get(i));
                                                System.out.println("Is the problem here?");
                                               // ((VBox) getNodeByRowColumnIndex(rowIndex, columnIndex, BoardGridPane)).getChildren().add(images.get(i));
                                                System.out.println("Did I reach here?");
                                                 BoardGridPane.add(images.get(i), columnIndex + i, rowIndex);
                                                GridPane.setHalignment(images.get(i), HPos.CENTER);
                                                GridPane.setValignment(images.get(i), VPos.CENTER);
                                            }
                                        }
                                    }
                                    if(deck1.get(sourceColumnIndex).getName().equals("heal") || deck1.get(sourceColumnIndex).getName().equals("shield"))
                                    {
                                        for(int  i = 0 ; i < dur ; i++)
                                        {
                                            if(!cellCodes.contains(Opponent.get(i)) && board2.get(columnIndex + i).state.equals(Cell.State.full))
                                            {
                                                board2.get(columnIndex + i).celldamage = 0;
                                                board2.get(columnIndex+i).state = Cell.State.boom;
                                                board2.get(columnIndex + i).cellstrength = 0;
                                                BoardGridPane.add(boomed.get(i), columnIndex + i, 0);
                                                GridPane.setHalignment(boomed.get(i), HPos.CENTER);
                                                GridPane.setValignment(boomed.get(i), VPos.CENTER);
                                                board1.get(columnIndex + i).cellstrength = deck1.get(sourceColumnIndex).getStrength();
                                                board1.get(columnIndex + i).celldamage = deck1.get(sourceColumnIndex).getDivideddamage();
                                                board1.get(columnIndex + i).state = Cell.State.full;
                                                board1.get(columnIndex + i).card = deck1.get(sourceColumnIndex);
                                                cellCodes.remove(codes.get(i));
                                                BoardGridPane.add(images.get(i), columnIndex + i, rowIndex);
                                                GridPane.setHalignment(images.get(i), HPos.CENTER);
                                                GridPane.setValignment(images.get(i), VPos.CENTER);
                                            }
                                            else
                                            {
                                                System.out.println("is the opponent empty?" + i);
                                                board1.get(columnIndex + i).cellstrength = deck1.get(sourceColumnIndex).getStrength();
                                                board1.get(columnIndex + i).celldamage = deck1.get(sourceColumnIndex).getDivideddamage();
                                                board1.get(columnIndex + i).state = Cell.State.full;
                                                board1.get(columnIndex + i).card = deck1.get(sourceColumnIndex);
                                                cellCodes.remove(codes.get(i));
                                                BoardGridPane.add(images.get(i), columnIndex + i, rowIndex);
                                                GridPane.setHalignment(images.get(i), HPos.CENTER);
                                                GridPane.setValignment(images.get(i), VPos.CENTER);
                                            }
                                            if(deck1.get(sourceColumnIndex).getName().equals("heal"))
                                            {
                                                if(player1Turn)
                                                {
                                                    Player1user.setHP(Player1user.getHP() + 20);
                                                }
                                                if(!player1Turn)
                                                {
                                                    Player2user.setHP(Player2user.getHP() + 20);
                                                }
                                            }

                                        }
                                    }


                                }
                                if (isFromDeck2)
                                {
                                    System.out.println("I'm in deck 2 the begining!");
                                    if(deck2.get(sourceColumnIndex).getType().equals("ORDINARY") || deck2.get(sourceColumnIndex).getName().equals("copyCard") || deck2.get(sourceColumnIndex).getName().equals("takeCardFromOpp") || deck2.get(sourceColumnIndex).getName().equals("hider"))
                                    {
                                        System.out.println("duration of deck 2 chosen; " + dur);
                                        for(int i = 0; i < dur; i ++)
                                        {
                                            System.out.println("Which turn is it? " + i);

                                            if(!cellCodes.contains(Opponent.get(i)) && board1.get(columnIndex + i).state.equals(Cell.State.full))
                                            {
                                                System.out.println("The opponent" + i + "house is full");
                                                System.out.println("I'm inside the if's in player2");
                                                System.out.println("Player2:----------------");
                                                System.out.println(deck2.get(sourceColumnIndex).getStrength() + " :card2 deck strength ");
                                                System.out.println(board1.get(columnIndex + i).cellstrength +" :board1 deck strength");
                                                if(deck2.get(sourceColumnIndex).getStrength() > board1.get(columnIndex + i).cellstrength)
                                                {
                                                    //TODO: making the labels 0 as well
                                                    board1.get(columnIndex + i).celldamage = 0;
                                                    board1.get(columnIndex+i).state = Cell.State.boom;
                                                    board1.get(columnIndex + i).cellstrength = 0;
                                                    BoardGridPane.add(boomed.get(i), columnIndex + i, 1);
                                                    GridPane.setHalignment(boomed.get(i), HPos.CENTER);
                                                    GridPane.setValignment(boomed.get(i), VPos.CENTER);
                                                    board2.get(columnIndex + i).cellstrength = deck2.get(sourceColumnIndex).getStrength();
                                                    board2.get(columnIndex + i).celldamage = deck2.get(sourceColumnIndex).getDivideddamage();
                                                    board2.get(columnIndex + i).state = Cell.State.full;
                                                    board2.get(columnIndex + i).card = deck2.get(sourceColumnIndex);
                                                    cellCodes.remove(codes.get(i));
                                                    BoardGridPane.add(images.get(i), columnIndex + i, 0);
                                                    GridPane.setHalignment(images.get(i), HPos.CENTER);
                                                    GridPane.setValignment(images.get(i), VPos.CENTER);
                                                }
                                                else
                                                {
                                                    board2.get(columnIndex + i).celldamage = 0;
                                                    board2.get(columnIndex + i).cellstrength = 0;
                                                    board2.get(columnIndex+i).state = Cell.State.boom;
                                                    cellCodes.remove(codes.get(i));
                                                    BoardGridPane.add(boomed.get(i), columnIndex + i, 0);
                                                    GridPane.setHalignment(boomed.get(i), HPos.CENTER);
                                                    GridPane.setValignment(boomed.get(i), VPos.CENTER);

                                                }
                                            }
                                            else
                                            {
                                                board2.get(columnIndex + i).cellstrength = deck2.get(sourceColumnIndex).getStrength();
                                                board2.get(columnIndex + i).celldamage = deck2.get(sourceColumnIndex).getDivideddamage();
                                                board2.get(columnIndex + i).state = Cell.State.full;
                                                board2.get(columnIndex + i).card = deck2.get(sourceColumnIndex);
                                                cellCodes.remove(codes.get(i));
                                                BoardGridPane.add(images.get(i), columnIndex + i, 0);
                                                GridPane.setHalignment(images.get(i), HPos.CENTER);
                                                GridPane.setValignment(images.get(i), VPos.CENTER);
                                            }
                                        }
                                    }
                                    if(deck2.get(sourceColumnIndex).getName().equals("heal") || deck2.get(sourceColumnIndex).getName().equals("shield"))
                                    {
                                        for(int  i = 0 ; i < dur ; i++)
                                        {
                                            if(!cellCodes.contains(Opponent.get(i)) && board1.get(columnIndex + i).state.equals(Cell.State.full))
                                            {
                                                board1.get(columnIndex + i).celldamage = 0;
                                                board1.get(columnIndex+i).state = Cell.State.boom;
                                                board1.get(columnIndex + i).cellstrength = 0;
                                                BoardGridPane.add(boomed.get(i), columnIndex + i, 1);
                                                GridPane.setHalignment(boomed.get(i), HPos.CENTER);
                                                GridPane.setValignment(boomed.get(i), VPos.CENTER);
                                                board2.get(columnIndex + i).cellstrength = deck2.get(sourceColumnIndex).getStrength();
                                                board2.get(columnIndex + i).celldamage = deck2.get(sourceColumnIndex).getDivideddamage();
                                                board2.get(columnIndex + i).state = Cell.State.full;
                                                board2.get(columnIndex + i).card = deck2.get(sourceColumnIndex);
                                                cellCodes.remove(codes.get(i));
                                                BoardGridPane.add(images.get(i), columnIndex + i, 0);
                                                GridPane.setHalignment(images.get(i), HPos.CENTER);
                                                GridPane.setValignment(images.get(i), VPos.CENTER);
                                            }
                                            else
                                            {
                                                board2.get(columnIndex + i).cellstrength = deck2.get(sourceColumnIndex).getStrength();
                                                board2.get(columnIndex + i).celldamage = deck2.get(sourceColumnIndex).getDivideddamage();
                                                board2.get(columnIndex + i).state = Cell.State.full;
                                                board2.get(columnIndex + i).card = deck2.get(sourceColumnIndex);
                                                cellCodes.remove(codes.get(i));
                                                BoardGridPane.add(images.get(i), columnIndex + i, 0);
                                                GridPane.setHalignment(images.get(i), HPos.CENTER);
                                                GridPane.setValignment(images.get(i), VPos.CENTER);
                                            }
                                            if(deck2.get(sourceColumnIndex).getName().equals("heal"))
                                            {
                                                if(player1Turn)
                                                {
                                                    Player1user.setHP(Player1user.getHP() + 20);
                                                    displayHP();
                                                }
                                                if(!player1Turn)
                                                {
                                                    Player2user.setHP(Player2user.getHP() + 20);
                                                    displayHP();
                                                }
                                            }

                                        }
                                    }

                                }
                                if(!player1Turn)
                                {
                                    neww = Controller.random();
                                    deck2.set(sourceColumnIndex, Player2user.cardsInCardType.get(neww));
                                    Player2user.ROUNDS -= 1;
                                }
                                if(player1Turn)
                                {
                                    neww = Controller.random();
                                    deck1.set(sourceColumnIndex, Player1user.cardsInCardType.get(neww));
                                    Player1user.ROUNDS -= 1;
                                }
                                success = true;
                                player1Turn = !player1Turn;
                                updateTurnIndicator();
                                populatedecks();
                                displayDMG();
                                displayHP();
                                displayROUNDS();
                                assignLabels();
                                if(Player1user.ROUNDS == 0 && Player2user.ROUNDS ==0)
                                {
                                    showAlert("Pending", "Card phase is over! Combat phase will start in a second...");
                                    Player1user.setDAMAGE(0);
                                    Player2user.setDAMAGE(0);
                                    displayDMG();
                                    starttimeline();
                                }

                            }
                            else{
                                showAlert("Invalid Move", "You can't place the card in this cell.");
                            }
                            break;

                  // }

            }
        }

        event.setDropCompleted(success);
        event.consume();
    }
    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

   private int[] getCellIndices(GridPane gridPane, DragEvent event) {
       for (Node node : gridPane.getChildren()) {
           Bounds bounds = node.localToScene(node.getBoundsInLocal());
           if (bounds.contains(event.getSceneX(), event.getSceneY())) {
               Integer colIndex = GridPane.getColumnIndex(node);
               Integer rowIndex = GridPane.getRowIndex(node);
               // Default to 0 if the index is null
               int col = (colIndex == null) ? 0 : colIndex;
               int row = (rowIndex == null) ? 0 : rowIndex;
               return new int[]{col, row};
           }
       }
       return null;
   }
    private void updateTurnIndicator() {
        if (player1Turn) {
            infoLabel.setText("Player 1's turn");
        } else {
            infoLabel.setText("Player 2's turn");
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showCardInfo(Card card) {
        infoLabel.setText(
                "Name: " + card.getName() +
                        "\nStrength: " + card.getStrength() +
                        "\nDamage: " + card.getPlayerdamage() +
                        "\nDuration: " + card.getDuration() +
                        "\nCharacter: "+ card.getPlayerCharacter().getName()
        );
    }

    private void clearCardInfo() {
        infoLabel.setText("");
    }
    private void removeImageFromGrid(GridPane gridPane, int col, int row) {
        Node nodeToRemove = null;
        for (Node node : gridPane.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);
            if (columnIndex != null && rowIndex != null && columnIndex == col && rowIndex == row) {
                nodeToRemove = node;
                break;
            }
        }
        if (nodeToRemove != null) {
            gridPane.getChildren().remove(nodeToRemove);
        }
    }
    private void setPlayerCharacterImage(ImageView imageView, User user) {
        String characterName = user.getCharactername();
        String characterImagePath = null;

        switch (characterName) {
            case "A.N.F.O.":
                characterImagePath = characters[0];
                break;
            case "ALPHA LUPEX":
                characterImagePath = characters[1];
                break;
            case "HELIO CELON":
                characterImagePath = characters[2];
                break;
            case "TAG PUNKS":
                characterImagePath = characters[3];
                break;
        }

        if (characterImagePath != null) {
            Image characterImage = new Image(getClass().getResourceAsStream(characterImagePath));

            imageView.setImage(characterImage);
        }
    }
    private void displayHP() {
        HP1.setText(String.valueOf(Player1user.getHP()));
        HP2.setText(String.valueOf(Player2user.getHP()));
    }
    private void displayDMG() {
        if(Player1user.ROUNDS != 0 || Player2user.ROUNDS != 0){
        damagecalculation();
        }
        Damage1.setText(String.valueOf(Player1user.getDAMAGE()));
        Damage2.setText(String.valueOf(Player2user.getDAMAGE()));
    }
    private void displayROUNDS() {
        rounds1.setText(String.valueOf(Player1user.getROUNDS()));
        rounds2.setText(String.valueOf(Player2user.getROUNDS()));
    }
    private void populateBoard() {
        for(int i=0; i<15; i++) {
        if(i==hole1)
        {
            labels1.get(i).setText("HOLE");
        }
        else {
            labels1.get(i).setText("EMPTY");
        }
            setCellEventHandlers(labels1.get(i));

        }
        for(int i=0; i<15; i++){
                        if(i==hole2){

                            labels.get(i).setText("HOLE");
                        }
                        else{
                            labels.get(i).setText("EMPTY");
                        }
            setCellEventHandlers(labels.get(i));

        }


    }
    public static int random()
    {
        int randomNum = ThreadLocalRandom.current().nextInt(0, 14);
        return randomNum;
    }
    private void updateLabel(Label label, String text, String backgroundColor) {
        label.setText(text);
        label.setStyle("-fx-background-color: " + backgroundColor + "; -fx-text-fill: black;");
    }
    private void damagecalculation()
    {
        Player1user.DAMAGE = 0;
        Player2user.DAMAGE = 0;
        for(int i = 0; i < board1.size(); i++)
        {
            Player1user.DAMAGE += board1.get(i).celldamage;
            Player2user.DAMAGE += board2.get(i).celldamage;
        }
        System.out.println(Player1user.DAMAGE);
        System.out.println(Player2user.DAMAGE);
    }
    private void assignDeck1Labels(){
        Deck1str.add(str0); Deck1str.add(str1); Deck1str.add(str2); Deck1str.add(str3); Deck1str.add(str4); Deck1str.add(str5);
        Deck1str.add(str6); Deck1str.add(str7); Deck1str.add(str8); Deck1str.add(str9); Deck1str.add(str10); Deck1str.add(str11);
        Deck1str.add(str12); Deck1str.add(str13); Deck1str.add(str14);

        Deck1dmg.add(dmg0); Deck1dmg.add(dmg1); Deck1dmg.add(dmg2); Deck1dmg.add(dmg3); Deck1dmg.add(dmg4); Deck1dmg.add(dmg5);
        Deck1dmg.add(dmg6); Deck1dmg.add(dmg7); Deck1dmg.add(dmg8); Deck1dmg.add(dmg9); Deck1dmg.add(dmg10); Deck1dmg.add(dmg11);
        Deck1dmg.add(dmg12); Deck1dmg.add(dmg13); Deck1dmg.add(dmg14);
    }
    private void assignDeck2Labels(){
        Deck2STR.add(STR0); Deck2STR.add(STR1); Deck2STR.add(STR2); Deck2STR.add(STR3); Deck2STR.add(STR4); Deck2STR.add(STR5);
        Deck2STR.add(STR6); Deck2STR.add(STR7); Deck2STR.add(STR8); Deck2STR.add(STR9); Deck2STR.add(STR10); Deck2STR.add(STR11);
        Deck2STR.add(STR12); Deck2STR.add(STR13); Deck2STR.add(STR14);

        Deck2DMG.add(DMG0); Deck2DMG.add(DMG1); Deck2DMG.add(DMG2); Deck2DMG.add(DMG3); Deck2DMG.add(DMG4); Deck2DMG.add(DMG5);
        Deck2DMG.add(DMG6); Deck2DMG.add(DMG7); Deck2DMG.add(DMG8); Deck2DMG.add(DMG9); Deck2DMG.add(DMG10); Deck2DMG.add(DMG11);
        Deck2DMG.add(DMG12); Deck2DMG.add(DMG13); Deck2DMG.add(DMG14);
    }

    private void assignLabels(){
        for(int i=0; i<15; i++){

                Deck1dmg.get(i).setText(String.valueOf(board1.get(i).celldamage));
                Deck1str.get(i).setText(String.valueOf(board1.get(i).cellstrength));
                Deck2DMG.get(i).setText(String.valueOf(board2.get(i).celldamage));
                Deck2STR.get(i).setText(String.valueOf(board2.get(i).cellstrength));
        }

    }
   public void starttimeline() {
       AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (startTime == 0) {
                    startTime = now;
                }
                System.out.println(index + "index");
                long elapsedSeconds = (now - startTime) / 1_000_000_000;

                if (elapsedSeconds <= 15) {
                    if (elapsedSeconds >= 5 && elapsedSeconds <= 10 && !clicked) {
                        if (!showProgressBar) {
                            showProgressBar = true;
                            progressBar.setVisible(true);
                        }
                        progress = (elapsedSeconds - 5) / 5.0;
                        progressBar.setProgress(progress);
                        progressLabel.setText(String.valueOf(progress));
                    }

                  //  double newX = (elapsedSeconds <= 5) ? elapsedSeconds * 50 : (elapsedSeconds - 5) * 50;
                    double newX = elapsedSeconds * 95;
                    if(previousx != newX && index < 15)
                    {
                        index += 1;
                        System.out.println(index + "index");
                        System.out.println(Player2user.getHP());
                        System.out.println(Player1user.getHP());
                        if(Player1user.getHP() < 0 || Player2user.getHP() < 0)
                        {
                    stop();
                    if(Player1user.getHP() < 0)
                    {
                        winnerController.loser = Player1user;
                    winnerController.winner = Player2user;
                    Player1user.setHP(0);
                    }
                    if(Player2user.getHP() < 0)
                    {
                        winnerController.loser = Player2user;
                        winnerController.winner = Player1user;
                        Player2user.setHP(0);
                    }
                    winner winnerr = new winner();
                    try {
                        winnerr.start(debutantMenu.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if(board1.get(index).state.equals(Cell.State.full))
                {
                    Player2user.setHP(Player2user.getHP()-board1.get(index).celldamage);
                    Player1user.DAMAGE += board1.get(index).celldamage;
                    System.out.println(Player1user.DAMAGE + "Damage of player 1");
                    displayDMG();
                    displayHP();
                }
                if(board2.get(index).state.equals(Cell.State.full))
                {
                    Player1user.setHP(Player1user.getHP()-board2.get(index).celldamage);
                    Player2user.DAMAGE += board2.get(index).celldamage;
                    System.out.println("Damage of player 2");
                    displayDMG();
                    displayHP();
                }
                    }
                    previousx = newX;
                    circle.setTranslateX(newX);
                    root.setOnMouseClicked(event -> {
                        showAlert("Pending", "Card phase is over! Combat phase will start in a second...");
                        if (progress >= 0.4 && progress <= 0.6) {
                            clicked = true;
                            progressBar.setVisible(false);
                            result.setText("WON");
                        } else {
                            clicked = false;
                            progressBar.setVisible(false);
                            result.setText("LOSE");
                        }
                        progressBar.setVisible(false);
                    });
                } else {

                    progressBar.setVisible(false);
                    stop();
                    game gamee = new game();
                    try {
                        gamee.start(debutantMenu.stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }

        };

        timer.start();
    }
}