package com.farms.fishfarm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.farms.fishfarm.services.UDService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
        private UDService uDService;
        private BCryptPasswordEncoder passwordEncoder;
        private SuccessHandler successHandler;
        

        public WebSecurityConfig(UDService uDService, BCryptPasswordEncoder passwordEncoder, SuccessHandler successHandler) {
                this.uDService = uDService;
                this.passwordEncoder = passwordEncoder;
                this.successHandler = successHandler;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                .httpBasic(httpBasic->{})                
                .authorizeHttpRequests((requests) -> requests
                                                // .requestMatchers("/").permitAll()
                                                .requestMatchers("/superadmin/**").hasRole("SUPER_ADMIN")
                                                .requestMatchers("/formadmin/**").hasRole("FIRM_ADMIN")
                                                .anyRequest().authenticated())
                                .formLogin((form) -> form
                                                .loginPage("/login")
                                                .successHandler(successHandler)
                                                //.defaultSuccessUrl("/main",true)
                                                .permitAll())
                                .logout((logout) -> logout
                                                .permitAll()
                                                .logoutSuccessUrl("/login"));

                return http.build();
        }

        @Bean
        public DaoAuthenticationProvider daoAuthenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setPasswordEncoder(passwordEncoder);
                provider.setUserDetailsService(uDService);
                
                return provider;


        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration aconfig) throws Exception{
                return aconfig.getAuthenticationManager();
        }

}
