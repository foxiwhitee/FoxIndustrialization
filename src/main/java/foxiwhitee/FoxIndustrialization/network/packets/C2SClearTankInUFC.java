package foxiwhitee.FoxIndustrialization.network.packets;

import foxiwhitee.FoxIndustrialization.tile.TileUniversalFluidComplex;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedMetalFormer;
import foxiwhitee.FoxIndustrialization.tile.machines.nano.TileNanoMetalFormer;
import foxiwhitee.FoxIndustrialization.tile.machines.quantum.TileQuantumMetalFormer;
import foxiwhitee.FoxLib.network.BasePacket;
import foxiwhitee.FoxLib.network.IInfoPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class C2SClearTankInUFC extends BasePacket {
    private final int xCoord, yCoord, zCoord, tankId;

    public C2SClearTankInUFC(ByteBuf data) {
        super(data);
        xCoord = data.readInt();
        yCoord = data.readInt();
        zCoord = data.readInt();
        tankId = data.readInt();
    }

    public C2SClearTankInUFC(int xCoord, int yCoord, int zCoord, int tankId) {
        super();
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.tankId = tankId;
        ByteBuf data = Unpooled.buffer();
        data.writeInt(getId());
        data.writeInt(xCoord);
        data.writeInt(yCoord);
        data.writeInt(zCoord);
        data.writeInt(tankId);
        setPacketData(data);
    }

    @Override
    public void handleServerSide(IInfoPacket network, BasePacket packet, EntityPlayer player) {
        if (player != null && player.worldObj != null) {
            TileEntity te = player.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord);
            if (te instanceof TileUniversalFluidComplex tile) {
                tile.clearTank(tankId);
            }
        }
    }
}
