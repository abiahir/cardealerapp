package com.cardealer;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

/**
 * Main CLI application for generating vehicle presentation PowerPoint files.
 */
@Command(name = "generate-ppt", 
         description = "Generate an editable vehicle PPTX from JSON or CLI arguments",
         mixinStandardHelpOptions = true,
         version = "1.0.0")
public class GeneratePpt implements Callable<Integer> {

    private static final Set<String> FUEL_TYPES = Set.of("Petrol", "Diesel", "Hybrid", "Electric");
    private static final Set<String> GEARBOX_TYPES = Set.of("Automatic", "Manual");
    private static final Set<String> ULEZ_CHOICES = Set.of("Yes", "No", "Unknown");

    @Option(names = "--input", description = "Path to vehicle JSON data file")
    private File inputFile;

    @Option(names = "--output", description = "PPTX output file path", defaultValue = "vehicle.pptx")
    private File outputFile;

    @Option(names = "--title", description = "Vehicle display title")
    private String title;

    @Option(names = "--price", description = "Price string, e.g. '£12,000'")
    private String price;

    @Option(names = "--registration", description = "Registration number")
    private String registration;

    @Option(names = "--year", description = "Year display value")
    private String year;

    @Option(names = "--gearbox", description = "Gearbox option: ${COMPLETION-CANDIDATES}", 
            completionCandidates = GearboxCandidates.class)
    private String gearbox;

    @Option(names = "--engine-size", description = "Engine size label")
    private String engineSize;

    @Option(names = "--fuel-type", description = "Fuel type option: ${COMPLETION-CANDIDATES}",
            completionCandidates = FuelTypeCandidates.class)
    private String fuelType;

    @Option(names = "--mileage", description = "Mileage display value")
    private String mileage;

    @Option(names = "--ulez", description = "ULEZ status: ${COMPLETION-CANDIDATES}",
            completionCandidates = UlezCandidates.class)
    private String ulez;

    @Option(names = "--mot-expiry", description = "MOT expiry display value")
    private String motExpiry;

    @Option(names = "--owners", description = "Number of owners or description")
    private String owners;

    @Option(names = "--specs", description = "List of spec bullet points", arity = "0..*")
    private List<String> specs;

    @Option(names = "--dealer-name", description = "Dealership name")
    private String dealerName;

    @Option(names = "--dealer-phone", description = "Dealership phone number")
    private String dealerPhone;

    @Option(names = "--dealer-email", description = "Dealership email address")
    private String dealerEmail;

    @Option(names = "--dealer-website", description = "Dealership website")
    private String dealerWebsite;

    // Completion candidates for CLI autocomplete
    static class FuelTypeCandidates extends java.util.ArrayList<String> {
        FuelTypeCandidates() { super(FUEL_TYPES); }
    }

    static class GearboxCandidates extends java.util.ArrayList<String> {
        GearboxCandidates() { super(GEARBOX_TYPES); }
    }

    static class UlezCandidates extends java.util.ArrayList<String> {
        UlezCandidates() { super(ULEZ_CHOICES); }
    }

    @Override
    public Integer call() throws Exception {
        try {
            // Load vehicle from JSON if provided
            VehicleListing vehicle = loadVehicle(inputFile);
            
            // Override with CLI arguments
            updateFromArgs(vehicle);
            
            // Build presentation
            PresentationBuilder builder = new PresentationBuilder();
            builder.buildPresentation(vehicle, outputFile.toPath());
            
            System.out.println("Saved editable PPTX to " + outputFile);
            return 0;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            return 1;
        }
    }

    private VehicleListing loadVehicle(File inputFile) throws Exception {
        if (inputFile == null) {
            return new VehicleListing();
        }
        
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("unchecked")
        java.util.Map<String, Object> data = mapper.readValue(inputFile, java.util.Map.class);
        return VehicleListing.fromMap(data);
    }

    private void updateFromArgs(VehicleListing vehicle) {
        if (title != null) vehicle.setTitle(title);
        if (price != null) vehicle.setPrice(price);
        if (registration != null) vehicle.setRegistration(registration);
        if (year != null) vehicle.setYear(year);
        if (gearbox != null) {
            if (!GEARBOX_TYPES.contains(gearbox)) {
                throw new IllegalArgumentException("Gearbox must be one of: " + GEARBOX_TYPES);
            }
            vehicle.setGearbox(gearbox);
        }
        if (engineSize != null) vehicle.setEngineSize(engineSize);
        if (fuelType != null) {
            if (!FUEL_TYPES.contains(fuelType)) {
                throw new IllegalArgumentException("Fuel type must be one of: " + FUEL_TYPES);
            }
            vehicle.setFuelType(fuelType);
        }
        if (mileage != null) vehicle.setMileage(mileage);
        if (ulez != null) {
            if (!ULEZ_CHOICES.contains(ulez)) {
                throw new IllegalArgumentException("ULEZ must be one of: " + ULEZ_CHOICES);
            }
            vehicle.setUlez(ulez);
        }
        if (motExpiry != null) vehicle.setMotExpiry(motExpiry);
        if (owners != null) vehicle.setOwners(owners);
        if (specs != null && !specs.isEmpty()) {
            vehicle.setSpecs(specs);
        }
        
        // Update dealer details
        DealerDetails currentDealer = vehicle.getDealer();
        vehicle.setDealer(new DealerDetails(
            dealerName != null ? dealerName : currentDealer.name(),
            dealerPhone != null ? dealerPhone : currentDealer.phone(),
            dealerEmail != null ? dealerEmail : currentDealer.email(),
            dealerWebsite != null ? dealerWebsite : currentDealer.website()
        ));
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new GeneratePpt()).execute(args);
        System.exit(exitCode);
    }
}

