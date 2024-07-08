package Model;

public class ASCIIART {
    public String[] lines = new String[9];
    public char value;
    public ASCIIART(String str1, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, char v){
        this.lines[0] = str1;
        this.lines[1] = str2;
        this.lines[2] = str3;
        this.lines[3] = str4;
        this.lines[4] = str5;
        this.lines[5] = str6;
        this.lines[6] = str7;
        this.lines[7] = str8;
        this.lines[8] = str9;
        this.value = v;
    }

}
