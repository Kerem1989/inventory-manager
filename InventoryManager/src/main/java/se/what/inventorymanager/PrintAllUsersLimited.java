package se.what.inventorymanager;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name ="printalluserslimited")
public class PrintAllUsersLimited {
    @Id
    private int id;
    private String name;
    private String department;
    private String email;
    private String telephone;

    @Override
    public String toString() {
        return "PrintAllUsersLimited{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", department='" + department + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
