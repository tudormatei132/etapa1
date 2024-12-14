package org.poo.command;

import org.poo.account.Account;
import org.poo.account.Card;
import org.poo.utils.Utils;

import java.util.HashMap;

public class CreateCard implements Command {

    private Account account;
    private HashMap<String, Card> cardMap;

    public CreateCard(final Account account, final HashMap<String, Card> cardMap) {
        this.account = account;
        this.cardMap = cardMap;
    }

    public void execute() {
        Card temp = new Card(new StringBuilder(Utils.generateCardNumber()), account);
        account.AddCard(temp);
        String cardNumber = temp.getCardNumber().toString();
        cardMap.put(cardNumber, temp);
    }

}
