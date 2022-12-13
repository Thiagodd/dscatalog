package com.thiagodd.dscatalog.domain.components;

import com.thiagodd.dscatalog.domain.model.User;
import com.thiagodd.dscatalog.domain.repository.UserRepository;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    private final UserRepository userRepository;

    public JwtTokenEnhancer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        User user = userRepository.findByEmail(oAuth2Authentication.getName());

        Map<String, Object> additionalInformation = new HashMap<>();
        additionalInformation.put("UserId", user.getId());
        additionalInformation.put("userFirstName", user.getFirstName());

        DefaultOAuth2AccessToken token = (DefaultOAuth2AccessToken) oAuth2AccessToken;
        token.setAdditionalInformation(additionalInformation);

        return oAuth2AccessToken;
    }
}
