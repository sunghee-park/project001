package org.example.config.auth;

import lombok.RequiredArgsConstructor;
import org.example.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable() //h2-console 화면을 사용하기 위해 해당 옵션 disable
                .and()
                .authorizeRequests()//URL별 권한관리 설정 옵션 시작점
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) //권한 관리 대상 지정옵션
                .anyRequest().authenticated() //설정된 값 이외 나머지 URL.모두 인증된 사용자(로그인후)만 허용
                .and()
                .logout()//로그아웃 기능 설정 진입점.
                .logoutSuccessUrl("/")// 로그아웃시 / 주소로 이동
                .and()
                .oauth2Login() //Oauth h로그인 기능 설정 진입점
                .userInfoEndpoint() // 로그인 성공후 사용자 정보 가져올 설정 담당
                .userService(customOAuth2UserService); //소셜 로그인 성공시 후속 인터페인스 구현체 등록
    }
}
