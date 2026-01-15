package foxiwhitee.FoxIndustrialization.tile;

import foxiwhitee.FoxIndustrialization.api.IHasMatterSynthesizerIntegration;
import foxiwhitee.FoxIndustrialization.tile.generator.matter.TileMatterGenerator;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxLib.tile.inventory.FoxInternalInventory;
import foxiwhitee.FoxLib.tile.inventory.InvOperation;
import ic2.core.Ic2Items;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class TileMatterSynthesizer extends TileMatterGenerator {
    private final FoxInternalInventory generators = new FoxInternalInventory(this, 9);

    public TileMatterSynthesizer() {
        super(1000, 0, 0);
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.MATTER_SYNTHESIZER;
    }

    public FoxInternalInventory getGeneratorsInventory() {
        return generators;
    }


    @Override
    public MachineTier getTier() {
        return MachineTier.SINGULAR;
    }

    @Override
    public void onChangeInventory(IInventory iInventory, int i, InvOperation invOperation, ItemStack itemStack, ItemStack itemStack1) {
        super.onChangeInventory(iInventory, i, invOperation, itemStack, itemStack1);
        if (iInventory == generators) {
            this.maxEnergy = 0;
            this.amount = 0;
            double newCapacity = 0;

            for (int j = 0; j < generators.getSizeInventory(); j++) {
                ItemStack stack = generators.getStackInSlot(j);
                if (stack != null) {
                    if (stack.getItem() instanceof IHasMatterSynthesizerIntegration item) {
                        this.maxEnergy += item.getEnergyNeed(stack);
                        this.amount += item.getGenerating(stack);
                        newCapacity += item.getTankCapacity(stack);
                    } else if (Block.getBlockFromItem(stack.getItem()) == Block.getBlockFromItem(Ic2Items.massFabricator.getItem())) {
                        this.maxEnergy += 1000000;
                        this.amount++;
                        newCapacity += 8000;
                    }
                }
            }
            newCapacity = Math.max(1000, newCapacity);
            this.energy = Math.min(this.maxEnergy, this.energy);
            this.tank.setCapacity((int) Math.min(Integer.MAX_VALUE, newCapacity));
            if (this.tank.getFluidAmount() > this.tank.getCapacity()) {
                this.tank.drain(this.tank.getFluidAmount() - this.tank.getCapacity(), true);
            }

            markForUpdate();
        }
    }
}
