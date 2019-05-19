package com.policyexpert.codingkata.rule;



import com.policyexpert.codingkata.model.SKU;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class XForThePriceOfYRule implements PricingRule {

    private  int x;
    private  int y;
    private  List<SKU> productList = new ArrayList<>();
    private  BigDecimal unitPrice;
    private String savingsSummary;
    private SKU product;

    private XForThePriceOfYRule(Builder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.productList = builder.productList;
        this.unitPrice = builder.unitPrice;
    }

    @Override
    public BigDecimal computePrice(Double quantity){
        if(quantity < x){
            return unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
        Double noOfItemsWithUnitPrice = quantity % x;
        Double noOfItemsWithOfferPrice = quantity / x;
        BigDecimal totalPrice  = unitPrice.multiply(BigDecimal.valueOf(noOfItemsWithUnitPrice));
        totalPrice = totalPrice.add(unitPrice.multiply(BigDecimal.valueOf(y)).multiply(BigDecimal.valueOf(noOfItemsWithOfferPrice)));
        BigDecimal savings = totalPrice.subtract(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        savingsSummary = String.format("%s %s for %s       %s",product,x,y,savings.setScale(2, RoundingMode.HALF_EVEN));
        return totalPrice;
    }

    @Override
    public boolean applicable(SKU product) {
        this.product = product;
        return productList.contains(product);
    }

    @Override
    public String getSavingsSummary() {
        return savingsSummary;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private  int x;
        private  int y;
        List<SKU> productList = new ArrayList<>();
        private  BigDecimal unitPrice;
        private Builder() {
        }

        public Builder setY(int y) {
            this.y = y;
            return this;
        }

        public Builder setX(int x) {
            this.x = x;
            return this;
        }

        public Builder setProductList(List<SKU> productList) {
            this.productList = productList;
            return this;
        }

        public Builder setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public XForThePriceOfYRule build() {
            return new XForThePriceOfYRule(this);
        }
    }
}


