package pl.migibud.nbp.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import pl.migibud.nbp.api.CurrencyProvider;

public class CurrencyConfiguration extends AbstractModule {

    @Override
    protected void configure() {
        // Bind ObjectMapper to its implementation
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        bind(ObjectMapper.class).toInstance(objectMapper);

        // Bind NbpClient to its implementation
        bind(NbpClient.class).in(Singleton.class);

        // Bind CurrencyFacade to its implementation
        bind(CurrencyFacade.class).in(Singleton.class);

        // Bind CurrencyProvider to its implementation (CurrencyFacade)
        bind(CurrencyProvider.class).to(CurrencyFacade.class);
    }
}
