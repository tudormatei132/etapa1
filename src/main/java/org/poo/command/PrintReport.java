package org.poo.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Account;
import org.poo.transactions.Transaction;

import java.util.ArrayList;

public class PrintReport implements Command {

    private Account account;
    private int start, stop, timestamp;
    private ArrayNode output;
    private ObjectMapper mapper;
    public PrintReport(Account account, int start, int stop,
                       ArrayNode output, ObjectMapper mapper, int timestamp) {
        this.account = account;
        this.start = start;
        this.stop = stop;
        this.output = output;
        this.mapper = mapper;
        this.timestamp = timestamp;
    }


    @Override
    public void execute() {
        if (account == null) {
            return;
        }
        ArrayList<Transaction> transactions = account.getTransactions(start, stop);
        ObjectNode node = mapper.createObjectNode();
        node.put("command", "report");
        ObjectNode details = mapper.createObjectNode();
        details.put("IBAN", account.getIBAN().toString());
        details.put("balance", account.getBalance());
        details.put("currency", account.getCurrency().toString());
        ArrayNode report = mapper.createArrayNode();
        for (Transaction transaction : transactions) {
            report.add(transaction.print(mapper));
        }
        details.put("transactions", report);
        node.put("output", details);
        node.put("timestamp", timestamp);
        output.add(node);
    }
}
