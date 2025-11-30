package com.cardealer;

import java.util.Objects;

/**
 * Enumerates valid ULEZ (Ultra Low Emission Zone) status options.
 */
public enum UlezStatus {
    YES("Yes"),
    NO("No"),
    UNKNOWN("Unknown");

    private final String displayName;

    UlezStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static UlezStatus fromString(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        if (normalized.isEmpty()) {
            return null;
        }
        for (UlezStatus status : values()) {
            if (status.displayName.equalsIgnoreCase(normalized) || status.name().equalsIgnoreCase(normalized)) {
                return status;
            }
        }
        return null;
    }

    public static UlezStatus fromStringOrDefault(String value, UlezStatus defaultValue) {
        UlezStatus parsed = fromString(value);
        return parsed != null ? parsed : Objects.requireNonNull(defaultValue, "Default ULEZ status cannot be null");
    }
}
