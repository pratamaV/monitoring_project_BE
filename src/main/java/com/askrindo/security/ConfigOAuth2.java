package com.askrindo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
public class ConfigOAuth2 extends AuthorizationServerConfigurerAdapter {

    private String clientId = "imo";
    private String clientSecret = "imo-secret-key";
    private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEogIBAAKCAQEAsxhgKtf1fw6d3mdz0f2AtZ0aXVpMBvatcI7yXvNdYpqE0XSR\n" +
            "EqvRTfJ1HMrkyGGqgDK4KALZZalV6CdzR6p5kywpMbTQQN0qrJDeeWOqknayWV+o\n" +
            "ikOx1QrEXGZiwiNaNWinb0b+4RiNM86AfRUKuJp+On6tWZbdFt+6fdRQ49HF9kxu\n" +
            "ZAtSbzk4wAxWyuRrdJKjTNx0Ank+uT2a8o1ZvImyUyKyVvAOmDs+cM8Agth4AUqh\n" +
            "LDtjiBQoTsflRrFxrWpMOSjycDIvJteNW4rqURxaHI50SVBPTFuFbgr4pxszSa2V\n" +
            "Hgo2v/f61LhXXR47+YriRNDu8Z7pdAVgxo0jMwIDAQABAoIBAEKXXaqQggesN6bq\n" +
            "cHYz/BeLQrJTUNGGBxdDD3ueoap4Kcp3vTlkCX8iF8mM52RabDseoLq/1HWocPjR\n" +
            "MlwDVxER6n9A6+4yThu8AfzAwjuTRt1UZCaE5SqBxmFU8IOfAXu6XgdSLpMjvdz1\n" +
            "e3EG0eWyDB1T3MDjB3Hp3j3LJAiQ58fQwanxh/YmHGLE+nLPF5Q+WFqU/K8rQd3O\n" +
            "V4wQtCqFORYgghKIgT+Yug724TQWrl1oDt13VLOFTFx9E1kvaVEccWL6Qbsb2yWz\n" +
            "zwzxphWI+zuuVetVVPyETQYPWze/V9a8IqueDFt7LeUTkoA1ObHsTsWwrAVyIlfY\n" +
            "jFBszoECgYEA4ZC4hxOhYY6ju/OcQ1Yv0JNaYhhNxI1KNll51BeGECcWW2jbB9r4\n" +
            "aRTc7xCv3XrgZJue1rztpVx3wbdOznBjt71xyP3OJLqlR4Gs3Wf69+0i8hTLt7S2\n" +
            "IDfAuF4u/VNiOvA6GSXRFQ048QVGZN0tq+aO3WZSdrhYRpHLfdIHxlUCgYEAy0KG\n" +
            "hmHcjszwD52GzBqtb6VRVfRgDF4SclfcxA6Lkj+gWXw/rftvn2B4GClQjacRGGWa\n" +
            "hEiT+z2o1zFX+jI4L0IbHN8ek/+I+RwvWAhl5IgfjfgyKbyoTNAYQ3TAO8x3wX0L\n" +
            "VpKFh0rJspG9rQoHJpto7v8b84l5UUFrF88b+2cCgYAPBuMkLIydwTDrPol0evNo\n" +
            "g8iLrG7R30/IWyGgnYiS3ufAYzXkShQue/NUAdqVeLAmQ6/LQn7ae1SHFT0sx5P9\n" +
            "E6PW7gGjtMN6BguMHHc9uRCQ7A2+SU2wy8YNAklDGyD6KDLWpgxNyLm4ZM+RvKJk\n" +
            "R1HRMhXgTtuip+fUk/9smQKBgAJ/JbHe39utLsGyWKaHKVw2tqIMp2VffnCDHcYW\n" +
            "u6YnO+v9d7bGYN+fSrpZP5EN3UrkbJp1XcP3ROynwYHkPgZulHCtWO3OdLtCml5F\n" +
            "dCLzfRe8VblnhNW0tKeXdokFgO8SfyIoGJXJ2GJmIeNPPTk/LXHKFmjOZnwWBp+7\n" +
            "Poe/AoGAH+cvCuvHRNulXC+X8PWJ8ErZO2P+J05uRaEtvdVw3Sb+xqgozNneKMWe\n" +
            "mqLaJrChQ8W/dM5wMElK2em3aMML9YNBcXnJ7+2qk9GBlcLjrLHTNx4UgXZkBHr2\n" +
            "tJ6Eofx9jLxU4cBU/dJDIn1nksvB8vh+kq7z2RPeQYqUm5oi3v4=\n" +
            "-----END RSA PRIVATE KEY-----";
    private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsxhgKtf1fw6d3mdz0f2A\n" +
            "tZ0aXVpMBvatcI7yXvNdYpqE0XSREqvRTfJ1HMrkyGGqgDK4KALZZalV6CdzR6p5\n" +
            "kywpMbTQQN0qrJDeeWOqknayWV+oikOx1QrEXGZiwiNaNWinb0b+4RiNM86AfRUK\n" +
            "uJp+On6tWZbdFt+6fdRQ49HF9kxuZAtSbzk4wAxWyuRrdJKjTNx0Ank+uT2a8o1Z\n" +
            "vImyUyKyVvAOmDs+cM8Agth4AUqhLDtjiBQoTsflRrFxrWpMOSjycDIvJteNW4rq\n" +
            "URxaHI50SVBPTFuFbgr4pxszSa2VHgo2v/f61LhXXR47+YriRNDu8Z7pdAVgxo0j\n" +
            "MwIDAQAB\n" +
            "-----END PUBLIC KEY-----";

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(privateKey);
        converter.setVerifierKey(publicKey);
        return converter;
    }


    @Bean
    public JwtTokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Override
    public void configure(
            AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList(tokenEnhancer(), accessTokenConverter()));

        endpoints.tokenStore(tokenStore())
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).scopes("read", "write")
                .authorizedGrantTypes("password", "refresh_token").accessTokenValiditySeconds(20000)
                .refreshTokenValiditySeconds(20000);

    }
}