package mod.coda.wcfarmlife.init;

import mod.coda.wcfarmlife.WCFarmLife;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.item.Rarity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WCFarmLifeItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WCFarmLife.MOD_ID);

    public static final Item TRIBULL_SHANK = register("tribull_shank", new Item(new Item.Properties().group(WCFarmLife.GROUP).food(new Food.Builder().hunger(4).saturation(0.2f).effect(() -> new EffectInstance(Effects.SLOWNESS, 100, 0), 0.25f).build())));
    public static final Item COOKED_TRIBULL_SHANK = register("cooked_tribull_shank", new Item(new Item.Properties().group(WCFarmLife.GROUP).food(new Food.Builder().hunger(10).saturation(0.6f).effect(() -> new EffectInstance(Effects.SLOWNESS, 100, 0), 0.10f).meat().build())));
    public static final Item MUSIC_DISC_LIFE_ON_THE_FARM = register("music_disc_life_on_the_farm", new MusicDiscItem(14, () -> WCFarmLifeSounds.MUSIC_DISC_LIFE_ON_THE_FARM.get(), new Item.Properties().group(WCFarmLife.GROUP).rarity(Rarity.RARE).maxStackSize(1)));

    private static Item register(String name, Item item) {
        REGISTRY.register(name, () -> item);
        return item;
    }
}