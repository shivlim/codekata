package com.policyexpert.codingkata.rule;


import com.policyexpert.codingkata.model.SKU;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class PricePerKiloRule implements PricingRule {
    private final BigDecimal unitPrice;
    private List<SKU> productList = new ArrayList<>();
    private String savingsSummary;

    private PricePerKiloRule(PricePerKiloRule.Builder builder) {
        this.productList = builder.productList;
        this.unitPrice = builder.unitPrice;
    }


    @Override
    public BigDecimal computePrice(Double quantity) {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public boolean applicable(SKU product) {
        return productList.contains(product);
    }

    @Override
    public String getSavingsSummary() {
        return savingsSummary;
    }


    public static PricePerKiloRule.Builder builder() {
        return new PricePerKiloRule.Builder();
    }

    public static final class Builder {
        List<SKU> productList = new ArrayList<>();
        private  BigDecimal unitPrice;
        private Builder() {
        }
        public PricePerKiloRule.Builder setProductList(List<SKU> productList) {
            this.productList = productList;
            return this;
        }

        public PricePerKiloRule.Builder setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public PricePerKiloRule build() {
            return new PricePerKiloRule(this);
        }
    }
}
