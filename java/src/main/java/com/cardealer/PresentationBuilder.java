package com.cardealer;

import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Builds PowerPoint presentations from vehicle listings.
 * This class handles the creation and styling of PowerPoint slides with proper resource management.
 */
public class PresentationBuilder {

    /**
     * Builds a complete PowerPoint presentation from a vehicle listing.
     * 
     * @param vehicle the vehicle listing data
     * @param outputPath the path where the PPTX file will be saved
     * @throws IOException if there's an error writing the file
     * @throws NullPointerException if vehicle or outputPath is null
     */
    public void buildPresentation(VehicleListing vehicle, Path outputPath) throws IOException {
        Objects.requireNonNull(vehicle, "Vehicle cannot be null");
        Objects.requireNonNull(outputPath, "Output path cannot be null");
        
        XMLSlideShow pptx = new XMLSlideShow();
        try {
            // Create blank slide
            XSLFSlide slide = pptx.createSlide();
            
            addTitleBlock(slide, vehicle);
            addSpecificationTable(slide, vehicle);
            addContactBlock(slide, vehicle.getDealer());
            
            // Save presentation
            try (FileOutputStream out = new FileOutputStream(outputPath.toFile())) {
                pptx.write(out);
            }
        } finally {
            pptx.close();
        }
    }

    /**
     * Adds the title block (dealer name, price, vehicle title) to the slide.
     * 
     * @param slide the slide to add to
     * @param vehicle the vehicle listing
     */
    private void addTitleBlock(XSLFSlide slide, VehicleListing vehicle) {
        // Dealer name - top left, large and bold
        XSLFTextBox dealerNameBox = slide.createTextBox();
        dealerNameBox.setAnchor(new Rectangle2D.Double(
            inchesToEMU(PresentationConstants.Layout.DEALER_NAME_X),
            inchesToEMU(PresentationConstants.Layout.DEALER_NAME_Y),
            inchesToEMU(PresentationConstants.Layout.DEALER_NAME_WIDTH),
            inchesToEMU(PresentationConstants.Layout.DEALER_NAME_HEIGHT)
        ));
        XSLFTextParagraph dealerPara = dealerNameBox.addNewTextParagraph();
        dealerPara.setTextAlign(org.apache.poi.sl.usermodel.TextParagraph.TextAlign.LEFT);
        XSLFTextRun dealerRun = dealerPara.addNewTextRun();
        dealerRun.setText(vehicle.getDealer().name());
        dealerRun.setFontSize(PresentationConstants.FontSize.DEALER_NAME);
        dealerRun.setBold(true);
        dealerRun.setFontColor(PresentationConstants.Colors.DEFAULT_TEXT);

        // Price pill (red rectangle) - top right
        XSLFAutoShape pricePill = slide.createAutoShape();
        pricePill.setAnchor(new Rectangle2D.Double(
            inchesToEMU(PresentationConstants.Layout.PRICE_PILL_X),
            inchesToEMU(PresentationConstants.Layout.PRICE_PILL_Y),
            inchesToEMU(PresentationConstants.Layout.PRICE_PILL_WIDTH),
            inchesToEMU(PresentationConstants.Layout.PRICE_PILL_HEIGHT)
        ));
        pricePill.setFillColor(PresentationConstants.Colors.PRICE_PILL_BACKGROUND);
        pricePill.setLineColor(PresentationConstants.Colors.PRICE_PILL_BACKGROUND);
        
        XSLFTextParagraph pricePara = pricePill.addNewTextParagraph();
        pricePara.setTextAlign(org.apache.poi.sl.usermodel.TextParagraph.TextAlign.CENTER);
        pricePara.setSpaceAfter(0.0);
        XSLFTextRun priceRun = pricePara.addNewTextRun();
        priceRun.setText(vehicle.getPrice());
        priceRun.setFontSize(PresentationConstants.FontSize.PRICE);
        priceRun.setBold(true);
        priceRun.setFontColor(PresentationConstants.Colors.PRICE_PILL_TEXT);

        // Vehicle title - below dealer name
        XSLFTextBox titleBox = slide.createTextBox();
        titleBox.setAnchor(new Rectangle2D.Double(
            inchesToEMU(PresentationConstants.Layout.VEHICLE_TITLE_X),
            inchesToEMU(PresentationConstants.Layout.VEHICLE_TITLE_Y),
            inchesToEMU(PresentationConstants.Layout.VEHICLE_TITLE_WIDTH),
            inchesToEMU(PresentationConstants.Layout.VEHICLE_TITLE_HEIGHT)
        ));
        XSLFTextParagraph titlePara = titleBox.addNewTextParagraph();
        titlePara.setTextAlign(org.apache.poi.sl.usermodel.TextParagraph.TextAlign.LEFT);
        XSLFTextRun titleRun = titlePara.addNewTextRun();
        titleRun.setText(vehicle.getTitle());
        titleRun.setFontSize(PresentationConstants.FontSize.VEHICLE_TITLE);
        titleRun.setBold(true);
        titleRun.setFontColor(PresentationConstants.Colors.DEFAULT_TEXT);
    }

