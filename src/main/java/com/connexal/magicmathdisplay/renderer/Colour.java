package com.connexal.magicmathdisplay.renderer;

/**
 * Represents a colour with red, green, and blue components.
 */
public class Colour {
    public static final Colour DARK_RED = new Colour(0xAA0000);
    public static final Colour RED = new Colour(0xFF5555);
    public static final Colour GOLD = new Colour(0xFFAA00);
    public static final Colour YELLOW = new Colour(0xFFFF55);
    public static final Colour DARK_GREEN = new Colour(0x00AA00);
    public static final Colour GREEN = new Colour(0x55FF55);
    public static final Colour AQUA = new Colour(0x55FFFF);
    public static final Colour DARK_AQUA = new Colour(0x00AAAA);
    public static final Colour DARK_BLUE = new Colour(0x0000AA);
    public static final Colour BLUE = new Colour(0x5555FF);
    public static final Colour LIGHT_PURPLE = new Colour(0xFF55FF);
    public static final Colour DARK_PURPLE = new Colour(0xAA00AA);
    public static final Colour WHITE = new Colour(0xFFFFFF);
    public static final Colour GRAY = new Colour(0xAAAAAA);
    public static final Colour DARK_GRAY = new Colour(0x555555);
    public static final Colour BLACK = new Colour(0x000000);

    private final int red;
    private final int green;
    private final int blue;

    /**
     * Creates a new Colour instance.
     * @param red the red component (0-255)
     * @param green the green component (0-255)
     * @param blue the blue component (0-255)
     * @throws IllegalArgumentException if any component is out of range
     */
    public Colour(int red, int green, int blue) {
        if (red < 0 || red > 255 ||
            green < 0 || green > 255 ||
            blue < 0 || blue > 255) {
            throw new IllegalArgumentException("Colour components must be between 0 and 255");
        }

        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    /**
     * Creates a new Colour instance from an RGB integer.
     * @param rgb the RGB integer representation of the colour (0xRRGGBB)
     */
    public Colour(int rgb) {
        this.red = (rgb >> 16) & 0xFF;
        this.green = (rgb >> 8) & 0xFF;
        this.blue = rgb & 0xFF;
    }

    /**
     * Returns the red component of the colour.
     * @return the red component (0-255)
     */
    public int getRed() {
        return this.red;
    }

    /**
     * Returns the green component of the colour.
     * @return the green component (0-255)
     */
    public int getGreen() {
        return this.green;
    }

    /**
     * Returns the blue component of the colour.
     * @return the blue component (0-255)
     */
    public int getBlue() {
        return this.blue;
    }

    /**
     * Converts the colour to an RGB integer.
     * @return the RGB integer representation of the colour
     */
    public int toRGBInt() {
        return (red << 16) | (green << 8) | blue;
    }
}
