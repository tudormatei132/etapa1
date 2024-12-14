package org.poo.command;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.account.Card;
import org.poo.account.OneTimeCard;
import org.poo.system.Converter;
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

    public PayOnline(final Card card, final double amount, final String currency,
                     final String email, final ArrayNode output, final ObjectMapper mapper,
                     final int timestamp, final HashMap<String, Card> cardMap, final Converter converter) {
        this.card = card;
        this.amount = amount;
        this.currency = currency;
        this.email = email;
        this.output = output;
        this.mapper = mapper;
        this.timestamp = timestamp;
        this.cardMap = cardMap;
        this.converter = converter;
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
        if (card.getAccount().getBalance() < amount) {
            return;
        }

        card.use(amount);
        if (card.getStatus().toString().equals("inactive")) {
            cardMap.remove(card.getCardNumber().toString());
            card = new OneTimeCard(new StringBuilder(Utils.generateCardNumber()),
                    card.getAccount());
            cardMap.put(card.getCardNumber().toString(), card);
        }

    }

}
