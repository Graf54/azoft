package my.test.azoft.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncriptionConfig {
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        // TODO: 23.07.2019 make normal Encoder 
        return NoOpPasswordEncoder.getInstance();
//        return new BCryptPasswordEncoder(8);
    }
}
