package Controller;

import Model.Card;
import Model.User;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import view.debutantMenu;
import view.loginMenu;
import view.mainMenu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ShopMenuController {
    public final String DB_URL = "jdbc:mysql://127.0.0.1:3306/oop_proj";
    public final String USERNAME = "root";
    public final String PASSWORD = "138387Amitis";

    @FXML
    private GridPane cardGrid;
    @FXML
    private Label cardDetails;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;
    @FXML
    private Label userDetailsLabel;
    private List<Card> allCards = new ArrayList<>();
    private List<Card> userDeck = new ArrayList<>();
    private int currentPage = 0;
    private final int cardsPerPage = 6;

    private final String[] imagePaths = {
            "/Media/AcidicAttack.jpg",
            "/Media/BikerSupport.jpg",
            "/Media/BulletAssault.jpg",
            "/Media/BulletFlurry.jpg",
            "/Media/Dandelion.jpg",
            "/Media/EmielRehis.jpg",
            "/Media/ExtremeFrenzy.jpg",
            "/Media/Filavandrel.jpg",
            "/Media/FinalWarning.jpg",
            "/Media/FlashPelletes.jpg",
            "/Media/GadgetBack-Up.jpg",
            "/Media/GeraltOfRivia.jpg",
            "/Media/IONBlast.jpg",
            "/Media/IONBurst.jpg",
            "/Media/MultiFire.jpg",
            "/Media/MysteriousElf.jpg",
            "/Media/PlasmaStrike.jpg",
            "/Media/RapidRecoil.jpg",
            "/Media/SnipperSupport.jpg",
            "/Media/StrikeBullet.jpg",
            "/Media/copyCard.jpg",
            "/Media/heal.jpg",
            "/Media/hider.jpg",
            "/Media/holeDisplace.jpg",
            "/Media/holeFixer.jpg",
            "/Media/roundDecrease.jpg",
            "/Media/shield.jpg",
            "/Media/strengthBooster.jpg",
            "/Media/takeCardFromOpp.jpg",
            "/Media/weakenCardFromOpp.jpg"
    };
     ArrayList<String> usercardnames = new ArrayList<>();
    @FXML
    public void initialize() {
        // Initialize all cards and user deck
        allCards.addAll(Controller.ordinarycards);
        allCards.addAll(Controller.specialcards);
        User currentUser = Controller.currentuser;
        System.out.println(currentUser.username);
        userDeck = currentUser.getCardsInCardType();
        for(int i = 0; i < userDeck.size(); i++)
        {
            usercardnames.add(currentUser.getCardsInCardType().get(i).getName());
        }
        System.out.println("-----------------------");
        displayPage(currentPage);
        updateUserDetails(currentUser);
    }
    @FXML
    private void mainmenu(MouseEvent mouseEvent)
    {
        mainMenu main=new mainMenu();
        try {
            main.start(debutantMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void displayPage(int page) {
        cardGrid.getChildren().clear();
        int start = page * cardsPerPage;
        int end = Math.min(start + cardsPerPage, allCards.size());
        List<Card> cardsToDisplay = allCards.subList(start, end);
        VBox cardBox;
        for (int i = 0; i < cardsToDisplay.size(); i++) {
            Card card = cardsToDisplay.get(i);
            String name = card.getName();
            User user = Controller.currentuser;
            if(usercardnames.contains(card.getName()))
            {
                for(int j = 0; j < Controller.currentuser.cardsInCardType.size() ; j++)
                {
                    if(user.cardsInCardType.get(j).getName().equals(name))
                    {
                        card = user.cardsInCardType.get(j);
                        break;
                    }
                }
            }
             cardBox = createCardBox(card, i);
            cardGrid.add(cardBox, i % 3, i / 3);
        }

        prevButton.setVisible(page > 0);
        nextButton.setVisible(end < allCards.size());
    }
    private boolean buyCard(String cardName, User user)
    {
        Card card = getcardbycardname(cardName);
        if(usercardnames.contains(cardName))
        {
           return false;
        }
        int price = card.getPrice();
        if(user.getCoins() >= price)
        {
            userDeck.add(card);
            user.setCoins(user.getCoins()-price);
            try{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String query = "update users set coins = " + user.getCoins() + " where username = \"" + user.getUsername() + "\";";
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
                System.out.println("User's coins changed in database.");
                System.out.println(rowsAffected + "(s) row affected.");
            }catch(SQLException e){
                e.printStackTrace();
            }
            user.cardsInCardType.add(card);
            user.cardNamesInString.add(card.getName());
            //insert into the table of user cards in DB
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                String tableName = user.getUsername() + "_cardtable";
                String query = "insert into " + tableName + " (name, strength, duration, playerDMG, upgradeLevel, upgradeCost, timesUpgraded, price, type, playerCharacter)"
                        + " values " + "(\"" + card.getName() + "\", " + card.getstrength() + ", " + card.getDuration() + ", " + card.getPlayerdamage()
                        + ", "+ card.getUpgradelevel() + ", " + card.getUpgradecost() + ", " + card.getTimesupgraded() + ", " + card.getPrice()
                        + ", \"" + card.getType() + "\", \"" + card.getPlayerCharacter().getName() +"\");";
                Statement statement = conn.createStatement();
                int rowsAffected = statement.executeUpdate(query);
                System.out.println("User's card table in DB changed successfully.");
                System.out.println(rowsAffected + "(s) row affected.");
            }catch(SQLException e){
                e.printStackTrace();
            }
            usercardnames.add(card.getName());
            updateUserDetails(user);
            return true;
        }
        return false;

    }
    //
       private boolean upgradeCard(String cardName, User user) {
            Card card = getcardbycardname(cardName);
           if(usercardnames.contains(card.getName()))
           {
               for(int j = 0; j < Controller.currentuser.cardsInCardType.size() ; j++)
               {
                   if(user.cardsInCardType.get(j).getName().equals(cardName))
                   {
                       card = user.cardsInCardType.get(j);
                       break;
                   }
               }
           }
            if (!usercardnames.contains(cardName)) {
               return false;
            }
            int upgradeCost = card.getUpgradecost();
            if(user.getLevel() >= card.getUpgradelevel())
            {
                if (user.getCoins() >= upgradeCost) {
                    user.setCoins(user.getCoins() - upgradeCost);
                    try{
                        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                        String query = "update users set coins = " + user.getCoins() + " where username = \"" + user.getUsername() + "\";";
                        Statement statement = conn.createStatement();
                        int rowsAffected = statement.executeUpdate(query);
                        System.out.println("User's coins modified in DB successfully.");
                        System.out.println(rowsAffected + "row(s) affected.");
                    }catch(SQLException e){
                        e.printStackTrace();
                    }
                    int newUpgradeCost = (int)Math.ceil(1.25 * card.getUpgradecost());
                    int newutimesupgraded = card.getTimesupgraded() + 1;
                    card.setUpgradecost(newUpgradeCost);
                    card.setTimesupgraded(newutimesupgraded);
                    card.setstrength(card.getstrength()+1);

                    String tableName = user.getUsername().toLowerCase() + "_cardtable";
                    try{
                        Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                        String query = "update " + tableName + " set strength = " + card.getstrength() + " where name = \"" + card.getName() + "\";";
                        Statement statement = conn.createStatement();
                        int rowsAffected = statement.executeUpdate(query);
                        System.out.println("Card's strength in user's cards table in DB was modified successfully.");
                        System.out.println(rowsAffected + " row(s) affected.");
                        query = "update " + tableName + " set upgradeCost = " + newUpgradeCost + " where name = \"" + card.getName() + "\";";
                        statement = conn.createStatement();
                        rowsAffected = statement.executeUpdate(query);
                        System.out.println("Card's upgrade cost was modified successfully.");
                        System.out.println(rowsAffected + " row(s) affected.");
                        query = "update " + tableName + " set timesUpgraded = " + newutimesupgraded + " where name = \"" + card.getName() + "\";";
                        statement = conn.createStatement();
                        rowsAffected = statement.executeUpdate(query);
                        System.out.println("Card's timesUpgraded was modified successfully.");
                        System.out.println(rowsAffected + " row(s) affected.");
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                    updateUserDetails(user);
                    return true;
                }
            }

           return false;
        }
    //
        private Card getcardbycardname(String cardName) {
            // Helper method to find a card by its name
            for (Card card : allCards) {
                if (card.getName().equals(cardName)) {
                    return card;
                }
            }
            return null;
        }

    private VBox createCardBox(Card card, int indexInPage) {
        int indexInAllCards = currentPage * cardsPerPage + indexInPage;
        VBox cardBox = new VBox();
        if (indexInAllCards < imagePaths.length) {
            ImageView cardImage = new ImageView(new Image(getClass().getResource(imagePaths[indexInAllCards]).toExternalForm()));
            cardImage.setFitWidth(206);
            cardImage.setFitHeight(292);
            cardImage.setPreserveRatio(true);
            cardImage.setSmooth(true);
            cardImage.setCache(true);
            String tooltipText = usercardnames.contains(card.getName()) ? "Upgrade" : "Unlock";
            Tooltip tooltip = new Tooltip(tooltipText);
            tooltip.setStyle("-fx-font-family: Berlin Sans FB Demi Bold; -fx-font-size: 14;");
            Tooltip.install(cardImage, tooltip); // Show tooltip on image hover
            cardImage.setOnMouseClicked(e -> {
                // Show confirmation dialog before proceeding with the action
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                if (!usercardnames.contains(card.getName())) {
                    confirmationAlert.setTitle("Confirm Purchase");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to buy this card?");
                } else {
                    confirmationAlert.setTitle("Confirm Upgrade");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to upgrade this card?");
                }

                Optional<ButtonType> result = confirmationAlert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    if (!usercardnames.contains(card.getName())) {
                        if (buyCard(card.getName(), Controller.currentuser)) {
                            showAlert("Card Purchased", "Card added to your deck successfully!");
                        } else {
                            showAlert("Insufficient Funds", "You do not have enough coins to buy this card.");
                        }
                    } else {
                        if (upgradeCard(card.getName(), Controller.currentuser)) {
                            showAlert("Card Upgraded", "Card upgraded successfully! 1 Point was added to Card's Attack Point!");
                        } else {
                            showAlert("Upgrade Failed", "You cannot upgrade this card at the moment.");
                        }
                    }
                    displayPage(currentPage);
                }

            });

            cardBox.getChildren().add(cardImage);
            cardBox.setAlignment(Pos.CENTER);
            cardBox.setOnMouseEntered(e -> showCardDetails(card));
        }
        return cardBox;
    }



    private void showCardDetails(Card card) {
        if (usercardnames.contains(card.getName())) {
            cardDetails.setText(String.format("Level: %d, Times Upgraded: %d, Price: %d",
                    card.getUpgradelevel(), card.getTimesupgraded(), card.getUpgradecost()));
        } else {
            cardDetails.setText(String.format("Price: %d", card.getPrice()));
        }
    }

    @FXML
    private void handlePrevPage() {
        if (currentPage > 0) {
            currentPage--;
            displayPage(currentPage);
        }
    }

    @FXML
    private void handleNextPage() {
        if ((currentPage + 1) * cardsPerPage < allCards.size()) {
            currentPage++;
            displayPage(currentPage);
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void updateUserDetails(User user) {
        userDetailsLabel.setText("Coins: " + user.getCoins() + " | Level: " + user.getLevel());
    }

}
