package com.cardealer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;
import java.util.Objects;

/**
 * Represents dealer contact information.
 * This is an immutable record that provides type-safe dealer data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DealerDetails(
    String name,
    String phone,
    String email,
    String website
) {
    /**
     * Default dealer details used when no dealer information is provided.
     */
    public static final DealerDetails DEFAULT = new DealerDetails(
        "Your Dealership",
        "0000 000 0000",
        "sales@example.com",
        "www.example.com"
    );

    /**
     * Compact constructor for validation.
     * Ensures that null values are replaced with defaults.
     */
    public DealerDetails {
        // Records don't allow reassignment in compact constructor
        // So we validate but defaults are handled in factory methods
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Dealer name cannot be null or blank");
        }
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Dealer phone cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Dealer email cannot be null or blank");
        }
        if (website == null || website.isBlank()) {
            throw new IllegalArgumentException("Dealer website cannot be null or blank");
        }
    }

    /**
     * Creates a DealerDetails from a map (typically from JSON).
     * Uses default values for any missing or null fields.
     * 
     * @param data the map containing dealer data
     * @return a new DealerDetails instance, or DEFAULT if data is null/empty
     */
    public static DealerDetails fromMap(Map<String, Object> data) {
        if (data == null || data.isEmpty()) {
            return DEFAULT;
        }
        
        String name = getString(data, "name", DEFAULT.name);
        String phone = getString(data, "phone", DEFAULT.phone);
        String email = getString(data, "email", DEFAULT.email);
        String website = getString(data, "website", DEFAULT.website);
        
        // If all values are defaults, return the default instance
        if (name.equals(DEFAULT.name) && phone.equals(DEFAULT.phone) 
            && email.equals(DEFAULT.email) && website.equals(DEFAULT.website)) {
            return DEFAULT;
        }
        
        return new DealerDetails(
            name.isBlank() ? DEFAULT.name : name,
            phone.isBlank() ? DEFAULT.phone : phone,
            email.isBlank() ? DEFAULT.email : email,
            website.isBlank() ? DEFAULT.website : website
        );
    }

    /**
     * Safely extracts a string value from a map.
     * 
     * @param map the map to extract from
     * @param key the key to look up
     * @param defaultValue the default value if key is missing or null
     * @return the string value or default
     */
    private static String getString(Map<String, Object> map, String key, String defaultValue) {
        Object value = map.get(key);
        return value != null ? value.toString() : defaultValue;
    }
}
