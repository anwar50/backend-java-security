package uk.sky.test.test.demo.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uk.sky.test.test.demo.model.RoleRpository;
import uk.sky.test.test.demo.model.UserRepository;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {
    private UserRepository userRepository;
    private RoleRpository roleRpository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRpository roleRpository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.roleRpository = roleRpository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
}


