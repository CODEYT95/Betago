package com.bnw.beta.service.member;

import com.bnw.beta.domain.member.dao.UserMapper;
import com.bnw.beta.domain.member.dto.MemberDTO;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Enumeration;

// 시큐리티 설정에서
//formLogin((formLogin)->formLogin.loginPage("/login") 로긴 요청이 오면
//자동으로 UserDetailsService 타입으로 IOC 되어있는 loadUserByUsername 함수가 실행됨
@Service
public class MemberSecurityService implements UserDetailsService {

    private final UserMapper userMapper;
    /*@Autowired
    마이바티스로 변경 !! private UserRepository userRepository;*/

    @Autowired
    public MemberSecurityService(UserMapper userMapper){
        this.userMapper=userMapper;
    }


    //수업내용 UserSecurityService와 동일파일

    @Override
    public UserDetails loadUserByUsername(String member_id) throws UsernameNotFoundException {


        MemberDTO memberDTO = userMapper.findByMemberId(member_id);

        if (memberDTO == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다" + member_id);

        }
        return User
                .withUsername(memberDTO.getMember_id())
                .password(memberDTO.getMember_pw())
                .authorities(memberDTO.getRole())
                .build();
    }
        //중요 (String username) 여기서 username은 로그인폼 name의 파라미터 이름 현재 username
       /* 마이바티스 변경 !!
        User userEntity = userRepository.findByUsername(username)
        if(userEntity!=null){
        return new PrincipalDetails(userEntity);
        }
        값이 존재한다면 저장된 값이
        시큐리티 session (내부 Authentication(내부 UserDetails)) 들어감

        (UserRepository 클래스에 필드 선언 필요
        public User findByUsername(String username);
        findBy는 규칙 -> username은 문법
        select * from user where username= ?
        
        public User findByEmail(String username);
        select * from user where email=> ?
        jpa기능인데 마이바티스로 변경해야해
        */
}
