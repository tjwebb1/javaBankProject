import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class savings_account{
    double balance;
    List<String> prev_transaction_notes = new ArrayList<>();
    int prev_transactions_occurred = 0;
    Scanner input = new Scanner(System.in);
    String invalid = "Invalid input, please enter a valid number.";
    checking_account checking_account;
    bank bank;

    public savings_account() {
        //
    }

    public savings_account(bank bank) {
        this.bank = bank;
    }

    public void savings_account_info() {
        System.out.println("Current user: " + bank.get_client_name());
        System.out.println("Current balance: " + get_balance());
        System.out.println("Amount of previous transactions: " + prev_transactions_occurred);
    }

    public int savings_account_options() {
        try{
            System.out.println("Please choose an option below.");
            System.out.println("1. Deposit into account.");
            System.out.println("2. Withdraw from account.");
            System.out.println("3. Transfer to Checking Account.");
            System.out.print("Please enter choice: ");
            return Integer.parseInt(input.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public void savings_account_options_switch(int choice) {
        double amount;
        switch(choice) {
            case 1: System.out.print("Please enter amount you wish to deposit: ");
                    amount = Double.parseDouble(input.nextLine());
                    amount = check_amount(amount);
                    if(amount == 0) {
                        break;
                    }
                    deposit_into_savings(amount);
                    break;
            case 2: System.out.print("Please enter amount you wish to withdraw: ");
                    amount = Double.parseDouble(input.nextLine());
                    amount = check_amount(amount);
                    if(amount == 0) {
                        break;
                    }
                    withdraw_from_savings(amount);
                    break;
            case 3: System.out.print("Please enter amount you wish to transfer: ");
                    amount = Double.parseDouble(input.nextLine());
                    amount = check_amount(amount);
                    if(amount == 0) {
                        break;
                    }
                    transfer_savings_to_checking(amount);
                    break;
            default: System.out.println(invalid);
                    break;
        }
    }

    public double check_amount(double amount) {
        try{
            if(amount < 0) {
                System.out.println(invalid);
                return 0;
            } else {
                return amount;
            }
        } catch (Exception e) {
            System.out.println(invalid);
            return 0;
        }
    }

    public void set_balance(double balance) {
        this.balance = balance;
    }

    public double get_balance() {
        return balance;
    }

    public void deposit_into_savings(double amount) {
        balance += amount;
        prev_transactions_occurred++;
        System.out.println(amount + " successfully deposited.");
        prev_transaction_notes.add("Transaction " + prev_transactions_occurred + ": You deposited " + amount + " dollars into this account.");
    }

    public void withdraw_from_savings(double amount) {
        if(balance - amount < 0) {
            System.out.println("Unable to withdraw, not enough money in account.");
        } else {
            balance -= amount;
            prev_transactions_occurred++;
            System.out.println(amount + " successfully withdrawn.");
            prev_transaction_notes.add("Transaction " + prev_transactions_occurred + ": You withdrew " + amount + " dollars into this account.");
        }
    }

    public void transfer_savings_to_checking(double amount) {
        if(balance - amount < 0) {
            System.out.println("Unable to trasfer, not enough money in account.");
        } else {
            withdraw_from_savings(amount);
            checking_account.deposit_into_checking(amount);
            prev_transactions_occurred++;
            checking_account.prev_transactions_occurred++;
            System.out.println(amount + " successfully transferred.");
            prev_transaction_notes.add("Transaction " + prev_transactions_occurred + ": You transferred " + amount + " dollars to your Savings Account.");
            checking_account.prev_transaction_notes.add("Transaction " + checking_account.prev_transactions_occurred + ": You transferred " + amount + " dollars to your Savings Account.");
        }
    }
}