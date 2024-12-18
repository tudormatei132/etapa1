package org.poo.command;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.account.Account;
import org.poo.account.SavingsAccount;
import org.poo.errors.Log;

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
            Log log = new Log.Builder("addInterest", timestamp).
                    detailsTimestamp(timestamp).
                    description("This is not a savings account").build();
            output.add(log.print(mapper));
            return;
        }

        SavingsAccount account = (SavingsAccount) savingsAccount;
        account.addFunds(account.getInterestRate() * account.getBalance());
    }
}
