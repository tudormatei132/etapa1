package org.poo.account;

public class ClassicAccount extends Account {

    public ClassicAccount(final User user, final StringBuilder IBAN, final StringBuilder currency) {
        super(user, IBAN, currency);
        this.setType(new StringBuilder("classic"));
    }
}
