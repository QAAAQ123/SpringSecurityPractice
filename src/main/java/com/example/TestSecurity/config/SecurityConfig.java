package com.example.TestSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //spring boot의 config로 등록
@EnableWebSecurity //spring security에서 관리됨
public class SecurityConfig {
    @Bean
    //SecurityFilterChain는 인터페이스이다
    //매개변수로 HttpSecurity를 받아온다
    //리턴 타입은 HttpSecurity의 builder이다.
    //경로별 인가 설정 메소드
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((auth) -> auth//람다식으로 작성해야 한다.
                                //requestMatchers()로 경로별 권한,인가 설정을 한다
                        .requestMatchers("/","/login").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                        //유저아이디 하나마다 설정해 줄 수 없기 때문에 와일드 카드(**) 사용하여 아이디가 많더라도 하나씩 설정해주지 않아도 된다.
                        .anyRequest().authenticated()
                        //처리 설정을 하지 않은 다른 모든 경로에 대해서는 anyRequest()로 설정 할 수 있다.
                        //**처리 순서가 위에서 아래로 진행되기 때문에 모든 경로에 대한 처리(anyRequest)는 가장 마지막에 해야한다.
                        //맨 처음에 anyRequest를 하게되면 아래에서 다르게 설정하더라도 모두 anyRequest로 빌드된다.
                );
        //

        return http.build(); //최종적으로 빌드해서 리턴해준다-> 접근 권한 설정 완료
    }

}
