package Controller;

import Model.Play;
import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import view.debutantMenu;
import view.game;

public class CharacterController {
    private boolean player1Chosen = false;
    private boolean player2Chosen = false;

    private int playerTurn = 1;

    @FXML
    private Label incrementLabel;

    @FXML
    private ImageView AlphaLupex;

    @FXML
    private ImageView anfo;

    @FXML
    private ImageView heliocelon;

    @FXML
    private ImageView tagpunks;

    @FXML
    private void chooseAlphaLupex(MouseEvent event) {
        handleCardSelection("ALPHA LUPEX", 2);
    }

    @FXML
    private void chooseAnfo(MouseEvent event) {
        handleCardSelection("A.N.F.O.", 3);
    }

    @FXML
    private void chooseHeliocelon(MouseEvent event) {
        handleCardSelection("HELIO CELON", 3);
    }

    @FXML
    private void chooseTagpunks(MouseEvent event) {
        handleCardSelection("TAG PUNKS", 1);
    }

    @FXML
    private void hoverAlphaLupex(MouseEvent event) {
        incrementLabel.setText("Increment value: 2");
    }

    @FXML
    private void hoverAnfo(MouseEvent event) {
        incrementLabel.setText("Increment value: 3");
    }

    @FXML
    private void hoverHeliocelon(MouseEvent event) {
        incrementLabel.setText("Increment value: 3");
    }

    @FXML
    private void hoverTagpunks(MouseEvent event) {
        incrementLabel.setText("Increment value: 1");
    }
    User Player1user = Controller.currentuser;
    User Player2user = modeController.guest;

    private void handleCardSelection(String cardName, int incrementValue) {
        String message = "";
        if(playerTurn == 1){
            message =  Controller.currentuser.username +" chose " + cardName + "!";
            for(int i = 0; i < Player1user.cardsInCardType.size(); i++) {
                if(Player1user.cardsInCardType.get(i).getPlayerCharacter().getName().equals(cardName))
                {

                    Player1user.cardsInCardType.get(i).setstrength(Player1user.cardsInCardType.get(i).getstrength() + Controller.getCharacterByName(cardName).getIncrement());
                }
            }
        }
        if(playerTurn == 2)
        {
            message = modeController.guest.username + " chose " + cardName + "!";
            for(int i = 0; i < Player2user.cardsInCardType.size(); i++) {
                if(Player2user.cardsInCardType.get(i).getPlayerCharacter().getName().equals(cardName))
                {
                    Player2user.cardsInCardType.get(i).setstrength(Player2user.cardsInCardType.get(i).getstrength() + Controller.getCharacterByName(cardName).getIncrement());
                }
            }
        }
        incrementLabel.setText(message);

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Card Selection");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

        // Switch turn to the next player
        //playerTurn = (playerTurn == 1) ? 2 : 1;
        if (playerTurn == 1) {
            Controller.currentuser.setCharactername(cardName);
            player1Chosen = true;
            playerTurn = 2;
        } else {
            modeController.guest.setCharactername(cardName);
            player2Chosen = true;
        }
        if (player1Chosen && player2Chosen) {
            game play = new game();
            try {
                play.start(debutantMenu.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
