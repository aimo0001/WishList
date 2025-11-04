package com.example.digitalwishlist;

import com.example.digitalwishlist.Model.User;
import com.example.digitalwishlist.Repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    UserDetailsService userDetailsService(UserRepository users) {
        return username -> {
            User u = users.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Not found"));
            return User.withUsername(u.getEmail())
                    .password(u.getPasswordHash())
                    .roles("USER")
                    .build();
        };
    }

    @Bean
    DaoAuthenticationProvider authProvider(UserDetailsService uds, PasswordEncoder enc) {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(uds);
        p.setPasswordEncoder(enc);
        return p;
    }

    @Bean
    SecurityFilterChain web(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/login", "/createUser", "/w/**", "/css/**", "/static/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(l -> l.loginPage("/login").defaultSuccessUrl("/wishlist", true).permitAll())
                .logout(l -> l.logoutUrl("/logout").logoutSuccessUrl("/login"))
                .csrf(Customizer.withDefaults());
        return http.build();
    }
}
