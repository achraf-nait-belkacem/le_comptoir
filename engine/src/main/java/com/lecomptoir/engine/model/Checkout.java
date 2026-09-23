package com.lecomptoir.engine.model;

public class Checkout {
    private static final double DISCOUNT_THRESHOLD = 50.0;
    private static final double DISCOUNT_RATE = 0.10;

    public Receipt checkout(Cart cart) {
        double total = cart.getTotal();

        if (total > DISCOUNT_THRESHOLD) {
            total = total - (total * DISCOUNT_RATE);
        }

        return new Receipt(cart.getLines(), total);
    }
}
