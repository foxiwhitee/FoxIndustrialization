package foxiwhitee.FoxIndustrialization.items;
import foxiwhitee.FoxIndustrialization.FICore;
import net.minecraft.item.Item;

public class DefaultItem extends Item {
    protected final String name;

    public DefaultItem(String name, String texture, int maxStackSize) {
        this.setUnlocalizedName(name);
        this.setTextureName(FICore.MODID + ":" + texture);
        this.setCreativeTab(FICore.TAB);
        this.maxStackSize = maxStackSize;
        this.name = name;
    }

    public DefaultItem(String name, String texture) {
        this(name, texture, 64);
    }

    public DefaultItem(String name) {
        this(name, name);
    }

    public DefaultItem(String name, int maxStackSize) {
        this(name, name, maxStackSize);
    }
}
