package com.lecomptoir.engine.model;

public class Checkout {
    public Receipt checkout(Cart cart) {
        return new Receipt(cart.getLines(), cart.getTotal());
    }
}
