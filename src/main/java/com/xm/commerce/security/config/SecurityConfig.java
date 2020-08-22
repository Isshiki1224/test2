package com.xm.commerce.security.config;

import com.xm.commerce.security.filter.JwtAuthenticationFilter;
import com.xm.commerce.security.filter.JwtAuthorizationFilter;
import com.xm.commerce.security.handler.JwtAccessDeniedHandler;
import com.xm.commerce.security.handler.JwtAuthenticationEntryPoint;
import com.xm.commerce.security.sevice.UserDetailsServiceImpl;
import com.xm.commerce.security.util.JwtTokenUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

import static com.xm.commerce.security.constant.SecurityConstant.AUTH_LOGIN_URL;

/**
 * Spring Security配置类
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserDetailsServiceImpl userDetailsServiceImpl;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private JwtTokenUtils jwtTokenUtils;

    /**
     * 密码编码器
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService createUserDetailsService() {
        return userDetailsServiceImpl;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置自定义的userDetailsService以及密码编码器
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                // 禁用 CSRF
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, AUTH_LOGIN_URL).permitAll()
                .antMatchers(
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/v3/**").permitAll()
                // 指定路径下的资源需要验证了的用户才能访问
                .antMatchers("/**").authenticated()
                .and()
                //添加自定义Filter
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), redisTemplate, jwtTokenUtils))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsServiceImpl, redisTemplate, jwtTokenUtils))
                // 不需要session（不创建会话）
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 授权异常处理
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .accessDeniedHandler(new JwtAccessDeniedHandler());
        // 防止H2 web 页面的Frame 被拦截
//        http.headers().frameOptions().disable();
    }

}
