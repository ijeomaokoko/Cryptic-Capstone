package learn.crypticRadio.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    private final JwtConverter converter;

    public SecurityConfig(JwtConverter converter) {
        this.converter = converter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors();

        http.authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/create_account").permitAll()
                .antMatchers(HttpMethod.GET, "/user", "/user/*")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/message", "/message/*", "/message/find/*/*")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/message", "/message/*")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/message", "/message/*")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/message", "/message/*")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/message/room/*")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers("/room").permitAll()
                .antMatchers(HttpMethod.GET, "/user", "/user/*")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/room", "/room/*")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/room/user", "/room/user/*")
                .hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/room", "/room/*")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/room", "/room/*")
                .hasAnyRole( "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/room", "/room/*")
                .hasAnyRole("ADMIN")
                .antMatchers(HttpMethod.GET,
                        "/cryptics", "/api/chat/*").permitAll()
                .antMatchers(HttpMethod.POST,
                        "/cryptic").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,
                        "/cryptic/*").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE,
                        "/cryptic/*").hasAnyRole("ADMIN")

                .antMatchers("/**").denyAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), converter))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }
}