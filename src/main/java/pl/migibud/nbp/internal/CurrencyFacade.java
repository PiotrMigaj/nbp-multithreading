package pl.migibud.nbp.internal;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pl.migibud.nbp.api.CurrencyDto;
import pl.migibud.nbp.api.CurrencyProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

@Singleton
class CurrencyFacade implements CurrencyProvider {

    private static final Logger LOGGER = LogManager.getLogger(CurrencyFacade.class);
    
    private final NbpClient nbpClient;

    @Inject
    public CurrencyFacade(NbpClient nbpClient) {
        this.nbpClient = nbpClient;
    }

    @Override
    public List<CurrencyDto> fetchCurrencies() {
        final long start = System.currentTimeMillis();
        final List<CurrencyDto> curriences = Arrays
            .stream(CurrencyCode.values())
            .map(nbpClient::downloadCurrency)
            .toList();
        final long end = System.currentTimeMillis();
        LOGGER.info("Time required to fetch data: {}",(end-start));
        return curriences;
    }

    @Override
    public List<CurrencyDto> fetchCurrenciesAsync(Executor executor) {
        final List<Future<CurrencyDto>> futures = new ArrayList<>();
        final long start = System.currentTimeMillis();
        for (CurrencyCode code :CurrencyCode.values()) {
            final Future<CurrencyDto> future = nbpClient.downloadCurrencyAsync(code, executor);
            futures.add(future);
        }
        final List<CurrencyDto> currencies = new ArrayList<>();
        for (Future<CurrencyDto> future:futures) {
            try {
                CurrencyDto currencyDto = future.get();
                currencies.add(currencyDto);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        final long end = System.currentTimeMillis();
        LOGGER.info("Time required to fetch data asynch: {}",(end-start));
        return currencies;
    }
}
