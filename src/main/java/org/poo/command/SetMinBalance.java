package org.poo.command;

import org.poo.account.Account;
import org.poo.fileio.CommandInput;

import java.util.HashMap;

public class SetMinBalance implements Command {

    private CommandInput action;
    private HashMap<String, Account> map;
    private int amount;

    public SetMinBalance(final CommandInput action, final HashMap<String, Account> map, final int amount) {
        this.action = action;
        this.map = map;
        this.amount = amount;
    }

    @Override
    public void execute() {
        Account temp = map.get(action.getAccount());
        temp.setMinBalance(amount);
    }
}