    /**
     * Adds the specification table to the slide.
     * 
     * @param slide the slide to add to
     * @param vehicle the vehicle listing
     */
    private void addSpecificationTable(XSLFSlide slide, VehicleListing vehicle) {
        double left = inchesToEMU(PresentationConstants.Layout.TABLE_X);
        double top = inchesToEMU(PresentationConstants.Layout.TABLE_Y);
        double width = inchesToEMU(PresentationConstants.Layout.TABLE_WIDTH);
        double height = inchesToEMU(PresentationConstants.Layout.TABLE_HEIGHT);
        
        String[] labels = {
            "Registration", "Year", "Gearbox", "Engine Size", "Fuel Type",
            "Owners", "Mileage", "ULEZ", "MOT Expiry", "Specs"
        };
        
        String[] values = {
            vehicle.getRegistration(),
            vehicle.getYear(),
            vehicle.getGearbox().getDisplayName(),
            vehicle.getEngineSize(),
            vehicle.getFuelType().getDisplayName(),
            vehicle.getOwners(),
            vehicle.getMileage(),
            vehicle.getUlez().getDisplayName(),
            vehicle.getMotExpiry(),
            String.join(", ", vehicle.getSpecs())
        };
        
        // Create table with 10 rows and 2 columns
        XSLFTable table = slide.createTable(10, 2);
        table.setAnchor(new Rectangle2D.Double(left, top, width, height));
        
        for (int i = 0; i < labels.length; i++) {
            XSLFTableRow row = table.getRows().get(i);
            
            // Label cell (blue background, white text)
            XSLFTableCell labelCell = row.getCells().get(0);
            labelCell.setFillColor(PresentationConstants.Colors.HEADER_FILL);
            labelCell.setLeftInset(inchesToEMU(PresentationConstants.Layout.CELL_LEFT_INSET));
            labelCell.setRightInset(inchesToEMU(PresentationConstants.Layout.CELL_RIGHT_INSET));
            labelCell.setTopInset(inchesToEMU(PresentationConstants.Layout.CELL_TOP_INSET));
            labelCell.setBottomInset(inchesToEMU(PresentationConstants.Layout.CELL_BOTTOM_INSET));
            
            XSLFTextParagraph labelPara = labelCell.addNewTextParagraph();
            labelPara.setTextAlign(org.apache.poi.sl.usermodel.TextParagraph.TextAlign.LEFT);
            labelPara.setSpaceAfter(0.0);
            XSLFTextRun labelRun = labelPara.addNewTextRun();
            labelRun.setText(labels[i]);
            labelRun.setFontSize(PresentationConstants.FontSize.TABLE_TEXT);
            labelRun.setBold(true);
            labelRun.setFontColor(PresentationConstants.Colors.HEADER_TEXT);
            
            // Value cell (gray background, black text)
            XSLFTableCell valueCell = row.getCells().get(1);
            valueCell.setFillColor(PresentationConstants.Colors.VALUE_FILL);
            valueCell.setLeftInset(inchesToEMU(PresentationConstants.Layout.CELL_LEFT_INSET));
            valueCell.setRightInset(inchesToEMU(PresentationConstants.Layout.CELL_RIGHT_INSET));
            valueCell.setTopInset(inchesToEMU(PresentationConstants.Layout.CELL_TOP_INSET));
            valueCell.setBottomInset(inchesToEMU(PresentationConstants.Layout.CELL_BOTTOM_INSET));
            
            XSLFTextParagraph valuePara = valueCell.addNewTextParagraph();
            valuePara.setTextAlign(org.apache.poi.sl.usermodel.TextParagraph.TextAlign.LEFT);
            valuePara.setSpaceAfter(0.0);
            XSLFTextRun valueRun = valuePara.addNewTextRun();
            valueRun.setText(values[i]);
            valueRun.setFontSize(PresentationConstants.FontSize.TABLE_TEXT);
            valueRun.setFontColor(PresentationConstants.Colors.VALUE_TEXT);
        }
    }

    /**
     * Adds the contact information block to the slide.
     * 
     * @param slide the slide to add to
     * @param dealer the dealer details
     */
    private void addContactBlock(XSLFSlide slide, DealerDetails dealer) {
        XSLFTextBox contactBox = slide.createTextBox();
        contactBox.setAnchor(new Rectangle2D.Double(
            inchesToEMU(PresentationConstants.Layout.CONTACT_X),
            inchesToEMU(PresentationConstants.Layout.CONTACT_Y),
            inchesToEMU(PresentationConstants.Layout.CONTACT_WIDTH),
            inchesToEMU(PresentationConstants.Layout.CONTACT_HEIGHT)
        ));
        XSLFTextParagraph contactPara = contactBox.addNewTextParagraph();
        contactPara.setTextAlign(org.apache.poi.sl.usermodel.TextParagraph.TextAlign.CENTER);
        contactPara.setSpaceAfter(0.0);
        XSLFTextRun contactRun = contactPara.addNewTextRun();
        contactRun.setText(String.format("Call: %s  |  Email: %s  |  Web: %s",
            dealer.phone(), dealer.email(), dealer.website()));
        contactRun.setFontSize(PresentationConstants.FontSize.CONTACT);
        contactRun.setFontColor(PresentationConstants.Colors.DEFAULT_TEXT);
    }

    /**
     * Converts inches to EMU (English Metric Units) used by PowerPoint.
     * 
     * @param inches the measurement in inches
     * @return the measurement in EMU
     */
    private double inchesToEMU(double inches) {
        return inches * PresentationConstants.INCH_TO_EMU;
    }
}
