package com.lecomptoir.engine.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class CheckoutTest {

    @Test
    void noDiscountWhenTotalIsBelowThreshold() {
        Product product = new Product("REF001", "Kiwi", 1.50, ProductCategory.FOODS);
        Cart cart = new Cart();
        cart.addLine(new CartLine(product, 20)); // total = 30

        Receipt receipt = new Checkout().checkout(cart);

        assertEquals(30.0, receipt.getTotal(), 0.001);
    }

    @Test
    void noDiscountWhenTotalEqualsThresholdExactly() {
        Product product = new Product("REF001", "Kiwi", 25.0, ProductCategory.FOODS);
        Cart cart = new Cart();
        cart.addLine(new CartLine(product, 2)); // total = 50 pile

        Receipt receipt = new Checkout().checkout(cart);

        assertEquals(50.0, receipt.getTotal(), 0.001); // "dépasse" = strictement supérieur
    }

    @Test
    void tenPercentDiscountWhenTotalExceedsFifty() {
        Product product = new Product("REF001", "Kiwi", 20.0, ProductCategory.FOODS);
        Cart cart = new Cart();
        cart.addLine(new CartLine(product, 3)); // total = 60

        Receipt receipt = new Checkout().checkout(cart);

        assertEquals(54.0, receipt.getTotal(), 0.001); // 60 - 10%
    }
}