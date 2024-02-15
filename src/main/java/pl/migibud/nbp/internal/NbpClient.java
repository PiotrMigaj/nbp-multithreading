package pl.migibud.nbp.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import pl.migibud.nbp.api.CurrencyDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@RequiredArgsConstructor
class NbpClient {
    
    private static final String NPB_URI = "https://api.nbp.pl/api/exchangerates/rates/a/%s/?format=json";

    private static final Logger LOGGER = LogManager.getLogger(NbpClient.class);
    
    private final ObjectMapper objectMapper;
    
    CurrencyDto downloadCurrency(CurrencyCode currencyCode){

        final var httpClient = HttpClient.newHttpClient();
        final var httpRequest = HttpRequest.newBuilder()
                                             .GET()
                                             .uri(URI.create(NPB_URI.formatted(currencyCode.toString())))
                                             .build();
        LOGGER.info("Current thread name: {}",Thread.currentThread().getName());
        try {
            final var responseJson = httpClient
                .send(httpRequest, HttpResponse.BodyHandlers.ofString())
                .body();
            LOGGER.info(responseJson);
            return objectMapper.readValue(responseJson, CurrencyDto.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Async("threadPoolTaskExecutor")
    public Future<CurrencyDto> downloadCurrencyAsync(CurrencyCode currencyCode){
        return CompletableFuture.supplyAsync(()->downloadCurrency(currencyCode));
    }
}
