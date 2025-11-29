package com.cardealer;

import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Builds PowerPoint presentations from vehicle listings.
 */
public class PresentationBuilder {

    private static final double INCH_TO_EMU = 914400.0; // 1 inch = 914400 EMU

    public void buildPresentation(VehicleListing vehicle, Path outputPath) throws IOException {
        XMLSlideShow pptx = new XMLSlideShow();
        
        // Get slide dimensions
        Dimension pgsize = pptx.getPageSize();
        double slideWidth = pgsize.getWidth();
        double slideHeight = pgsize.getHeight();
        
        // Create blank slide
        XSLFSlide slide = pptx.createSlide();
        
        addTitleBlock(slide, vehicle, slideWidth, slideHeight);
        addSpecificationTable(slide, vehicle, slideWidth, slideHeight);
        addContactBlock(slide, vehicle.getDealer(), slideWidth, slideHeight);
        
        // Save presentation
        try (FileOutputStream out = new FileOutputStream(outputPath.toFile())) {
            pptx.write(out);
        }
        pptx.close();
    }

    private void addTitleBlock(XSLFSlide slide, VehicleListing vehicle, double slideWidth, double slideHeight) {
        // Dealer name - top left, large and bold
        XSLFTextBox dealerNameBox = slide.createTextBox();
        dealerNameBox.setAnchor(new Rectangle2D.Double(
            inchesToEMU(0.5), inchesToEMU(0.3),
            inchesToEMU(6.5), inchesToEMU(0.8)
        ));
        XSLFTextParagraph dealerPara = dealerNameBox.addNewTextParagraph();
        dealerPara.setTextAlign(org.apache.poi.sl.usermodel.TextParagraph.TextAlign.LEFT);
        XSLFTextRun dealerRun = dealerPara.addNewTextRun();
        dealerRun.setText(vehicle.getDealer().name());
        dealerRun.setFontSize(36.0);
        dealerRun.setBold(true);
        dealerRun.setFontColor(new Color(0, 0, 0));

        // Price pill (red rectangle) - top right
        XSLFAutoShape pricePill = slide.createAutoShape();
        pricePill.setAnchor(new Rectangle2D.Double(
            inchesToEMU(7.5), inchesToEMU(0.3),
            inchesToEMU(2.0), inchesToEMU(0.8)
        ));
        // Set fill and line colors
        pricePill.setFillColor(new Color(0xF7, 0x00, 0x00));
        pricePill.setLineColor(new Color(0xF7, 0x00, 0x00)); // Match fill for seamless look
        
        XSLFTextParagraph pricePara = pricePill.addNewTextParagraph();
        pricePara.setTextAlign(org.apache.poi.sl.usermodel.TextParagraph.TextAlign.CENTER);
        pricePara.setSpaceAfter(0.0);
        XSLFTextRun priceRun = pricePara.addNewTextRun();
        priceRun.setText(vehicle.getPrice());
        priceRun.setFontSize(32.0);
        priceRun.setBold(true);
        priceRun.setFontColor(Color.WHITE);

        // Vehicle title - below dealer name
        XSLFTextBox titleBox = slide.createTextBox();
        titleBox.setAnchor(new Rectangle2D.Double(
            inchesToEMU(0.5), inchesToEMU(1.2),
            inchesToEMU(9), inchesToEMU(0.7)
        ));
        XSLFTextParagraph titlePara = titleBox.addNewTextParagraph();
        titlePara.setTextAlign(org.apache.poi.sl.usermodel.TextParagraph.TextAlign.LEFT);
        XSLFTextRun titleRun = titlePara.addNewTextRun();
        titleRun.setText(vehicle.getTitle());
        titleRun.setFontSize(36.0);
        titleRun.setBold(true);
        titleRun.setFontColor(new Color(0, 0, 0));
    }

