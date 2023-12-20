package se.what.inventorymanager;

public enum EquipmentType {
    LAPTOP,
    PHONE,
    MONITOR,
    PROJECTOR,
    CHAIR;

    public static EquipmentType fromString(String typeString) {
        for (EquipmentType type : EquipmentType.values()) {
            if (type.name().equalsIgnoreCase(typeString)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid input.");
    }
}
