package es.iesrafaelalberti.daw.dwes.jparestdemo;

import es.iesrafaelalberti.daw.dwes.jparestdemo.security.JWTAuthorizationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@SpringBootApplication
public class JpaRestDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaRestDemoApplication.class, args);
    }

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.cors().and().csrf().disable()
                    .addFilterAfter(new JWTAuthorizationFilter(getApplicationContext()), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                        .antMatchers("/login/**").permitAll()
                        .antMatchers("/teachers/agesum**").permitAll()
                        .antMatchers("/logout/**").authenticated()
                        .antMatchers("/students/**").authenticated()
                        .antMatchers("/teachers/**").hasAnyRole("ADMIN", "GOD")
                        .antMatchers("/").authenticated();

        }

//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.inMemoryAuthentication()
//                    .withUser("cristian").password(getPasswordEncoder().encode("melocreo")).roles("loser")
//                    .and()
//                    .withUser("antonio").password(getPasswordEncoder().encode("haydiosmio")).roles("admin", "loser")
//                    .and()
//                    .withUser("javier").password(getPasswordEncoder().encode("pestillo")).roles("god");
//        }

        @Bean
        public PasswordEncoder getPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }

    }

}
