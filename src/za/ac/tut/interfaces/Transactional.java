package za.ac.tut.interfaces;

import za.ac.tut.exceptions.InsufficientFundsException;

/**
 * Transactional interface defines the core banking operations
 * (deposit, withdraw, balance inquiry, and PIN change).
 * 
 * Implemented by account types to ensure consistent behavior.
 *
 * @author bdndi
 */
public interface Transactional {
    void deposit(double amount);
    void withdraw(double amount) throws InsufficientFundsException;
    double checkBalance();
    void changePin(int oldPin, int newPin) throws Exception;
}
