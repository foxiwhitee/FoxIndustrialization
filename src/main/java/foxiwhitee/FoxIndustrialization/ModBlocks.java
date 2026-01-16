package foxiwhitee.FoxIndustrialization;

import foxiwhitee.FoxIndustrialization.blocks.*;
import foxiwhitee.FoxIndustrialization.blocks.generator.fluid.BlockLavaGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.fluid.BlockWaterGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.fuel.BlockAdvancedGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.fuel.BlockNanoGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.fuel.BlockQuantumGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.infinity.BlockInfinityGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.kinetic.BlockAdvancedKineticGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.kinetic.BlockNanoKineticGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.kinetic.BlockQuantumKineticGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.matter.BlockAdvancedMatterGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.matter.BlockNanoMatterGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.matter.BlockQuantumMatterGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.matter.BlockSingularMatterGenerator;
import foxiwhitee.FoxIndustrialization.blocks.generator.panel.*;
import foxiwhitee.FoxIndustrialization.blocks.machines.advanced.*;
import foxiwhitee.FoxIndustrialization.blocks.machines.nano.*;
import foxiwhitee.FoxIndustrialization.blocks.machines.quantum.*;
import foxiwhitee.FoxIndustrialization.blocks.storage.*;
import foxiwhitee.FoxIndustrialization.config.ContentConfig;
import foxiwhitee.FoxIndustrialization.items.block.*;
import foxiwhitee.FoxIndustrialization.tile.*;
import foxiwhitee.FoxIndustrialization.tile.generator.fluid.TileLavaGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fluid.TileWaterGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileAdvancedGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileNanoGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.fuel.TileQuantumGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.infinity.TileInfinityGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.kinetic.TileAdvancedKineticGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.kinetic.TileNanoKineticGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.kinetic.TileQuantumKineticGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.matter.TileAdvancedMatterGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.matter.TileNanoMatterGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.matter.TileQuantumMatterGenerator;
import foxiwhitee.FoxIndustrialization.tile.generator.matter.TileSingularMatterGenerator;
import foxiwhitee.FoxIndustrialization.tile.machines.advanced.*;
import foxiwhitee.FoxIndustrialization.tile.machines.nano.*;
import foxiwhitee.FoxIndustrialization.tile.machines.quantum.*;
import foxiwhitee.FoxLib.registries.RegisterUtils;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class ModBlocks {
    public static final Block casingNano = new BlockCasing("casingNano");
    public static final Block casingQuantum = new BlockCasing("casingQuantum");

    public static final Block universalFluidComplex = new BlockUniversalFluidComplex("universalFluidComplex");
    public static final Block molecularTransformer = new BlockMolecularTransformer("molecularTransformer");

    public static final Block advancedCompressor = new BlockAdvancedCompressor("advancedCompressor");
    public static final Block advancedExtractor = new BlockAdvancedExtractor("advancedExtractor");
    public static final Block advancedFurnace = new BlockAdvancedFurnace("advancedFurnace");
    public static final Block advancedMacerator = new BlockAdvancedMacerator("advancedMacerator");
    public static final Block advancedMetalFormer = new BlockAdvancedMetalFormer("advancedMetalFormer");
    public static final Block advancedRecycler = new BlockAdvancedRecycler("advancedRecycler");

    public static final Block nanoCompressor = new BlockNanoCompressor("nanoCompressor");
    public static final Block nanoExtractor = new BlockNanoExtractor("nanoExtractor");
    public static final Block nanoFurnace = new BlockNanoFurnace("nanoFurnace");
    public static final Block nanoMacerator = new BlockNanoMacerator("nanoMacerator");
    public static final Block nanoMetalFormer = new BlockNanoMetalFormer("nanoMetalFormer");
    public static final Block nanoRecycler = new BlockNanoRecycler("nanoRecycler");

    public static final Block quantumCompressor = new BlockQuantumCompressor("quantumCompressor");
    public static final Block quantumExtractor = new BlockQuantumExtractor("quantumExtractor");
    public static final Block quantumFurnace = new BlockQuantumFurnace("quantumFurnace");
    public static final Block quantumMacerator = new BlockQuantumMacerator("quantumMacerator");
    public static final Block quantumMetalFormer = new BlockQuantumMetalFormer("quantumMetalFormer");
    public static final Block quantumRecycler = new BlockQuantumRecycler("quantumRecycler");

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

    public static final Block advancedGenerator = new BlockAdvancedGenerator("advancedGenerator");
    public static final Block nanoGenerator = new BlockNanoGenerator("nanoGenerator");
    public static final Block quantumGenerator = new BlockQuantumGenerator("quantumGenerator");

    public static final Block advancedKineticGenerator = new BlockAdvancedKineticGenerator("advancedKineticGenerator");
    public static final Block nanoKineticGenerator = new BlockNanoKineticGenerator("nanoKineticGenerator");
    public static final Block quantumKineticGenerator = new BlockQuantumKineticGenerator("quantumKineticGenerator");

    public static final Block solarPanelLevel1 = new BlockSolarPanelLevel1("solarPanelLevel1");
    public static final Block solarPanelLevel2 = new BlockSolarPanelLevel2("solarPanelLevel2");
    public static final Block solarPanelLevel3 = new BlockSolarPanelLevel3("solarPanelLevel3");
    public static final Block solarPanelLevel4 = new BlockSolarPanelLevel4("solarPanelLevel4");
    public static final Block solarPanelLevel5 = new BlockSolarPanelLevel5("solarPanelLevel5");
    public static final Block solarPanelLevel6 = new BlockSolarPanelLevel6("solarPanelLevel6");
    public static final Block solarPanelLevel7 = new BlockSolarPanelLevel7("solarPanelLevel7");
    public static final Block solarPanelLevel8 = new BlockSolarPanelLevel8("solarPanelLevel8");

    public static final Block infinityGenerator = new BlockInfinityGenerator("infinityGenerator");

    public static final Block synthesizer = new BlockSynthesizer("synthesizer");
    public static final Block matterSynthesizer = new BlockMatterSynthesizer("matterSynthesizer");

    public static final Block waterGenerator = new BlockWaterGenerator("waterGenerator");
    public static final Block lavaGenerator = new BlockLavaGenerator("lavaGenerator");

    public static final Block advancedMatterGenerator = new BlockAdvancedMatterGenerator("advancedMatterGenerator");
    public static final Block nanoMatterGenerator = new BlockNanoMatterGenerator("nanoMatterGenerator");
    public static final Block quantumMatterGenerator = new BlockQuantumMatterGenerator("quantumMatterGenerator");
    public static final Block singularMatterGenerator = new BlockSingularMatterGenerator("singularMatterGenerator");

    public static final Block advancedScanner = new BlockAdvancedScanner("advancedScanner");
    public static final Block quantumReplicator = new BlockQuantumReplicator("quantumReplicator");

    public static final Block witherKiller = new BlockWitherKiller("witherKiller");

    public static void registerBlocks() {
        registerCasings();
        registerWitherKiller();
        registerUniversalFluidComplex();
        registerMolecularTransformer();
        registerFluidGenerators();
        registerAdvancedMachines();
        registerNanoMachines();
        registerQuantumMachines();
        registerMatterGenerators();
        registerAdvancedScanner();
        registerReplicator();
        registerGenerators();
        registerEnergyStorages();
        registerCustomSolarPanels();
        registerInfinityGenerator();
        registerSynthesizers();
    }

    private static void registerCasings() {
        if (ContentConfig.enableNanoCasing) {
            RegisterUtils.registerBlock(casingNano);
        }
        if (ContentConfig.enableQuantumCasing) {
            RegisterUtils.registerBlock(casingQuantum);
        }
    }

    private static void registerWitherKiller() {
        if (ContentConfig.enableWitherKiller) {
            RegisterUtils.registerBlock(witherKiller);
            RegisterUtils.registerTile(TileWitherKiller.class);
        }
    }

    private static void registerUniversalFluidComplex() {
        if (ContentConfig.enableUniversalFluidComplex) {
            RegisterUtils.registerBlock(universalFluidComplex, ItemBlockUFC.class);
            RegisterUtils.registerTile(TileUniversalFluidComplex.class);
        }
    }

    private static void registerMolecularTransformer() {
        if (ContentConfig.enableMolecularTransformer) {
            RegisterUtils.registerBlock(molecularTransformer, ItemBlockTransformer.class);
            RegisterUtils.registerTile(TileMolecularTransformer.class);
        }
    }

    private static void registerFluidGenerators() {
        if (ContentConfig.enableWaterGenerator) {
            RegisterUtils.registerBlock(waterGenerator, ItemBlockFluidGenerator.class);
            RegisterUtils.registerTile(TileWaterGenerator.class);
        }
        if (ContentConfig.enableLavaGenerator) {
            RegisterUtils.registerBlock(lavaGenerator, ItemBlockFluidGenerator.class);
            RegisterUtils.registerTile(TileLavaGenerator.class);
        }
    }

    private static void registerAdvancedMachines() {
        if (ContentConfig.enableAdvancedCompressor) {
            RegisterUtils.registerBlock(advancedCompressor, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileAdvancedCompressor.class);
        }
        if (ContentConfig.enableAdvancedExtractor) {
            RegisterUtils.registerBlock(advancedExtractor, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileAdvancedExtractor.class);
        }
        if (ContentConfig.enableAdvancedFurnace) {
            RegisterUtils.registerBlock(advancedFurnace, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileAdvancedFurnace.class);
        }
        if (ContentConfig.enableAdvancedMacerator) {
            RegisterUtils.registerBlock(advancedMacerator, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileAdvancedMacerator.class);
        }
        if (ContentConfig.enableAdvancedMetalFormer) {
            RegisterUtils.registerBlock(advancedMetalFormer, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileAdvancedMetalFormer.class);
        }
        if (ContentConfig.enableAdvancedRecycler) {
            RegisterUtils.registerBlock(advancedRecycler, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileAdvancedRecycler.class);
        }
    }

    private static void registerNanoMachines() {
        if (ContentConfig.enableNanoCompressor) {
            RegisterUtils.registerBlock(nanoCompressor, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileNanoCompressor.class);
        }
        if (ContentConfig.enableNanoExtractor) {
            RegisterUtils.registerBlock(nanoExtractor, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileNanoExtractor.class);
        }
        if (ContentConfig.enableNanoFurnace) {
            RegisterUtils.registerBlock(nanoFurnace, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileNanoFurnace.class);
        }
        if (ContentConfig.enableNanoMacerator) {
            RegisterUtils.registerBlock(nanoMacerator, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileNanoMacerator.class);
        }
        if (ContentConfig.enableNanoMetalFormer) {
            RegisterUtils.registerBlock(nanoMetalFormer, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileNanoMetalFormer.class);
        }
        if (ContentConfig.enableNanoRecycler) {
            RegisterUtils.registerBlock(nanoRecycler, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileNanoRecycler.class);
        }
    }

    private static void registerQuantumMachines() {
        if (ContentConfig.enableQuantumCompressor) {
            RegisterUtils.registerBlock(quantumCompressor, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileQuantumCompressor.class);
        }
        if (ContentConfig.enableQuantumExtractor) {
            RegisterUtils.registerBlock(quantumExtractor, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileQuantumExtractor.class);
        }
        if (ContentConfig.enableQuantumFurnace) {
            RegisterUtils.registerBlock(quantumFurnace, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileQuantumFurnace.class);
        }
        if (ContentConfig.enableQuantumMacerator) {
            RegisterUtils.registerBlock(quantumMacerator, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileQuantumMacerator.class);
        }
        if (ContentConfig.enableQuantumMetalFormer) {
            RegisterUtils.registerBlock(quantumMetalFormer, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileQuantumMetalFormer.class);
        }
        if (ContentConfig.enableQuantumRecycler) {
            RegisterUtils.registerBlock(quantumRecycler, ItemBlockMachine.class);
            RegisterUtils.registerTile(TileQuantumRecycler.class);
        }
    }

    private static void registerMatterGenerators() {
        if (ContentConfig.enableAdvancedMatterGenerator) {
            RegisterUtils.registerBlock(advancedMatterGenerator, ItemBlockMatterGenerator.class);
            RegisterUtils.registerTile(TileAdvancedMatterGenerator.class);
        }
        if (ContentConfig.enableNanoMatterGenerator) {
            RegisterUtils.registerBlock(nanoMatterGenerator, ItemBlockMatterGenerator.class);
            RegisterUtils.registerTile(TileNanoMatterGenerator.class);
        }
        if (ContentConfig.enableQuantumMatterGenerator) {
            RegisterUtils.registerBlock(quantumMatterGenerator, ItemBlockMatterGenerator.class);
            RegisterUtils.registerTile(TileQuantumMatterGenerator.class);
        }
        if (ContentConfig.enableSingularMatterGenerator) {
            RegisterUtils.registerBlock(singularMatterGenerator, ItemBlockMatterGenerator.class);
            RegisterUtils.registerTile(TileSingularMatterGenerator.class);
        }
    }

    private static void registerAdvancedScanner() {
        if (ContentConfig.enableAdvancedScanner) {
            RegisterUtils.registerBlock(advancedScanner);
            RegisterUtils.registerTile(TileAdvancedScanner.class);
        }
    }

    private static void registerReplicator() {
        if (ContentConfig.enableQuantumReplicator) {
            RegisterUtils.registerBlock(quantumReplicator, ItemBlockQuantumReplicator.class);
            RegisterUtils.registerTile(TileQuantumReplicator.class);
        }
    }

    private static void registerGenerators() {
        if (ContentConfig.enableAdvancedGenerator) {
            RegisterUtils.registerBlock(advancedGenerator, ItemBlockGenerator.class);
            RegisterUtils.registerTile(TileAdvancedGenerator.class);
        }
        if (ContentConfig.enableNanoGenerator) {
            RegisterUtils.registerBlock(nanoGenerator, ItemBlockGenerator.class);
            RegisterUtils.registerTile(TileNanoGenerator.class);
        }
        if (ContentConfig.enableQuantumGenerator) {
            RegisterUtils.registerBlock(quantumGenerator, ItemBlockGenerator.class);
            RegisterUtils.registerTile(TileQuantumGenerator.class);
        }
        if (ContentConfig.enableAdvancedKineticGenerator) {
            RegisterUtils.registerBlock(advancedKineticGenerator, ItemBlockKineticGenerator.class);
            RegisterUtils.registerTile(TileAdvancedKineticGenerator.class);
        }
        if (ContentConfig.enableNanoKineticGenerator) {
            RegisterUtils.registerBlock(nanoKineticGenerator, ItemBlockKineticGenerator.class);
            RegisterUtils.registerTile(TileNanoKineticGenerator.class);
        }
        if (ContentConfig.enableQuantumKineticGenerator) {
            RegisterUtils.registerBlock(quantumKineticGenerator, ItemBlockKineticGenerator.class);
            RegisterUtils.registerTile(TileQuantumKineticGenerator.class);
        }
    }

    private static void registerEnergyStorages() {
        if (ContentConfig.enableNewEnergyStorages) {
            RegisterUtils.registerBlocks(ItemBlockEnergyStorage.class, basicEnergyStorage, advancedEnergyStorage, hybridEnergyStorage, nanoEnergyStorage, ultimateEnergyStorage, quantumEnergyStorage, singularEnergyStorage);
            RegisterUtils.registerBlocks(ItemBlockEnergyStorage.class, basicChargePad, advancedChargePad, hybridChargePad, nanoChargePad, ultimateChargePad, quantumChargePad, singularChargePad);
            RegisterUtils.findClasses("foxiwhitee.FoxIndustrialization.tile.storage.advanced", TileEntity.class).forEach(RegisterUtils::registerTile);
            RegisterUtils.findClasses("foxiwhitee.FoxIndustrialization.tile.storage.nano", TileEntity.class).forEach(RegisterUtils::registerTile);
            RegisterUtils.findClasses("foxiwhitee.FoxIndustrialization.tile.storage.quantum", TileEntity.class).forEach(RegisterUtils::registerTile);
            RegisterUtils.findClasses("foxiwhitee.FoxIndustrialization.tile.storage.singular", TileEntity.class).forEach(RegisterUtils::registerTile);
        }
    }

    private static void registerCustomSolarPanels() {
        if (ContentConfig.enableCustomSolarPanels) {
            RegisterUtils.registerBlocks(ItemBlockSolarPanel.class, solarPanelLevel1, solarPanelLevel2, solarPanelLevel3, solarPanelLevel4, solarPanelLevel5, solarPanelLevel6, solarPanelLevel7, solarPanelLevel8);
            RegisterUtils.findClasses("foxiwhitee.FoxIndustrialization.tile.generator.panel", TileEntity.class).forEach(RegisterUtils::registerTile);
        }
    }

    private static void registerInfinityGenerator() {
        if (ContentConfig.enableInfinityGenerator) {
            RegisterUtils.registerBlock(ItemBlockInfinityGenerator.class, infinityGenerator);
            RegisterUtils.registerTile(TileInfinityGenerator.class);
        }
    }

    private static void registerSynthesizers() {
        if (ContentConfig.enableSynthesizer) {
            RegisterUtils.registerBlock(ItemBlockSynthesizer.class, synthesizer);
            RegisterUtils.registerTile(TileSynthesizer.class);
        }
        if (ContentConfig.enableMatterSynthesizer) {
            RegisterUtils.registerBlock(matterSynthesizer);
            RegisterUtils.registerTile(TileMatterSynthesizer.class);
        }
    }
}
