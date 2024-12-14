package org.poo.command;

import org.poo.account.Account;

public class AddFunds implements Command {

    private Account account;
    private double amount;
    public AddFunds(final Account account, final double amount) {
        this.account = account;
        this.amount = amount;
    }

    public void execute() {
        account.addFunds(amount);
    }
}
