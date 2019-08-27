package uk.sky.test.test.demo.model;


import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="userId")
    private int id;
        //setting the email column
    @Column(name="email")
    @Email(message = "*Can you please provide a valid email!")
    @NotEmpty(message = "*The email cannot be empty")
    private String email;
        //setting the passowrd column
    @Column(name="password")
    @Length(min=5, message = "*Please provide a valid password thats at least 5 characters")
    @NotEmpty(message = "*Please provide a password")
    private String password;
        //setting the first name column
    @Column(name="name")
    @NotEmpty(message = "*Please provide your name!")
    private String name;
        //setting the last name column
    @Column(name="last name")
    @NotEmpty(message = "*Please priovide your last name!")
    private String lastName;
    @Column(name="active")
    private int active;

        //defining joins in database tables!
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles;

    public static class UserService {
        private UserRepository userRepository;
        private RoleRpository roleRpository;
        private BCryptPasswordEncoder bCryptPasswordEncoder;

                //simple constructor placing objects
        @Autowired
        public UserService(UserRepository userRepository, RoleRpository roleRpository, BCryptPasswordEncoder bCryptPasswordEncoder){
            this.userRepository = userRepository;
            this.roleRpository = roleRpository;
            this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        }
            //retrieve the email from the repository which gets it from the database
        public User findUserByEmail(String email){
            return userRepository.findByEmail(email);
        }

        public void saveUser(User user){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setActive(1);
            Role userRole = roleRpository.findByRole(("ADMIN"));
            user.setRoles((new HashSet<Role>(Arrays.asList(userRole))));
        }
    }
}
