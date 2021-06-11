package com.rixon.learn.tibcoems.model;

import lombok.Data;

@Data
public class Instrument {
    private long id;
    private String type;
    private String name;
    private String metadata;
}
