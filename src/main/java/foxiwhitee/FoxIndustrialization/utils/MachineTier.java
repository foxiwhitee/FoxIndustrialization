package foxiwhitee.FoxIndustrialization.utils;

public enum MachineTier {
    ADVANCED(2, 2, 3, 2),
    NANO(3, 3, 3, 3),
    QUANTUM(4, 4, 3, 4);

    private final int invInpSize, invOutSize, invUpgradesSize, maxOperations;

    MachineTier(int invInpSize, int invOutSize, int invUpgradesSize, int maxOperations) {
        this.invInpSize = invInpSize;
        this.invOutSize = invOutSize;
        this.invUpgradesSize = invUpgradesSize;
        this.maxOperations = maxOperations;
    }

    public int getInvInpSize() {
        return invInpSize;
    }

    public int getInvOutSize() {
        return invOutSize;
    }

    public int getInvUpgradesSize() {
        return invUpgradesSize;
    }

    public int getMaxOperations() {
        return maxOperations;
    }
}
