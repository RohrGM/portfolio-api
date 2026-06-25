package com.rohr.portfolio_api.v1.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@UtilityClass
public class MonetaryUtils {
    private static final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.of("pt", "BR"));

    public static String formatCurrency(BigDecimal value) {
        if (value == null) {
            return null;
        }
        return currencyFormatter.format(value);
    }
}
