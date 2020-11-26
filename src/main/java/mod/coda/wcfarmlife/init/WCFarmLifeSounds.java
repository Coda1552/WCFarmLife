package mod.coda.wcfarmlife.init;

import mod.coda.wcfarmlife.WCFarmLife;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class WCFarmLifeSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WCFarmLife.MOD_ID);

    public static final RegistryObject<SoundEvent> DOMESTIC_TRIBULL_AMBIENT = REGISTRY.register("domestic_tribull_ambient", () -> new SoundEvent(new ResourceLocation(WCFarmLife.MOD_ID, "domestic_tribull.ambient")));
    public static final RegistryObject<SoundEvent> DOMESTIC_TRIBULL_HURT = REGISTRY.register("domestic_tribull_hurt", () -> new SoundEvent(new ResourceLocation(WCFarmLife.MOD_ID, "domestic_tribull.hurt")));
    public static final RegistryObject<SoundEvent> DOMESTIC_TRIBULL_DEATH = REGISTRY.register("domestic_tribull_death", () -> new SoundEvent(new ResourceLocation(WCFarmLife.MOD_ID, "domestic_tribull.death")));

    public static final RegistryObject<SoundEvent> GALLIRAPTOR_AMBIENT = REGISTRY.register("galliraptor_ambient", () -> new SoundEvent(new ResourceLocation(WCFarmLife.MOD_ID, "galliraptor.ambient")));
    public static final RegistryObject<SoundEvent> GALLIRAPTOR_HURT = REGISTRY.register("galliraptor_hurt", () -> new SoundEvent(new ResourceLocation(WCFarmLife.MOD_ID, "galliraptor.hurt")));
    public static final RegistryObject<SoundEvent> GALLIRAPTOR_DEATH = REGISTRY.register("galliraptor_death", () -> new SoundEvent(new ResourceLocation(WCFarmLife.MOD_ID, "galliraptor.death")));
}

