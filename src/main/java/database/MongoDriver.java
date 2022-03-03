package database;

import com.mongodb.client.model.Filters;
import com.mongodb.rx.client.MongoClient;
import com.mongodb.rx.client.MongoClients;
import com.mongodb.rx.client.Success;
import entity.Currency;
import entity.Entity;
import entity.Item;
import entity.User;
import rx.Observable;

import static util.Names.*;

public class MongoDriver {
    private static final MongoClient client = MongoClients.create("mongodb://localhost");

    private static <T extends Entity> void insert(final String collectionName, final T entity) {
        client.getDatabase(DATABASE_NAME)
                .getCollection(collectionName)
                .insertOne(entity.getDoc());
    }

    public static Observable<String> register(final int id, final Currency currency) {
        final User user = new User(id, currency);
        insert(USER_COLLECTION_NAME, user);
        return Observable.just("Successfully registered: " + user);
    }

    public static Observable<String> addNewItem(final String name, final double price, final Currency currency) {
        final Item item = new Item(name, price, currency);
        insert(ITEM_COLLECTION_NAME, item);
        return Observable.just("Added new item: " + item);
    }

    private static Observable<Item> getItems() {
        return client.getDatabase(DATABASE_NAME)
                .getCollection(ITEM_COLLECTION_NAME)
                .find()
                .toObservable()
                .map(Item::new);
    }

    private static Observable<User> getUser(final int id) {
        return client.getDatabase(DATABASE_NAME)
                .getCollection(USER_COLLECTION_NAME)
                .find(Filters.eq(ID_PARAMETER_NAME, id))
                .toObservable()
                .map(User::new);
    }

    public static Observable<String> getAllItems(final int id) {
        return getUser(id)
                .map(User::getCurrency)
                .flatMap(currency -> getItems().map(
                        item -> String.format("%s: %f%n",
                                item.getName(),
                                Currency.convert(item.getPrice(), item.getCurrency(), currency)
                        )
                ));
    }
}
