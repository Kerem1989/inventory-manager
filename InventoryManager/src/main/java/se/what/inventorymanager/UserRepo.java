package se.what.inventorymanager;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {
    User getUserByUsernameAndPassword(String username, String password);
    boolean existsUserById(int id);

}
