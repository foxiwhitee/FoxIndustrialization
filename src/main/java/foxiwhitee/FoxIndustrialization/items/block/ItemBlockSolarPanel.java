package foxiwhitee.FoxIndustrialization.items.block;

import foxiwhitee.FoxIndustrialization.ModBlocks;
import foxiwhitee.FoxIndustrialization.api.IHasSynthesizerIntegration;
import foxiwhitee.FoxIndustrialization.config.FIConfig;
import foxiwhitee.FoxLib.items.ModItemBlock;
import foxiwhitee.FoxLib.utils.helpers.EnergyUtility;
import foxiwhitee.FoxLib.utils.helpers.LocalizationUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemBlockSolarPanel extends ModItemBlock implements IHasSynthesizerIntegration {
    private final double dayGenerating, nightGenerating, output, storage;

    public ItemBlockSolarPanel(Block b) {
        super(b);

        if (isBlock(ModBlocks.solarPanelLevel1)) {
            this.dayGenerating = FIConfig.solarPanel1GeneratingDay;
            this.nightGenerating = FIConfig.solarPanel1GeneratingNight;
            this.output = FIConfig.solarPanel1Output;
            this.storage = FIConfig.solarPanel1Storage;
        } else if (isBlock(ModBlocks.solarPanelLevel2)) {
            this.dayGenerating = FIConfig.solarPanel2GeneratingDay;
            this.nightGenerating = FIConfig.solarPanel2GeneratingNight;
            this.output = FIConfig.solarPanel2Output;
            this.storage = FIConfig.solarPanel2Storage;
        } else if (isBlock(ModBlocks.solarPanelLevel3)) {
            this.dayGenerating = FIConfig.solarPanel3GeneratingDay;
            this.nightGenerating = FIConfig.solarPanel3GeneratingNight;
            this.output = FIConfig.solarPanel3Output;
            this.storage = FIConfig.solarPanel3Storage;
        } else if (isBlock(ModBlocks.solarPanelLevel4)) {
            this.dayGenerating = FIConfig.solarPanel4GeneratingDay;
            this.nightGenerating = FIConfig.solarPanel4GeneratingNight;
            this.output = FIConfig.solarPanel4Output;
            this.storage = FIConfig.solarPanel4Storage;
        } else if (isBlock(ModBlocks.solarPanelLevel5)) {
            this.dayGenerating = FIConfig.solarPanel5GeneratingDay;
            this.nightGenerating = FIConfig.solarPanel5GeneratingNight;
            this.output = FIConfig.solarPanel5Output;
            this.storage = FIConfig.solarPanel5Storage;
        } else if (isBlock(ModBlocks.solarPanelLevel6)) {
            this.dayGenerating = FIConfig.solarPanel6GeneratingDay;
            this.nightGenerating = FIConfig.solarPanel6GeneratingNight;
            this.output = FIConfig.solarPanel6Output;
            this.storage = FIConfig.solarPanel6Storage;
        } else if (isBlock(ModBlocks.solarPanelLevel7)) {
            this.dayGenerating = FIConfig.solarPanel7GeneratingDay;
            this.nightGenerating = FIConfig.solarPanel7GeneratingNight;
            this.output = FIConfig.solarPanel7Output;
            this.storage = FIConfig.solarPanel7Storage;
        } else if (isBlock(ModBlocks.solarPanelLevel8)) {
            this.dayGenerating = FIConfig.solarPanel8GeneratingDay;
            this.nightGenerating = FIConfig.solarPanel8GeneratingNight;
            this.output = FIConfig.solarPanel8Output;
            this.storage = FIConfig.solarPanel8Storage;
        } else {
            this.dayGenerating = 0;
            this.nightGenerating = 0;
            this.output = 0;
            this.storage = 0;
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer p_77624_2_, List<String> list, boolean p_77624_4_) {
        if (FIConfig.enableTooltips) {
            list.add(LocalizationUtils.localize("tooltip.machine.capacity", EnergyUtility.formatNumber(storage)));
            list.add(LocalizationUtils.localize("tooltip.solarPanel.output", EnergyUtility.formatNumber(output)));
            list.add(LocalizationUtils.localize("tooltip.solarPanel.generateInDay", EnergyUtility.formatNumber(dayGenerating)));
            list.add(LocalizationUtils.localize("tooltip.solarPanel.generateInNight", EnergyUtility.formatNumber(nightGenerating)));
        }
    }

    @Override
    public double getDayGen(ItemStack stack) {
        return this.dayGenerating;
    }

    @Override
    public double getNightGen(ItemStack stack) {
        return this.nightGenerating;
    }
}
