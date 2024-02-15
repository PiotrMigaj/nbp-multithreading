package pl.migibud.nbp.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.migibud.nbp.api.CurrencyProvider;

@Configuration(proxyBeanMethods = false)
class CurrencyConfiguration {

    @Bean
    CurrencyProvider currencyProvider(){
        final var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        final var npbClient = new NbpClient(objectMapper);
        return new CurrencyFacade(npbClient);
    }
}
