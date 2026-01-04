package foxiwhitee.FoxIndustrialization.blocks.machines.quantum;

import foxiwhitee.FoxIndustrialization.blocks.machines.BlockMachine;
import net.minecraft.tileentity.TileEntity;

public abstract class BlockQuantumMachine extends BlockMachine {
    public BlockQuantumMachine(String name, Class<? extends TileEntity> tileEntityClass) {
        super(name, tileEntityClass);
    }

    @Override
    public String getFolder() {
        return "machines/quantum/";
    }
}
