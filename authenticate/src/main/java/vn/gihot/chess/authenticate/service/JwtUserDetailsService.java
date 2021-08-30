package vn.gihot.chess.authenticate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import vn.gihot.chess.authenticate.model.EntityUser;
import vn.gihot.chess.authenticate.model.User;
import vn.gihot.chess.authenticate.repo.UserRepository;

import java.util.ArrayList;


@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        EntityUser entityUser = userRepository.findByUsername(s);
        if (entityUser == null) {
            throw new UsernameNotFoundException("User not found : " + s);
        }
        return new org.springframework.security.core.userdetails.User(entityUser.getUsername(), entityUser.getPassword(),
                new ArrayList<>());
    }


    //save User to DB follow structure in Entity
    public EntityUser saveToDB(User userr){
        EntityUser newUser = new EntityUser();
        newUser.setUsername(userr.getUsername());
        newUser.setPassword(passwordEncoder.encode(userr.getPassword()));
        return userRepository.save(newUser);
    }

}
