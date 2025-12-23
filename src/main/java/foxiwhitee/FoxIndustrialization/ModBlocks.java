package foxiwhitee.FoxIndustrialization;

import foxiwhitee.FoxIndustrialization.blocks.BlockTestNanoCompressor;
import foxiwhitee.FoxIndustrialization.tile.machines.TIleTestNanoCompressor;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import net.minecraft.block.Block;

public class ModBlocks {
    public static final Block test = new BlockTestNanoCompressor();

    public static void registerBlocks() {
        RegisterUtils.registerBlock(test);
        RegisterUtils.registerTile(TIleTestNanoCompressor.class);
    }
}
