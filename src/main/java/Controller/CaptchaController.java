package Controller;

import Model.ASCIIART;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import view.debutantMenu;
import view.loginMenu;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

public class CaptchaController {
    public static ArrayList<ASCIIART> asciiList = new ArrayList();

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()_+";

    public String randomgenerator() {
        List<Character> charList = new ArrayList<>();
        for (char c : CHARACTERS.toCharArray()) {
            charList.add(c);
        }

        SecureRandom random = new SecureRandom();
        Collections.shuffle(charList, random);

        StringBuilder password = new StringBuilder();
        password.append(charList.get(random.nextInt(26))); // at least one uppercase
        password.append(charList.get(random.nextInt(26) + 26)); // at least one lowercase
        password.append(charList.get(random.nextInt(10) + 52)); // at least one number
        password.append(charList.get(random.nextInt(8) + 62)); // at least one special character

        while (password.length() < 8) {
            password.append(charList.get(random.nextInt(CHARACTERS.length())));
        }

        Collections.shuffle(charList, random);
        return password.toString();
    }

    private TextField usernameField = new signupMenuController().usernameField;
    private TextField securityqField = new SecuityController().securityqField;
    private TextField securityaField = new SecuityController().securityaField;


    @FXML
    private TextArea captchaTextArea;

    @FXML
    private TextField captchaInput;

    @FXML
    private Label captchaResultLabel;

    private String generatedCaptcha;
    private String Ans = "";

    @FXML
    private void initialize() {
        generateCaptcha();
    }

