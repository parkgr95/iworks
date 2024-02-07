package com.example.iworks.global.config;

import com.example.iworks.domain.user.repository.UserRepository;
import com.example.iworks.global.filter.CustomCorsFilter;
import com.example.iworks.global.filter.JwtAuthenticationFilter;
import com.example.iworks.global.filter.JwtAuthorizationFilter;
import com.example.iworks.global.filter.JwtExceptionFilter;
import com.example.iworks.global.model.Response;
import com.example.iworks.global.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터(SecurityConfig)가 스프링 필터체인에 등록됨.
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;
    private final CustomCorsFilter corsFilter;

    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder sharedObject = http.getSharedObject(AuthenticationManagerBuilder.class);

        sharedObject.userDetailsService(userDetailsService)
                .passwordEncoder(encodePwd());
        AuthenticationManager authenticationManager = sharedObject.build();

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션을 stateless로 관리
                .httpBasic(AbstractHttpConfigurer::disable) // 기본적인 로그인 기능 사용 x
                .formLogin(AbstractHttpConfigurer::disable)
                // 특정 URL에 대한 권한 설정.
                .authorizeHttpRequests((authorizeRequests) -> {
                    authorizeRequests
                            .requestMatchers("/api/user/join").anonymous() // 나중에 어드민으로 바꿈
                            .requestMatchers("/api/user/login").anonymous()

                            .requestMatchers("/api/user/**").authenticated()

                            .requestMatchers("/api/user/join").permitAll()
                            .requestMatchers("/api/user/login").permitAll()

                            .requestMatchers("/api/leader/**")
                            .hasAnyRole("ADMIN", "LEADER", "CEO")

                            .requestMatchers("/api/admin/**")
                            .hasRole("ADMIN")

                            .anyRequest().permitAll();
                })
                .authenticationManager(authenticationManager)
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/api/user/login"))
                .addFilterBefore(corsFilter, SecurityContextHolderFilter.class)
                .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtProvider))
                .addFilter(new JwtAuthorizationFilter(authenticationManager, userRepository, jwtProvider))
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthorizationFilter.class)
                .build();
    }
}

