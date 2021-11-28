package mod.coda.farmlife.init;

import mod.coda.farmlife.FarmLife;
import mod.coda.farmlife.items.FLSpawnEggItem;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FLItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, FarmLife.MOD_ID);

    public static final RegistryObject<Item> TRIBULL_SHANK = REGISTRY.register("tribull_shank", () -> new Item(new Item.Properties().tab(FarmLife.GROUP).food(new Food.Builder().nutrition(4).saturationMod(0.2f).effect(() -> new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 0), 0.25f).meat().build())));
    public static final RegistryObject<Item> COOKED_TRIBULL_SHANK = REGISTRY.register("cooked_tribull_shank", () -> new Item(new Item.Properties().tab(FarmLife.GROUP).food(new Food.Builder().nutrition(10).saturationMod(0.6f).effect(() -> new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 0), 0.10f).meat().build())));
    public static final RegistryObject<Item> MUSIC_DISC_LIFE_ON_THE_FARM = REGISTRY.register("music_disc_life_on_the_farm", () -> new MusicDiscItem(14, FLSounds.MUSIC_DISC_LIFE_ON_THE_FARM::get, new Item.Properties().tab(FarmLife.GROUP).rarity(Rarity.RARE).stacksTo(1)));

    public static final RegistryObject<Item> DOMESTIC_TRIBULL_SPAWN_EGG = REGISTRY.register("domestic_tribull_spawn_egg", () -> new FLSpawnEggItem(FlLifeEntities.DOMESTIC_TRIBULL, 0x92b3b0, 0x738681, new Item.Properties().tab(FarmLife.GROUP)));
    public static final RegistryObject<Item> GALLIRAPTOR_SPAWN_EGG = REGISTRY.register("galliraptor_spawn_egg", () -> new FLSpawnEggItem(FlLifeEntities.GALLIRAPTOR, 0xefa83e, 0x182749, new Item.Properties().tab(FarmLife.GROUP)));

    public static final RegistryObject<Item> PEACOCK_BURST_POPPY = REGISTRY.register("peacock_burst_poppy", () -> new BlockItem(FLBlocks.PEACOCK_BURST_POPPY.get(), new Item.Properties().tab(FarmLife.GROUP)));
    public static final RegistryObject<Item> ELECTRIC_BURST_POPPY = REGISTRY.register("electric_burst_poppy", () -> new BlockItem(FLBlocks.ELECTRIC_BURST_POPPY.get(), new Item.Properties().tab(FarmLife.GROUP)));
    public static final RegistryObject<Item> FANCY_BURST_POPPY = REGISTRY.register("fancy_burst_poppy", () -> new BlockItem(FLBlocks.FANCY_BURST_POPPY.get(), new Item.Properties().tab(FarmLife.GROUP)));
    public static final RegistryObject<Item> OLIVE_BURST_POPPY = REGISTRY.register("olive_burst_poppy", () -> new BlockItem(FLBlocks.OLIVE_BURST_POPPY.get(), new Item.Properties().tab(FarmLife.GROUP)));
    public static final RegistryObject<Item> RUSTY_BURST_POPPY = REGISTRY.register("rusty_burst_poppy", () -> new BlockItem(FLBlocks.RUSTY_BURST_POPPY.get(), new Item.Properties().tab(FarmLife.GROUP)));
    public static final RegistryObject<Item> SUNSTREAK_BURST_POPPY = REGISTRY.register("sunstreak_burst_poppy", () -> new BlockItem(FLBlocks.SUNSTREAK_BURST_POPPY.get(), new Item.Properties().tab(FarmLife.GROUP)));
}