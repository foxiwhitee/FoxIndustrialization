package foxiwhitee.FoxIndustrialization.tile.machines.quantum;

import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.BasicIC2MachineRecipe;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import ic2.core.Ic2Items;
import ic2.core.block.machine.tileentity.TileEntityRecycler;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class TileQuantumRecycler extends TileQuantumMachine {
    private static final InfoGui info = new InfoGui(0, 128, 95);

    public TileQuantumRecycler() {
        super(MachineTier.QUANTUM, FIConfig.quantumRecyclerStorage, FIConfig.quantumRecyclerItemsPerOp, FIConfig.quantumRecyclerEnergyPerTick);
    }

    @Override
    protected void getRecipe(ItemStack stack, int idx) {
        if (stack == null) {
            currentRecipes[idx] = null;
            return;
        }
        if (!TileEntityRecycler.getIsItemBlacklisted(stack.copy())) {
            ItemStack inputCopy = stack.copy();
            inputCopy.stackSize = 1;
            currentRecipes[idx] = new BasicIC2MachineRecipe(inputCopy, Ic2Items.scrapBox.copy());
        } else {
            currentRecipes[idx] = null;
        }
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return Collections.emptyList();
    }

    @Override
    public InfoGui getInfoAboutGui() {
        return info;
    }

    @Override
    protected boolean supportsRF() {
        return FIConfig.quantumRecyclerSupportsRF;
    }
}
