package entity;

import org.bson.Document;

public class Item {
    private final String name;
    private final double price;
    private final Currency currency;

    public Item(final String name, final double price, final Currency currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public Document getDoc() {
        return new Document().append("name", name).append("price", price).append("currency", currency);
    }
}
