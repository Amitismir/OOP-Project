package Model;

public class Cell {
    public int index;
    public User user;
    public enum State {empty , full, hole, boom};
    public State state;
    public int celldamage;
    public int cellstrength;
    public Card card;

    public static String toStringg(State state)
    {
        switch (state)
        {
            case empty:
                return "empty";
            case boom:
                return "boom";
            case hole:
                return "hole";
            default:
                return null;

        }
    }

    public Cell(int index, User user, State state, int celldamage, int cellstrength) {
        this.index = index;
        this.user = user;
        this.state = state;
        this.celldamage = celldamage;
        this.cellstrength = cellstrength;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getCelldamage() {
        return celldamage;
    }

    public void setCelldamage(int celldamage) {
        this.celldamage = celldamage;
    }

    public int getCellstrength() {
        return cellstrength;
    }

    public void setCellstrength(int cellstrength) {
        this.cellstrength = cellstrength;
    }
}
