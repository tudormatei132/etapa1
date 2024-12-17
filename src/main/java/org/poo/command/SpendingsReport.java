package org.poo.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Account;
import org.poo.transactions.Payment;

import java.util.*;

public class SpendingsReport implements Command {

    private Account account;
    private int start, end, timestamp;
    private ArrayNode output;
    private ObjectMapper mapper;
    public SpendingsReport(Account account, int start, int end, ArrayNode output,
                           ObjectMapper mapper, int timestamp) {
        this.account = account;
        this.start = start;
        this.end = end;
        this.output = output;
        this.mapper = mapper;
        this.timestamp = timestamp;
    }


    @Override
    public void execute() {
        if (account == null) {
            ObjectNode error = mapper.createObjectNode();
            ObjectNode details = mapper.createObjectNode();
            error.put("command", "spendingsReport");
            details.put("description", "Account not found");
            details.put("timestamp", timestamp);
            error.put("output", details);
            error.put("timestamp", timestamp);
            output.add(error);
            return;
        }

        if (account.getType().toString().equals("savings")) {
            ObjectNode error = mapper.createObjectNode();
            error.put("command", "spendingsReport");
            ObjectNode details = mapper.createObjectNode();
            details.put("error", "This kind of report is not supported for a saving account");
            error.put("output", details);
            error.put("timestamp", timestamp);
            output.add(error);
            return;
        }

        ArrayList<Payment> payments = account.getPayments(start, end);
        HashMap<String, Double> spendings = new HashMap<>();
        ArrayList<CommerciantSpendings> commerciants = new ArrayList<>();
        for (Payment payment : payments) {
            if (spendings.containsKey(payment.getCommerciant())) {
                double new_amount = spendings.get(payment.getCommerciant()) + payment.getAmount();
                spendings.put(payment.getCommerciant(), new_amount);
            }
            else {
                spendings.put(payment.getCommerciant(), payment.getAmount());
            }
        }

        for (Map.Entry<String, Double> entry : spendings.entrySet()) {
            commerciants.add(new CommerciantSpendings(entry.getKey(), entry.getValue()));
        }

        Collections.sort(commerciants);

        ObjectNode report = mapper.createObjectNode();
        report.put("command", "spendingsReport");
        ObjectNode accountDetails = mapper.createObjectNode();
        accountDetails.put("IBAN", account.getIBAN().toString());
        accountDetails.put("balance", account.getBalance());
        accountDetails.put("currency", account.getCurrency().toString());
        ArrayNode transactions = mapper.createArrayNode();

        for (Payment payment : payments) {
            transactions.add(payment.print(mapper));
        }

        accountDetails.put("transactions", transactions);

        ArrayNode totalSpendings = mapper.createArrayNode();
        for (CommerciantSpendings spending : commerciants) {
            totalSpendings.add(spending.print(mapper));
        }

        accountDetails.put("commerciants", totalSpendings);
        report.put("output", accountDetails);
        report.put("timestamp", timestamp);
        output.add(report);
    }
}
