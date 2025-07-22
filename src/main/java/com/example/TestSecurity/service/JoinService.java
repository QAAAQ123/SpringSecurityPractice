package com.example.TestSecurity.service;

import com.example.TestSecurity.dto.JoinDTO;
import com.example.TestSecurity.entity.UserEntity;
import com.example.TestSecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void JoinProcess(JoinDTO joinDTO){
        UserEntity data = new UserEntity();

        //db에 이미 동일한 username을 가진 user가 존재하는지 확인해야함
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if(isUser) {
            System.out.println("username \"" + joinDTO.getUsername() +"\" is already existed");
            return;
        }

        data.setUsername(joinDTO.getUsername());
        //비밀번호는 반드시 암호화 하여 저장해야 한다.
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        //ROLE_ 뒤에 알맞은 역할 문자열 작성
        data.setRole("USER_ADMIN");

        userRepository.save(data);

    }
}
