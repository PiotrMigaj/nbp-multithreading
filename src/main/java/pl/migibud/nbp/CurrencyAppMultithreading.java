package pl.migibud.nbp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import pl.migibud.nbp.api.CurrencyProvider;

@Configuration
@ComponentScan
class CurrencyAppMultithreading {

    private static final Logger LOGGER = LogManager.getLogger(CurrencyAppMultithreading.class);
    public static void main(String[] args) {
        final var applicationContext = new AnnotationConfigApplicationContext(CurrencyAppMultithreading.class);
        final var currencyProvider = applicationContext.getBean(CurrencyProvider.class);
        currencyProvider.fetchCurrenciesAsync().forEach(LOGGER::info);
        final var executor = applicationContext.getBean(ThreadPoolTaskExecutor.class);
        executor.shutdown();
    }
}
