import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.HttpResponseStatus;
import rx.Observable;

import java.util.List;
import java.util.Map;

public class RxHttpServer {
    private static final int PORT = 11451;

    private static Observable<Void> handle(final HttpServerRequest<ByteBuf> request,
                                           final HttpServerResponse<ByteBuf> response) {
        final String query = request.getDecodedPath();
        if (query == null || query.isEmpty()) {
            response.setStatus(io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST);
            return response.writeString(Observable.just("Expected query after localhost:" + PORT + "/"));
        }

        final Map<String, List<String>> parameters = request.getQueryParameters();
        response.setStatus(HttpResponseStatus.OK);
        return response.writeString(Observable.just(
                String.format("query is: '%s'", query),
                String.format("parameters: %s", parameters)
        ));
    }

    public static void main(final String[] args) {
        HttpServer
                .newServer(PORT)
                .start(RxHttpServer::handle)
                .awaitShutdown();
    }
}
