package za.ac.tut.model;

import za.ac.tut.exceptions.InsufficientFundsException;
/**
 * Checking account with transaction fee applied on withdrawals.
 * Extends Account and overrides withdraw behavior.
 * 
 * @author bdndi
 */
public class CheckingAccount extends Account {
    public static final double TRANSACTION_FEE = 2.50;

    public CheckingAccount(int accountNumber, int pin, double balance, AccountHolder holder) {
        super(accountNumber, pin, balance, holder);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        double totalDeduction = amount + TRANSACTION_FEE;
        if (totalDeduction > balance) {
            throw new InsufficientFundsException("Transaction Failed: Insufficient funds for amount + R" + TRANSACTION_FEE + " fee.");
        }
        balance -= totalDeduction;
    }

    @Override
    public String getAccountType() {
        return "Checking Accoint (Fee: R" + TRANSACTION_FEE + ")";
    }
    
    
}
