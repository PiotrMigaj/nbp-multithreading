package pl.migibud.nbp.internal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import pl.migibud.nbp.api.CurrencyProvider;

@Configuration(proxyBeanMethods = false)
@EnableAsync
class CurrencyConfiguration {

    @Bean(name = "threadPoolTaskExecutor")
    ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(6);
        executor.setMaxPoolSize(6);
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }
    
    @Bean
    NbpClient nbpClient(){
        final var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new NbpClient(objectMapper);
    }
    
    @Bean
    CurrencyProvider currencyProvider(NbpClient nbpClient){
        return new CurrencyFacade(nbpClient);
    }
}
