package vn.gihot.chess.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.gihot.chess.admin.config.ChessAuthenticationProvider;
import vn.gihot.chess.admin.model.AdminUser;
import vn.gihot.chess.admin.repos.AdminUserRepos;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AdminUserService implements IAdminUserService, UserDetailsService{
    private static final Logger logger = LoggerFactory.getLogger(AdminUserService.class);

    @Autowired
    AdminUserRepos adminUserRepo;

    @Override
    public AdminUser create(AdminUser entity){
        AdminUser adminSaved = adminUserRepo.save(entity);
        return adminSaved;
    }

    @Override
    public AdminUser findById(String id) {
        return adminUserRepo.findById(id).get();
    }

    @Override
    public List<AdminUser> findAll() {
        return adminUserRepo.findAll();
    }

    @Override
    public boolean update(AdminUser entity) {
        AdminUser adminUpdated = adminUserRepo.save(entity);
        return (adminUpdated != null);
    }

    @Override
    public void delete(String id) {
        adminUserRepo.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            AdminUser adminUser = findById(s);
            if (adminUser == null){
                throw new UsernameNotFoundException("User not found: " + s);
            }
            return createUser(adminUser);

        } catch (NoSuchElementException ex){
            throw new UsernameNotFoundException("User not found: " + s );
        }

    }

    private UserDetails createUser(AdminUser adminUser) {
        return new User(adminUser.getEmail(), adminUser.getPassword(), Collections.singleton(createAuthority(adminUser)));
    }

    private GrantedAuthority createAuthority(AdminUser adminUser) {
        return new SimpleGrantedAuthority(adminUser.getRoles().get(0));
    }

    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    @Override
    public Authentication autoLogin(String username, String password) throws AuthenticationException {

        try {
            UserDetails userDetails = loadUserByUsername(username);
            if (userDetails.getPassword().equals(password)) {
                return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            } else {
                throw new AuthenticationCredentialsNotFoundException("Password is wrong!");
            }

        } catch (UsernameNotFoundException ex){
            logger.error(ex.getMessage(), ex);
            throw ex;
        } catch (AuthenticationCredentialsNotFoundException aex){
            logger.error(aex.getMessage(), aex);
            throw aex;
        }
    }
}
