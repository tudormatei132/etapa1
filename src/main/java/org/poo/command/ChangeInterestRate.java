package org.poo.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.account.Account;
import org.poo.errors.Log;
import org.poo.transactions.Transaction;

public class ChangeInterestRate implements Command {


    private Account account;
    private double newRate;
    private int timestamp;
    private ArrayNode output;
    private ObjectMapper mapper;

    public ChangeInterestRate(Account account, double newRate, int timestamp,
                              ArrayNode output, ObjectMapper mapper) {
        this.account = account;
        this.newRate = newRate;
        this.timestamp = timestamp;
        this.output = output;
        this.mapper = mapper;
    }

    @Override
    public void execute() {
        if (account == null) {
            return;
        }



        if (account.setInterestRate(newRate) == -1) {
            Log log = new Log.Builder("changeInterestRate", timestamp).
                    detailsTimestamp(timestamp).
                    description("This is not a savings account").build();

            output.add(log.print(mapper));
            return;
        }

        Transaction newInterestRate = new Transaction(timestamp, "Interest rate of " +
                "the account changed to " + newRate);
        account.getTransactions().add(newInterestRate);
        account.getUser().getTransactions().add(newInterestRate);

    }
}
