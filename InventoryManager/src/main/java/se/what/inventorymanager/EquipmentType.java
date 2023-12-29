package se.what.inventorymanager;

public enum EquipmentType {
    laptop,
    phone,
    monitor,
    projector,
    office_chair;

    public static EquipmentType fromString(String typeString) {
        for (EquipmentType type : EquipmentType.values()) {
            if (type.name().equalsIgnoreCase(typeString)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid input.");
    }
}
