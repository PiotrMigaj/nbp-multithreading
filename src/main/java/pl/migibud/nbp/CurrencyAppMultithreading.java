package pl.migibud.nbp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.migibud.nbp.api.CurrencyProvider;
import pl.migibud.nbp.internal.CurrencyConfiguration;

import java.util.concurrent.Executors;

class CurrencyAppMultithreading {

    private static final Logger LOGGER = LogManager.getLogger(CurrencyAppMultithreading.class);
    public static void main(String[] args) {
        final var applicationContext = new AnnotationConfigApplicationContext(CurrencyConfiguration.class);
        final var currencyProvider = applicationContext.getBean(CurrencyProvider.class);
        final var executorService = Executors.newFixedThreadPool(6);
        currencyProvider.fetchCurrenciesAsync(executorService).forEach(LOGGER::info);
        executorService.shutdown();
    }
}
