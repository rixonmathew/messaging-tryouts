package com.rixon.learn.tibcoems.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Instrument {
    @Id
    private long id;
    private String type;
    private String name;
    private String metadata;
}
