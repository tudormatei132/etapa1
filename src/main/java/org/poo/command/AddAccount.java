package org.poo.command;

import org.poo.account.Account;
import org.poo.account.ClassicAccount;
import org.poo.account.SavingsAccount;
import org.poo.account.User;
import org.poo.fileio.CommandInput;
import org.poo.transactions.AccountCreation;
import org.poo.utils.Utils;

import java.util.HashMap;

public class AddAccount implements  Command {


    private CommandInput command;
    private HashMap<String, User> userMap;
    private HashMap<String, Account> map;
    public AddAccount(final CommandInput action, final HashMap<String, User> userMap,
                      final HashMap<String, Account> map) {
        command = action;
        this.userMap = userMap;
        this.map = map;
    }


    @Override
    public void execute() {
        User temp = userMap.get(command.getEmail());
        Account account;
        if (command.getAccountType().equals("classic")) {
            account = new ClassicAccount(temp,
                    new StringBuilder(Utils.generateIBAN()),
                    new StringBuilder(command.getCurrency()),
                    command.getInterestRate());
        } else {
            account = new SavingsAccount(temp,
                    new StringBuilder(Utils.generateIBAN()),
                    new StringBuilder(command.getCurrency()),
                    command.getInterestRate());
        }

        temp.addAccount(account);
        System.out.println(account.getIBAN().toString());
        String IBAN = account.getIBAN().toString();
        map.put(IBAN, account);
        AccountCreation creation = new AccountCreation(command.getTimestamp());
        account.getTransactions().add(creation);
        userMap.get(command.getEmail()).getTransactions().add(creation);
    }
}
