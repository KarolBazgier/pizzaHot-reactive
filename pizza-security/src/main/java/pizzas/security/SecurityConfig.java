package pizzas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers(HttpMethod.POST, "/api/ingredients").permitAll()
                .requestMatchers("/design", "orders/**").permitAll()
                .requestMatchers( "/ingredients/**").permitAll()
                .requestMatchers("/**").access("permitAll")
                .and()
                .formLogin().loginPage("/login")
                .and()
                .httpBasic().realmName("PIZZA")
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .csrf().ignoringRequestMatchers(
                        "/h2-console/**", "/ingredients/**", "/design", "/orders/**", "/api/**"
                )
                .and()
                .headers().frameOptions().sameOrigin();
       return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder()); // Stwórz nową instancję
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

