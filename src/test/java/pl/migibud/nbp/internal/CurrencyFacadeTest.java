package pl.migibud.nbp.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.migibud.nbp.api.CurrencyProvider;

import java.util.concurrent.Executors;

class CurrencyFacadeTest {

    private static final Logger LOGGER = LogManager.getLogger(CurrencyFacadeTest.class);

    ObjectMapper objectMapper = new ObjectMapper();
    NbpClient nbpClient = new NbpClient(objectMapper);
    CurrencyProvider currencyProvider = new CurrencyFacade(nbpClient);

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
    }
    
    @Test
    void shouldDownloadCurrencies(){
        currencyProvider.fetchCurrencies().forEach(LOGGER::info);
    }
    
    @Test
    void shouldDownloadCurrenciesAsync(){
        final var executorService = Executors.newFixedThreadPool(6);
        currencyProvider.fetchCurrenciesAsync(executorService).forEach(LOGGER::info);
        executorService.shutdown();
    }
}