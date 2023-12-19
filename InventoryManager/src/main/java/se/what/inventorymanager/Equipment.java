package se.what.inventorymanager;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name="equipment")
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="equipment_name")
    private String name;

    @Column(name="purchase_date")
    private Date purchaseDate;

    @Column(name="purchase_price")
    private int purchasePrice;

    @Column(name="purchased_by")
    private int purchasedBy;

    @Enumerated(EnumType.STRING)
    private EquipmentState state;

    @Enumerated(EnumType.STRING)
    private EquipmentType type;

    @ManyToOne
    @JoinColumn(name = "purchased_by", referencedColumnName = "id")
    private User user;

    public Equipment() {}

    public Equipment(String name, Date purchaseDate, int purchasePrice, int purchasedBy,
                     EquipmentState state, EquipmentType type) {
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.purchasedBy = purchasedBy;
        this.state = state;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
    public int getPurchasePrice() {
        return purchasePrice;
    }
    public void setPurchasePrice(int purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
    public int getPurchasedBy() {
        return purchasedBy;
    }
    public void setPurchasedBy(int purchasedBy) {
        this.purchasedBy = purchasedBy;
    }
    public EquipmentState getState() {
        return state;
    }
    public void setState(EquipmentState state) {
        this.state = state;
    }
    public EquipmentType getType() {
        return type;
    }
    public void setType(EquipmentType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", purchasePrice=" + purchasePrice +
                ", purchasedBy=" + purchasedBy +
                ", state=" + state +
                ", type=" + type +
                '}';
    }
}
