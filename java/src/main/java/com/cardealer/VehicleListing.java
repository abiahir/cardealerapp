package com.cardealer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents a vehicle listing with all its details.
 * This class provides a clean, type-safe model for vehicle data with proper validation.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleListing {
    
    private String title;
    private String price;
    private String registration;
    private String year;
    private GearboxType gearbox;
    private String engineSize;
    private FuelType fuelType;
    private String mileage;
    private UlezStatus ulez;
    private String motExpiry;
    private String owners;
    private List<String> specs;
    private DealerDetails dealer;

    /**
     * Default constructor initializes with default values.
     */
    public VehicleListing() {
        this.specs = new ArrayList<>();
        this.dealer = DealerDetails.DEFAULT;
        this.gearbox = GearboxType.AUTOMATIC;
        this.fuelType = FuelType.DIESEL;
        this.ulez = UlezStatus.UNKNOWN;
    }

    /**
     * Full constructor with all parameters.
     * 
     * @param title vehicle title
     * @param price price string
     * @param registration registration number
     * @param year year string
     * @param gearbox gearbox type (cannot be null)
     * @param engineSize engine size string
     * @param fuelType fuel type (cannot be null)
     * @param mileage mileage string
     * @param ulez ULEZ status (cannot be null)
     * @param motExpiry MOT expiry string
     * @param owners owners string
     * @param specs list of specifications
     * @param dealer dealer details (cannot be null)
     */
    public VehicleListing(String title, String price, String registration, String year,
                         GearboxType gearbox, String engineSize, FuelType fuelType, String mileage,
                         UlezStatus ulez, String motExpiry, String owners, List<String> specs,
                         DealerDetails dealer) {
        this.title = title;
        this.price = price;
        this.registration = registration;
        this.year = year;
        this.gearbox = Objects.requireNonNull(gearbox, "Gearbox cannot be null");
        this.engineSize = engineSize;
        this.fuelType = Objects.requireNonNull(fuelType, "Fuel type cannot be null");
        this.mileage = mileage;
        this.ulez = Objects.requireNonNull(ulez, "ULEZ status cannot be null");
        this.motExpiry = motExpiry;
        this.owners = owners;
        this.specs = specs != null ? new ArrayList<>(specs) : new ArrayList<>();
        this.dealer = dealer != null ? dealer : DealerDetails.DEFAULT;
    }

    /**
     * Creates a VehicleListing from a map of data (typically from JSON).
     * 
     * @param data map containing vehicle data
     * @return a new VehicleListing instance
     * @throws IllegalArgumentException if enum values are invalid
     */
    public static VehicleListing fromMap(Map<String, Object> data) {
        if (data == null) {
            return new VehicleListing();
        }
        
        VehicleListing vehicle = new VehicleListing();
        
        vehicle.title = getString(data, "title", PresentationConstants.Defaults.TITLE);
        vehicle.price = getString(data, "price", PresentationConstants.Defaults.PRICE);
        vehicle.registration = getString(data, "registration", PresentationConstants.Defaults.REGISTRATION);
        vehicle.year = getString(data, "year", PresentationConstants.Defaults.YEAR);
        
        String gearboxStr = getString(data, "gearbox", GearboxType.AUTOMATIC.getDisplayName());
        vehicle.gearbox = GearboxType.fromStringOrDefault(gearboxStr, GearboxType.AUTOMATIC);
        if (vehicle.gearbox == null) {
            throw new IllegalArgumentException(
                String.format("Invalid gearbox value: %s. Must be one of: %s", 
                    gearboxStr, java.util.Arrays.toString(GearboxType.values()))
            );
        }
        
        vehicle.engineSize = getString(data, "engine_size", PresentationConstants.Defaults.ENGINE_SIZE);
        
        String fuelTypeStr = getString(data, "fuel_type", FuelType.DIESEL.getDisplayName());
        vehicle.fuelType = FuelType.fromStringOrDefault(fuelTypeStr, FuelType.DIESEL);
        if (vehicle.fuelType == null) {
            throw new IllegalArgumentException(
                String.format("Invalid fuel type value: %s. Must be one of: %s", 
                    fuelTypeStr, java.util.Arrays.toString(FuelType.values()))
            );
        }
        
        vehicle.mileage = getString(data, "mileage", PresentationConstants.Defaults.MILEAGE);
        
        String ulezStr = getString(data, "ulez", UlezStatus.UNKNOWN.getDisplayName());
        vehicle.ulez = UlezStatus.fromStringOrDefault(ulezStr, UlezStatus.UNKNOWN);
        if (vehicle.ulez == null) {
            throw new IllegalArgumentException(
                String.format("Invalid ULEZ value: %s. Must be one of: %s", 
                    ulezStr, java.util.Arrays.toString(UlezStatus.values()))
            );
        }
        
        vehicle.motExpiry = getString(data, "mot_expiry", PresentationConstants.Defaults.MOT_EXPIRY);
        vehicle.owners = getString(data, "owners", PresentationConstants.Defaults.OWNERS);
        
        @SuppressWarnings("unchecked")
        List<Object> specsList = (List<Object>) data.get("specs");
        if (specsList != null) {
            vehicle.specs = specsList.stream()
                .filter(Objects::nonNull)
                .map(Object::toString)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        }
        
        @SuppressWarnings("unchecked")
        Map<String, Object> dealerMap = (Map<String, Object>) data.get("dealer");
        vehicle.dealer = DealerDetails.fromMap(dealerMap);
        
        return vehicle;
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

    // Getters and setters
    public String getTitle() { 
        return title; 
    }
    
    public void setTitle(String title) { 
        this.title = title; 
    }

    public String getPrice() { 
        return price; 
    }
    
    public void setPrice(String price) { 
        this.price = price; 
    }

    public String getRegistration() { 
        return registration; 
    }
    
    public void setRegistration(String registration) { 
        this.registration = registration; 
    }

    public String getYear() { 
        return year; 
    }
    
    public void setYear(String year) { 
        this.year = year; 
    }

    public GearboxType getGearbox() { 
        return gearbox; 
    }
    
    public void setGearbox(GearboxType gearbox) { 
        this.gearbox = Objects.requireNonNull(gearbox, "Gearbox cannot be null");
    }
    
    /**
     * Sets gearbox from string value (for backward compatibility).
     * 
     * @param gearboxStr the gearbox string value
     * @throws IllegalArgumentException if the string is not a valid gearbox type
     */
    public void setGearbox(String gearboxStr) {
        GearboxType type = GearboxType.fromString(gearboxStr);
        if (type == null) {
            throw new IllegalArgumentException(
                String.format("Invalid gearbox: %s. Must be one of: %s", 
                    gearboxStr, java.util.Arrays.toString(GearboxType.values()))
            );
        }
        this.gearbox = type;
    }

    public String getEngineSize() { 
        return engineSize; 
    }
    
    public void setEngineSize(String engineSize) { 
        this.engineSize = engineSize; 
    }

    public FuelType getFuelType() { 
        return fuelType; 
    }
    
    public void setFuelType(FuelType fuelType) { 
        this.fuelType = Objects.requireNonNull(fuelType, "Fuel type cannot be null");
    }
    
    /**
     * Sets fuel type from string value (for backward compatibility).
     * 
     * @param fuelTypeStr the fuel type string value
     * @throws IllegalArgumentException if the string is not a valid fuel type
     */
    public void setFuelType(String fuelTypeStr) {
        FuelType type = FuelType.fromString(fuelTypeStr);
        if (type == null) {
            throw new IllegalArgumentException(
                String.format("Invalid fuel type: %s. Must be one of: %s", 
                    fuelTypeStr, java.util.Arrays.toString(FuelType.values()))
            );
        }
        this.fuelType = type;
    }

    public String getMileage() { 
        return mileage; 
    }
    
    public void setMileage(String mileage) { 
        this.mileage = mileage; 
    }

    public UlezStatus getUlez() { 
        return ulez; 
    }
    
    public void setUlez(UlezStatus ulez) { 
        this.ulez = Objects.requireNonNull(ulez, "ULEZ status cannot be null");
    }
    
    /**
     * Sets ULEZ status from string value (for backward compatibility).
     * 
     * @param ulezStr the ULEZ string value
     * @throws IllegalArgumentException if the string is not a valid ULEZ status
     */
    public void setUlez(String ulezStr) {
        UlezStatus status = UlezStatus.fromString(ulezStr);
        if (status == null) {
            throw new IllegalArgumentException(
                String.format("Invalid ULEZ status: %s. Must be one of: %s", 
                    ulezStr, java.util.Arrays.toString(UlezStatus.values()))
            );
        }
        this.ulez = status;
    }

    public String getMotExpiry() { 
        return motExpiry; 
    }
    
    public void setMotExpiry(String motExpiry) { 
        this.motExpiry = motExpiry; 
    }

    public String getOwners() { 
        return owners; 
    }
    
    public void setOwners(String owners) { 
        this.owners = owners; 
    }

    /**
     * Returns an unmodifiable view of the specs list.
     * 
     * @return unmodifiable list of specifications
     */
    public List<String> getSpecs() { 
        return Collections.unmodifiableList(specs); 
    }
    
    public void setSpecs(List<String> specs) { 
        this.specs = specs != null ? new ArrayList<>(specs) : new ArrayList<>(); 
    }

    public DealerDetails getDealer() { 
        return dealer; 
    }
    
    public void setDealer(DealerDetails dealer) { 
        this.dealer = dealer != null ? dealer : DealerDetails.DEFAULT; 
    }
}
