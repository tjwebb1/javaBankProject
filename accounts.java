import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Accounts{
    private double checkingBalance;
    private double savingsBalance;
    private List<String> checkingNotesForPrevTransactions = new ArrayList<>();
    private List<String> savingsNotesForPrevTransactions = new ArrayList<>();
    private int checkingPrevTransactionsPerformed = 0;
    private int savingsPrevTransactionsPerformed = 0;
    private DecimalFormat df = new DecimalFormat("#0.00");
    private static Scanner input = new Scanner(System.in);
    private String invalid = "\nInvalid input, please enter a valid number.";
    private Bank bank;

    public Accounts() {
        //
    }

    public Accounts(Bank bank) {
        this.bank = bank;
    }
    
    public void setCheckingBalance(double checkingBalance) {
        this.checkingBalance = checkingBalance;
    }

    public void setSavingsBalance(double savingsBalance) {
        this.savingsBalance = savingsBalance;
    }

    public double getCheckingBalance() {
        return checkingBalance;
    }

    public double getSavingsBalance() {
        return savingsBalance;
    }

    public void accountInfo(int accountChoice) {
        System.out.println("\n--------------------------------------------------------------");
        System.out.println("Current user: " + bank.getClientName());
        if(accountChoice == 1) {
            System.out.println("Current balance: $" + df.format(getCheckingBalance()));
            System.out.println("Amount of previous transactions: " + checkingPrevTransactionsPerformed);
            System.out.println("Account Type: Checking Account");
        } else if(accountChoice == 2) {
            System.out.println("Current balance: $" + df.format(getSavingsBalance()));
            System.out.println("Amount of previous transactions: " + savingsPrevTransactionsPerformed);
            System.out.println("Account Type: Savings Account");
        }
        System.out.println("--------------------------------------------------------------");
    }

    public int accountOptions(int accountChoice) {
        try{
            accountInfo(accountChoice);
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

    public void accountOptionsSwitch(int accountChoice) {
        Boolean done = false;
        do{
            int choice = accountOptions(accountChoice);
            double amount;
            try{
                switch(choice) {
                    case 1: System.out.print("\nPlease enter amount you wish to deposit: ");
                            amount = Double.parseDouble(input.nextLine());
                            amount = checkAmount(amount);
                            if(amount == 0) {
                                break;
                            }
                            depositIntoAccount(amount, accountChoice, 0); // added 3rd parameter transfer_check to deposit and withdraw, to avoid multiple
                            break;                                                          // transactions being counted when transferring between accounts.
                    case 2: System.out.print("\nPlease enter amount you wish to withdraw: ");
                            amount = Double.parseDouble(input.nextLine());
                            amount = checkAmount(amount);
                            if(amount == 0) {
                                break;
                            }
                            withdrawFromAccount(amount, accountChoice, 0);
                            break;
                    case 3: System.out.print("\nPlease enter amount you wish to transfer: ");
                            amount = Double.parseDouble(input.nextLine());
                            amount = checkAmount(amount);
                            if(amount == 0) {
                                break;
                            }
                            transferToOtherAccount(amount, accountChoice);
                            break;
                    case 4: System.out.print("\nPlease enter amount of transactions to see: ");
                            amount = Integer.parseInt(input.nextLine());
                            amount = checkAmount(amount);
                            if(amount == 0) {
                                break;
                            }
                            displayPrevTransactions(amount, accountChoice);
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

    public void depositIntoAccount(double amount, int accountChoice, int transferCheck) {
        if(accountChoice == 1) {
            checkingBalance += amount;
            if(transferCheck!=1){
                checkingNotesForPrevTransactions.add("\nTransaction " + ++checkingPrevTransactionsPerformed + ": You deposited $" + df.format(amount) + " dollars into this account.");
            }
        } else if(accountChoice == 2) {
            savingsBalance += amount;
            if(transferCheck!=1){
                savingsNotesForPrevTransactions.add("\nTransaction " + ++savingsPrevTransactionsPerformed + ": You deposited $" + df.format(amount) + " dollars into this account.");
            }
        }
    }

    public void withdrawFromAccount(double amount, int accountChoice, int transferCheck) {
        if(accountChoice == 1) {
            if(checkingBalance - amount < 0) {
                System.out.println("\nUnable to withdraw, not enough money in account.");
            } else {
                checkingBalance -= amount;
                if(transferCheck!=1){
                    System.out.println("\n$" + df.format(amount) + " successfully withdrawn.");
                    checkingNotesForPrevTransactions.add("\nTransaction " + ++checkingPrevTransactionsPerformed + ": You withdrew $" + df.format(amount) + " dollars from this account.");
                }
            }
        } else if(accountChoice == 2) {
            if(savingsBalance - amount < 0) {
                System.out.println("\nUnable to withdraw, not enough money in account.");
            } else {
                savingsBalance -= amount;
                if(transferCheck!=1){
                    System.out.println("\n$" + df.format(amount) + " successfully withdrawn.");
                    savingsNotesForPrevTransactions.add("\nTransaction " + ++savingsPrevTransactionsPerformed + ": You withdrew $" + df.format(amount) + " dollars from this account.");
                }
            }
        }
    }

    public void transferToOtherAccount(double amount, int accountChoice) {
        int oppositeAccount = 0;
        if(accountChoice == 1) {
            oppositeAccount = 2;
            if(checkingBalance - amount < 0) {
                System.out.println("\nUnable to transfer, not enough money in account.");
                return;
            } else {
                checkingNotesForPrevTransactions.add("\nTransaction " + ++checkingPrevTransactionsPerformed + ": You transferred $" + df.format(amount) + " dollars to your Savings Account.");
                savingsNotesForPrevTransactions.add("\nTransaction " + ++savingsPrevTransactionsPerformed + ": You received $" + df.format(amount) + " dollars from your Checking Account.");
            }
        }else if(accountChoice == 2) {
            oppositeAccount = 1;
            if(savingsBalance - amount < 0) {
                System.out.println("\nUnable to transfer, not enough money in account.");
                return;     
            } else {
                savingsNotesForPrevTransactions.add("\nTransaction " + ++checkingPrevTransactionsPerformed + ": You transferred $" + df.format(amount) + " dollars to your Checking Account.");
                checkingNotesForPrevTransactions.add("\nTransaction " + ++savingsPrevTransactionsPerformed + ": You received $" + df.format(amount) + " dollars from your Savings Account.");
            }
        }
        withdrawFromAccount(amount, accountChoice, 1);
        depositIntoAccount(amount, oppositeAccount, 1);
        System.out.println("\n$" + df.format(amount) + " successfully transferred.");
        }

    public void displayPrevTransactions(double amount, int accountChoice) {
        int intAmount = (int)amount;
        System.out.println("--------------------------------------------------------------");
        if(accountChoice == 1) {
            if(intAmount > checkingPrevTransactionsPerformed) {
                intAmount = checkingPrevTransactionsPerformed;
            }
            for(int i = checkingPrevTransactionsPerformed-1; i >= checkingPrevTransactionsPerformed-intAmount; i--) {
                System.out.println(checkingNotesForPrevTransactions.get(i));
            }
        } else if(accountChoice == 2) {
            if(intAmount > savingsPrevTransactionsPerformed) {
                intAmount = savingsPrevTransactionsPerformed;
            }
            for(int i = savingsPrevTransactionsPerformed-1; i >= savingsPrevTransactionsPerformed-intAmount; i--) {
                System.out.println(savingsNotesForPrevTransactions.get(i));
            }
        }
    }
}