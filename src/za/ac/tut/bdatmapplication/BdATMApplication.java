package za.ac.tut.bdatmapplication;

import za.ac.tut.processor.ATM;
import za.ac.tut.model.*;
import za.ac.tut.exceptions.InsufficientFundsException;
import javax.swing.JOptionPane;
/**
 * Main ATM application class.
 * Originally inspired by my TUT lecturer’s banking app example,
 * then customized by me to apply everything I have learned:
 * composition, inheritance, polymorphism, ArrayList collections,
 * interfaces, abstract classes, and exception handling.
 * Provides GUI menu for deposits, withdrawals, balance inquiry,
 * PIN changes, and account summaries.
 * 
 * @author bdndi
 */
public class BdATMApplication {
    
    public static void main(String[] args) {
        ATM atm = new ATM();
        initializeData(atm);   // Load demo accounts into ArrayList
        
        // System initialization
        JOptionPane.showMessageDialog(null, 
            "Bd Corporation Bank ATM Operations Module loaded successfully.", 
            "System Status", JOptionPane.INFORMATION_MESSAGE);
    
        // Main loop - keeps running until user cancels
        while (true) {            
            // Ask user for account number
            String accStr = JOptionPane.showInputDialog(null,"Enter Account ID (Try 101 or 102):\nOr Cancel to shutdown.");
            if (accStr == null) {
                break;            // Exit if cancelled
            }
            
            // Ask for PIN
            String pinStr = JOptionPane.showInputDialog(null, "Enter Security PIN:");
            if (pinStr == null) {
                break;     
            }
            
            try {
                int accNum = Integer.parseInt(accStr);
                int pin = Integer.parseInt(pinStr);
                
                // Authenticate account
                if (atm.authenticate(accNum, pin)) {
                    // welcome msg once login succeeds
                    JOptionPane.showMessageDialog(null,
                        "Welcome " + atm.getCurrentAccount().getHolder().getFullName() + "!",
                   "Access Granted", JOptionPane.INFORMATION_MESSAGE);
                    
                    runTransactionMenu(atm);  // launch menu
                    
                    // After menu finishes, check if user logged out
                    if (atm.getCurrentAccount() == null) {
                        JOptionPane.showMessageDialog(null,
                    "Goodbye! ATM system shutting down.",
                     "System Exit", JOptionPane.INFORMATION_MESSAGE);
                        break; // Exit the main loop completely
                    }
                    
                } else {
                    JOptionPane.showMessageDialog(null,
                 "Authentication Failed: Mismatched identity pair.",
                   "Access blocked", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, 
                    "Format Error: Please input numbers only.", 
                    "Format Warning", JOptionPane.WARNING_MESSAGE);
            }
        }
        JOptionPane.showMessageDialog(null, "ATM system shutting down...");
    }
    
    private static void runTransactionMenu(ATM atm) {
        Account activeAcc = atm.getCurrentAccount();  // Current logged-in account
        int choice = 0;
        
        do {            
            // Menu account details + options
            String menu = "=== BD Corporate Bank ATM ===\n" + 
                    "Profile: " + activeAcc.getHolder().getFullName() + "\n " +
                    "Account Type: " + activeAcc.getAccountType() + "\n" +
                    "Select Operation:\n" +
                    "1 - CASH DEPOSIT\n" + 
                    "2 - CASH WITHDRAWAL\n" +
                    "3 - INQUIRE CURRENT BALANCE\n" + 
                    "4 - PRINT ACCOUNT SUMMARY STATEMENTS\n" + 
                    "5 - CHANGE SECURITY PIN CODE\n" + 
                    "6 - LOGOUT \n\n" +
                    "Enter Choice Selection Number:";
            
            String input = JOptionPane.showInputDialog(null, menu);
            if (input == null) {
                break;
            }
        
            try {
                choice = Integer.parseInt(input);
                switch (choice) {
                    case 1:  // deposit
                        String deposit = JOptionPane.showInputDialog(null,
                                "Enter amount to deposit (R):");
                        if (deposit != null) {
                            try {
                                double depositAmount = Double.parseDouble(deposit);
                                activeAcc.deposit(depositAmount);   // polymorphism,works for any account type
                                 JOptionPane.showMessageDialog(null, 
                                    "Deposit Complete. Added R" + depositAmount + 
                                    "\nNew Balance: R" + activeAcc.checkBalance());
                            } catch (NumberFormatException e) {
                               JOptionPane.showMessageDialog(null,
                             "Error: Invalid number format for deposits.",
                              "Input Error", JOptionPane.ERROR_MESSAGE); 
                            }
                        }
                        break;
                        
                    case 2:
                        String withdrawal = JOptionPane.showInputDialog(null,
                                "Enter amount to withdraw (R):");
                        if (withdrawal != null) {
                            try {
                                double withdrawAmount = Double.parseDouble(withdrawal);
                                activeAcc.withdraw(withdrawAmount);
                                JOptionPane.showMessageDialog(null, 
                                    "Dispensing Notes: R" + withdrawAmount + 
                                    "\nBalance: R" + activeAcc.checkBalance());
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null,
                             "Error: Invalid withdrawal entry.",
                             "Input Error", JOptionPane.ERROR_MESSAGE);
                            } catch (InsufficientFundsException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage(),
                              "Transaction Declined", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                        break;
                        
                    case 3: // Balance inquiry
                        JOptionPane.showMessageDialog(null,
                           "Current Balance:\nR" + activeAcc.checkBalance(),
                      "Balance Inquiry", JOptionPane.INFORMATION_MESSAGE);
                        break;
                        
                    case 4: // Account summary
                        String summary = "=== ACCOUNT STATEMENT ===\n" +
                                         "Holder: " + activeAcc.getHolder().getFullName() + "\n" +
                                         "Account ID: " + activeAcc.getAccountNumber() + "\n" +
                                         "Type: " + activeAcc.getAccountType() + "\n" +
                                         "Balance: R" + activeAcc.checkBalance();
                        JOptionPane.showMessageDialog(null, summary, "Account Statement",
                                JOptionPane.INFORMATION_MESSAGE);
                        break;
                        
                    case 5: // PIN change 
                        String oldPinStr = JOptionPane.showInputDialog(null, "Enter current PIN:");
                        if (oldPinStr == null) {
                            break;
                        }
                        
                        String newPinStr = JOptionPane.showInputDialog(null, "Enter new PIN:");
                        if (newPinStr == null) {
                            break;
                        }
                        
                        try {
                            int oldPin = Integer.parseInt(oldPinStr);
                            int newPin = Integer.parseInt(newPinStr);
                            activeAcc.changePin(oldPin, newPin); // 🔹 Interface method implemented in Account
                            JOptionPane.showMessageDialog(null, "PIN updated successfully.");
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Error: PIN must be numeric.", "Format Error", JOptionPane.ERROR_MESSAGE);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e.getMessage(), "Security Authorization Denied", JOptionPane.WARNING_MESSAGE);
                        }
                        break;
                    
                    case 6: //  logout
                        atm.logOut();
                        JOptionPane.showMessageDialog(null, "Profile logged out successfully.");
                        break;
                        
                    default:
                        JOptionPane.showMessageDialog(null, "Invalid menu option.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Menu selection requires numeric input.", "Selection Error", JOptionPane.ERROR_MESSAGE);
                choice = 0; 
            }
        
        } while (choice != 6);
    }
    
    //create demo accounts
    private static void initializeData(ATM atm) {
        AccountHolder holder1 = new AccountHolder("Bodrick", "Ndilu");
        AccountHolder holder2 = new AccountHolder("Marie-Therese", "Mateso");
        
        atm.addAccount(new CheckingAccount(101, 1234, 1500.00, holder1));
        atm.addAccount(new SavingsAccount(102, 1234, 2500.00, holder2));
    }
}
