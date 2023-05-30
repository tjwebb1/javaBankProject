
import java.util.UUID;
import java.util.Scanner;

public class bank{
    String client_name;
    String client_username;
    String client_password;
    UUID client_id;
    UUID random_id = UUID.randomUUID();
    static Scanner input = new Scanner(System.in);
    accounts accounts = new accounts(this);

    public bank() {
        //
    }

    public accounts get_accounts() {
        return accounts;
    }

    public void create_client_account() {
        System.out.print("\nPlease enter clients name: ");
        set_client_name(input.nextLine());
        System.out.print("Please enter desired username: ");
        set_client_username(input.nextLine());
        System.out.print("Please enter desired password: ");
        set_client_password(input.nextLine());
        set_client_id();
        System.out.println("\nNew client created.");
    }

    public static int client_login() {
        int index = 0;
        System.out.print("\nPlease enter username: ");
        String username = input.nextLine();
        System.out.print("Please enter password: ");
        String password = input.nextLine();
        for(bank element : main.list_of_clients) {
            if(element.get_client_username().equals(username) && element.get_client_password().equals(password)) {
                System.out.println("\nClient successfully found.");
                return index;
            } else {
                index++;
            }
        }
        System.out.println("\nUsername or password entered incorrectly.");
        return -1;
    }

    public static int find_client() {
        int index = 0;
        System.out.print("\nPlease enter clients name: ");
        String check = input.nextLine().toLowerCase();
        for(bank element : main.list_of_clients) {
            if(element.get_client_name().equals(check)) {
                System.out.println("\nClient successfully found.");
                return index;
            } else {
                index++;
            }
        }
        System.out.println("\nClient not in system.");
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