import java.util.UUID;
import java.util.Scanner;

public class Bank{
    private String clientName;
    private String clientUsername;
    private String clientPassword;
    private UUID clientID;
    private UUID randomID = UUID.randomUUID();
    private static Scanner input = new Scanner(System.in);
    private Accounts accounts = new Accounts(this);

    public Bank() {
        //
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void createClientAccount() {
        System.out.print("\nPlease enter clients name: ");
        setClientName(input.nextLine());
        System.out.print("Please enter desired username: ");
        setClientUsername(input.nextLine());
        System.out.print("Please enter desired password: ");
        setClientPassword(input.nextLine());
        setClientID();
        System.out.println("\nNew client created.");
    }

    public static int clientLogin() {
        int index = 0;
        System.out.print("\nPlease enter username: ");
        String username = input.nextLine();
        System.out.print("Please enter password: ");
        String password = input.nextLine();
        for(Bank element : Main.listOfClients) {
            if(element.getClientUsername().equals(username) && element.getClientPassword().equals(password)) {
                System.out.println("\nClient successfully found.");
                return index;
            } else {
                index++;
            }
        }
        System.out.println("\nUsername or password entered incorrectly.");
        return -1;
    }

    public static int findClient() {
        int index = 0;
        System.out.print("\nPlease enter clients name: ");
        String check = input.nextLine().toLowerCase();
        for(Bank element : Main.listOfClients) {
            if(element.getClientName().equals(check)) {
                System.out.println("\nClient successfully found.");
                return index;
            } else {
                index++;
            }
        }
        System.out.println("\nClient not in system.");
        return -1;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public void setClientID() {
        this.clientID = randomID;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public UUID getClientID() {
        return clientID;
    }
}   