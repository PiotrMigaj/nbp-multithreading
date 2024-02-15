package pl.migibud.nbp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.migibud.nbp.api.CurrencyProvider;

import java.util.concurrent.Executors;

@Configuration
@ComponentScan
class CurrencyAppMultithreading {

    private static final Logger LOGGER = LogManager.getLogger(CurrencyAppMultithreading.class);
    public static void main(String[] args) {
        final var applicationContext = new AnnotationConfigApplicationContext(CurrencyAppMultithreading.class);
        final var currencyProvider = applicationContext.getBean(CurrencyProvider.class);
        final var executorService = Executors.newFixedThreadPool(6);
        currencyProvider.fetchCurrenciesAsync(executorService).forEach(LOGGER::info);
        executorService.shutdown();
    }
}
