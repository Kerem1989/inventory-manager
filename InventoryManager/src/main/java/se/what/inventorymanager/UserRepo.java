package se.what.inventorymanager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    User getUserByUsernameAndPassword(String username, String password);
}
