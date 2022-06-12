package com.mybank.gatewayserver.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
        if (realmAccess == null || realmAccess.isEmpty()) {
            return new ArrayList<>();
        }
        Collection<GrantedAuthority> returnValue = ((List<String>) realmAccess.get("roles"))
                .stream().map(roleName -> "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return returnValue;
    }

    /** jwt token example
     * {
     *   "exp": 1655062648,
     *   "iat": 1655062588,
     *   "jti": "db17f85f-c601-43b4-890a-301b3d3fe63e",
     *   "iss": "http://localhost:7080/realms/master",
     *   "aud": "account",
     *   "sub": "a8a3c0d7-a967-4a3f-b63f-df85d4b6fa65",
     *   "typ": "Bearer",
     *   "azp": "mybank-callcenter",
     *   "acr": "1",
     *   "realm_access": {
     *     "roles": [
     *       "ACCOUNT",
     *       "default-roles-master",
     *       "offline_access",
     *       "uma_authorization"
     *     ]
     *   },
     *   "resource_access": {
     *     "account": {
     *       "roles": [
     *         "manage-account",
     *         "manage-account-links",
     *         "view-profile"
     *       ]
     *     }
     *   },
     *   "scope": "openid profile email",
     *   "email_verified": false,
     *   "clientId": "mybank-callcenter",
     *   "clientHost": "172.26.0.1",
     *   "preferred_username": "service-account-mybank-callcenter",
     *   "clientAddress": "172.26.0.1"
     * }
     */
}
