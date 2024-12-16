package org.poo.command;

import org.poo.account.Account;
import org.poo.account.User;
import org.poo.system.Converter;
import org.poo.transactions.Transaction;
import org.poo.transactions.Transfer;

import java.util.HashMap;

public class SendMoney implements Command {

    private String description;
    private User user;
    private double amount;
    private int timestamp;
    private Converter converter;
    private String sender, receiver;
    private HashMap<String, Account> accountMap;

    public SendMoney(String description, User user, double amount, Converter converter,
                     String sender, String receiver, int timestamp, HashMap<String, Account> accountMap) {
        this.description = description;
        this.user = user;
        this.amount = amount;
        this.converter = converter;
        this.sender = sender;
        this.receiver = receiver;
        this.timestamp = timestamp;
        this.accountMap = accountMap;
    }

    public void execute() {
        // get sender
//        Account senderAccount = user.getAliases().get(sender);
//        if (senderAccount == null) {
//            senderAccount = accountMap.get(sender);
//            if (senderAccount == null) {
//                System.out.println("doesn't exist");
//                return;
//            }
//        }
        Account senderAccount = accountMap.get(sender);
        if (senderAccount == null) {
            return;
        }
        Account receiverAccount = user.getAliases().get(receiver);
        if (receiverAccount == null) {
            receiverAccount = accountMap.get(receiver);
            if (receiverAccount == null) {
                System.out.println("doesn't exist");

                return;
            }
        }





        if (!senderAccount.getUser().getEmail().toString().equals(user.getEmail().toString())) {

            return;
        }

        if (senderAccount.getBalance() - senderAccount.getMinBalance() < amount) {
            Transaction transaction = new Transaction(timestamp, "Insufficient funds");
            senderAccount.getUser().getTransactions().add(transaction);
            senderAccount.getTransactions().add(transaction);
            return;
        }

        senderAccount.addFunds(-amount);
        receiverAccount.addFunds(amount * converter.convert(senderAccount.getCurrency().toString(),
                receiverAccount.getCurrency().toString()));

        Transfer transfer = new Transfer(timestamp, description,
                Double.toString(amount) + " " + senderAccount.getCurrency(), "sent",
                senderAccount.getIBAN().toString(), receiverAccount.getIBAN().toString());
        senderAccount.getTransactions().add(transfer);
        senderAccount.getUser().getTransactions().add(transfer);

    }
}
