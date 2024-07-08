package Model;

public class DebutantMenu implements Menu{
    @Override
    public void display() {
        System.out.println("===Debutant Menu===");
        System.out.println("1.Signup");
        System.out.println("2.Login");
        System.out.println("3.Admin");

    }

    @Override
    public Menu handleInput(String input) {

        switch (input)
        {
            case "Signup":
                return new SignupMenu();
            case "Login":
                return new LoginMenu();
            case "Admin":
                return new AdminMenu();
            default:
                System.out.println("Invalid Option");
                return this;
        }
    }
}