    @FXML
    private void handleCaptchaSubmit() {
        String enteredCaptcha = captchaInput.getText().trim();
        if (captchaInput.getText().trim().equals(Ans)) {
            captchaResultLabel.setText("CAPTCHA correct!");
            Stage stage = (Stage) captchaInput.getScene().getWindow();
            stage.close();
            System.out.println(Ans);
            showAlert(Alert.AlertType.INFORMATION, "Success!", "Security question answered and CAPTCHA verified successfully!");
            debutantMenu debutantMenu=new debutantMenu();
            try {
                debutantMenu.start(debutantMenu.stage);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            captchaResultLabel.setText("Incorrect CAPTCHA! Please try again.");
            System.out.println(Ans);
            Ans = "";
            generateCaptcha();

            enteredCaptcha = captchaInput.getText().trim();

        }
    }

    private void generateCaptcha() {
        assign();
        String captcha = "";

            ArrayList<ASCIIART> selectedMembers = new ArrayList<>();
            Collections.shuffle(asciiList);
            for (int j = 0; j < 4; j++) {
                selectedMembers.add(asciiList.get(j));
            }
            for(int j=0; j<9; j++){
                for(int k=0; k<4; k++){
                    captcha += selectedMembers.get(k).lines[j];
                   // captcha += " ";

                }
                if(j!= 8)
                captcha+="\n";
            }
        System.out.println(captcha);
        for(int j=0; j<4; j++){
            Ans+= selectedMembers.get(j).value;
        }

        generatedCaptcha = captcha;
        captchaTextArea.setText(generatedCaptcha);
        captchaInput.clear();
        //captchaResultTextArea.setText("");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void assign() {
        ASCIIART A = new ASCIIART(" _______ ", "(  ___  )", "| (   ) |", "| (___) |", "|  ___  |", "| (   ) |", "| )   ( |", "|/     \\|", "         ", 'A');
        asciiList.add(A);
        ASCIIART B = new ASCIIART(" ______  ", "(  ___ \\ ", "| (   ) )", "| (__/ / ", "|  __ (  ", "| (  \\ \\ ", "| )___) )", "|/ \\___/ ", "         ", 'B');
        asciiList.add(B);
        ASCIIART C = new ASCIIART(" _______ ", "(  ____ \\", "| (    \\/", "| |      ", "| |      ", "| |      ", "| (____/\\", "(_______/", "         ", 'C');
        asciiList.add(C);
        ASCIIART D = new ASCIIART(" ______  ", "(  __  \\ ", "| (  \\  )", "| |   ) |", "| |   | |", "| |   ) |", "| (__/  )", "(______/ ", "         ", 'D');
        asciiList.add(D);
        ASCIIART E = new ASCIIART(" _______ ", "(  ____ \\", "| (    \\/", "| (__    ", "|  __)   ", "| (      ", "| (____/\\", "(_______/", "         ", 'E');
        asciiList.add(E);
        ASCIIART F = new ASCIIART(" _______ ", "(  ____ \\", "| (    \\/", "| (__    ", "|  __)   ", "| (      ", "| )      ", "|/       ", "         ", 'F');
        asciiList.add(F);
        ASCIIART G = new ASCIIART(" _______ ", "(  ____ \\", "| (    \\/", "| |      ", "| | ____ ", "| | \\_  )", "| (___) |", "(_______)", "         ", 'G');
        asciiList.add(G);
        ASCIIART H = new ASCIIART("         ", "|\\     /|", "| )   ( |", "| (___) |", "|  ___  |", "| (   ) |", "| )   ( |", "|/     \\|", "         ", 'H');
        asciiList.add(H);
        ASCIIART I = new ASCIIART("_________", "\\__   __/", "   ) (   ", "   | |   ", "   | |   ", "   | |   ", "___) (___", "\\_______/", "         ", 'I');
        asciiList.add(I);
        ASCIIART J = new ASCIIART("_________", "\\__    _/", "   ) (   ", "   |  |  ", "   |  |  ", "   |  |  ", "|\\_)  )  ", "(____/   ", "         ", 'J');
        asciiList.add(J);
        ASCIIART K = new ASCIIART(" _       ", "| \\    /\\", "|  \\  / /", "|  (_/ / ", "|   _ (  ", "|  ( \\ \\ ", "|  /  \\ \\", "|_/    \\/", "         ", 'K');
        asciiList.add(K);
        ASCIIART L = new ASCIIART(" _       ", "( \\      ", "| (      ", "| |      ", "| |      ", "| |      ", "| (____/\\", "(_______/", "         ", 'L');
        asciiList.add(L);
        ASCIIART M = new ASCIIART(" _______ ", "(       )", "| () () |", "| || || |", "| |(_)| |", "| |   | |", "| )   ( |", "|/     \\|", "         ", 'M');
        asciiList.add(M);
        ASCIIART N = new ASCIIART(" _       ", "( (    /|", "|  \\  ( |", "|   \\ | |", "| (\\ \\) |", "| | \\   |", "| )  \\  |", "|/    )_)", "         ", 'N');
        asciiList.add(N);
        ASCIIART O = new ASCIIART(" _______ ", "(  ___  )", "| (   ) |", "| |   | |", "| |   | |", "| |   | |", "| (___) |", "(_______)", "         ", 'O');
        asciiList.add(O);
        ASCIIART P = new ASCIIART(" _______ ", "(  ____ )", "| (    )|", "| (____)|", "|  _____)", "| (      ", "| )      ", "|/       ", "         ", 'P');
        asciiList.add(P);
        ASCIIART Q = new ASCIIART(" _______ ", "(  ___  )", "| (   ) |", "| |   | |", "| |   | |", "| | /\\| |", "| (_\\ \\ |", "(____\\/_)", "         ", 'Q');
        asciiList.add(Q);
        ASCIIART R = new ASCIIART(" _______ ", "(  ____ )", "| (    )|", "| (____)|", "|     __)", "| (\\ (   ", "| ) \\ \\__", "|/   \\__/", "         ", 'R');
        asciiList.add(R);
        ASCIIART S = new ASCIIART(" _______ ", "(  ____ \\", "| (    \\/", "| (_____ ", "(_____  )", "      ) |", "/\\____) |", "\\_______)", "         ", 'S');
        asciiList.add(S);
        ASCIIART T = new ASCIIART("_________", "\\__   __/", "   ) (   ", "   | |   ", "   | |   ", "   | |   ", "   | |   ", "   )_(   ", "         ", 'T');
        asciiList.add(T);
        ASCIIART U = new ASCIIART("         ", "|\\     /|", "| )   ( |", "| |   | |", "| |   | |", "| |   | |", "| (___) |", "(_______)", "         ", 'U');
        asciiList.add(U);
        ASCIIART V = new ASCIIART("         ", "|\\     /|", "| )   ( |", "| |   | |", "( (   ) )", " \\ \\_/ / ", "  \\   /  ", "   \\_/   ", "         ", 'V');
        asciiList.add(V);
        ASCIIART W = new ASCIIART("         ", "|\\     /|", "| )   ( |", "| | _ | |", "| |( )| |", "| || || |", "| () () |", "(_______)", "         ", 'W');
        asciiList.add(W);
        ASCIIART X = new ASCIIART("         ", "|\\     /|", "( \\   / )", " \\ (_) / ", "  ) _ (  ", " / ( ) \\ ", "( /   \\ )", "|/     \\|", "         ", 'X');
        asciiList.add(X);
        ASCIIART Y = new ASCIIART("         ", "|\\     /|", "( \\   / )", " \\ (_) / ", "  \\   /  ", "   ) (   ", "   | |   ", "   \\_/   ", "         ", 'Y');
        asciiList.add(Y);
        ASCIIART Z = new ASCIIART(" _______ ", "/ ___   )", "\\/   )  |", "    /   )", "   /   / ", "  /   /  ", " /   (_/\\", "(_______/", "         ", 'Z');
        asciiList.add(Z);
        ASCIIART Num1 = new ASCIIART("  __   ", " /  \\  ", " \\/) ) ", "   | | ", "   | | ", "   | | ", " __) (_", " \\____/", "       ", '1');
        asciiList.add(Num1);
        ASCIIART Num2 = new ASCIIART(" _______ ", "/ ___   )", "\\/   )  |", "    /   )", "  _/   / ", " /   _/  ", "(   (__/\\", "\\_______/", "         ", '2');
        asciiList.add(Num2);
        ASCIIART Num3 = new ASCIIART(" ______  ", "/ ___  \\ ", "\\/   \\  \\", "   ___) /", "  (___ ( ", "      ) \\", "/\\___/  /", "\\______/ ", "         ", '3');
        asciiList.add(Num3);
        ASCIIART Num4 = new ASCIIART("    ___   ", "   /   ) ", "  / /) | ", " / (_) (_ ", "(____   _)", "     ) (  ", "     | |  ", "     (_)  ", "         ", '4');
        asciiList.add(Num4);
        ASCIIART Num5 = new ASCIIART(" _______ ", " (  ____ \\", "| (    \\/", "| (____  ", "(_____ \\ ", "      ) )", "/\\____) )", "\\______/ ", "         ", '5');
        asciiList.add(Num5);
        ASCIIART Num6 = new ASCIIART("  ______ ", " / ____ \\", "| (    \\/", "| (____  ", "|  ___ \\ ", "| (   ) )", "( (___) )", " \\_____/ ", "         ", '6');
        asciiList.add(Num6);
        ASCIIART Num7 = new ASCIIART(" ______  ", "/ ___  \\ ", "\\/   )  )", "    /  / ", "   /  /  ", "  /  /   ", " /  /    ", " \\_/     ", "         ", '7');
        asciiList.add(Num7);
        ASCIIART Num8 = new ASCIIART("  _____  ", " / ___ \\ ", "( (___) )", " \\     / ", " / ___ \\ ", "( (   ) )", "( (___) )", " \\_____/ ", "         ", '8');
        asciiList.add(Num8);
        ASCIIART Num9 = new ASCIIART("  _____  ", " / ___ \\ ", "( (   ) )", "( (___) |", " \\____  |", "      ) |", "/\\____) )", "\\______/ ", "         ", '9');
        asciiList.add(Num9);
        ASCIIART Num0 = new ASCIIART(" _______ ", "(  __   )", "| (  )  |", "| | /   |", "| (/ /) |", "|   / | |", "|  (__) |", "(_______)", "         ", '0');
        asciiList.add(Num0);
    }


}
