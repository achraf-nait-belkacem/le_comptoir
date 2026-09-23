package com.lecomptoir.engine.model;

public class Main {
    public static void main(String[] args) {
        Product fruit = new Product("REF001", "Kiwi", 1.50, ProductCategory.FOODS);
        Product coca = new Product("REF002", "CocaCola 1L", 2.50, ProductCategory.DRINKS);

        Cart cart = new Cart();
        cart.addLine(new CartLine(fruit, 5));
        cart.addLine(new CartLine(coca, 2));

        Receipt receipt = new Checkout().checkout(cart);
        System.out.println(receipt.display());
    }
}
