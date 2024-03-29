package mod.coda.farmlife.init;

import mod.coda.farmlife.FarmLife;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class FLConfiguredStructures {
    public static StructureFeature<?, ?> CONFIGURED_TRIBULL_RANCH = FLStructures.TRIBULL_RANCH.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_GREENHOUSE = FLStructures.GREENHOUSE.get().configured(IFeatureConfig.NONE);

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(FarmLife.MOD_ID, "configured_tribull_ranch"), CONFIGURED_TRIBULL_RANCH);
        Registry.register(registry, new ResourceLocation(FarmLife.MOD_ID, "configured_greenhouse"), CONFIGURED_GREENHOUSE);

        FlatGenerationSettings.STRUCTURE_FEATURES.put(FLStructures.TRIBULL_RANCH.get(), CONFIGURED_TRIBULL_RANCH);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(FLStructures.GREENHOUSE.get(), CONFIGURED_GREENHOUSE);
    }
}