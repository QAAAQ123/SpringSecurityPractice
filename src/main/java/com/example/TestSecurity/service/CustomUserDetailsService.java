package com.example.TestSecurity.service;

import com.example.TestSecurity.dto.CustomUserDetails;
import com.example.TestSecurity.entity.UserEntity;
import com.example.TestSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //DB에서 가져온 데이터와 클라이언트에서 받아온 데이터 검증하는 로직 override
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //매개변수의 username-> 클라이언트에서 받아온 username
        UserEntity userData = userRepository.findByUsername(username);

        if(userData != null){
            //CustomUserDetails 클래스 -> 받아온 username을 통해 만든 userDate를 security config에
            //넘겨주어서 각종 검증을 한뒤 세션에 데이터를 저장하는 클래스
            return new CustomUserDetails(userData);
        }

        return null;
    }
}
