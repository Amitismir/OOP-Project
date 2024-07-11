package Model;

public class ANFO implements character{
    String name = "A.N.F.O.";
    public int increment = 3;
    @Override
    public String getName() {
        return name;
    }

    public int getIncrement() {
        return increment;
    }
}
