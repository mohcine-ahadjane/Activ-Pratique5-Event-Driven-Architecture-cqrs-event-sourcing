package org.mohcine.comptecqrseventsourcing.commonapi.dtos;

import lombok.Data;

@Data
public class DebitAccounRequestDTO {
    private String accountId;
    private double amount;
    private String currency;
}
