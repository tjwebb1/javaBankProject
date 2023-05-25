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

    public static int choose_account() {
        try {
            System.out.println("Which account would you like to access?");
            System.out.println("1. Checking account.");
            System.out.println("2. Savings account.");
            System.out.println("3. Exit.");
            return Integer.parseInt(input.nextLine());
        }catch (Exception e) {
                return -1;
        }       
    }

    public static void account_switch_case(int account_choice) {
        boolean account_done = true;
        do{
            switch(account_choice) {
                case 1: bank bank = list_of_clients.get(main.get_client_index());
                        checking_account checking_account = bank.get_checking_account();
                        checking_account.checking_account_info();
                        checking_account.checking_account_options_switch(bank.checking_account.checking_account_options());
                        break;
                case 2: bank = list_of_clients.get(main.get_client_index());
                        savings_account savings_account = bank.get_savings_account();
                        savings_account.savings_account_info();
                        savings_account.savings_account_options_switch(bank.savings_account.savings_account_options());
                        break;
                case 3: account_done = true;
                        break;
            }
        } while(!(account_done));
    }
    public static void main(String[] args) {
        Boolean banking_done = false;
        do{
            System.out.print("Enter 1 to add, 2 to check, 3 to add, 4 to quit: ");
            int choice = Integer.parseInt(input.nextLine());
            switch(choice) { // may change to "create client" and "access client", access client finds index once and uses it throughout the process till user is done
                case 1: bank new_client = new bank();
                        store_client(new_client);
                        new_client.create_client_account();
                        break;
                case 2: main.set_client_index(bank.find_client());
                        if(main.get_client_index() == -1) {
                            break;
                        }
                        bank client = list_of_clients.get(main.get_client_index());
                        System.out.println(client);
                        break;
                case 3: main.set_client_index(bank.find_client());
                        if(main.get_client_index() == -1) {
                            break;
                        }
                        account_switch_case(choose_account());
                        break;
                case 4: banking_done = true;
                        break;
                default: System.out.println("Invalid input, please enter a valid number.");
                        break;
            }
        } while(!(banking_done));
    }
}