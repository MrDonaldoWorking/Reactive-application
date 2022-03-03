package util;

import entity.Currency;

import java.util.Locale;

public class Names {
    public static final String ID_PARAMETER_NAME = "id";
    public static final String CURRENCY_PARAMETER_NAME = "currency";
    public static final String NAME_PARAMETER_NAME = "name";
    public static final String PRICE_PARAMETER_NAME = "price";

    public static final String DATABASE_NAME = "Reactive";
    public static final String USER_COLLECTION_NAME = "User";
    public static final String ITEM_COLLECTION_NAME = "Item";

    public static Currency getCurrencyFromString(final String currencyString) {
        return Currency.valueOf(currencyString.toUpperCase(Locale.ROOT));
    }
}
