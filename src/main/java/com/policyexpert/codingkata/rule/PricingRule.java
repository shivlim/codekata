package com.policyexpert.codingkata.rule;


import com.policyexpert.codingkata.model.SKU;

import java.math.BigDecimal;


public interface PricingRule {
    BigDecimal computePrice(Double quantity);
    boolean applicable(SKU product);
    String getSavingsSummary();
}
