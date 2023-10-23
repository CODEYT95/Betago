package com.bnw.beta.config.security;

import com.bnw.beta.controller.guide.QuestionController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();

        http.authorizeHttpRequests()
                .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers("/educator/**").hasAnyAuthority("ROLE_TEACHER")
                .requestMatchers("/student/**").hasAnyAuthority("ROLE_STUDENT")
                .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER")
                .requestMatchers("/pay/**").hasAnyAuthority("ROLE_TEACHER")
                .requestMatchers("/pay/**").hasAnyAuthority("ROLE_USER")

                .requestMatchers("/**").permitAll()
                .and()
                .csrf().ignoringRequestMatchers(new AntPathRequestMatcher("/**"))
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .deleteCookies();
        return http.build();
    }


        //비밀번호를 암호화시켜줌
        @Bean
        public BCryptPasswordEncoder encodePWD() {
            return new BCryptPasswordEncoder();
        }

}
