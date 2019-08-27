package uk.sky.test.test.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uk.sky.test.test.demo.model.Role;

//used to access role information from database
@Repository("roleRepository")
public interface RoleRpository extends JpaRepository<Role, Integer> {
    Role findByRole(String role);
}
