package foxiwhitee.FoxIndustrialization.tile.generator.fluid;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class TileLavaGenerator extends TileFluidGenerator {
    public TileLavaGenerator() {
        super(FIConfig.lavaGeneratorFluidStorage);
    }

    @Override
    protected Fluid getFluid() {
        return FluidRegistry.LAVA;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.LAVA_GENERATOR;
    }
}
