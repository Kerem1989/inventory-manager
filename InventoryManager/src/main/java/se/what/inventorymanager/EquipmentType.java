package se.what.inventorymanager;

public enum EquipmentType {
    laptop,
    screen,
    phone;

    public static EquipmentType fromString(String typeString) {
        for (EquipmentType type : EquipmentType.values()) {
            if (type.name().equalsIgnoreCase(typeString)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid input.");
    }
}
