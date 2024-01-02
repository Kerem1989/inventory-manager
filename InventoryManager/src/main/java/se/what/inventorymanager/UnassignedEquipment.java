package se.what.inventorymanager;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name ="unassignedequipment")
public class UnassignedEquipment {
    @Id
    private int id;

    @Column(name="equipment_name")
    private String name;

    @Column(name="purchase_date")
    private Date purchaseDate;

    @Column(name="purchase_price")
    private double purchasePrice;

    @Enumerated(EnumType.STRING)
    private EquipmentState state;

    @Enumerated(EnumType.STRING)
    private EquipmentType type;

    @Override
    public String toString() {
        return "\nThe " + type + " of model " + name + " is not assigned to any user.";
    }
}