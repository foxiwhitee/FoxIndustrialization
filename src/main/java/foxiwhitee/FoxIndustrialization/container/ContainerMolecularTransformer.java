package foxiwhitee.FoxIndustrialization.container;

import foxiwhitee.FoxIndustrialization.container.slots.SlotUpgrade;
import foxiwhitee.FoxIndustrialization.tile.TileMolecularTransformer;
import foxiwhitee.FoxLib.container.FoxBaseContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMolecularTransformer extends FoxBaseContainer {
    public ContainerMolecularTransformer(EntityPlayer player, TileMolecularTransformer tileEntity) {
        super(player, tileEntity);

        bindPlayerInventory(player.inventory, 53, 178);

        for (int i = 0; i < 3; i++) {
            addSlotToContainer(new SlotUpgrade(tileEntity.getUpgradesInventory(), tileEntity, i, 23, 85 + i * 19));
        }

        for (int i = 0; i < 2; i++) {
            addSlotToContainer(new Slot(tileEntity.getInternalInventory(), i, 97 + i * 18, 66));
        }

        addSlotToContainer(new Slot(tileEntity.getOutputInventory(), 0, 106, 109) {
            @Override
            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });
    }
}
