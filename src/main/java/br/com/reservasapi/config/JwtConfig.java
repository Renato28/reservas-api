package br.com.reservasapi.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;

@Configuration
public class JwtConfig {

    @Bean
    public JwtEncoder jwtEncoder() throws Exception {
        RSAKey rsaKey = new RSAKey.Builder(publicKey()).privateKey(privateKey()).build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(rsaKey));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder() throws Exception {
        return NimbusJwtDecoder.withPublicKey(publicKey()).build();
    }

    private RSAPublicKey publicKey() throws Exception {
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/keys/public.pem")) {
            String key = new String(inputStream.readAllBytes())
                    .replace("-----BEGIN PUBLIC KEY-----", "")
                    .replace("-----END PUBLIC KEY-----", "");
            byte[] decodedKey = Base64.getDecoder().decode(key);
            return (RSAPublicKey) KeyFactory.getInstance("RSA")
                    .generatePublic(new X509EncodedKeySpec(decodedKey));
        }
    }

    private RSAPrivateKey privateKey() throws Exception {
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/keys/private.pem")) {
            String key = new String(inputStream.readAllBytes())
                    .replace("-----BEGIN PRIVATE KEY-----", "")
                    .replace("-----END PRIVATE KEY-----", "")
                    .replace("\\s+", "");
            byte[] decodedKey = Base64.getDecoder().decode(key);
            return (RSAPrivateKey) KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(decodedKey));

        }
    }
}
