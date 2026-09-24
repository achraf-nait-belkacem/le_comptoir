package com.lecomptoir.engine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Checkout {
    private static final double DISCOUNT_THRESHOLD = 50.0;
    private static final double DISCOUNT_RATE = 0.10;

    public Receipt checkout(Cart cart) {
        Map<Double, Double> baseByRate = computeBaseByRate(cart);

        double drinksDiscount = computeDrinksThirdFreeDiscount(cart);
        baseByRate.computeIfPresent(ProductCategory.DRINKS.getVatRate(), (rate, base) -> base - drinksDiscount);

        if (roundToCents(sum(baseByRate)) > DISCOUNT_THRESHOLD) {
            baseByRate.replaceAll((rate, base) -> base - (base * DISCOUNT_RATE));
        }
        baseByRate.replaceAll((rate, base) -> roundToCents(base));

        Map<Double, Double> vatByRate = new TreeMap<>();
        baseByRate.forEach((rate, base) -> vatByRate.put(rate, roundToCents(base * rate)));

        double totalExclTax = roundToCents(sum(baseByRate));
        double totalInclTax = roundToCents(totalExclTax + sum(vatByRate));

        return new Receipt(cart.getLines(), totalExclTax, vatByRate, totalInclTax);
    }

    private Map<Double, Double> computeBaseByRate(Cart cart) {
        Map<Double, Double> baseByRate = new TreeMap<>();
        for (CartLine line : cart.getLines()) {
            baseByRate.merge(line.getProduct().getCategory().getVatRate(), line.getLineTotal(), Double::sum);
        }
        return baseByRate;
    }

    private double computeDrinksThirdFreeDiscount(Cart cart) {
        List<Double> drinksPrices = new ArrayList<>();

        for (CartLine line : cart.getLines()) {
            if (line.getProduct().getCategory() == ProductCategory.DRINKS) {
                for (int i = 0; i < line.getQuantity(); i++) {
                    drinksPrices.add(line.getProduct().getUnitPrice());
                }
            }
        }

        drinksPrices.sort(Collections.reverseOrder());

        double discount = 0;
        for (int i = 2; i < drinksPrices.size(); i += 3) {
            discount += drinksPrices.get(i);
        }

        return discount;
    }

    private double sum(Map<Double, Double> amounts) {
        double total = 0;
        for (double amount : amounts.values()) {
            total += amount;
        }
        return total;
    }

    private double roundToCents(double amount) {
        return Math.round(amount * 100) / 100.0;
    }
}
