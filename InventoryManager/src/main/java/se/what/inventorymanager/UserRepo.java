package se.what.inventorymanager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    boolean existsUserByUsernameAndPassword (String username, String password);
}
