package pl.migibud.nbp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.migibud.nbp.api.CurrencyProvider;
import pl.migibud.nbp.internal.CurrencyConfiguration;

class CurrencyApp {

    private static final Logger LOGGER = LogManager.getLogger(CurrencyApp.class);
    public static void main(String[] args) {
        final Injector injector = Guice.createInjector(new CurrencyConfiguration());
        final CurrencyProvider currencyProvider = injector.getInstance(CurrencyProvider.class);
        currencyProvider.fetchCurrencies().forEach(LOGGER::info);
    }
}
