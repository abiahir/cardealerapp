package com.cardealer;

import java.util.Objects;

/**
 * Enumerates gearbox options exposed to both the CLI and JSON payloads.
 */
public enum GearboxType {
    AUTOMATIC("Automatic"),
    MANUAL("Manual");

    private final String displayName;

    GearboxType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static GearboxType fromString(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        if (normalized.isEmpty()) {
            return null;
        }
        for (GearboxType type : values()) {
            if (type.displayName.equalsIgnoreCase(normalized) || type.name().equalsIgnoreCase(normalized)) {
                return type;
            }
        }
        return null;
    }

    public static GearboxType fromStringOrDefault(String value, GearboxType defaultValue) {
        GearboxType parsed = fromString(value);
        return parsed != null ? parsed : Objects.requireNonNull(defaultValue, "Default gearbox cannot be null");
    }
}
