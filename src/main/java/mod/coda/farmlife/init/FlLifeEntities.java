package mod.coda.farmlife.init;

import mod.coda.farmlife.FarmLife;
import mod.coda.farmlife.entities.DomesticTribullEntity;
import mod.coda.farmlife.entities.GalliraptorEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FlLifeEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, FarmLife.MOD_ID);

    public static final RegistryObject<EntityType<DomesticTribullEntity>> DOMESTIC_TRIBULL = create("domestic_tribull", EntityType.Builder.of(DomesticTribullEntity::new, EntityClassification.CREATURE).sized(1.1f, 1.2f));
    public static final RegistryObject<EntityType<GalliraptorEntity>> GALLIRAPTOR = create("galliraptor", EntityType.Builder.of(GalliraptorEntity::new, EntityClassification.CREATURE).sized(0.6f, 0.8f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTRY.register(name, () -> builder.build(FarmLife.MOD_ID + "." + name));
    }
}