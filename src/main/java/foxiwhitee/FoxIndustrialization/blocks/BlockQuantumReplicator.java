package foxiwhitee.FoxIndustrialization.blocks;

import foxiwhitee.FoxIndustrialization.client.gui.GuiQuantumReplicator;
import foxiwhitee.FoxIndustrialization.container.ContainerQuantumReplicator;
import foxiwhitee.FoxIndustrialization.tile.TileQuantumReplicator;
import foxiwhitee.FoxLib.utils.handler.SimpleGuiHandler;

@SimpleGuiHandler(tile = TileQuantumReplicator.class, container = ContainerQuantumReplicator.class, gui = GuiQuantumReplicator.class)
public class BlockQuantumReplicator extends BaseIC2Block {
    public BlockQuantumReplicator(String name) {
        super(name, TileQuantumReplicator.class);
    }

    @Override
    public String getFolder() {
        return "quantumReplicator/";
    }
}
