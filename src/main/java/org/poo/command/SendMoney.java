package org.poo.command;

import org.poo.account.Account;
import org.poo.system.Converter;
import org.poo.transactions.Transfer;

public class SendMoney implements Command {

    private String description, email;
    private Account sender, receiver;
    private double amount;
    private int timestamp;
    private Converter converter;
    public SendMoney(String description, String email, Account sender, Account receiver,
                     double amount, int timestamp, Converter converter) {
        this.description = description;
        this.email = email;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.timestamp = timestamp;
        this.converter = converter;
    }

    public void execute() {
        if (sender == null) {
            return;
        }

        if (receiver == null) {
            return;
        }

        if (!sender.getUser().getEmail().toString().equals(email)) {
            // error
            return;
        }

        if (amount > sender.getBalance()) {

            return;
        }

        double received = amount * converter.convert(sender.getCurrency().toString(),
                                                     receiver.getCurrency().toString());
        Transfer transfer = new Transfer(timestamp, description,
                Double.toString(amount) + " " + sender.getCurrency(), "sent",
                sender.getIBAN().toString(), receiver.getIBAN().toString());
        sender.getTransactions().add(transfer);
        sender.getUser().getTransactions().add(transfer);
        sender.addFunds(-amount);
        receiver.addFunds(received);

    }
}
