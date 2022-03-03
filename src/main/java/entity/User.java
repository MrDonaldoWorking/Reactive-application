package entity;

import org.bson.Document;

import static util.Names.*;

public class User implements Entity {
    private final int id;
    private final Currency currency;

    public User(final int id, final Currency currency) {
        this.id = id;
        this.currency = currency;
    }

    public User(final Document document) {
        this(document.getInteger(ID_PARAMETER_NAME), getCurrencyFromString(CURRENCY_PARAMETER_NAME));
    }

    public Currency getCurrency() {
        return currency;
    }

    @Override
    public Document getDoc() {
        return new Document().append("id", id).append("currency", currency);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", currency=" + currency +
                '}';
    }
}
