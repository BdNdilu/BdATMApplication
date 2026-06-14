package za.ac.tut.processor;

import java.util.ArrayList;
import java.util.List;
import za.ac.tut.model.Account;
/**
 * ATM processor class.
 * Manages account storage, authentication, and session handling
 * 
 * @author bdndi
 */
public class ATM {
    private List<Account> accountDatabase;
    private Account currentAthenticatedAccount;

    public ATM() {
        this.accountDatabase = new ArrayList<>();
        this.currentAthenticatedAccount = null;
    }
    
    public void addAccount(Account account) {
        accountDatabase.add(account);
    }
       
    public boolean authenticate(int accNum, int pin) {
        for (Account acc : accountDatabase) {
            if (acc.getAccountNumber() == accNum && acc.validatePin(pin)) {
                currentAthenticatedAccount = acc;
                return true;
            }
        }
        return false;
    }
    
    public Account getCurrentAccount() {
        return currentAthenticatedAccount;
    }
    
    public void logOut() {
        currentAthenticatedAccount = null;
    }
}
