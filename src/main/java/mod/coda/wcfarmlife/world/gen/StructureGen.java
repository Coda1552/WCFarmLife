package mod.coda.wcfarmlife.world.gen;

import mod.coda.wcfarmlife.init.WCFarmLifeFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.*;
import net.minecraftforge.registries.ForgeRegistries;

public class StructureGen {
    public static void generateStructures() {
        for (Biome biome : ForgeRegistries.BIOMES) {
            if (biome == Biomes.PLAINS) {
                biome.addStructure(WCFarmLifeFeatures.TRIBULL_RANCH.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
            }
            biome.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, WCFarmLifeFeatures.TRIBULL_RANCH.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.COUNT_EXTRA_HEIGHTMAP.configure(new AtSurfaceWithExtraConfig(1, 5.0f, 0))));
        }
    }
}
