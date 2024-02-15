package pl.migibud.nbp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.migibud.nbp.api.CurrencyProvider;

@Configuration
@ComponentScan
class CurrencyApp {

    private static final Logger LOGGER = LogManager.getLogger(CurrencyApp.class);
    public static void main(String[] args) {
        final var applicationContext = new AnnotationConfigApplicationContext(CurrencyApp.class);
        final var currencyProvider = applicationContext.getBean(CurrencyProvider.class);
        currencyProvider.fetchCurrencies().forEach(LOGGER::info);
    }
}
