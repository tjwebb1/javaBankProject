import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class checking_account{
    double balance;
    List<String> prev_transaction_notes = new ArrayList<>();
    int prev_transactions_occurred = 0;
    Scanner input = new Scanner(System.in);
    String invalid = "Invalid input, please enter a valid number.";
    savings_account savings_account;
    bank bank;

    public checking_account() {
        //
    }

    public checking_account(bank bank) {
        this.bank = bank;
    }
    
    public void checking_account_info() {
        System.out.println("Current user: " + bank.get_client_name());
        System.out.println("Current balance: " + get_balance());
        System.out.println("Amount of previous transactions: " + prev_transactions_occurred + "\n");
    }

    public int checking_account_options() {
        try{
            System.out.println("Please choose an option below.");
            System.out.println("1. Deposit into account.");
            System.out.println("2. Withdraw from account.");
            System.out.println("3. Transfer to Savings Account.");
            System.out.println("4. Check previous transactions.");
            System.out.print("Please enter choice: ");
            return Integer.parseInt(input.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public void checking_account_options_switch(int choice) {
        double amount;
        switch(choice) {
            case 1: System.out.print("Please enter amount you wish to deposit: ");
                    amount = Double.parseDouble(input.nextLine());
                    amount = check_amount(amount);
                    if(amount == 0) {
                        break;
                    }
                    deposit_into_checking(amount);
                    break;
            case 2: System.out.print("Please enter amount you wish to withdraw: ");
                    amount = Double.parseDouble(input.nextLine());
                    amount = check_amount(amount);
                    if(amount == 0) {
                        break;
                    }
                    withdraw_from_checking(amount);
                    break;
            case 3: System.out.print("Please enter amount you wish to transfer: ");
                    amount = Double.parseDouble(input.nextLine());
                    amount = check_amount(amount);
                    if(amount == 0) {
                        break;
                    }
                    transfer_checking_to_savings(amount);
                    break;
            case 4: System.out.print("Please enter amount of transactions to see: ");
                    amount = Integer.parseInt(input.nextLine());
                    amount = check_amount(amount);
                    if(amount == 0) {
                        break;
                    }
                    display_prev_transactions(amount);
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

    public void deposit_into_checking(double amount) {
        balance += amount;
        System.out.println(amount + " successfully deposited.");
        prev_transactions_occurred++;
        prev_transaction_notes.add("Transaction " + prev_transactions_occurred + ": You deposited " + amount + " dollars into this account.");
    }

    public void withdraw_from_checking(double amount) {
        if(balance - amount < 0) {
            System.out.println("Unable to withdraw, not enough money in account.");
        } else {
            balance -= amount;
            System.out.println(amount + " successfully withdrawn.");
            prev_transactions_occurred++;
            prev_transaction_notes.add("Transaction " + prev_transactions_occurred + ": You withdrew " + amount + " dollars into this account.");
        }
    }

    public void transfer_checking_to_savings(double amount) {
        if(balance - amount < 0) {
            System.out.println("Unable to transfer, not enough money in account.");
        } else {
            withdraw_from_checking(amount);
            savings_account.deposit_into_savings(amount);
            prev_transactions_occurred++;
            savings_account.prev_transactions_occurred++;
            System.out.println(amount + " successfully transferred.");
            prev_transaction_notes.add("Transaction " + prev_transactions_occurred + ": You transferred " + amount + " dollars to your Savings Account.");
            savings_account.prev_transaction_notes.add("Transaction " + savings_account.prev_transactions_occurred + ": You transferred " + amount + " dollars to your Savings Account.");
        }
    }

    public void display_prev_transactions(double amount) {
        int int_amount = (int)amount;
        if(int_amount > prev_transactions_occurred) {
            int_amount = prev_transactions_occurred;
        }
        for(int i = prev_transactions_occurred-1; i >= prev_transactions_occurred-int_amount; i--) {
            System.out.println(prev_transaction_notes.get(i));
        }
    }
}