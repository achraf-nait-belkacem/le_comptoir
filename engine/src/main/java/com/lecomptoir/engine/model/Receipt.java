package com.lecomptoir.engine.model;

import java.util.List;

public class Receipt {
    private final List<CartLine> lines;
    private final double total;

    public Receipt(List<CartLine> lines, double total) {
        this.lines = lines;
        this.total = total;
    }

    public List<CartLine> getLines() { return lines; }
    public double getTotal() { return total; }

    public String display() {
        StringBuilder sb = new StringBuilder();
        for (CartLine line : lines) {
            sb.append(String.format("%-20s x%d %6.2f EUR%n",
                line.getProduct().getName(),line.getQuantity(),line.getLineTotal()
            ));
        }
        sb.append(String.format("Total: %.2f EUR%n", total));
            return sb.toString();
    }
}
