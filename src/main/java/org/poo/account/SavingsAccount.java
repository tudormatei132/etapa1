package org.poo.account;

import lombok.Getter;
import lombok.Setter;

public class SavingsAccount extends Account {
    @Getter
    private double interestRate;

    public SavingsAccount(final User user, final StringBuilder IBAN, final StringBuilder currency,
                   final double interestRate) {
        super(user, IBAN, currency);
        this.interestRate = interestRate;
        setBalance(0);
        this.setType(new StringBuilder("savings"));
    }

    public int setInterestRate(final double rate) {
        interestRate = rate;
        return 0;
    }
}
