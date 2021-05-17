package mod.coda.wcfarmlife.init;

import mod.coda.wcfarmlife.WCFarmLife;
import mod.coda.wcfarmlife.items.WCFarmLifeSpawnEggItem;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WCFarmLifeItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WCFarmLife.MOD_ID);

    public static final RegistryObject<Item> TRIBULL_SHANK = REGISTRY.register("tribull_shank", () -> new Item(new Item.Properties().group(WCFarmLife.GROUP).food(new Food.Builder().hunger(4).saturation(0.2f).effect(() -> new EffectInstance(Effects.SLOWNESS, 100, 0), 0.25f).meat().build())));
    public static final RegistryObject<Item> COOKED_TRIBULL_SHANK = REGISTRY.register("cooked_tribull_shank", () -> new Item(new Item.Properties().group(WCFarmLife.GROUP).food(new Food.Builder().hunger(10).saturation(0.6f).effect(() -> new EffectInstance(Effects.SLOWNESS, 100, 0), 0.10f).meat().build())));
    public static final RegistryObject<Item> MUSIC_DISC_LIFE_ON_THE_FARM = REGISTRY.register("music_disc_life_on_the_farm", () -> new MusicDiscItem(14, WCFarmLifeSounds.MUSIC_DISC_LIFE_ON_THE_FARM::get, new Item.Properties().group(WCFarmLife.GROUP).rarity(Rarity.RARE).maxStackSize(1)));

    public static final RegistryObject<Item> DOMESTIC_TRIBULL_SPAWN_EGG = REGISTRY.register("domestic_tribull_spawn_egg", () -> new WCFarmLifeSpawnEggItem(WCFarmLifeEntities.DOMESTIC_TRIBULL, 0x92b3b0, 0x738681, new Item.Properties().group(WCFarmLife.GROUP)));
    public static final RegistryObject<Item> GALLIRAPTOR_SPAWN_EGG = REGISTRY.register("galliraptor_spawn_egg", () -> new WCFarmLifeSpawnEggItem(WCFarmLifeEntities.GALLIRAPTOR, 0xefa83e, 0x182749, new Item.Properties().group(WCFarmLife.GROUP)));

    public static final RegistryObject<Item> PEACOCK_BURST_POPPY = REGISTRY.register("peacock_burst_poppy", () -> new BlockItem(WCFarmLifeBlocks.PEACOCK_BURST_POPPY.get(), new Item.Properties().group(WCFarmLife.GROUP)));
    public static final RegistryObject<Item> ELECTRIC_BURST_POPPY = REGISTRY.register("electric_burst_poppy", () -> new BlockItem(WCFarmLifeBlocks.ELECTRIC_BURST_POPPY.get(), new Item.Properties().group(WCFarmLife.GROUP)));
    public static final RegistryObject<Item> FANCY_BURST_POPPY = REGISTRY.register("fancy_burst_poppy", () -> new BlockItem(WCFarmLifeBlocks.FANCY_BURST_POPPY.get(), new Item.Properties().group(WCFarmLife.GROUP)));
    public static final RegistryObject<Item> OLIVE_BURST_POPPY = REGISTRY.register("olive_burst_poppy", () -> new BlockItem(WCFarmLifeBlocks.OLIVE_BURST_POPPY.get(), new Item.Properties().group(WCFarmLife.GROUP)));
    public static final RegistryObject<Item> RUSTY_BURST_POPPY = REGISTRY.register("rusty_burst_poppy", () -> new BlockItem(WCFarmLifeBlocks.RUSTY_BURST_POPPY.get(), new Item.Properties().group(WCFarmLife.GROUP)));
    public static final RegistryObject<Item> SUNSTREAK_BURST_POPPY = REGISTRY.register("sunstreak_burst_poppy", () -> new BlockItem(WCFarmLifeBlocks.SUNSTREAK_BURST_POPPY.get(), new Item.Properties().group(WCFarmLife.GROUP)));
}