package foxiwhitee.FoxIndustrialization.integration.cofh;

import cofh.api.energy.IEnergyContainerItem;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import foxiwhitee.FoxIndustrialization.api.IPowerConverterUpgradeItem;
import foxiwhitee.FoxIndustrialization.config.ContentConfig;
import foxiwhitee.FoxIndustrialization.integration.cofh.blocks.BlockPowerConverter;
import foxiwhitee.FoxIndustrialization.integration.cofh.items.ItemBlockPowerConvertor;
import foxiwhitee.FoxIndustrialization.integration.cofh.items.ItemPowerConverterUpgrade;
import foxiwhitee.FoxIndustrialization.integration.cofh.network.packets.C2SSetPowerConverterMode;
import foxiwhitee.FoxIndustrialization.integration.cofh.tile.TilePowerConverter;
import foxiwhitee.FoxIndustrialization.items.ItemStorageUpgrade;
import foxiwhitee.FoxIndustrialization.utils.FilterInitializer;
import foxiwhitee.FoxLib.FoxLib;
import foxiwhitee.FoxLib.api.FoxLibApi;
import foxiwhitee.FoxLib.container.slots.SlotFiltered;
import foxiwhitee.FoxLib.integration.IIntegration;
import foxiwhitee.FoxLib.integration.Integration;
import foxiwhitee.FoxLib.network.NetworkManager;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

@Integration(modid = "CoFHCore")
public class CoFHIntegration implements IIntegration {
    public static final String FILTER_POWER_CONVERTER = "powerConverter";

    public static final Item powerConverterUpgrade = new ItemPowerConverterUpgrade("powerConverterUpgrade");

    public static final Block powerConverter = new BlockPowerConverter("powerConverter");

    @Override
    public void preInit(FMLPreInitializationEvent fmlPreInitializationEvent) {
        if (ContentConfig.enablePowerConverter) {
            RegisterUtils.registerItem(powerConverterUpgrade);
            RegisterUtils.registerBlock(powerConverter, ItemBlockPowerConvertor.class);
            RegisterUtils.registerTile(TilePowerConverter.class);
        }
    }

    @Override
    public void init(FMLInitializationEvent fmlInitializationEvent) {
        FoxLibApi.instance.registries().registerPacket().register(C2SSetPowerConverterMode.class);
        FilterInitializer.addClassToFilterUltimate(IEnergyContainerItem.class);
        FilterInitializer.addClassToFilterQuantum(IEnergyContainerItem.class);
        FilterInitializer.addClassToFilterSingular(IEnergyContainerItem.class);
    }

    @Override
    public void postInit(FMLPostInitializationEvent fmlPostInitializationEvent) {
        SlotFiltered.filters.put(FILTER_POWER_CONVERTER, stack -> stack.getItem() instanceof ItemStorageUpgrade || stack.getItem() instanceof IPowerConverterUpgradeItem);
    }
}
