package se.what.inventorymanager;
import jakarta.persistence.*;

@Entity
@Table(name = "equipment_support")
public class EquipmentSupport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EquipmentStatus status;

    @Column(name = "support_record")
    private int supportRecord;

    @ManyToOne
    @JoinColumn(name="equipment_id")
    private Equipment equipment;

    public EquipmentSupport() {
    }

    public EquipmentSupport(EquipmentStatus status, int support_record, Equipment equipment) {
        this.status = status;
        this.supportRecord = supportRecord;
        this.equipment = equipment;
    }

    public int getId() {
        return id;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }

    public int getSupportRecord() {
        return supportRecord;
    }

    public void setSupportRecord(int supportRecord) {
        this.supportRecord = supportRecord;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    @Override
    public String toString() {
        return "EquipmentSupport{" +
                "id=" + id +
                ", status=" + status +
                ", support_record=" + supportRecord +
                ", equipment=" + (equipment != null ? equipment.getId() : null) +
                '}';
    }
}