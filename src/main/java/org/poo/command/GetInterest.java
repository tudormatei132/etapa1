package org.poo.command;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Account;
import org.poo.account.SavingsAccount;
import org.poo.transactions.Transaction;

public class GetInterest implements Command {

    private Account savingsAccount;
    private int timestamp;
    private ArrayNode output;
    private ObjectMapper mapper;

    public GetInterest(Account savingsAccount, int timestamp, ArrayNode output, ObjectMapper mapper) {
        this.savingsAccount = savingsAccount;
        this.timestamp = timestamp;
        this.output = output;
        this.mapper = mapper;
    }

    @Override
    public void execute() {
        if (savingsAccount == null) {
            return;
        }

        if (!savingsAccount.getType().toString().equals("savings")) {
            ObjectNode error = mapper.createObjectNode();
            error.put("command", "addInterest");
            ObjectNode errorOutput = mapper.createObjectNode();
            errorOutput.put("timestamp", timestamp);
            errorOutput.put("description", "This is not a savings account");
            error.put("output", errorOutput);
            error.put("timestamp", timestamp);
            output.add(error);
            return;
        }

        SavingsAccount account = (SavingsAccount) savingsAccount;
        account.addFunds(account.getInterestRate() * account.getBalance());
    }
}
