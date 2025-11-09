package br.com.reservasapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.UUID;

@Configuration
public class AuthorizationBeansConfig {

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("reservas-client")
                .clientSecret("{noop}secret123")
                .scope("read")
                .scope("write")
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }
}
