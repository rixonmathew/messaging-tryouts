package com.rixon.learn.tibcoems.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
public class Contract {
    private String id;
    private String type;
    private LocalDate tradeDate;
    private LocalDate settlementDate;
    private String assetIdentifier;
    private String assetIdentifierType;
    private BigDecimal quantity;
    private String comments;
}
