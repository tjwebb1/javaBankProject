
import java.util.UUID;
import java.util.Scanner;

public class bank{
    String client_name;
    String client_username;
    String client_password;
    UUID client_id;
    UUID random_id = UUID.randomUUID();
    static Scanner input = new Scanner(System.in);
    checking_account checking_account = new checking_account(this);
    savings_account savings_account = new savings_account(this);


    @Override public String toString() {
        System.out.println("Client name: " + client_name + "; Client Username: " + client_username + "; Client Password: " + client_password + "; Client ID: " + client_id + ";");
        System.out.println("Client checking balance: " + checking_account.balance + "; Checking account previous transactions amount: " + checking_account.prev_transactions_occurred + ";");
        System.out.println("Client savings balance: " + savings_account.balance + "; Savings account previous transactions amount: " + savings_account.prev_transactions_occurred + ";");
        return "";
    }

    public bank() {
        //
    }

    public checking_account get_checking_account() {
        return checking_account;
    }

    public savings_account get_savings_account() {
        return savings_account;
    }

    public void create_client_account() {
        System.out.print("Please enter clients name: ");
        set_client_name(input.nextLine().toLowerCase());
        System.out.print("Please enter desired username: ");
        set_client_username(input.nextLine().toLowerCase());
        System.out.print("Please enter desired password: ");
        set_client_password(input.nextLine().toLowerCase());
        set_client_id();
    }

    public static int find_client() {
        int index = 0;
        System.out.print("Please enter clients name: ");
        String check = input.nextLine().toLowerCase();
        for(bank element : main.list_of_clients) {
            if(element.get_client_name().equals(check)) {
                System.out.println("\nClient successfully found.");
                return index;
            } else {
                index++;
            }
        }
        System.out.println("\nClient not in system.\n");
        return -1;
    }

    public void set_client_name(String client_name) {
        this.client_name = client_name;
    }

    public void set_client_password(String client_password) {
        this.client_password = client_password;
    }

    public void set_client_username(String client_username) {
        this.client_username = client_username;
    }

    public void set_client_id() {
        this.client_id = random_id;
    }

    public String get_client_name() {
        return client_name;
    }

    public String get_client_username() {
        return client_username;
    }

    public String get_client_password() {
        return client_password;
    }

    public UUID get_client_id() {
        return client_id;
    }
}   