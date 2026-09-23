package com.lecomptoir.engine.model;

public class Main {
    public static void main(String[] args) {
        Product fruit = new Product("REF001", "Kiwi", 2.50, ProductCategory.FOODS);
        Product coca = new Product("REF002", "CocaCola 1.5L", 1.80, ProductCategory.DRINKS);

        Cart smallCart = new Cart();
        smallCart.addLine(new CartLine(fruit, 2));
        smallCart.addLine(new CartLine(coca, 3));

        System.out.println("Petit panier (pas de remise) :");
        System.out.println(new Checkout().checkout(smallCart).display());

        Cart bigCart = new Cart();
        bigCart.addLine(new CartLine(fruit, 10));  // 25.00
        bigCart.addLine(new CartLine(coca, 15));   // 27.00 → total 52.00

        System.out.println("Gros panier (remise 10%) :");
        System.out.println(new Checkout().checkout(bigCart).display());
    }
}
