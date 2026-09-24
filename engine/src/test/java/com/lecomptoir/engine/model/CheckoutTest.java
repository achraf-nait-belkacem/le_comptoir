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

    @Test
    void cheapestOfThreeBeveragesIsFree() {
        Product water = new Product("REF003", "Eau", 1.00, ProductCategory.DRINKS);
        Product juice = new Product("REF004", "Jus", 1.50, ProductCategory.DRINKS);
        Product coca = new Product("REF002", "Cola 1L", 2.00, ProductCategory.DRINKS);

        Cart cart = new Cart();
        cart.addLine(new CartLine(coca, 1));
        cart.addLine(new CartLine(water, 1));
        cart.addLine(new CartLine(juice, 1));

        Receipt receipt = new Checkout().checkout(cart);

        assertEquals(3.50, receipt.getTotal(), 0.001);
    }

    @Test
    void noBeverageDiscountWithOnlyTwoItems() {
        Product coca = new Product("REF002", "Cola 1L", 2.00, ProductCategory.DRINKS);

        Cart cart = new Cart();
        cart.addLine(new CartLine(coca, 2));

        Receipt receipt = new Checkout().checkout(cart);

        assertEquals(4.00, receipt.getTotal(), 0.001);
    }

    @Test
    void sixBeveragesGiveTwoFreeItems() {
        Product coca = new Product("REF002", "Cola 1L", 3.00, ProductCategory.DRINKS);

        Cart cart = new Cart();
        cart.addLine(new CartLine(coca, 6));

        Receipt receipt = new Checkout().checkout(cart);

        assertEquals(12.00, receipt.getTotal(), 0.001);
    }

    @Test
    void foodCategoryIsNeverAffectedByBeverageOffer() {
        Product fruit = new Product("REF001", "Kiwi", 5.00, ProductCategory.FOODS);

        Cart cart = new Cart();
        cart.addLine(new CartLine(fruit, 3));

        Receipt receipt = new Checkout().checkout(cart);

        assertEquals(15.00, receipt.getTotal(), 0.001);
    }

    @Test
    void fourBeveragesOfDifferentPricesOffersOnlyOneItem() {
        Product a = new Product("REF010", "Boisson A", 3.00, ProductCategory.DRINKS);
        Product b = new Product("REF011", "Boisson B", 2.00, ProductCategory.DRINKS);
        Product c = new Product("REF012", "Boisson C", 1.50, ProductCategory.DRINKS);
        Product d = new Product("REF013", "Boisson D", 1.00, ProductCategory.DRINKS);

        Cart cart = new Cart();
        cart.addLine(new CartLine(d, 1));
        cart.addLine(new CartLine(a, 1));
        cart.addLine(new CartLine(c, 1));
        cart.addLine(new CartLine(b, 1));

        Receipt receipt = new Checkout().checkout(cart);

        assertEquals(6.00, receipt.getTotal(), 0.001); // 7.50 - 1.50 (cheapest of the first three)
    }

    @Test
    void beverageOfferIsAppliedBeforeFiftyThreshold() {
        Product fruit = new Product("REF001", "Kiwi", 5.00, ProductCategory.FOODS);
        Product coca = new Product("REF002", "Cola 1L", 2.00, ProductCategory.DRINKS);

        Cart cart = new Cart();
        cart.addLine(new CartLine(fruit, 9));  // 45.00
        cart.addLine(new CartLine(coca, 3));   // 6.00 -> 51.00 before offer, 49.00 after

        Receipt receipt = new Checkout().checkout(cart);

        assertEquals(49.00, receipt.getTotal(), 0.001); // 49 is not above 50: no 10% discount
    }

    @Test
    void beverageOfferAndTenPercentDiscountCombine() {
        Product fruit = new Product("REF001", "Kiwi", 5.00, ProductCategory.FOODS);
        Product coca = new Product("REF002", "Cola 1L", 2.00, ProductCategory.DRINKS);

        Cart cart = new Cart();
        cart.addLine(new CartLine(fruit, 10)); // 50.00
        cart.addLine(new CartLine(coca, 3));   // 6.00 -> 56.00, 54.00 after offer

        Receipt receipt = new Checkout().checkout(cart);

        assertEquals(48.60, receipt.getTotal(), 0.001); // 54 - 10%
    }
}
