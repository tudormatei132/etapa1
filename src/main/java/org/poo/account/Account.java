package org.poo.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.poo.transactions.Payment;
import org.poo.transactions.Transaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.ArrayList;

@Getter
@Setter
public class Account {
    private User user;
    private StringBuilder IBAN;
    private StringBuilder currency;
    private StringBuilder type;
    private double interestRate;
    private double balance;
    private ArrayList<Card> cards;
    private double minBalance;
    private ArrayList<Transaction> transactions;
    private ArrayList<Payment> payments;
    private HashMap<String, Double> moneySpent;
    public Account(final User user, final StringBuilder IBAN, final StringBuilder currency,
                   final double interestRate) {
        this.user = user;
        this.IBAN = IBAN;
        this.currency = currency;
        this.interestRate = interestRate;
        this.balance = 0;
        cards = new ArrayList<>();
        minBalance = 0;
        transactions = new ArrayList<>();
        payments = new ArrayList<>();
        moneySpent = new HashMap<>();
    }


    public void AddCard(final Card card) {
        cards.add(card);
    }

    public void removeCard(final Card card) {
        cards.remove(card);
    }

    public ObjectNode printAccount(ObjectMapper mapper) {
        ObjectNode node = mapper.createObjectNode();
        node.put("IBAN", IBAN.toString());
        node.put("currency", currency.toString());
        node.put("balance", balance);
        node.put("type", type.toString());
        ArrayNode cardsNode = mapper.createArrayNode();
        for (Card card : cards) {
            cardsNode.add(card.printCard(mapper));
        }
        node.set("cards", cardsNode);
        return node;
    }

    public void addFunds(final double funds) {
        balance += funds;
    }

    public ArrayList<Transaction> getTransactions(final int startTimestamp, final int endTimestamp) {
        ArrayList<Transaction> result = new ArrayList<>();
        for (Transaction transaction : transactions) {
            if (transaction.getTimestamp() >= startTimestamp && transaction.getTimestamp() <= endTimestamp) {
                result.add(transaction);
            }
            if (transaction.getTimestamp() > endTimestamp) {
                break;
            }
        }
        return result;

    }

    public ArrayList<Payment> getPayments(final int start, final int end) {
        ArrayList<Payment> result = new ArrayList<>();
        for (Payment payment : payments) {
            if (payment.getTimestamp() >= start && payment.getTimestamp() <= end) {
                result.add(payment);
            }
            if (payment.getTimestamp() > end) {
                break;
            }
        }

        return result;
    }



}
