package org.poo.command;

import org.poo.account.Account;
import org.poo.account.User;
import org.poo.errors.Log;

import java.util.HashMap;

public class SetAlias implements Command {

    private String alias;
    private Account account;
    private User user;
    public SetAlias(String alias, Account account, User user) {
        this.alias = alias;
        this.account = account;
        this.user = user;

    }


    public void execute() {
        if (account == null) {
            return;
        }
        user.getAliases().put(alias, account);
    }
}
