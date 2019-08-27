package uk.sky.test.test.demo.model;
    //used to access user information from database

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.sky.test.test.demo.model.User;

@Repository("userRepository")
public interface  UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
