package org.mohcine.comptecqrseventsourcing.commonapi.exceptions;

public class BalanceNotSuffisantException extends RuntimeException {
    public BalanceNotSuffisantException(String message) {
        super(message);
    }
}
