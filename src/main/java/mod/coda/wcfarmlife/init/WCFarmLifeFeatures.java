package mod.coda.wcfarmlife.init;

import mod.coda.wcfarmlife.WCFarmLife;
import mod.coda.wcfarmlife.world.gen.TribullRanchPieces;
import mod.coda.wcfarmlife.world.gen.TribullRanchStructure;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WCFarmLifeFeatures {
    public static final DeferredRegister<Feature<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.FEATURES, WCFarmLife.MOD_ID);

    public static final TribullRanchStructure TRIBULL_RANCH = register("tribull_ranch", new TribullRanchStructure());

    public static IStructurePieceType TRIBULL_RANCH_PIECE = TribullRanchPieces.Piece::new;

    private static <T extends Feature<?>> T register(String name, T feature) {
        REGISTRY.register(name, () -> feature);
        return feature;
    }
}
