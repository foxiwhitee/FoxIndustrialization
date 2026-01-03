package foxiwhitee.FoxIndustrialization.utils;

public enum ButtonInventoryMode {
    NONE(288, 36),
    MERGE(288, 0),
    FILL(288, 18);

    private final int xStart, yStart;

    ButtonInventoryMode(int xStart, int yStart) {
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
