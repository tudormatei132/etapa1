package org.poo.account;

public class OneTimeCard extends Card {


    public OneTimeCard(StringBuilder cardNumber, Account account) {
        super(cardNumber, account);
    }

    public void use() {
        setStatus(new StringBuilder("inactive"));
    }

}
