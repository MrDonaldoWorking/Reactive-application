import database.MongoDriver;
import entity.Currency;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import rx.Observable;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import static util.Names.*;

public class RxHttpServer {
    private static final int PORT = 11451;

    private static String getParameter(final String key, final Map<String, List<String>> parameters) {
        return parameters.get(key).get(0);
    }

    private static Observable<Void> handle(final HttpServerRequest<ByteBuf> request,
                                           final HttpServerResponse<ByteBuf> response) {
        final String query = request.getDecodedPath().substring(1);
        final Map<String, List<String>> parameters = request.getQueryParameters();
        final Currency currency;
        final int id;
        final Observable<String> message;
        try {
            switch (query) {
                case "register":
                    id = Integer.parseInt(getParameter(ID_PARAMETER_NAME, parameters));
                    currency = getCurrencyFromString(getParameter(CURRENCY_PARAMETER_NAME, parameters));
                    message = MongoDriver.register(id, currency);
                    break;
                case "add-item":
                    final String name = getParameter(NAME_PARAMETER_NAME, parameters);
                    final double price = Double.parseDouble(getParameter(PRICE_PARAMETER_NAME, parameters));
                    currency = getCurrencyFromString(getParameter(CURRENCY_PARAMETER_NAME, parameters));
                    message = MongoDriver.addNewItem(name, price, currency);
                    break;
                case "all-items":
                    id = Integer.parseInt(getParameter(ID_PARAMETER_NAME, parameters));
                    message = MongoDriver.getAllItems(id);
                    break;
                default:
                    throw new IllegalStateException("Unsupported query '" + query + "'");
            }
        } catch (final RuntimeException e) {
            response.setStatus(HttpResponseStatus.BAD_REQUEST);
            return response.writeString(Observable.just(e.getMessage()));
        }

        response.setStatus(HttpResponseStatus.OK);
        return response.writeString(message);
    }

    public static void main(final String[] args) {
        HttpServer
                .newServer(PORT)
                .start(RxHttpServer::handle)
                .awaitShutdown();
    }
}
