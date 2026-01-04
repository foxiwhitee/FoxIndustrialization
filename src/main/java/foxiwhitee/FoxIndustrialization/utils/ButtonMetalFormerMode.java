package foxiwhitee.FoxIndustrialization.utils;

public enum ButtonMetalFormerMode {
    ROLLING(306, 0), // Hammer
    CUTTING(306, 18), // CUtter
    EXTRUDING(306, 36); // Cable

    private final int xStart, yStart;

    ButtonMetalFormerMode(int xStart, int yStart) {
        this.xStart = xStart;
        this.yStart = yStart;
    }

    public int getXStart() {
        return xStart;
    }

    public int getYStart() {
        return yStart;
    }
}
