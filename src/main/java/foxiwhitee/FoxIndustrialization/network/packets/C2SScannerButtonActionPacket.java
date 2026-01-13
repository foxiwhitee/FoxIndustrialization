package foxiwhitee.FoxIndustrialization.network.packets;

import foxiwhitee.FoxIndustrialization.tile.TileAdvancedScanner;
import foxiwhitee.FoxIndustrialization.tile.TileUniversalFluidComplex;
import foxiwhitee.FoxLib.network.BasePacket;
import foxiwhitee.FoxLib.network.IInfoPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

public class C2SScannerButtonActionPacket extends BasePacket {
    private final int xCoord, yCoord, zCoord, button;

    public C2SScannerButtonActionPacket(ByteBuf data) {
        super(data);
        xCoord = data.readInt();
        yCoord = data.readInt();
        zCoord = data.readInt();
        button = data.readInt();
    }

    public C2SScannerButtonActionPacket(int xCoord, int yCoord, int zCoord, int button) {
        super();
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.button = button;
        ByteBuf data = Unpooled.buffer();
        data.writeInt(getId());
        data.writeInt(xCoord);
        data.writeInt(yCoord);
        data.writeInt(zCoord);
        data.writeInt(button);
        setPacketData(data);
    }

    @Override
    public void handleServerSide(IInfoPacket network, BasePacket packet, EntityPlayer player) {
        if (player != null && player.worldObj != null) {
            TileEntity te = player.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord);
            if (te instanceof TileAdvancedScanner tile) {
                if (button == 0) {
                    tile.reset();
                    tile.setIdle();
                } else if (button == 1) {
                    tile.record();
                    tile.setIdle();
                }
            }
        }
    }
}
