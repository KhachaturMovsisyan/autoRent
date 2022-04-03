package com.example.autorent.config;

import com.example.autorent.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsImpl userDetails;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                .loginPage("/sign")
                .loginProcessingUrl("/loginProcess")
                .defaultSuccessUrl("/profile", true)
                .usernameParameter("email")
                .passwordParameter("password")
                .failureUrl("/sign?error=true")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)

                .and()

                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers(HttpMethod.GET,"/profile").authenticated()
                .anyRequest().permitAll();

    }


    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/resources/");
        webSecurity.ignoring().antMatchers("/css/");
        webSecurity.ignoring().antMatchers("/fonts/");
        webSecurity.ignoring().antMatchers("/images/");
        webSecurity.ignoring().antMatchers("/js/");
        webSecurity.ignoring().antMatchers("/assets/");
        webSecurity.ignoring().antMatchers("/plugins/");
        webSecurity.ignoring().antMatchers("/pages/");
        webSecurity.ignoring().antMatchers("/web/");
        webSecurity.ignoring().antMatchers("/revolution/");
        webSecurity.ignoring().antMatchers("/pe-icon-7-stroke/");

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


//