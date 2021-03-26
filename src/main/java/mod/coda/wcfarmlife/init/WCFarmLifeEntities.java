package mod.coda.wcfarmlife.init;

import mod.coda.wcfarmlife.WCFarmLife;
import mod.coda.wcfarmlife.entities.DomesticTribullEntity;
import mod.coda.wcfarmlife.entities.GalliraptorEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WCFarmLifeEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, WCFarmLife.MOD_ID);

    public static final RegistryObject<EntityType<DomesticTribullEntity>> DOMESTIC_TRIBULL = create("domestic_tribull", EntityType.Builder.create(DomesticTribullEntity::new, EntityClassification.CREATURE).size(1.1f, 1.2f));
    public static final RegistryObject<EntityType<GalliraptorEntity>> GALLIRAPTOR = create("galliraptor", EntityType.Builder.create(GalliraptorEntity::new, EntityClassification.CREATURE).size(0.6f, 0.8f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return REGISTRY.register(name, () -> builder.build(WCFarmLife.MOD_ID + "." + name));
    }
}