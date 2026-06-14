package za.ac.tut.model;

import za.ac.tut.interfaces.Transactional;
import za.ac.tut.exceptions.InsufficientFundsException;

/**
 * Abstract base class for all account types.
 * Implements Transactional interface and enforces core banking behavior.
 * Uses composition with AccountHolder.
 *
 * @author bdndi
 */
public abstract class Account implements Transactional {
    protected int accountNumber;
    protected int pin;
    protected double balance;
    protected AccountHolder holder;

    public Account(int accountNumber, int pin, double balance, AccountHolder holder) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.holder = holder;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public AccountHolder getHolder() {
        return holder;
    }
    
    public boolean validatePin(int inputPin) {
        return this.pin == inputPin;
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    @Override
    public double checkBalance() {
        return this.balance;
    }

    @Override
    public void changePin(int oldPin, int newPin) throws Exception {
        if (this.pin != oldPin) {
           throw new Exception("PIN change failed: The current PIN you entered is incorrect.");
        }
        this.pin = newPin;
    }
    
    public abstract String getAccountType();
}