    private void addSpecificationTable(XSLFSlide slide, VehicleListing vehicle, 
                                      double slideWidth, double slideHeight) {
        double left = inchesToEMU(0.5);
        double top = inchesToEMU(2.0);
        double width = inchesToEMU(9);
        double height = inchesToEMU(6.5);
        
        String[] labels = {
            "Registration", "Year", "Gearbox", "Engine Size", "Fuel Type",
            "Owners", "Mileage", "ULEZ", "MOT Expiry", "Specs"
        };
        
        String[] values = {
            vehicle.getRegistration(), vehicle.getYear(), vehicle.getGearbox(),
            vehicle.getEngineSize(), vehicle.getFuelType(), vehicle.getOwners(),
            vehicle.getMileage(), vehicle.getUlez(), vehicle.getMotExpiry(),
            String.join(", ", vehicle.getSpecs())
        };
        
        // Create table with 10 rows and 2 columns
        XSLFTable table = slide.createTable(10, 2);
        table.setAnchor(new Rectangle2D.Double(left, top, width, height));
        
        Color headerFill = new Color(0x00, 0x38, 0x64);
        Color valueFill = new Color(0xEC, 0xEC, 0xEC);
        
        for (int i = 0; i < labels.length; i++) {
            XSLFTableRow row = table.getRows().get(i);
            
            // Label cell (blue background, white text)
            XSLFTableCell labelCell = row.getCells().get(0);
            labelCell.setFillColor(headerFill);
            labelCell.setLeftInset(inchesToEMU(0.1));
            labelCell.setRightInset(inchesToEMU(0.1));
            labelCell.setTopInset(inchesToEMU(0.05));
            labelCell.setBottomInset(inchesToEMU(0.05));
            
            XSLFTextParagraph labelPara = labelCell.addNewTextParagraph();
            labelPara.setTextAlign(org.apache.poi.sl.usermodel.TextParagraph.TextAlign.LEFT);
            labelPara.setSpaceAfter(0.0);
            XSLFTextRun labelRun = labelPara.addNewTextRun();
            labelRun.setText(labels[i]);
            labelRun.setFontSize(20.0);
            labelRun.setBold(true);
            labelRun.setFontColor(Color.WHITE);
            
            // Value cell (gray background, black text)
            XSLFTableCell valueCell = row.getCells().get(1);
            valueCell.setFillColor(valueFill);
            valueCell.setLeftInset(inchesToEMU(0.1));
            valueCell.setRightInset(inchesToEMU(0.1));
            valueCell.setTopInset(inchesToEMU(0.05));
            valueCell.setBottomInset(inchesToEMU(0.05));
            
            XSLFTextParagraph valuePara = valueCell.addNewTextParagraph();
            valuePara.setTextAlign(org.apache.poi.sl.usermodel.TextParagraph.TextAlign.LEFT);
            valuePara.setSpaceAfter(0.0);
            XSLFTextRun valueRun = valuePara.addNewTextRun();
            valueRun.setText(values[i]);
            valueRun.setFontSize(20.0);
            valueRun.setFontColor(Color.BLACK);
        }
        
        // Column widths are automatically calculated by POI based on table width
        // The first column will be approximately 3.2 inches, second 5.8 inches
    }

    private void addContactBlock(XSLFSlide slide, DealerDetails dealer, 
                                double slideWidth, double slideHeight) {
        XSLFTextBox contactBox = slide.createTextBox();
        contactBox.setAnchor(new Rectangle2D.Double(
            inchesToEMU(0.5), inchesToEMU(8.7),
            inchesToEMU(9), inchesToEMU(0.6)
        ));
        XSLFTextParagraph contactPara = contactBox.addNewTextParagraph();
        contactPara.setTextAlign(org.apache.poi.sl.usermodel.TextParagraph.TextAlign.CENTER);
        contactPara.setSpaceAfter(0.0);
        XSLFTextRun contactRun = contactPara.addNewTextRun();
        contactRun.setText(String.format("Call: %s  |  Email: %s  |  Web: %s",
            dealer.phone(), dealer.email(), dealer.website()));
        contactRun.setFontSize(18.0);
        contactRun.setFontColor(new Color(0, 0, 0));
    }

    private double inchesToEMU(double inches) {
        return inches * INCH_TO_EMU;
    }
}

