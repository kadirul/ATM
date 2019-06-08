

public class TransferFunds extends Transaction {

    private double amount; // amount to deposit
    private Keypad keypad; // reference to keypad
    private DepositSlot depositSlot; // reference to deposit slot
    private final static int CANCELED = 0; // constant for cancel option

    // Deposit constructor
    public TransferFunds(int userAccountNumber, Screen atmScreen,
            BankDatabase atmBankDatabase, Keypad atmKeypad,
            DepositSlot atmDepositSlot) {
        // initialize superclass variables
        super(userAccountNumber, atmScreen, atmBankDatabase);

        // initialize references to keypad and deposit slot
        keypad = atmKeypad;
        depositSlot = atmDepositSlot;
    } // end Deposit constructor

    // perform transaction
    public void execute() {
        BankDatabase bankDatabase = getBankDatabase(); // get reference
        Screen screen = getScreen(); // get reference

        amount = promptForDepositAmount(); // get  amount from user

        // check whether user entered a deposit amount or canceled
        if (amount != CANCELED) {

            screen.displayDollarAmount(amount);
            screen.displayMessageLine(" has transfered to checking account.");

            // credit account to reflect the deposit
            bankDatabase.debit(getAccountNumber(), amount);
            if(getAccountNumber()==12345){
                bankDatabase.credit(98765, amount);
            }else{
                bankDatabase.credit(12345, amount);
            }
//            bankDatabase.debit(getAccountNumber(), amount);
            // end if

        } // end if
        else // user canceled instead of entering amount
        {
            screen.displayMessageLine("\nCanceling transaction...");
        } // end else
    } // end method execute

    // prompt user to enter a deposit amount in cents
    private double promptForDepositAmount() {
        Screen screen = getScreen(); // get reference to screen

        // display the prompt
        screen.displayMessage("\nPlease enter an amount in "
                + "CENTS (or 0 to cancel): ");
        int input = keypad.getInput(); // receive input of  amount

        // check whether the user canceled or entered a valid amount
        if (input == CANCELED) {
            return CANCELED;
        } else {
            return (double) input / 100; // return dollar amount
        } // end else
    } // 
} // end class 

