package Model;

public class Card {
    String name;
    int strength;
    int duration = 1;
    int playerdamage;
    int upgradelevel;
    int upgradecost;
    int timesupgraded;
    int price;
    int divideddamage;
    String type; //"SPECIAL" or "ORDINARY"
    character playerCharacter;

    public int getDivideddamage() {
        return divideddamage;
    }

    public void setDivideddamage(int divideddamage) {
        this.divideddamage = divideddamage;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPlayerCharacter(character mychar) {
        this.playerCharacter = mychar;
    }

    public int getStrength() {
        return strength;
    }

    public character getPlayerCharacter() {
        return playerCharacter;
    }

    public String getType() {
        return type;
    }

    public Card(String name, int strength, int duration, int playerdamage, int upgradelevel,
                int upgradecost, int timesupgraded, int price, boolean ordinary, character mychar) {        this.name = name;
        this.strength = strength;
        this.name = name;
        this.duration = duration;
        this.playerdamage = playerdamage;
        this.upgradelevel = upgradelevel;
        this.upgradecost = upgradecost;
        this.timesupgraded = timesupgraded;
        this.price = price;
        this.playerCharacter = mychar;
        this.divideddamage = playerdamage/duration;
        if(ordinary)
            this.type =  "ORDINARY";
        else
            this.type = "SPECIAL";
    }
    public Card(Card cardInput) {
        this.name = cardInput.getName();
        this.strength = cardInput.getStrength();
        this.duration = cardInput.getDuration();
        this.playerdamage = cardInput.getPlayerdamage();
        this.upgradelevel = cardInput.getUpgradelevel();
        this.upgradecost = cardInput.getUpgradecost();
        this.timesupgraded = cardInput.getTimesupgraded();
        this.price = cardInput.getPrice();
        this.type = cardInput.getType();
        this.playerCharacter = cardInput.getPlayerCharacter();
        this.divideddamage = playerdamage/duration;
    }
    public Card(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getstrength() {
        return strength;
    }

    public void setstrength(int strength) {
        this.strength = strength;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPlayerdamage() {
        return playerdamage;
    }

    public void setPlayerdamage(int playerdamage) {
        this.playerdamage = playerdamage;
    }

    public int getUpgradelevel() {
        return upgradelevel;
    }

    public void setUpgradelevel(int upgradelevel) {
        this.upgradelevel = upgradelevel;
    }

    public int getUpgradecost() {
        return upgradecost;
    }

    public void setUpgradecost(int upgradecost) {
        this.upgradecost = upgradecost;
    }

    public int getTimesupgraded() {
        return timesupgraded;
    }

    public void setTimesupgraded(int timesupgraded) {
        this.timesupgraded = timesupgraded;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
