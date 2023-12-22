package se.what.inventorymanager;
import jakarta.persistence.*;

@Entity
@Table(name ="equipmentowner")
public class AssignedEquipment {
    @Id
    private int id;
    private String name;
    private String email;
    @Column(name = "equipment_name")
    private String equipmentName;

    @Override
    public String toString() {
        return "\nThe equipment " + equipmentName + " is assigned to user " + name + "." +  "  Contact at " + email + " for more information.\n";
    }
}
