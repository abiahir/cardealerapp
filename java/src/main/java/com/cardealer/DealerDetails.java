package com.cardealer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Map;

/**
 * Represents dealer contact information.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DealerDetails(
    String name,
    String phone,
    String email,
    String website
) {
    public static final DealerDetails DEFAULT = new DealerDetails(
        "Your Dealership",
        "0000 000 0000",
        "sales@example.com",
        "www.example.com"
    );

    public DealerDetails {
        // Compact constructor for validation
        if (name == null) name = DEFAULT.name;
        if (phone == null) phone = DEFAULT.phone;
        if (email == null) email = DEFAULT.email;
        if (website == null) website = DEFAULT.website;
    }

    public static DealerDetails fromMap(Map<String, Object> data) {
        if (data == null || data.isEmpty()) {
            return DEFAULT;
        }
        return new DealerDetails(
            getString(data, "name", DEFAULT.name),
            getString(data, "phone", DEFAULT.phone),
            getString(data, "email", DEFAULT.email),
            getString(data, "website", DEFAULT.website)
        );
    }

    private static String getString(Map<String, Object> map, String key, String defaultValue) {
        Object value = map.get(key);
        return value != null ? value.toString() : defaultValue;
    }
}

