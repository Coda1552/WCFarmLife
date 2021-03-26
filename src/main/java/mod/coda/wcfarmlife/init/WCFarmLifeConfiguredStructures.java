package mod.coda.wcfarmlife.init;

import mod.coda.wcfarmlife.WCFarmLife;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class WCFarmLifeConfiguredStructures {
    public static StructureFeature<?, ?> CONFIGURED_TRIBULL_RANCH = WCFarmLifeStructures.TRIBULL_RANCH.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> CONFIGURED_GREENHOUSE = WCFarmLifeStructures.GREENHOUSE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(WCFarmLife.MOD_ID, "configured_tribull_ranch"), CONFIGURED_TRIBULL_RANCH);
        Registry.register(registry, new ResourceLocation(WCFarmLife.MOD_ID, "configured_greenhouse"), CONFIGURED_GREENHOUSE);

        FlatGenerationSettings.STRUCTURES.put(WCFarmLifeStructures.TRIBULL_RANCH.get(), CONFIGURED_TRIBULL_RANCH);
        FlatGenerationSettings.STRUCTURES.put(WCFarmLifeStructures.GREENHOUSE.get(), CONFIGURED_GREENHOUSE);
    }
}