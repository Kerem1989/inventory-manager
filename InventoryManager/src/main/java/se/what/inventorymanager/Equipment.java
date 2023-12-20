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
    private double purchasePrice;

    @Enumerated(EnumType.STRING)
    private EquipmentState state;

    @Enumerated(EnumType.STRING)
    private EquipmentType type;

    @ManyToOne
    @JoinColumn(name = "purchased_by", referencedColumnName = "id")
    private User user;

    public Equipment() {}

    public Equipment(String name, Date purchaseDate, double purchasePrice,
                     EquipmentState state, EquipmentType type) {
        this.name = name;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.state = state;
        this.type = type;
    }

    public int getId() {
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

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", purchasePrice=" + purchasePrice +
                ", state=" + state +
                ", type=" + type +
                ", user=" + user.getName() +
                '}';
    }
}
