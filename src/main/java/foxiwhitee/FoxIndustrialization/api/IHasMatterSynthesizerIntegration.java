package foxiwhitee.FoxIndustrialization.api;

import net.minecraft.item.ItemStack;

public interface IHasMatterSynthesizerIntegration {
    double getEnergyNeed(ItemStack stack);

    int getTankCapacity(ItemStack stack);

    int getGenerating(ItemStack stack);
}
