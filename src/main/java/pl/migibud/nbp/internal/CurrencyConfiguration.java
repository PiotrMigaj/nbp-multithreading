package pl.migibud.nbp.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.migibud.nbp.api.CurrencyProvider;

@Configuration(proxyBeanMethods = false)
class CurrencyConfiguration {
    
    @Bean
    ObjectMapper objectMapper(){
        final var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
    
    @Bean
    NbpClient nbpClient(ObjectMapper objectMapper){
        return new NbpClient(objectMapper);
    }
    
    @Bean
    CurrencyProvider currencyProvider(NbpClient nbpClient){
        return new CurrencyFacade(nbpClient);
    }
}
