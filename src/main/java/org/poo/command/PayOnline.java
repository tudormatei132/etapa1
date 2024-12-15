package org.poo.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Card;
import org.poo.account.OneTimeCard;
import org.poo.system.Converter;

import org.poo.transactions.Payment;
import org.poo.transactions.Transaction;
import org.poo.utils.Utils;

import java.util.HashMap;

public class PayOnline implements Command {


    private Card card;
    private double amount;
    private String currency;
    private String email;
    private ArrayNode output;
    private ObjectMapper mapper;
    private int timestamp;
    private HashMap<String, Card> cardMap;
    private Converter converter;
    private String commerciant;


    public PayOnline(final Card card, final double amount, final String currency,
                     final String email, final ArrayNode output, final ObjectMapper mapper,
                     final int timestamp, final HashMap<String, Card> cardMap,
                     final Converter converter, final String commerciant) {
        this.card = card;
        this.amount = amount;
        this.currency = currency;
        this.email = email;
        this.output = output;
        this.mapper = mapper;
        this.timestamp = timestamp;
        this.cardMap = cardMap;
        this.converter = converter;
        this.commerciant = commerciant;
    }

    public void execute() {
        if (card == null) {
            ObjectNode doesntExist = mapper.createObjectNode();
            doesntExist.put("command", "payOnline");
            doesntExist.put("timestamp", timestamp);
            ObjectNode info = mapper.createObjectNode();
            info.put("timestamp", timestamp);
            info.put("description", "Card not found");
            doesntExist.put("output", info);
            output.add(doesntExist);
            return;
        }
        amount = amount * converter.convert(currency, card.getAccount().getCurrency().toString());

        String correspondingEmail = card.getAccount().getUser().getEmail().toString();


        if (!correspondingEmail.equals(email)) {
            return;
        }
        if (Double.compare(card.getAccount().getBalance() - card.getAccount().getMinBalance(),
                amount) < 0) {
            Transaction error = new Transaction(timestamp, "Insufficient funds");
            card.getAccount().getTransactions().add(error);
            card.getAccount().getUser().getTransactions().add(error);
            return;
        }

        if (card.getStatus().toString().equals("frozen")) {
            Transaction error = new Transaction(timestamp, "The card is frozen");
            card.getAccount().getTransactions().add(error);
            card.getAccount().getUser().getTransactions().add(error);
            return;
        }

        Payment new_payment = new Payment(timestamp, "Card payment", amount, commerciant);

        card.getAccount().getTransactions().add(new_payment);
        card.getAccount().getPayments().add(new_payment);
        card.getAccount().getUser().getTransactions().add(new_payment);

        card.use(amount);
        if (card.getStatus().toString().equals("inactive")) {
            cardMap.remove(card.getCardNumber().toString());
            card = new OneTimeCard(new StringBuilder(Utils.generateCardNumber()),
                    card.getAccount());
            cardMap.put(card.getCardNumber().toString(), card);
        }

    }

}
