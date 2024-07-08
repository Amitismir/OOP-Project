package Controller;

import Model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import view.*;

public class MainMenuController {
    @FXML
    private Label userInfoLabel;
    @FXML
    public void initialize() {
        userInfoLabel.setEffect(new Glow(0.8));
        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(javafx.scene.paint.Color.web("#cb63ab"));
        borderGlow.setWidth(30);
        borderGlow.setHeight(30);
        borderGlow.setSpread(0.5);
        userInfoLabel.setEffect(borderGlow);
        System.out.println("Initializing MainMenuController...");
        updateUserInfo();
    }

    private void updateUserInfo() {
        if (loginMenuController.currentuser != null) {
            String userInfo = String.format("Welcome %s !\nHP: %d\nXP: %d\nLevel: %d\nCoins: %d",
                    loginMenuController.currentuser.getUsername(), loginMenuController.currentuser.getMaxHP(), loginMenuController.currentuser.getXP(),
                    loginMenuController.currentuser.getLevel(), loginMenuController.currentuser.getCoins());
            userInfoLabel.setText(userInfo);
        } else {
            userInfoLabel.setText("User information not available.");
        }
    }

    public void gotoshop(MouseEvent mouseEvent) {
        shopMenu shop = new shopMenu();
        try {
            shop.start(debutantMenu.stage);
            updateUserInfo();
        } catch (Exception e) {
            throw new RuntimeException("Error navigating to shop.", e);
        }
    }
    @FXML
    public void gameHistoryClick(){
        gameHistory gameHistory = new gameHistory();
        try {
            gameHistory.start(debutantMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void logout(MouseEvent mouseEvent) {
        debutantMenu debut = new debutantMenu();
        try {
            debut.start(debutantMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException("Error logging out.", e);
        }
    }
    public void edit(MouseEvent mouseEvent) {

        editprofile edit=new editprofile();
        try {
            edit.start(debutantMenu.stage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
