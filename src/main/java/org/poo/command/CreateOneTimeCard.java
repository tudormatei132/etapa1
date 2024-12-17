package org.poo.command;

import org.poo.account.Account;
import org.poo.account.Card;
import org.poo.account.OneTimeCard;
import org.poo.transactions.CardCreation;
import org.poo.utils.Utils;

import java.util.HashMap;

public class CreateOneTimeCard implements Command {

    private Account account;
    private HashMap<String, Card> cardMap;
    private int timestamp;
    public CreateOneTimeCard(final Account account, final HashMap<String, Card> cardMap,
                             final int timestamp) {
        this.account = account;
        this.cardMap = cardMap;
        this.timestamp = timestamp;
    }

    public void execute() {
        OneTimeCard temp = new OneTimeCard(new StringBuilder(Utils.generateCardNumber()), account);
        account.AddCard(temp);
        String cardNumber = temp.getCardNumber().toString();
        cardMap.put(cardNumber, temp);
        CardCreation new_card = new CardCreation(timestamp, temp.getCardNumber().toString(),
                account.getUser().getEmail().toString(), account.getIBAN().toString());
        account.getUser().getTransactions().add(new_card);
        account.getTransactions().add(new_card);
    }
}
