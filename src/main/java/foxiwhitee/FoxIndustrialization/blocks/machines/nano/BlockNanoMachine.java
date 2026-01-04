package foxiwhitee.FoxIndustrialization.blocks.machines.nano;

import foxiwhitee.FoxIndustrialization.blocks.machines.BlockMachine;
import net.minecraft.tileentity.TileEntity;

public abstract class BlockNanoMachine extends BlockMachine {
    public BlockNanoMachine(String name, Class<? extends TileEntity> tileEntityClass) {
        super(name, tileEntityClass);
    }

    @Override
    public String getFolder() {
        return "machines/nano/";
    }
}
