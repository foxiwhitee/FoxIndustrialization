package foxiwhitee.FoxIndustrialization.network.packets;

import foxiwhitee.FoxIndustrialization.tile.machines.TileBaseMachine;
import foxiwhitee.FoxLib.network.BasePacket;
import foxiwhitee.FoxLib.network.IInfoPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class C2SUpdateMachineInventoryModePacket extends BasePacket {
    private final int xCoord, yCoord, zCoord;

    public C2SUpdateMachineInventoryModePacket(ByteBuf data) {
        super(data);
        xCoord = data.readInt();
        yCoord = data.readInt();
        zCoord = data.readInt();
    }

    public C2SUpdateMachineInventoryModePacket(int xCoord, int yCoord, int zCoord) {
        super();
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        ByteBuf data = Unpooled.buffer();
        data.writeInt(getId());
        data.writeInt(xCoord);
        data.writeInt(yCoord);
        data.writeInt(zCoord);
        setPacketData(data);
    }

    @Override
    public void handleServerSide(IInfoPacket network, BasePacket packet, EntityPlayer player) {
        if (player != null && player.worldObj != null) {
            TileEntity te = player.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord);
            if (te instanceof TileBaseMachine tile) {
                tile.changeInventoryMode();
            }
        }
    }
}
