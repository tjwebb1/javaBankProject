import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CheckingAccount{
    private double checkingBalance;
    private List<String> checkingNotesForPrevTransactions = new ArrayList<>();
    private int checkingPrevTransactionsPerformed = 0;
    private DecimalFormat df = new DecimalFormat("#0.00");
    private static Scanner input = new Scanner(System.in);
    private String invalid = "\nInvalid input, please enter a valid number.";
    private Bank bank;

    public CheckingAccount() {
        //
    }

    public CheckingAccount(Bank bank) {
        this.bank = bank;
    }
    
    public void setCheckingBalance(double checkingBalance) {
        this.checkingBalance = checkingBalance;
    }

    public double getCheckingBalance() {
        return checkingBalance;
    }

    public void setCheckingNotesForPrevTransactions(double amount, String type) { // added type parameter to each setter to make sure text info is accurate in display
        if(type.equals("transferTo")) {
            checkingNotesForPrevTransactions.add("\nTransaction " + ++checkingPrevTransactionsPerformed + ": You transferred $" + df.format(amount) + " dollars to your Savings Account.");
        } else if (type.equals("transferFrom")) {
            checkingNotesForPrevTransactions.add("\nTransaction " + ++checkingPrevTransactionsPerformed + ": You received $" + df.format(amount) + " dollars from your Savings Account.");
        } else if (type.equals("withdraw")) {
            checkingNotesForPrevTransactions.add("\nTransaction " + ++checkingPrevTransactionsPerformed + ": You withdrew $" + df.format(amount) + " dollars from this account.");
        } else if (type.equals("deposit")) {
            checkingNotesForPrevTransactions.add("\nTransaction " + ++checkingPrevTransactionsPerformed + ": You deposited $" + df.format(amount) + " dollars into this account.");
        }
    }


    public void checkingAccountInfo() {
        System.out.println("\n--------------------------------------------------------------");
        System.out.println("Account Type: Checking Account");
        System.out.println("Current user: " + bank.getClientName());
        System.out.println("Current balance: $" + df.format(getCheckingBalance()));
        System.out.println("Amount of previous transactions: " + checkingPrevTransactionsPerformed);
        System.out.println("--------------------------------------------------------------");
    }

    public int checkingAccountOptions() {
        try{
            checkingAccountInfo();
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

    public void checkingAccountOptionsSwitch() {
        Boolean done = false;
        do{
            int choice = checkingAccountOptions();
            double amount;
            try{
                switch(choice) {
                    case 1: System.out.print("\nPlease enter amount you wish to deposit: ");
                            amount = Double.parseDouble(input.nextLine());
                            amount = checkAmount(amount);
                            if(amount == 0) {
                                break;
                            }
                            depositIntoCheckingAccount(amount, 0);                          // added parameter transferCheck to deposit and withdraw, to avoid multiple
                            break;                                                          // transactions being counted when transferring between accounts.
                    case 2: System.out.print("\nPlease enter amount you wish to withdraw: ");
                            amount = Double.parseDouble(input.nextLine());
                            amount = checkAmount(amount);
                            if(amount == 0) {
                                break;
                            }
                            withdrawFromCheckingAccount(amount, 0);
                            break;
                    case 3: System.out.print("\nPlease enter amount you wish to transfer: ");
                            amount = Double.parseDouble(input.nextLine());
                            amount = checkAmount(amount);
                            if(amount == 0) {
                                break;
                            }
                            transferToSavingsAccount(amount, 1);
                            break;
                    case 4: System.out.print("\nPlease enter amount of transactions to see: ");
                            amount = Integer.parseInt(input.nextLine());
                            amount = checkAmount(amount);
                            if(amount == 0) {
                                break;
                            }
                            displayCheckingPrevTransactions(amount);
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

    public void depositIntoCheckingAccount(double amount, int transferCheck) {
        checkingBalance += amount;
        if(transferCheck!=1){
            setCheckingNotesForPrevTransactions(amount, "deposit");
        }
    }

    public void withdrawFromCheckingAccount(double amount, int transferCheck) {
        if(checkingBalance - amount < 0) {
            System.out.println("\nUnable to withdraw, not enough money in account.");
        } else {
            checkingBalance -= amount;
            if(transferCheck!=1){
                System.out.println("\n$" + df.format(amount) + " successfully withdrawn.");
                setCheckingNotesForPrevTransactions(amount, "withdraw");
            }
        }
    }

    public void transferToSavingsAccount(double amount, int accountChoice) {
        if(checkingBalance - amount < 0) {
            System.out.println("\nUnable to transfer, not enough money in account.");
            return;
        } else {
            setCheckingNotesForPrevTransactions(amount, "transferTo");
            bank.savingsAccount.setSavingsNotesForPrevTransactions(amount, "transferFrom");
        }
        withdrawFromCheckingAccount(amount, 1);
        bank.savingsAccount.depositIntoSavingsAccount(amount, 1);
        System.out.println("\n$" + df.format(amount) + " successfully transferred.");
        }

    public void displayCheckingPrevTransactions(double amount) {
        int intAmount = (int)amount;
        System.out.println("--------------------------------------------------------------");
        if(intAmount > checkingPrevTransactionsPerformed) {
            intAmount = checkingPrevTransactionsPerformed;
        }
        for(int i = checkingPrevTransactionsPerformed-1; i >= checkingPrevTransactionsPerformed-intAmount; i--) {
            System.out.println(checkingNotesForPrevTransactions.get(i));
        }
    }
}