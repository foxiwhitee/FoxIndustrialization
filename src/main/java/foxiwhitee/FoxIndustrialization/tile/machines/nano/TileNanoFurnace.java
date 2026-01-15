package foxiwhitee.FoxIndustrialization.tile.machines.nano;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;

import java.util.List;

public class TileNanoFurnace extends TileNanoMachine {
    public TileNanoFurnace() {
        super(MachineTier.NANO, FIConfig.nanoFurnaceStorage, FIConfig.nanoFurnaceItemsPerOp, FIConfig.nanoFurnaceEnergyPerTick);
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return ModRecipes.furnaceRecipes;
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.NANO_ELECTRIC_FURNACE;
    }
}
