package mod.coda.wcfarmlife.init;

import mod.coda.wcfarmlife.WCFarmLife;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WCFarmLifeItems {
    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, WCFarmLife.MOD_ID);

    public static final Item TRIBULL_SHANK = register("tribull_shank", new Item(new Item.Properties().group(WCFarmLife.GROUP).food(new Food.Builder().hunger(4).saturation(0.2f).effect(() -> new EffectInstance(Effects.SLOWNESS, 100, 0), 0.25f).build())));
    public static final Item COOKED_TRIBULL_SHANK = register("cooked_tribull_shank", new Item(new Item.Properties().group(WCFarmLife.GROUP).food(new Food.Builder().hunger(10).saturation(0.6f).effect(() -> new EffectInstance(Effects.SLOWNESS, 100, 0), 0.10f).meat().build())));

    private static Item register(String name, Item item) {
        REGISTRY.register(name, () -> item);
        return item;
    }
}