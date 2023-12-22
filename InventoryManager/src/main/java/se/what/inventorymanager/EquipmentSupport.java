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
    private int support_record;

    @ManyToOne
    @JoinColumn(name="equipment_id")
    private Equipment equipment;

    public EquipmentSupport() {
    }

    public EquipmentSupport(EquipmentStatus status, int support_record, Equipment equipment) {
        this.status = status;
        this.support_record = support_record;
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

    public int getSupport_record() {
        return support_record;
    }

    public void setSupport_record(int support_record) {
        this.support_record = support_record;
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
                ", support_record=" + support_record +
                ", equipment=" + (equipment != null ? equipment.getId() : null) +
                '}';
    }
}
