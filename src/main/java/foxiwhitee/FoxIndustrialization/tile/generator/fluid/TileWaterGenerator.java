package foxiwhitee.FoxIndustrialization.tile.generator.fluid;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class TileWaterGenerator extends TileFluidGenerator {
    public TileWaterGenerator() {
        super(FIConfig.waterGeneratorFluidStorage);
    }

    @Override
    protected Fluid getFluid() {
        return FluidRegistry.WATER;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.WATER_GENERATOR;
    }
}
