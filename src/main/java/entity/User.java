package entity;

import org.bson.Document;

import java.util.Currency;

public class User {
    private final int id;
    private final Currency currency;

    public User(final int id, final Currency currency) {
        this.id = id;
        this.currency = currency;
    }

    public Document getDoc() {
        return new Document().append("id", id).append("currency", currency);
    }
}
