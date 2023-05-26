import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class accounts{
    double checking_balance;
    double savings_balance;
    List<String> prev_transactions_notes_checking = new ArrayList<>();
    List<String> prev_transactions_notes_savings = new ArrayList<>();
    int prev_transactions_occurred_checking = 0;
    int prev_transactions_occurred_savings = 0;
    Scanner input = new Scanner(System.in);
    String invalid = "\nInvalid input, please enter a valid number.";
    bank bank;

    public accounts() {
        //
    }

    public accounts(bank bank) {
        this.bank = bank;
    }
    
    public void set_checking_balance(double checking_balance) {
        this.checking_balance = checking_balance;
    }

    public void set_savings_balance(double savings_balance) {
        this.savings_balance = savings_balance;
    }

    public double get_checking_balance() {
        return checking_balance;
    }

    public double get_savings_balance() {
        return savings_balance;
    }

    public void account_info(int account_choice) {
        System.out.println("\n--------------------------------------------------------------");
        System.out.println("Current user: " + bank.get_client_name());
        if(account_choice == 1) {
            System.out.println("Current balance: " + get_checking_balance());
            System.out.println("Amount of previous transactions: " + prev_transactions_occurred_checking);
        } else if(account_choice == 2) {
            System.out.println("Current balance " + get_savings_balance());
            System.out.println("Amount of previous transactions: " + prev_transactions_occurred_savings);
        }
        System.out.println("--------------------------------------------------------------");
    }

    public int account_options() {
        try{
            System.out.println("\nPlease choose an option below.");
            System.out.println("1. Deposit into account.");
            System.out.println("2. Withdraw from account.");
            System.out.println("3. Transfer to Savings Account.");
            System.out.println("4. Check previous transactions.");
            System.out.println("5. Exit.");
            System.out.print("Please enter choice: ");
            return Integer.parseInt(input.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public void account_options_switch(int choice, int account_choice) {
        double amount;
        switch(choice) {
            case 1: System.out.print("\nPlease enter amount you wish to deposit: ");
                    amount = Double.parseDouble(input.nextLine());
                    amount = check_amount(amount);
                    if(amount == 0) {
                        break;
                    }
                    deposit_into_account(amount, account_choice);
                    break;
            case 2: System.out.print("\nPlease enter amount you wish to withdraw: ");
                    amount = Double.parseDouble(input.nextLine());
                    amount = check_amount(amount);
                    if(amount == 0) {
                        break;
                    }
                    withdraw_from_account(amount, account_choice);
                    break;
            case 3: System.out.print("\nPlease enter amount you wish to transfer: ");
                    amount = Double.parseDouble(input.nextLine());
                    amount = check_amount(amount);
                    if(amount == 0) {
                        break;
                    }
                    transfer_checking_to_savings(amount, account_choice);
                    break;
            case 4: System.out.print("\nPlease enter amount of transactions to see: ");
                    amount = Integer.parseInt(input.nextLine());
                    amount = check_amount(amount);
                    if(amount == 0) {
                        break;
                    }
                    display_prev_transactions(amount, account_choice);
                    break;
            case 5: break;
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

    public void deposit_into_account(double amount, int account_choice) {
        if(account_choice == 1) {
            checking_balance += amount;
            prev_transactions_notes_checking.add("\nTransaction " + ++prev_transactions_occurred_checking + ": You deposited " + amount + " dollars into this account.");
        } else if(account_choice == 2) {
            savings_balance += amount;
            prev_transactions_notes_savings.add("\nTransaction " + ++prev_transactions_occurred_savings + ": You deposited " + amount + " dollars into this account.");
        }
        System.out.println("\n" + amount + " successfully deposited.");
    }

    public void withdraw_from_account(double amount, int account_choice) {
        if(account_choice == 1) {
            if(checking_balance - amount < 0) {
                System.out.println("\nUnable to withdraw, not enough money in account.");
            } else {
                checking_balance -= amount;
                System.out.println("\n" + amount + " successfully withdrawn.");
                prev_transactions_notes_checking.add("\nTransaction " + ++prev_transactions_occurred_checking + ": You withdrew " + amount + " dollars from this account.");
            }
        } else if(account_choice == 2) {
            if(savings_balance - amount < 0) {
                System.out.println("\nUnable to withdraw, not enough money in account.");
            } else {
                savings_balance -= amount;
                System.out.println("\n" + amount + " successfully withdrawn.");
                prev_transactions_notes_savings.add("\nTransaction " + ++prev_transactions_occurred_savings + ": You withdrew " + amount + " dollars from this account.");
            }
        }
    }

    public void transfer_checking_to_savings(double amount, int account_choice) {
        int opposite_account = 0;
        if(account_choice == 1) {
            opposite_account = 2;
            if(checking_balance - amount < 0) {
                System.out.println("\nUnable to transfer, not enough money in account.");
                return;
            } else {
                prev_transactions_notes_checking.add("\nTransaction " + ++prev_transactions_occurred_checking + ": You transferred " + amount + " dollars to your Savings Account.");
                prev_transactions_notes_savings.add("\nTransaction " + ++prev_transactions_occurred_savings + ": You received " + amount + " dollars from your Checking Account.");
            }
        }else if(account_choice == 2) {
            opposite_account = 1;
            if(savings_balance - amount < 0) {
                System.out.println("\nUnable to transfer, not enough money in account.");
                return;     
            } else {
                prev_transactions_notes_savings.add("\nTransaction " + ++prev_transactions_occurred_checking + ": You transferred " + amount + " dollars to your Checking Account.");
                prev_transactions_notes_checking.add("\nTransaction " + ++prev_transactions_occurred_savings + ": You received " + amount + " dollars from your Savings Account.");
            }
        }
        withdraw_from_account(amount, account_choice);
        deposit_into_account(amount, opposite_account);
        System.out.println("\n" + amount + " successfully transferred.");
        }

    public void display_prev_transactions(double amount, int account_choice) {
        int int_amount = (int)amount;
        if(account_choice == 1) {
            if(int_amount > prev_transactions_occurred_checking) {
                int_amount = prev_transactions_occurred_checking;
            }
            for(int i = prev_transactions_occurred_checking-1; i >= prev_transactions_occurred_checking-int_amount; i--) {
                System.out.println(prev_transactions_notes_checking.get(i));
            }
        } else if(account_choice == 2) {
            if(int_amount > prev_transactions_occurred_savings) {
                int_amount = prev_transactions_occurred_savings;
            }
            for(int i = prev_transactions_occurred_savings-1; i >= prev_transactions_occurred_savings-int_amount; i--) {
                System.out.println(prev_transactions_notes_savings.get(i));
            }
        }
    }
}