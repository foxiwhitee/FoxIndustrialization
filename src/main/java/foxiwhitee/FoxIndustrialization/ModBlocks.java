package foxiwhitee.FoxIndustrialization;

import foxiwhitee.FoxIndustrialization.blocks.machines.advanced.BlockAdvancedFurnace;
import foxiwhitee.FoxIndustrialization.blocks.machines.nano.BlockNanoFurnace;
import foxiwhitee.FoxIndustrialization.blocks.machines.quantum.BlockQuantumFurnace;
import foxiwhitee.FoxIndustrialization.blocks.storage.*;
import foxiwhitee.FoxIndustrialization.config.ContentConfig;
import foxiwhitee.FoxIndustrialization.items.block.ItemBlockEnergyStorage;
import foxiwhitee.FoxIndustrialization.items.block.ItemBlockMachine;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.TileAdvancedFurnace;
import foxiwhitee.FoxIndustrialization.tile.machines.nano.TileNanoFurnace;
import foxiwhitee.FoxIndustrialization.tile.machines.quantum.TileQuantumFurnace;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class ModBlocks {
    public static final Block advancedFurnace = new BlockAdvancedFurnace("advancedFurnace");

    public static final Block nanoFurnace = new BlockNanoFurnace("nanoFurnace");

    public static final Block quantumFurnace = new BlockQuantumFurnace("quantumFurnace");

    public static final Block basicEnergyStorage = new BlockBasicEnergyStorage("basicEnergyStorage");
    public static final Block advancedEnergyStorage = new BlockAdvancedEnergyStorage("advancedEnergyStorage");
    public static final Block hybridEnergyStorage = new BlockHybridEnergyStorage("hybridEnergyStorage");
    public static final Block nanoEnergyStorage = new BlockNanoEnergyStorage("nanoEnergyStorage");
    public static final Block ultimateEnergyStorage = new BlockUltimateEnergyStorage("ultimateEnergyStorage");
    public static final Block quantumEnergyStorage = new BlockQuantumEnergyStorage("quantumEnergyStorage");
    public static final Block singularEnergyStorage = new BlockSingularEnergyStorage("singularEnergyStorage");

    public static final Block basicChargePad = new BlockBasicChargePad("basicChargePad");
    public static final Block advancedChargePad = new BlockAdvancedChargePad("advancedChargePad");
    public static final Block hybridChargePad = new BlockHybridChargePad("hybridChargePad");
    public static final Block nanoChargePad = new BlockNanoChargePad("nanoChargePad");
    public static final Block ultimateChargePad = new BlockUltimateChargePad("ultimateChargePad");
    public static final Block quantumChargePad = new BlockQuantumChargePad("quantumChargePad");
    public static final Block singularChargePad = new BlockSingularChargePad("singularChargePad");

    public static void registerBlocks() {
        if (ContentConfig.enableAdvancedFurnace) {
            RegisterUtils.registerBlock(advancedFurnace, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileAdvancedFurnace.class);
        }
        if (ContentConfig.enableNanoFurnace) {
            RegisterUtils.registerBlock(nanoFurnace, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileNanoFurnace.class);
        }
        if (ContentConfig.enableQuantumFurnace) {
            RegisterUtils.registerBlock(quantumFurnace, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileQuantumFurnace.class);
        }
        if (ContentConfig.enableNewEnergyStorages) {
            RegisterUtils.registerBlocks(ItemBlockEnergyStorage.class, basicEnergyStorage, advancedEnergyStorage, hybridEnergyStorage, nanoEnergyStorage, ultimateEnergyStorage, quantumEnergyStorage, singularEnergyStorage);
            RegisterUtils.registerBlocks(ItemBlockEnergyStorage.class, basicChargePad, advancedChargePad, hybridChargePad, nanoChargePad, ultimateChargePad, quantumChargePad, singularChargePad);
            RegisterUtils.findClasses("foxiwhitee.FoxIndustrialization.tile.storage.advanced", TileEntity.class).forEach(RegisterUtils::registerTile);
            RegisterUtils.findClasses("foxiwhitee.FoxIndustrialization.tile.storage.nano", TileEntity.class).forEach(RegisterUtils::registerTile);
            RegisterUtils.findClasses("foxiwhitee.FoxIndustrialization.tile.storage.quantum", TileEntity.class).forEach(RegisterUtils::registerTile);
            RegisterUtils.findClasses("foxiwhitee.FoxIndustrialization.tile.storage.singular", TileEntity.class).forEach(RegisterUtils::registerTile);
        }
    }
}
