package com.wedsite.sale.common.custom;


import com.wedsite.sale.entity.RoleEntity;
import com.wedsite.sale.entity.UserEntity;
import com.wedsite.sale.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


/**
 * The type Custom user details service.
 *
 * @author Hoapham
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Load user Database with username
     *
     * @param userName
     * @return return one userdetails have Username ,password ,authorities
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //Use JPA find  User name
        UserEntity user = userRepository.findByUserName(userName);
        //If User Not found => User not found Exception
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        //find roles for user Set only
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        Set<RoleEntity> roles = user.getRoles();
        for (RoleEntity role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
        }
        //return one userdetails have Username ,password ,authorities
        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getEncrytedPassword(), grantedAuthorities);
    }

}
