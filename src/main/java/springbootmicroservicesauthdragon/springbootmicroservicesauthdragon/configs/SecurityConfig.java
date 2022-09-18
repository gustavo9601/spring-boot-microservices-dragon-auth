package springbootmicroservicesauthdragon.springbootmicroservicesauthdragon.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf().disable() // Dehabilitamos el csrf
                .authorizeRequests().anyRequest().permitAll(); // Permitimos el acceso ya que debe ser publico para autenticar


        return httpSecurity.build();
    }


    // Bean para la encriptacion de la password
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
