import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SavingsAccount{
    private double savingsBalance;
    private List<String> savingsNotesForPrevTransactions = new ArrayList<>();
    private int savingsPrevTransactionsPerformed = 0;
    private DecimalFormat df = new DecimalFormat("#0.00");
    private static Scanner input = new Scanner(System.in);
    private String invalid = "\nInvalid input, please enter a valid number.";
    private Bank bank;

    public SavingsAccount() {
        //
    }

    public SavingsAccount(Bank bank) {
        this.bank = bank;
    }

    public void setSavingsBalance(double savingsBalance) {
        this.savingsBalance = savingsBalance;
    }

    public double getSavingsBalance() {
        return savingsBalance;
    }

    public void setSavingsNotesForPrevTransactions(double amount, String type) { // added type parameter to each setter to make sure text info is accurate in display
        if(type.equals("transferTo")) {
            savingsNotesForPrevTransactions.add("\nTransaction " + ++savingsPrevTransactionsPerformed + ": You transferred $" + df.format(amount) + " dollars to your Checking Account.");
        } else if (type.equals("transferFrom")) {
            savingsNotesForPrevTransactions.add("\nTransaction " + ++savingsPrevTransactionsPerformed + ": You received $" + df.format(amount) + " dollars from your Checking Account.");
        } else if (type.equals("withdraw")) {
            savingsNotesForPrevTransactions.add("\nTransaction " + ++savingsPrevTransactionsPerformed + ": You withdrew $" + df.format(amount) + " dollars from this account.");
        } else if (type.equals("deposit")) {
            savingsNotesForPrevTransactions.add("\nTransaction " + ++savingsPrevTransactionsPerformed + ": You deposited $" + df.format(amount) + " dollars into this account.");
        }
    }


    public void savingsAccountInfo() {
        System.out.println("\n--------------------------------------------------------------");
        System.out.println("Account Type: Savings Account");
        System.out.println("Current user: " + bank.getClientName());
        System.out.println("Current balance: $" + df.format(getSavingsBalance()));
        System.out.println("Amount of previous transactions: " + savingsPrevTransactionsPerformed);
        System.out.println("--------------------------------------------------------------");
    }

    public int savingsAccountOptions() {
        try{
            savingsAccountInfo();
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

    public void savingsAccountOptionsSwitch() {
        Boolean done = false;
        do{
            int choice = savingsAccountOptions();
            double amount;
            try{
                switch(choice) {
                    case 1: System.out.print("\nPlease enter amount you wish to deposit: ");
                            amount = Double.parseDouble(input.nextLine());
                            amount = checkAmount(amount);
                            if(amount == 0) {
                                break;
                            }
                            depositIntoSavingsAccount(amount, 0);               // added 2nd parameter transferCheck to deposit and withdraw, to avoid multiple
                            break;                                                            // transactions in display notes being counted when transferring between accounts.
                    case 2: System.out.print("\nPlease enter amount you wish to withdraw: ");
                            amount = Double.parseDouble(input.nextLine());
                            amount = checkAmount(amount);
                            if(amount == 0) {
                                break;
                            }
                            withdrawFromSavingsAccount(amount, 0);
                            break;
                    case 3: System.out.print("\nPlease enter amount you wish to transfer: ");
                            amount = Double.parseDouble(input.nextLine());
                            amount = checkAmount(amount);
                            if(amount == 0) {
                                break;
                            }
                            transferToCheckingAccount(amount, 1);
                            break;
                    case 4: System.out.print("\nPlease enter amount of transactions to see: ");
                            amount = Integer.parseInt(input.nextLine());
                            amount = checkAmount(amount);
                            if(amount == 0) {
                                break;
                            }
                            displaySavingsPrevTransactions(amount);
                            break;
                    case 5: done = true;
                            break;
                    default: System.out.println(invalid);
                            break;
                }
            } catch(Exception e) {
                System.out.println(invalid);
            }
        }while(Boolean.FALSE.equals((done)));
    }

    public double checkAmount(double amount) {
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

    public void depositIntoSavingsAccount(double amount, int transferCheck) {
        savingsBalance += amount;
        if(transferCheck!=1){
            setSavingsNotesForPrevTransactions(amount, "deposit");
        }
    }

    public void withdrawFromSavingsAccount(double amount, int transferCheck) {
        if(savingsBalance - amount < 0) {
            System.out.println("\nUnable to withdraw, not enough money in account.");
        } else {
            savingsBalance -= amount;
            if(transferCheck!=1){
                System.out.println("\n$" + df.format(amount) + " successfully withdrawn.");
                setSavingsNotesForPrevTransactions(amount, "withdraw");
            }
        }
    }

    public void transferToCheckingAccount(double amount, int accountChoice) {
        if(savingsBalance - amount < 0) {
            System.out.println("\nUnable to transfer, not enough money in account.");
            return;
        } else {
            setSavingsNotesForPrevTransactions(amount, "transferTo");
            bank.checkingAccount.setCheckingNotesForPrevTransactions(amount, "transferFrom");
        }
        withdrawFromSavingsAccount(amount, 1);
        bank.checkingAccount.depositIntoCheckingAccount(amount, 1);
        System.out.println("\n$" + df.format(amount) + " successfully transferred.");
        }


        public void displaySavingsPrevTransactions(double amount) {
            int intAmount = (int)amount;
            System.out.println("--------------------------------------------------------------");
            if(intAmount > savingsPrevTransactionsPerformed) {
                intAmount = savingsPrevTransactionsPerformed;
            }
            for(int i = savingsPrevTransactionsPerformed-1; i >= savingsPrevTransactionsPerformed-intAmount; i--) {
                System.out.println(savingsNotesForPrevTransactions.get(i));
            }
        }
}