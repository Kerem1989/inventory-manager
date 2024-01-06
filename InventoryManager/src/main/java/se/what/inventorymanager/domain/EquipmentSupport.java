package se.what.inventorymanager.domain;
import jakarta.persistence.*;
import se.what.inventorymanager.enums.EquipmentStatus;

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

    @OneToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Column(name = "description")
    private String description;


    public EquipmentSupport() {
    }

    public EquipmentSupport(EquipmentStatus status, int support_record, String description,
                            Equipment equipment) {
        this.status = status;
        this.supportRecord = supportRecord;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                ", description=" + description +
                ", equipment=" + (equipment != null ? equipment.getId() : null) +
                '}';
    }
}
