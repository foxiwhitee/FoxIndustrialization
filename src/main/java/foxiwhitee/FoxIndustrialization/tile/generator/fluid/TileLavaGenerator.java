package foxiwhitee.FoxIndustrialization.tile.generator.fluid;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class TileLavaGenerator extends TileFluidGenerator {
    private final static InfoGui info = new InfoGui(0, 297, 82);

    public TileLavaGenerator() {
        super(FIConfig.lavaGeneratorFluidStorage);
    }

    @Override
    protected Fluid getFluid() {
        return FluidRegistry.LAVA;
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }
}
