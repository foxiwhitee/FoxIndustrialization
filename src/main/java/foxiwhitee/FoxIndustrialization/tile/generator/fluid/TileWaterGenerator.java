package foxiwhitee.FoxIndustrialization.tile.generator.fluid;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class TileWaterGenerator extends TileFluidGenerator {
    private final static InfoGui info = new InfoGui(0, 289, 88);

    public TileWaterGenerator() {
        super(FIConfig.waterGeneratorFluidStorage);
    }

    @Override
    protected Fluid getFluid() {
        return FluidRegistry.WATER;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }
}
