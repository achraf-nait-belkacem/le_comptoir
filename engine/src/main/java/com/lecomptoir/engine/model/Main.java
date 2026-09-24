package com.lecomptoir.engine.model;

public class Main {
    public static void main(String[] args) {
        Product fruit = new Product("REF001", "Kiwi", 2.50, ProductCategory.FOODS);
        Product coca = new Product("REF002", "CocaCola 1.5L", 1.80, ProductCategory.DRINKS);

        Cart smallCart = new Cart();
        smallCart.addLine(new CartLine(fruit, 2));
        smallCart.addLine(new CartLine(coca, 2));

        System.out.println("Petit panier (aucune remise) :");
        System.out.println(new Checkout().checkout(smallCart).display());

        Cart drinksCart = new Cart();
        drinksCart.addLine(new CartLine(fruit, 2));
        drinksCart.addLine(new CartLine(coca, 3));  // 3e boisson offerte : -1.80

        System.out.println("Panier boissons (3e boisson offerte) :");
        System.out.println(new Checkout().checkout(drinksCart).display());

        Cart bigCart = new Cart();
        bigCart.addLine(new CartLine(fruit, 22));  // 55.00
        bigCart.addLine(new CartLine(coca, 2));    // 3.60 -> total 58.60, supérieur à 50

        System.out.println("Gros panier (remise 10%) :");
        System.out.println(new Checkout().checkout(bigCart).display());
    }
}
