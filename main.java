import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class main{
    public static List<bank> list_of_clients = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    int client_index;
    static main main = new main();

    public static void store_client(bank new_client) {
        list_of_clients.add(new_client);
    }
    
    public int get_client_index() {
        return client_index;
    }

    public void set_client_index(int client_index) {
        this.client_index = client_index;
    }
    public static int initial_choice() {
        try{
            System.out.println("\nPlease select an option from below.");
            System.out.println("1. Create new account.");
            System.out.println("2. Login to account.");
            System.out.println("3. Exit the program.");
            System.out.print("Enter choice: ");
            return Integer.parseInt(input.nextLine());
        }catch (Exception e) {
            return -1;
        }
    }

    public static int choose_account() {
        try {
            System.out.println("\nWhich account would you like to access?");
            System.out.println("1. Checking account."); 
            System.out.println("2. Savings account.");
            System.out.println("3. Exit.");
            System.out.print("Enter choice: ");
            return Integer.parseInt(input.nextLine()); // This will be account_choice in every method, checking always 1, savings always 2.
        }catch (Exception e) {
            return -1;
        }       
    }

    public static boolean account_switch_case(int account_choice) {
        do{
            switch(account_choice) {
                case 1:
                case 2: bank bank = list_of_clients.get(main.get_client_index());
                        accounts accounts = bank.get_accounts();
                        accounts.account_info(account_choice);
                        accounts.account_options_switch(bank.accounts.account_options(), account_choice);
                        return false;
                case 3: return true;
            }
        } while(true);
    }
    public static void main(String[] args) {
        Boolean banking_done = false;
        Boolean account_done = false;
        do{
            
            int choice = initial_choice();
            switch(choice) { 
                case 1: bank new_client = new bank();
                        store_client(new_client);
                        new_client.create_client_account();
                        break;
                case 2: main.set_client_index(bank.client_login());
                        if(main.get_client_index() == -1) {
                           break;
                        }
                        do{
                            account_done = account_switch_case(choose_account());
                        }while(!(account_done));
                        break;
                case 3: banking_done = true;
                        break;
                default: System.out.println("\nInvalid input, please enter a valid number.");
                        break;
            }
        } while(!(banking_done));
        System.out.println("\nThank you for using the program!");
    }
}