package com.lecomptoir.engine.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Checkout {
    private static final double DISCOUNT_THRESHOLD = 50.0;
    private static final double DISCOUNT_RATE = 0.10;

    public Receipt checkout(Cart cart) {
        double total = cart.getTotal();

        total -= computeDrinksThirdFreeDiscount(cart);

        if (total > DISCOUNT_THRESHOLD) {
            total = total - (total * DISCOUNT_RATE);
        }

        return new Receipt(cart.getLines(), total);
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
}