package com.cardealer;

import java.awt.Color;

/**
 * Shared constants for slide layout, colors, and default values to ensure the
 * Java implementation mirrors the Python generator output.
 */
public final class PresentationConstants {
    private PresentationConstants() {
    }

    /** Number of EMUs per inch in the PowerPoint coordinate system. */
    public static final double INCH_TO_EMU = 914_400d;

    /**
     * Default strings for vehicle data when fields are omitted in the JSON or CLI
     * input.
     */
    public static final class Defaults {
        private Defaults() {
        }

        public static final String TITLE = "Vehicle Title";
        public static final String PRICE = "Price on enquiry";
        public static final String REGISTRATION = "Registration";
        public static final String YEAR = "Year";
        public static final String ENGINE_SIZE = "2.0 L";
        public static final String MILEAGE = "0";
        public static final String MOT_EXPIRY = "Unknown";
        public static final String OWNERS = "Unknown";
    }

    /**
     * Layout measurements for the slide elements, expressed in inches to match the
     * PowerPoint authoring UX.
     */
    public static final class Layout {
        private Layout() {
        }

        public static final double DEALER_NAME_X = 0.5;
        public static final double DEALER_NAME_Y = 0.3;
        public static final double DEALER_NAME_WIDTH = 6.5;
        public static final double DEALER_NAME_HEIGHT = 0.8;

        public static final double PRICE_PILL_X = 7.5;
        public static final double PRICE_PILL_Y = 0.3;
        public static final double PRICE_PILL_WIDTH = 2.0;
        public static final double PRICE_PILL_HEIGHT = 0.8;

        public static final double VEHICLE_TITLE_X = 0.5;
        public static final double VEHICLE_TITLE_Y = 1.2;
        public static final double VEHICLE_TITLE_WIDTH = 9.0;
        public static final double VEHICLE_TITLE_HEIGHT = 0.7;

        public static final double TABLE_X = 0.5;
        public static final double TABLE_Y = 2.0;
        public static final double TABLE_WIDTH = 9.0;
        public static final double TABLE_HEIGHT = 6.5;

        public static final double CELL_LEFT_INSET = 0.1;
        public static final double CELL_RIGHT_INSET = 0.1;
        public static final double CELL_TOP_INSET = 0.05;
        public static final double CELL_BOTTOM_INSET = 0.05;

        public static final double CONTACT_X = 0.5;
        public static final double CONTACT_Y = 8.7;
        public static final double CONTACT_WIDTH = 9.0;
        public static final double CONTACT_HEIGHT = 0.6;
    }

    /** Centralized color palette. */
    public static final class Colors {
        private Colors() {
        }

        public static final Color DEFAULT_TEXT = new Color(0x00, 0x00, 0x00);
        public static final Color PRICE_PILL_BACKGROUND = new Color(0xF7, 0x00, 0x00);
        public static final Color PRICE_PILL_TEXT = new Color(0xFF, 0xFF, 0xFF);
        public static final Color HEADER_FILL = new Color(0x00, 0x38, 0x64);
        public static final Color HEADER_TEXT = new Color(0xFF, 0xFF, 0xFF);
        public static final Color VALUE_FILL = new Color(0xEC, 0xEC, 0xEC);
        public static final Color VALUE_TEXT = new Color(0x00, 0x00, 0x00);
    }

    /** Font sizes expressed in points to keep styling consistent. */
    public static final class FontSize {
        private FontSize() {
        }

        public static final double DEALER_NAME = 36.0;
        public static final double PRICE = 32.0;
        public static final double VEHICLE_TITLE = 36.0;
        public static final double TABLE_TEXT = 20.0;
        public static final double CONTACT = 18.0;
    }
}
