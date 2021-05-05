package lt.codeacademy.project.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final String h2ConsolePath;
    private final UserDetailsService userDetailsService;

    public SecurityConfiguration(@Value("${spring.h2.console.path:}") String h2ConsolePath,
                                 UserDetailsService userDetailsService) {
        this.h2ConsolePath = h2ConsolePath;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/blogLogin", "/public/**", "/").permitAll()
                    .antMatchers("/private/**").authenticated()
                    .anyRequest()
                    .authenticated()
                    .and()
                .formLogin()
                    .permitAll()
                    .loginPage("/blogLogin")
                    .loginProcessingUrl("/blogLogin")
                    .defaultSuccessUrl("/public/posts", true)
                    .failureUrl("/blogLogin?error");
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations())
                .antMatchers(h2ConsolePath + "/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(encoder());
    }

    @Bean
    public PasswordEncoder encoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
