package com.example.TestSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
                        .requestMatchers("/","/login","/join","/joinProc").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                        //유저아이디 하나마다 설정해 줄 수 없기 때문에 와일드 카드(**) 사용하여 아이디가 많더라도 하나씩 설정해주지 않아도 된다.
                        .anyRequest().authenticated()
                        //처리 설정을 하지 않은 다른 모든 경로에 대해서는 anyRequest()로 설정 할 수 있다.
                        //**처리 순서가 위에서 아래로 진행되기 때문에 모든 경로에 대한 처리(anyRequest)는 가장 마지막에 해야한다.
                        //맨 처음에 anyRequest를 하게되면 아래에서 다르게 설정하더라도 모두 anyRequest로 빌드된다.
                );

        http
                //formLogin()으로 로그인 페이지의 URL을 지정해준다.
                //인증이 필요한 페이지에 인증 안된 사용자가 접근 할 경우 자동으로 loginPage()에 들어있는 주소로 이동한다.
                .formLogin((auth) -> auth.loginPage("/login")
                        //html login form태그의 action의 값
                        //loginProcessingUrl()안에 경로를 지정해두면
                        //해당 경로의 POST 요청이 오면 Spring Security가 자동으로 로그인 작업 수행한다.
                        .loginProcessingUrl("/loginProc")
                        .permitAll()
                );

        http
                //csrf가 켜져있으면 POST request로 로그인 할때 csrf값도 같이 보내주어야 로그인이 진행된다
                //csrf를 보내주지 않으면 로그인이 동작하지 않기 때문에 잠깐 disable
                .csrf((auth) -> auth.disable());

        return http.build(); //최종적으로 빌드해서 리턴해준다-> 접근 권한 설정 완료
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }


}
