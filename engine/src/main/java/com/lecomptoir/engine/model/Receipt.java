package com.lecomptoir.engine.model;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class Receipt {
    private final List<CartLine> lines;
    private final double totalExclTax;
    private final Map<Double, Double> vatByRate;
    private final double totalInclTax;

    public Receipt(List<CartLine> lines, double totalExclTax, Map<Double, Double> vatByRate, double totalInclTax) {
        this.lines = List.copyOf(lines);
        this.totalExclTax = totalExclTax;
        this.vatByRate = Collections.unmodifiableMap(new TreeMap<>(vatByRate));
        this.totalInclTax = totalInclTax;
    }

    public List<CartLine> getLines() { return lines; }
    public double getTotalExclTax() { return totalExclTax; }
    public Map<Double, Double> getVatByRate() { return vatByRate; }
    public double getTotalInclTax() { return totalInclTax; }

    public String display() {
        StringBuilder sb = new StringBuilder();
        for (CartLine line : lines) {
            sb.append(String.format(Locale.FRANCE, "%-20s x%-3d %8.2f EUR%n",
                    line.getProduct().getName(), line.getQuantity(), line.getLineTotal()));
        }
        sb.append(formatAmountLine("Total HT", totalExclTax));
        for (Map.Entry<Double, Double> entry : vatByRate.entrySet()) {
            sb.append(formatAmountLine("TVA " + formatRate(entry.getKey()) + " %", entry.getValue()));
        }
        sb.append(formatAmountLine("Total TTC", totalInclTax));
        return sb.toString();
    }

    private String formatAmountLine(String label, double amount) {
        return String.format(Locale.FRANCE, "%-25s %8.2f EUR%n", label, amount);
    }

    private String formatRate(double rate) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.FRANCE);
        format.setMaximumFractionDigits(1);
        return format.format(rate * 100);
    }
}
