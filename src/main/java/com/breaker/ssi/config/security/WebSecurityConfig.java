package com.breaker.ssi.config.security;


import com.breaker.ssi.config.filter.JwtFilter;
import com.breaker.ssi.config.filter.JwtLoginFilter;
import com.breaker.ssi.utils.annotation.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 通过@Bean注入配置文件中的参数
    @Bean
    JwtLoginFilter getJwtLoginFilter() throws Exception {
        return new JwtLoginFilter("/login", authenticationManager());
    }
    // 通过@Bean注入配置文件中的参数
    @Bean
    JwtFilter getJwtFilter(){
        return new JwtFilter();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/doc.html", "/webjars/**","/swagger-resources/configuration/ui","/swagge‌​r-ui.html").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                // 不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()  //验证不通过的配置
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
//                .accessDeniedHandler(new MyAccessDeniedHandler())
                .and()
                .cors()
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(getJwtLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(getJwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
