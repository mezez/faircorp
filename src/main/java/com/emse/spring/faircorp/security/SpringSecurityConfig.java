package com.emse.spring.faircorp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig {
    public static final String ROLE_USER = "USER";
    public static final String ROLE_ADMIN = "ADMIN";

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    @Bean
    public UserDetailsService userDetailsService(){
        // password encoder
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        //create regular user
        manager.createUser(
                User.withUsername("user").password(encoder.encode("password")).roles(ROLE_USER).build()
        );

        //create admin user
        manager.createUser(
                User.withUsername("admin").password(encoder.encode("password")).roles(ROLE_ADMIN).build()
        );

        return manager;
    }

    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf().disable()
                .antMatcher("/**")
                .authorizeRequests(authorize -> authorize.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

//    @Bean
//    @Order(1)
//    public SecurityFilterChain filterChainC(HttpSecurity httpSecurity) throws Exception{
//        return httpSecurity
//                .csrf().disable()
//                .antMatcher("/api/**")
//                .authorizeRequests(authorize -> authorize.anyRequest().hasRole(ROLE_ADMIN))
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults())
//                .build();
//    }

    @Bean
    @Order(1)
    public SecurityFilterChain filterChainC(HttpSecurity httpSecurity) throws Exception{


        return httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()  // whitelist Swagger UI resources
                .antMatchers("/api/**").hasRole(ROLE_ADMIN)
                .antMatchers("/**").authenticated()  // require authentication
//                .anyRequest()
//                .authenticated()
                .and()
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
