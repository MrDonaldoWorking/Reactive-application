package entity;

import org.bson.Document;
import util.Names;

import static util.Names.*;

public class Item implements Entity {
    private final String name;
    private final double price;
    private final Currency currency;

    public Item(final String name, final double price, final Currency currency) {
        this.name = name;
        this.price = price;
        this.currency = currency;
    }

    public Item(final Document document) {
        this(
                document.getString(NAME_PARAMETER_NAME),
                document.getDouble(PRICE_PARAMETER_NAME),
                getCurrencyFromString(document.getString(CURRENCY_PARAMETER_NAME))
        );
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public Document getDoc() {
        return new Document().append("name", name).append("price", price).append("currency", currency);
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", currency=" + currency +
                '}';
    }
}
