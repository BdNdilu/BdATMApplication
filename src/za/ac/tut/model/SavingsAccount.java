package za.ac.tut.model;

import za.ac.tut.exceptions.InsufficientFundsException;


/**
 * Savings account with minimum balance requirement.
 * Extends Account and overrides withdraw behavior.
 *
 * @author bdndi
 */
public class SavingsAccount extends Account {
    private static final double MINIMUM_BALANCE = 100.00;

    public SavingsAccount(int accountNumber, int pin, double balance, AccountHolder holder) {
        super(accountNumber, pin, balance, holder);
    }

    @Override
    public void withdraw(double amount) throws InsufficientFundsException {
        if (balance - amount < MINIMUM_BALANCE) {
            throw  new InsufficientFundsException("Transaction Halted: Savings criteria requires a minimum of R" + MINIMUM_BALANCE + " to stay in the account.");
        }
        balance -= amount;
    }
    
    @Override
    public String getAccountType() {
        return "Savings Account (Min limit: R" + MINIMUM_BALANCE + ")";
    }

}
