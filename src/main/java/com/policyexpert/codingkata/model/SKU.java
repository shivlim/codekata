package com.policyexpert.codingkata.model;


public enum SKU {
    BEANS("beans",1),COKE("coke",2),ORANGES("oranges",3);

    private final String description;
    private final int sku;

    SKU(String description, int sku) {
        this.description = description;
        this.sku = sku;
    }
}
