package com.cardealer;

import java.util.Objects;

/**
 * Enumerates supported fuel type options with display labels and helpers
 * to parse user input safely.
 */
public enum FuelType {
    PETROL("Petrol"),
    DIESEL("Diesel"),
    HYBRID("Hybrid"),
    ELECTRIC("Electric");

    private final String displayName;

    FuelType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Attempts to parse the provided value into a fuel type, accepting either
     * the enum name or the human-friendly display string, case-insensitively.
     *
     * @param value raw input value
     * @return a matching FuelType, or null if no match is found
     */
    public static FuelType fromString(String value) {
        if (value == null) {
            return null;
        }
        String normalized = value.trim();
        if (normalized.isEmpty()) {
            return null;
        }
        for (FuelType type : values()) {
            if (type.displayName.equalsIgnoreCase(normalized) || type.name().equalsIgnoreCase(normalized)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Parses the provided value to a {@link FuelType} or returns the supplied default.
     *
     * @param value raw input value
     * @param defaultValue fallback to use when parsing fails
     * @return parsed FuelType or {@code defaultValue} when no match is found
     */
    public static FuelType fromStringOrDefault(String value, FuelType defaultValue) {
        FuelType parsed = fromString(value);
        return parsed != null ? parsed : Objects.requireNonNull(defaultValue, "Default fuel type cannot be null");
    }
}
