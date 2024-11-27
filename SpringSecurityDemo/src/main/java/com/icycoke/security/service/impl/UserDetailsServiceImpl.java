package com.icycoke.security.service.impl;

import com.icycoke.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author: JianJian
 * @date: 2023-11-21 10:59
 * @description:
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if (username.equals("jianjian")){
//            String password = "jianjian";
//            String encodePassword = passwordEncoder.encode(password);
//
//            UserDetails userDetails = new User(username,encodePassword,
//                    AuthorityUtils.commaSeparatedStringToAuthorityList("jian1,jian2"));
//            return userDetails;
//        }else {
//            throw new UsernameNotFoundException("用户不存在");
//        }

        com.icycoke.pojo.User user = userMapper.selectByUsername(username);

        if (null == user){
            throw new UsernameNotFoundException("用户不存在");
        }
        String passWord = user.getPassWord();

        UserDetails userDetails = new User(username, passWord, AuthorityUtils.commaSeparatedStringToAuthorityList("jian1, jian2"));

        return userDetails;

    }
}
