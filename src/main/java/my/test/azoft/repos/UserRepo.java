package my.test.azoft.repos;


import my.test.azoft.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String login);

    Page<User> findAll(Pageable pageable);

    Page<User> findByUsernameContaining(String username, Pageable pageable);

    @Query(value = "SELECT * FROM USER usr WHERE usr.ID not in(SELECT USER_ID from USER_ROLES where ROLE_ID=?1);",
            countQuery = "SELECT count(*) FROM USER usr WHERE usr.ID not in(SELECT USER_ID from USER_ROLES where ROLE_ID=?1);"
            , nativeQuery = true)
    Page<User> findAllExceptRoleId(int roleId, Pageable pageable);
}
