package foxiwhitee.FoxIndustrialization.api;

import net.minecraft.item.ItemStack;

public interface IHasSynthesizerIntegration {
    double getDayGen(ItemStack stack);

    double getNightGen(ItemStack stack);
}
