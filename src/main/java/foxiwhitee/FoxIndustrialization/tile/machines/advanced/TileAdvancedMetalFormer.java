package foxiwhitee.FoxIndustrialization.tile.machines.advanced;

import foxiwhitee.FoxIndustrialization.ModRecipes;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxIndustrialization.recipes.IRecipeIC2;
import foxiwhitee.FoxIndustrialization.utils.ButtonMetalFormerMode;
import foxiwhitee.FoxIndustrialization.utils.GuiInfo;
import foxiwhitee.FoxIndustrialization.utils.MachineTier;
import foxiwhitee.FoxLib.tile.event.TileEvent;
import foxiwhitee.FoxLib.tile.event.TileEventType;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;
import java.util.List;

public class TileAdvancedMetalFormer extends TileAdvancedMachine {
    private ButtonMetalFormerMode mode = ButtonMetalFormerMode.ROLLING;

    public TileAdvancedMetalFormer() {
        super(MachineTier.ADVANCED, FIConfig.advancedMetalFormerStorage, FIConfig.advancedMetalFormerItemsPerOp, FIConfig.advancedMetalFormerEnergyPerTick);
    }

    @TileEvent(TileEventType.SERVER_NBT_WRITE)
    @Override
    public void writeToNbt_(NBTTagCompound data) {
        super.writeToNbt_(data);
        data.setByte("mode", (byte) mode.ordinal());
    }

    @TileEvent(TileEventType.SERVER_NBT_READ)
    @Override
    public void readFromNbt_(NBTTagCompound data) {
        super.readFromNbt_(data);
        mode = ButtonMetalFormerMode.values()[data.getByte("mode")];
    }

    @TileEvent(TileEventType.CLIENT_NBT_WRITE)
    @Override
    public void writeToStream(ByteBuf data) {
        super.writeToStream(data);
        data.writeByte(mode.ordinal());
    }

    @TileEvent(TileEventType.CLIENT_NBT_READ)
    @Override
    public boolean readFromStream(ByteBuf data) {
        boolean old = super.readFromStream(data);
        ButtonMetalFormerMode oldMode = mode;
        mode = ButtonMetalFormerMode.values()[data.readByte()];
        return old || oldMode != mode;
    }

    @Override
    protected List<? extends IRecipeIC2> getRecipes() {
        return switch (mode) {
            case ROLLING -> ModRecipes.metalformerRollingRecipes;
            case CUTTING -> ModRecipes.metalformerCuttingRecipes;
            case EXTRUDING -> ModRecipes.metalformerExtrudingRecipes;
        };
    }

    @Override
    public GuiInfo getGuiInfo() {
        return GuiInfo.ADVANCED_METAL_FORMER;
    }

    public void changeMode() {
        int next = mode.ordinal() + 1;
        if (next >= ButtonMetalFormerMode.values().length) {
            next -= ButtonMetalFormerMode.values().length;
        }
        mode = ButtonMetalFormerMode.values()[next];
        Arrays.fill(currentRecipes, null);
        Arrays.fill(ticks, 0);
        markForUpdate();
    }

    public ButtonMetalFormerMode getMode() {
        return mode;
    }
}
