package org.poo.command;

import org.poo.account.Account;

public class SetMinimumBalance implements Command {

    private double minBalance;
    private Account account;

    public SetMinimumBalance(double minBalance, Account account) {
        this.minBalance = minBalance;
        this.account = account;
    }

    @Override
    public void execute() {
        if (account == null) {
            return;
        }
        account.setMinBalance(minBalance);
    }
}
