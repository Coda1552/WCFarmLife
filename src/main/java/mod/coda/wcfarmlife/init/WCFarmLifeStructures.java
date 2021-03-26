package mod.coda.wcfarmlife.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import mod.coda.wcfarmlife.WCFarmLife;
import mod.coda.wcfarmlife.world.features.GreenhouseStructure;
import mod.coda.wcfarmlife.world.features.TribullRanchStructure;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Locale;

@Mod.EventBusSubscriber(modid = WCFarmLife.MOD_ID)
public class WCFarmLifeStructures {
    public static IStructurePieceType TRIBULL_RANCH_PIECE = register(TribullRanchStructure.Piece::new, "TribullRanch");
    public static IStructurePieceType GREENHOUSE_PIECE = register(GreenhouseStructure.Piece::new, "Greenhouse");
    public static final DeferredRegister<Structure<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, WCFarmLife.MOD_ID);

    public static final RegistryObject<Structure<NoFeatureConfig>> TRIBULL_RANCH = REGISTRY.register("tribull_ranch", () -> (new TribullRanchStructure(NoFeatureConfig.field_236558_a_)));
    public static final RegistryObject<Structure<NoFeatureConfig>> GREENHOUSE = REGISTRY.register("greenhouse", () -> (new GreenhouseStructure(NoFeatureConfig.field_236558_a_)));

    public static void setupStructures() {
        setupMapSpacingAndLand(TRIBULL_RANCH.get(), new StructureSeparationSettings(80, 35, 253895293), true);
        setupMapSpacingAndLand(GREENHOUSE.get(), new StructureSeparationSettings(60, 30, 523185382), true);
    }

    static IStructurePieceType register(IStructurePieceType type, String key) {
        return Registry.register(Registry.STRUCTURE_PIECE, key.toLowerCase(Locale.ROOT), type);
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        if (transformSurroundingLand) {
            Structure.field_236384_t_ = ImmutableList.<Structure<?>>builder().addAll(Structure.field_236384_t_).add(structure).build();
        }

        DimensionStructuresSettings.field_236191_b_ = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.field_236191_b_).put(structure, structureSeparationSettings).build();
    }
}