package com.example.chat.config;

import com.example.chat.security.AppUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author liuyiqian
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
           .authorizeRequests()
             .antMatchers("/login", "/register", "/user/add").permitAll()
             .anyRequest().authenticated()
             .and()
           .formLogin()
             .loginPage("/login")
             .defaultSuccessUrl("/chat")
             .permitAll()
             .and()
           .logout()
             .logoutUrl("/logout")
             .permitAll()
             .and()
           .csrf()
              .disable()
              .antMatcher("/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
