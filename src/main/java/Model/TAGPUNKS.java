package Model;

public class TAGPUNKS implements character{
    String name = "TAG PUNKS";
    public int increment = 1;
    @Override
    public String getName() {
        return name;
    }

    public int getIncrement() {
        return increment;
    }
}
