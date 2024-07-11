package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class HISTORY {
    private SimpleIntegerProperty number;
    private SimpleStringProperty state;
    private SimpleStringProperty outcome;
    private SimpleStringProperty opponent;
    private SimpleIntegerProperty opponentLevel;
    private SimpleStringProperty time;

    public HISTORY(int number, String state, String outcome, String opponent, int opponentLevel, String time) {
        this.number = new SimpleIntegerProperty(number);
        this.state = new SimpleStringProperty(state);
        this.outcome = new SimpleStringProperty(outcome);
        this.opponent = new SimpleStringProperty(opponent);
        this.opponentLevel = new SimpleIntegerProperty(opponentLevel);
        this.time = new SimpleStringProperty(time);
    }

    public int getNumber() {
        return number.get();
    }
    public String getState() {
        return state.get();
    }
    public String getOutcome() {
        return outcome.get();
    }
    public String getOpponent() {
        return opponent.get();
    }
    public int getOpponentLevel() {
        return opponentLevel.get();
    }

    public String getTime() {
        return time.get();
    }
}
