package pl.migibud.nbp.api;

import java.util.List;
import java.util.concurrent.Executor;

public interface CurrencyProvider {

    List<CurrencyDto> fetchCurrencies();
    
    List<CurrencyDto> fetchCurrenciesAsync(Executor executor);
}
