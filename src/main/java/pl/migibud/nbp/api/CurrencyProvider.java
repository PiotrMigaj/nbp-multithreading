package pl.migibud.nbp.api;

import java.util.List;

public interface CurrencyProvider {

    List<CurrencyDto> fetchCurrencies();
    
    List<CurrencyDto> fetchCurrenciesAsync();
}
