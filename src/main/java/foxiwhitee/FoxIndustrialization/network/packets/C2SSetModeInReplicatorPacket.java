package foxiwhitee.FoxIndustrialization.network.packets;

import foxiwhitee.FoxIndustrialization.tile.TileQuantumReplicator;
import foxiwhitee.FoxIndustrialization.tile.machines.TileBaseMachine;
import foxiwhitee.FoxLib.network.BasePacket;
import foxiwhitee.FoxLib.network.IInfoPacket;
import ic2.core.block.machine.tileentity.TileEntityReplicator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class C2SSetModeInReplicatorPacket extends BasePacket {
    private final int xCoord, yCoord, zCoord;
    private final TileEntityReplicator.Mode mode;

    public C2SSetModeInReplicatorPacket(ByteBuf data) {
        super(data);
        xCoord = data.readInt();
        yCoord = data.readInt();
        zCoord = data.readInt();
        mode = TileEntityReplicator.Mode.values()[data.readInt()];
    }

    public C2SSetModeInReplicatorPacket(int xCoord, int yCoord, int zCoord, TileEntityReplicator.Mode mode) {
        super();
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.mode = mode;
        ByteBuf data = Unpooled.buffer();
        data.writeInt(getId());
        data.writeInt(xCoord);
        data.writeInt(yCoord);
        data.writeInt(zCoord);
        data.writeInt(mode.ordinal());
        setPacketData(data);
    }

    @Override
    public void handleServerSide(IInfoPacket network, BasePacket packet, EntityPlayer player) {
        if (player != null && player.worldObj != null) {
            TileEntity te = player.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord);
            if (te instanceof TileQuantumReplicator tile) {
                tile.setMode(this.mode);
            }
        }
    }
}
