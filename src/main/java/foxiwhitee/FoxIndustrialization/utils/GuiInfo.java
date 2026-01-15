package foxiwhitee.FoxIndustrialization.utils;

public enum GuiInfo {
    ADVANCED_COMPRESSOR("guiAdvancedMachine", 0, 107),
    ADVANCED_EXTRACTOR("guiAdvancedMachine", 8, 101),
    ADVANCED_ELECTRIC_FURNACE("guiAdvancedMachine", 16, 143),
    ADVANCED_MACERATOR("guiAdvancedMachine", 24, 101),
    ADVANCED_RECYCLER("guiAdvancedMachine", 32, 95),
    ADVANCED_METAL_FORMER("guiAdvancedMetalFormer", 40, 119),
    ADVANCED_GENERATOR("guiAdvancedGenerator", 48, 101),
    ADVANCED_KINETIC_GENERATOR("guiAdvancedKineticGenerator", 56, 149),
    ADVANCED_MATTER_GENERATOR("guiAdvancedMatter", 64, 143),
    ADVANCED_SCANNER("guiAdvancedScanner", 72, 89),
    NANO_COMPRESSOR("guiNanoMachine", 80, 89),
    NANO_EXTRACTOR("guiNanoMachine", 88, 83),
    NANO_ELECTRIC_FURNACE("guiNanoMachine", 96, 125),
    NANO_MACERATOR("guiNanoMachine", 104, 83),
    NANO_RECYCLER("guiNanoMachine", 112, 77),
    NANO_METAL_FORMER("guiNanoMetalFormer", 120, 101),
    NANO_GENERATOR("guiNanoGenerator", 128, 83),
    NANO_KINETIC_GENERATOR("guiNanoKineticGenerator", 136, 131),
    NANO_MATTER_GENERATOR("guiNanoMatter", 144, 125),
    QUANTUM_COMPRESSOR("guiQuantumMachine", 152, 107),
    QUANTUM_EXTRACTOR("guiQuantumMachine", 160, 143),
    QUANTUM_ELECTRIC_FURNACE("guiQuantumMachine", 168, 101),
    QUANTUM_MACERATOR("guiQuantumMachine", 176, 95),
    QUANTUM_RECYCLER("guiQuantumMachine", 184, 119),
    QUANTUM_METAL_FORMER("guiQuantumMetalFormer", 192, 101),
    QUANTUM_GENERATOR("guiQuantumGenerator", 200, 149),
    QUANTUM_KINETIC_GENERATOR("guiQuantumKineticGenerator", 208, 149),
    QUANTUM_MATTER_GENERATOR("guiQuantumMatter", 216, 143),
    QUANTUM_REPLICATOR("guiQuantumReplicator", 224, 107),
    SINGULAR_COMPRESSOR("...", 232, 113),
    SINGULAR_EXTRACTOR("...", 240, 107),
    SINGULAR_ELECTRIC_FURNACE("...", 248, 149),
    SINGULAR_MACERATOR("...", 256, 107),
    SINGULAR_RECYCLER("...", 264, 101),
    SINGULAR_METAL_FORMER("...", 272, 125),
    SINGULAR_MATTER_GENERATOR("guiSingularMatter", 280, 149),
    SINGULAR_FLUID_COMPLEX("...", 288, 131),
    SINGULAR_MOLECULAR_TRANSFORMER("...", 296, 179),
    BASIC_ENERGY_STORAGE("guiAdvancedEnergyStorage", 304, 119),
    ADVANCED_ENERGY_STORAGE("guiAdvancedEnergyStorage", 312, 131),
    HYBRID_ENERGY_STORAGE("guiNanoEnergyStorage", 320, 125),
    NANO_ENERGY_STORAGE("guiNanoEnergyStorage", 328, 113),
    ULTIMATE_ENERGY_STORAGE("guiQuantumEnergyStorage", 336, 137),
    QUANTUM_ENERGY_STORAGE("guiQuantumEnergyStorage", 344, 131),
    SINGULAR_ENERGY_STORAGE("guiSingularEnergyStorage", 352, 137),
    INFINITY_GENERATOR("guiInfinityGenerator", 360, 107),
    ENERGY_SYNTHESIZER("guiSynthesizer", 368, 107),
    POWER_CONVERTER("guiPowerConverter", 376, 89),
    SOLAR_PANEL("guiCustomSolarPanel", 384, 65),
    UNIVERSAL_FLUID_COMPLEX("guiUniversalFluidComplex", 392, 137, 56, 151),
    WATER_GENERATOR("guiFluidGenerator", 400, 89),
    LAVA_GENERATOR("guiFluidGenerator", 408, 83),
    MATTER_SYNTHESIZER("guiMatterSynthesizer", 416, 107),
    WITHER_KILLER("guiWitherKiller", 424, 77),
    MOLECULAR_TRANSFORMER("guiMolecularTransformer", 432, 125, 58, 151);

    private final String textureName;
    private final int yStart, length, fieldXStart, fieldLength;

    GuiInfo(String textureName, int yStart, int length, int fieldXStart, int fieldLength) {
        this.textureName = textureName;
        this.yStart = yStart;
        this.length = length;
        this.fieldXStart = fieldXStart;
        this.fieldLength = fieldLength;
    }

    GuiInfo(String textureName, int yStart, int length) {
        this(textureName, yStart, length, 48, 151);
    }

    public String getTextureName() {
        return textureName;
    }

    public int getYStart() {
        return yStart;
    }

    public int getLength() {
        return length;
    }

    public int getFieldXStart() {
        return fieldXStart;
    }

    public int getFieldLength() {
        return fieldLength;
    }
}
