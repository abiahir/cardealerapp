package com.cardealer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a vehicle listing with all its details.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleListing {
    private static final Set<String> FUEL_TYPES = Set.of("Petrol", "Diesel", "Hybrid", "Electric");
    private static final Set<String> GEARBOX_TYPES = Set.of("Automatic", "Manual");
    private static final Set<String> ULEZ_CHOICES = Set.of("Yes", "No", "Unknown");

    private String title;
    private String price;
    private String registration;
    private String year;
    private String gearbox;
    private String engineSize;
    private String fuelType;
    private String mileage;
    private String ulez;
    private String motExpiry;
    private String owners;
    private List<String> specs;
    private DealerDetails dealer;

    public VehicleListing() {
        this.specs = new ArrayList<>();
        this.dealer = DealerDetails.DEFAULT;
    }

    public VehicleListing(String title, String price, String registration, String year,
                         String gearbox, String engineSize, String fuelType, String mileage,
                         String ulez, String motExpiry, String owners, List<String> specs,
                         DealerDetails dealer) {
        this.title = title;
        this.price = price;
        this.registration = registration;
        this.year = year;
        this.gearbox = gearbox;
        this.engineSize = engineSize;
        this.fuelType = fuelType;
        this.mileage = mileage;
        this.ulez = ulez;
        this.motExpiry = motExpiry;
        this.owners = owners;
        this.specs = specs != null ? new ArrayList<>(specs) : new ArrayList<>();
        this.dealer = dealer != null ? dealer : DealerDetails.DEFAULT;
    }

    public static VehicleListing fromMap(Map<String, Object> data) {
        VehicleListing vehicle = new VehicleListing();
        
        vehicle.title = getString(data, "title", "Vehicle Title");
        vehicle.price = getString(data, "price", "Price on enquiry");
        vehicle.registration = getString(data, "registration", "Registration");
        vehicle.year = getString(data, "year", "Year");
        vehicle.gearbox = validateChoice(getString(data, "gearbox", "Automatic"), 
                                         GEARBOX_TYPES, "Gearbox");
        vehicle.engineSize = getString(data, "engine_size", "2.0 L");
        vehicle.fuelType = validateChoice(getString(data, "fuel_type", "Diesel"),
                                          FUEL_TYPES, "Fuel type");
        vehicle.mileage = getString(data, "mileage", "0");
        vehicle.ulez = validateChoice(getString(data, "ulez", "Unknown"),
                                     ULEZ_CHOICES, "ULEZ");
        vehicle.motExpiry = getString(data, "mot_expiry", "Unknown");
        vehicle.owners = getString(data, "owners", "Unknown");
        
        @SuppressWarnings("unchecked")
        List<Object> specsList = (List<Object>) data.get("specs");
        if (specsList != null) {
            vehicle.specs = specsList.stream()
                .map(Object::toString)
                .toList();
        }
        
        @SuppressWarnings("unchecked")
        Map<String, Object> dealerMap = (Map<String, Object>) data.get("dealer");
        vehicle.dealer = DealerDetails.fromMap(dealerMap);
        
        return vehicle;
    }

    private static String getString(Map<String, Object> map, String key, String defaultValue) {
        Object value = map.get(key);
        return value != null ? value.toString() : defaultValue;
    }

    private static String validateChoice(String value, Set<String> allowed, String label) {
        if (!allowed.contains(value)) {
            throw new IllegalArgumentException(
                String.format("%s must be one of: %s", label, String.join(", ", allowed))
            );
        }
        return value;
    }

    // Getters and setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getRegistration() { return registration; }
    public void setRegistration(String registration) { this.registration = registration; }

    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }

    public String getGearbox() { return gearbox; }
    public void setGearbox(String gearbox) { this.gearbox = gearbox; }

    public String getEngineSize() { return engineSize; }
    public void setEngineSize(String engineSize) { this.engineSize = engineSize; }

    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }

    public String getMileage() { return mileage; }
    public void setMileage(String mileage) { this.mileage = mileage; }

    public String getUlez() { return ulez; }
    public void setUlez(String ulez) { this.ulez = ulez; }

    public String getMotExpiry() { return motExpiry; }
    public void setMotExpiry(String motExpiry) { this.motExpiry = motExpiry; }

    public String getOwners() { return owners; }
    public void setOwners(String owners) { this.owners = owners; }

    public List<String> getSpecs() { return specs; }
    public void setSpecs(List<String> specs) { this.specs = specs != null ? new ArrayList<>(specs) : new ArrayList<>(); }

    public DealerDetails getDealer() { return dealer; }
    public void setDealer(DealerDetails dealer) { this.dealer = dealer != null ? dealer : DealerDetails.DEFAULT; }
}

