package com.itmsg.episode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import com.itmsg.episode.security.JwtAuthenticationEntryPoint;
import com.itmsg.episode.security.JwtAuthorizationTokenFilter;
import com.itmsg.episode.security.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    
    public static final String CRYPTO_SALT = "c046e03dcb8c2af4";

    public static final String CRYPTO_SECRET = "FAAOCAQ8AMIIBCgKCAQEAr5ui+5AA8v1LivSYNGdZ0O76SBgktU3CjG+BcUb0m/+n8AJP//Xp3OS62niQyRB5L";

    public static final String JWT_HEADER = "EpisodeAuthorization";
    
    public static final String JWT_SECRET = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr5ui+5AA8v1LivSYNGdZ0O76SBgktU3CjG+BcUb0mJ1fl34+So75bRI3/+n8AJP//Xp3OS62niQyRB5LEdCr7ox5RAQlLHFkkJHQysi2/236Br8ZDiM1AfT4Hz5AQ9D4Pk5H/n6stKuS2ZffHAWAW7/qK7PC6z4ncSlhVYr/zXNj7HVx9YFP7gZx6faaar6UvV96vX8W4xcITxvLpaCBnF9TeOUxLHYI3tmnxmE8gRQXpdCH7X3IB3f4QfVCMIIrSD8d3si5w5VArlEMRQ9ITEOA0Iq8VJKGjlu+haycTgIPJD3yGhU7Zb3EQfBZvQGC31beogycEf9imnvHDW4NvQIDAQAB";
    
    public static final int JWT_EXPIRATION = 60 * 60 * 12;  // 12 hours minutes
    
    public static final String JWT_ROUTE_AUTHENTICATION_PATH = "/api/authentication/signin";

    public static final String JWT_ROUTE_AUTHENTICATION_REFRESH = "/api/authentication/refresh";

    public static final String JWT_ROUTE_AUTHENTICATION_SIGNOUT = "/api/authentication/signout";

    public static final String JWT_ROUTE_AUTHENTICATION_USER = "/api/authentication/user";

    public static final String AUTHORIZATION_USER = "authrizeduser";

    @Resource
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Resource
    private JwtUserDetailsService jwtUserDetailsService;

    @Resource
    JwtAuthorizationTokenFilter jwtAuthorizationTokenFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBean());
    }

    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TextEncryptor textEncryptor(){
        return Encryptors.queryableText(WebSecurityConfig.CRYPTO_SECRET,WebSecurityConfig.CRYPTO_SALT);
    }

    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            .antMatchers("/api/system/scheduler/*/startScheduler").permitAll()
            .antMatchers("/api/system/scheduler/*/stopScheduler").permitAll()
            .antMatchers("/api/authentication/**").permitAll()
            .antMatchers("/api/system/application/**").permitAll()
            .antMatchers("/api/system/common/**").permitAll()
            .antMatchers("/api/system/label").permitAll()
            .antMatchers("/ws/*.wsdl").permitAll()
            .antMatchers("/async/*.xml").permitAll()
            .antMatchers("/static/**").permitAll()
            .antMatchers("/").permitAll()
            .antMatchers("/hello").permitAll()
            .antMatchers("/test").permitAll()
            .anyRequest().authenticated();

        //   Authentication by KAI SSO
        // httpSecurity.addFilterBefore(kaiAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        
        //httpSecurity.addFilterBefore(kaiWSAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        
        //   Authentication by EPISODE JWT
        httpSecurity.addFilterBefore(jwtAuthorizationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        
        httpSecurity
            .headers()
            .frameOptions().sameOrigin()
            .cacheControl();
    }


    //@Override
    //public void configure(WebSecurity web) throws Exception {
    //    web.ignoring().antMatchers(HttpMethod.POST,WebSecurityConfig.JWT_ROUTE_AUTHENTICATION_PATH);
    //    web.ignoring().antMatchers(HttpMethod.GET,"/","/*.html","/favicon.ico","/**/*.html","/**/*.css","/**/*.js");
        
        // Un-secure H2 Database (for testing purposes, H2 console shouldn't be unprotected in production)
        //.and()
        //.ignoring()
        //.antMatchers("/h2-console/**/**");
    //}
    
}
