package javabankproject;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    protected static List<Bank> listOfClients = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
    private int clientIndex;
    private static Main main = new Main();

    public static void storeClient(Bank newClient) {
        listOfClients.add(newClient);
    }
    
    public int getClientIndex() {
        return clientIndex;
    }

    public void setClientIndex(int clientIndex) {
        this.clientIndex = clientIndex;
    }
    public static int initialChoice() {
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

    public static int chooseAccount() {
        try {
            System.out.println("\nWhich account would you like to access?");
            System.out.println("1. Checking account."); 
            System.out.println("2. Savings account.");
            System.out.println("3. Logout of account.");
            System.out.print("Enter choice: ");
            return Integer.parseInt(input.nextLine()); // This will be account_choice in every method, checking always 1, savings always 2.
        }catch (Exception e) {
            return -1;
        }       
    }

    public static boolean accountSwitchCase(int accountChoice) {
        do{
            switch(accountChoice) {
                case 1:
                case 2: Bank bank = listOfClients.get(main.getClientIndex());
                        Accounts accounts = bank.getAccounts();
                        accounts.accountOptionsSwitch(accountChoice);
                        return false;
                case 3: return true;
                default: System.out.println("\nInvalid input, please enter a valid number.");
                        return false;
            }
        } while(true);
    }
    public static void main(String[] args) {
        Boolean bankingDone = false;
        Boolean accountDone = false; // Used to stay in logged in account until user prompts to log out.
        do{           
            int choice = initialChoice();
            switch(choice) { 
                case 1: Bank newClient = new Bank();
                        storeClient(newClient);
                        newClient.createClientAccount();
                        break;
                case 2: main.setClientIndex(Bank.clientLogin());
                        if(main.getClientIndex() == -1) {
                           break;
                        }
                        do{
                            accountDone = accountSwitchCase(chooseAccount());
                        }while(Boolean.FALSE.equals((accountDone)));
                        break;
                case 3: bankingDone = true;
                        break;
                default: System.out.println("\nInvalid input, please enter a valid number.");
                        break;
            }
        } while(Boolean.FALSE.equals((bankingDone)));
        System.out.println("\nThank you for using the program!");
    }
}