package com.policyexpert.codingkata.rule;



import com.policyexpert.codingkata.model.SKU;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class MultiItemsDiscountRule implements PricingRule{

    private  int noOfProducts;
    private  BigDecimal offerPrice;
    private List<SKU> productList = new ArrayList<>();
    private  BigDecimal unitPrice;
    private static RoundingMode ROUNDING_MODE = RoundingMode.HALF_EVEN;
    private String savingsSummary;
    private SKU product;

    @Override
    public BigDecimal computePrice(Double quantity) {
        if(quantity< noOfProducts){
            return unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
        Double noOfItemsWithUnitPrice = quantity % noOfProducts;
        Double noOfItemsWithOfferPrice = quantity - noOfItemsWithUnitPrice;
        BigDecimal totalPrice  = unitPrice.multiply(BigDecimal.valueOf(noOfItemsWithUnitPrice));
        totalPrice = totalPrice.add(
                (offerPrice.multiply(BigDecimal.valueOf(noOfItemsWithOfferPrice)))
                        .divide(BigDecimal.valueOf(noOfProducts),ROUNDING_MODE));
        BigDecimal savings = totalPrice.subtract(unitPrice.multiply(BigDecimal.valueOf(quantity)));
        savingsSummary = String.format("%s %s for £%s       %s",product,noOfProducts,offerPrice,savings.setScale(2, RoundingMode.HALF_EVEN));
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



    private MultiItemsDiscountRule(MultiItemsDiscountRule.Builder builder) {
        this.noOfProducts = builder.x;
        this.offerPrice = builder.y;
        this.productList = builder.productList;
        this.unitPrice = builder.unitPrice;
    }

    public static MultiItemsDiscountRule.Builder builder() {
        return new MultiItemsDiscountRule.Builder();
    }

    public static final class Builder {

        private  int x;
        private  BigDecimal y;
        List<SKU> productList = new ArrayList<>();
        private  BigDecimal unitPrice;
        private Builder() {
        }

        public MultiItemsDiscountRule.Builder setY(BigDecimal y) {
            this.y = y;
            return this;
        }

        public MultiItemsDiscountRule.Builder setProductList(List<SKU> productList) {
            this.productList = productList;
            return this;
        }

        public MultiItemsDiscountRule.Builder setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
            return this;
        }

        public MultiItemsDiscountRule build() {
            return new MultiItemsDiscountRule(this);
        }

        public MultiItemsDiscountRule.Builder setX(int x) {
            this.x = x;
            return this;
        }
    }
}
