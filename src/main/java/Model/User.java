package Model;

import java.util.ArrayList;

public class User {
    public String username;
    public String password;
    public String nickname;
    public String securityQ;
    public String securityANS;
    public String email;
    public ArrayList<String> cardNamesInString;
    public ArrayList<Card> cardsInCardType;
    int level;
    int MaxHP;
    int XP;
    int coins;
    String charactername;
    int DAMAGE;
    int ROUNDS;
    int HP;

    public String getSecurityQ() {
        return securityQ;
    }

    public String getSecurityANS() {
        return securityANS;
    }

    public void setSecurityANS(String securityANS) {
        this.securityANS = securityANS;
    }

    public ArrayList<String> getCardNamesInString() {
        return cardNamesInString;
    }

    public void setCardNamesInString(ArrayList<String> cardNamesInString) {
        this.cardNamesInString = cardNamesInString;
    }

    public ArrayList<Card> getCardsInCardType() {
        return cardsInCardType;
    }

    public void setCardsInCardType(ArrayList<Card> cardsInCardType) {
        this.cardsInCardType = cardsInCardType;
    }

    public int getDAMAGE() {
        return DAMAGE;
    }

    public void setDAMAGE(int DAMAGE) {
        this.DAMAGE = DAMAGE;
    }

    public int getROUNDS() {
        return ROUNDS;
    }

    public void setROUNDS(int ROUNDS) {
        this.ROUNDS = ROUNDS;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public String getCharactername() {
        return charactername;
    }

    public void setCharactername(String charactername) {
        this.charactername = charactername;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHP() {
        return MaxHP;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setMaxHP(int maxHP) {
        MaxHP = maxHP;
    }

    public void setXP(int XP) {
        this.XP = XP;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getXP() {
        return XP;
    }

    public int getCoins() {
        return coins;
    }

    public User(String username, String password, String nickname, String securityquestion, String answer, String email,
                int level, int MaxHP, int XP, int coins) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.securityQ = securityquestion;
        this.securityANS = answer;
        this.email = email;
        //default values for xp, level, maxHP, cardsInString and cardsInCardType:
        cardNamesInString = new ArrayList<>();
        cardsInCardType = new ArrayList<>();
        this.level = level;
        this.MaxHP = MaxHP;
        this.XP = XP;
        this.coins = coins;

    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getsecurityQ() {
        return securityQ;
    }

    public String getsecurityANS() {
        return securityANS;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSecurityQ(String securityquestion) {
        this.securityQ = securityquestion;
    }

    public void setsecurityANS(String answer) {
        this.securityANS = answer;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
