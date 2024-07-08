package Model;

public class HELIOCELON implements character{
    String name = "HELIO CELON";
    public int increment = 3;
    @Override
    public String getName() {
        return name;
    }

    public int getIncrement() {
        return increment;
    }
}
