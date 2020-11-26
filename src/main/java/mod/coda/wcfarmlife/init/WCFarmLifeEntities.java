package mod.coda.wcfarmlife.init;

import mod.coda.wcfarmlife.WCFarmLife;
import mod.coda.wcfarmlife.entity.DomesticTribullEntity;
import mod.coda.wcfarmlife.entity.GalliraptorEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WCFarmLifeEntities {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, WCFarmLife.MOD_ID);

    public static final EntityType<DomesticTribullEntity> DOMESTIC_TRIBULL = create("domestic_tribull", DomesticTribullEntity::new, EntityClassification.CREATURE, 1.1f, 1.2f, 0x92b3b0, 0x738681);
    public static final EntityType<GalliraptorEntity> GALLIRAPTOR = create("galliraptor", GalliraptorEntity::new, EntityClassification.CREATURE, 0.6f, 0.8f, 0xefa83e, 0x182749);

    private static <T extends CreatureEntity> EntityType<T> create(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height, int pri, int sec) {
        final Item.Properties properties = new Item.Properties().group(WCFarmLife.GROUP);
        EntityType<T> type = create(name, factory, classification, width, height);
        WCFarmLifeItems.REGISTRY.register(name + "_spawn_egg", () -> new SpawnEggItem(type, pri, sec, properties));
        return type;
    }

    private static <T extends Entity> EntityType<T> create(String name, EntityType.IFactory<T> factory, EntityClassification classification, float width, float height) {
        EntityType<T> type = EntityType.Builder.create(factory, classification).size(width, height).setTrackingRange(128).build(name);
        REGISTRY.register(name, () -> type);
        return type;
    }
}