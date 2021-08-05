package vn.gihot.chess.admin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import vn.gihot.chess.admin.core.PBKDF2Encoder;
import vn.gihot.chess.admin.service.AdminUserService;

@Component
public class ChessAuthenticationProvider implements AuthenticationProvider {
    private static final Logger logger = LoggerFactory.getLogger(ChessAuthenticationProvider.class);

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    PBKDF2Encoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        logger.info("Account {} is accessing into system...", name);
        return adminUserService.autoLogin(name, passwordEncoder.encode(password));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
