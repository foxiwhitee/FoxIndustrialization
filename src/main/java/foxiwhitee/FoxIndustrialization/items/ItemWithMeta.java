package foxiwhitee.FoxIndustrialization.items;

import foxiwhitee.FoxIndustrialization.FICore;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemWithMeta extends DefaultItem {
    protected enum NameType {DEFAULT, FORMATTED}

    private final String[] prefixes;
    private final IIcon[] icons;
    private final String package_;
    private final NameType nameType;

    public ItemWithMeta(String name, String package_, NameType nameType, String... prefixes) {
        super(name);
        this.nameType = nameType;
        this.hasSubtypes = true;
        this.icons = new IIcon[prefixes.length];
        this.prefixes = prefixes;
        this.package_ = package_;
    }

    public ItemWithMeta(String name, String package_, String... prefixes) {
        this(name, package_, NameType.DEFAULT, prefixes);
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        meta = Math.max(0, Math.min(meta, prefixes.length - 1));
        return icons[meta];
    }

    @Override
    public void registerIcons(IIconRegister register) {
        for (int i = 0; i < prefixes.length; i++) {
            icons[i] = register.registerIcon(FICore.MODID + ":" + package_ + name + prefixes[i]);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        int meta = Math.min(prefixes.length - 1, (stack != null) ? stack.getItemDamage() : 0);
        return switch (nameType) {
            case FORMATTED -> LocalizationUtils.localize(getUnlocalizedName() + ".name", prefixes[meta]);
            case DEFAULT -> LocalizationUtils.localize(getUnlocalizedName() + "." + prefixes[meta] + ".name");
        };
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < prefixes.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }
}
