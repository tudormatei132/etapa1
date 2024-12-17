package org.poo.transactions;

public class NonNullBalance extends Transaction {
    public NonNullBalance(int timestamp) {
        super(timestamp, "Account couldn't be deleted - there are funds remaining");
    }
}
