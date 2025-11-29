package com.cardealer;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Main CLI application for generating vehicle presentation PowerPoint files.
 * Provides a modern command-line interface with autocomplete support.
 */
@Command(name = "generate-ppt", 
         description = "Generate an editable vehicle PPTX from JSON or CLI arguments",
         mixinStandardHelpOptions = true,
         version = "1.0.0")
public class GeneratePpt implements Callable<Integer> {

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
    static class FuelTypeCandidates extends ArrayList<String> {
        FuelTypeCandidates() {
            for (FuelType type : FuelType.values()) {
                add(type.getDisplayName());
            }
        }
    }

    static class GearboxCandidates extends ArrayList<String> {
        GearboxCandidates() {
            for (GearboxType type : GearboxType.values()) {
                add(type.getDisplayName());
            }
        }
    }

    static class UlezCandidates extends ArrayList<String> {
        UlezCandidates() {
            for (UlezStatus status : UlezStatus.values()) {
                add(status.getDisplayName());
            }
        }
    }

    @Override
    public Integer call() {
        try {
            // Load vehicle from JSON if provided
            VehicleListing vehicle = loadVehicle(inputFile);
            
            // Override with CLI arguments
            updateFromArgs(vehicle);
            
            // Build presentation
            PresentationBuilder builder = new PresentationBuilder();
            builder.buildPresentation(vehicle, outputFile.toPath());
            
            System.out.println("Saved editable PPTX to " + outputFile);
            return CommandLine.ExitCode.OK;
        } catch (IllegalArgumentException e) {
            System.err.println("Validation error: " + e.getMessage());
            return CommandLine.ExitCode.USAGE;
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            if (System.getProperty("debug") != null) {
                e.printStackTrace();
            }
            return CommandLine.ExitCode.SOFTWARE;
        }
    }

    /**
     * Loads a vehicle listing from a JSON file.
     * 
     * @param inputFile the JSON file to load, or null for a default vehicle
     * @return a VehicleListing instance
     * @throws Exception if there's an error reading or parsing the file
     */
    private VehicleListing loadVehicle(File inputFile) throws Exception {
        if (inputFile == null) {
            return new VehicleListing();
        }
        
        if (!inputFile.exists()) {
            throw new IllegalArgumentException("Input file does not exist: " + inputFile);
        }
        
        if (!inputFile.canRead()) {
            throw new IllegalArgumentException("Cannot read input file: " + inputFile);
        }
        
        ObjectMapper mapper = new ObjectMapper();
        @SuppressWarnings("unchecked")
        java.util.Map<String, Object> data = mapper.readValue(inputFile, java.util.Map.class);
        return VehicleListing.fromMap(data);
    }

    /**
     * Updates the vehicle listing with values from CLI arguments.
     * 
     * @param vehicle the vehicle listing to update
     * @throws IllegalArgumentException if any enum values are invalid
     */
    private void updateFromArgs(VehicleListing vehicle) {
        if (title != null) {
            vehicle.setTitle(title);
        }
        if (price != null) {
            vehicle.setPrice(price);
        }
        if (registration != null) {
            vehicle.setRegistration(registration);
        }
        if (year != null) {
            vehicle.setYear(year);
        }
        if (gearbox != null) {
            vehicle.setGearbox(gearbox); // This will validate and throw if invalid
        }
        if (engineSize != null) {
            vehicle.setEngineSize(engineSize);
        }
        if (fuelType != null) {
            vehicle.setFuelType(fuelType); // This will validate and throw if invalid
        }
        if (mileage != null) {
            vehicle.setMileage(mileage);
        }
        if (ulez != null) {
            vehicle.setUlez(ulez); // This will validate and throw if invalid
        }
        if (motExpiry != null) {
            vehicle.setMotExpiry(motExpiry);
        }
        if (owners != null) {
            vehicle.setOwners(owners);
        }
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
