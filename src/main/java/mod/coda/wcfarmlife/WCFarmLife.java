package mod.coda.wcfarmlife;

import mod.coda.wcfarmlife.client.ClientEventHandler;
import mod.coda.wcfarmlife.entity.DomesticTribullEntity;
import mod.coda.wcfarmlife.entity.GalliraptorEntity;
import mod.coda.wcfarmlife.init.WCFarmLifeEntities;
import mod.coda.wcfarmlife.init.WCFarmLifeItems;
import mod.coda.wcfarmlife.init.WCFarmLifeSounds;
import mod.coda.wcfarmlife.init.WCFarmLifeStructures;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod.EventBusSubscriber
@Mod(WCFarmLife.MOD_ID)
public class WCFarmLife {
    public static final String MOD_ID = "wcfarmlife";

    public WCFarmLife() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerClient);
        modEventBus.addListener(this::registerCommon);

        WCFarmLifeItems.REGISTRY.register(modEventBus);
        WCFarmLifeEntities.REGISTRY.register(modEventBus);
        WCFarmLifeStructures.REGISTRY.register(modEventBus);
        WCFarmLifeSounds.REGISTRY.register(modEventBus);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
    }

    private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(WCFarmLifeEntities.DOMESTIC_TRIBULL, DomesticTribullEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(WCFarmLifeEntities.GALLIRAPTOR, GalliraptorEntity.createAttributes().create());
    }

    private void registerClient(FMLClientSetupEvent event) {
        ClientEventHandler.init();
    }

    @SubscribeEvent
    private static void setupBiomes(BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.PLAINS) {
            event.getGeneration().getStructures().add(() -> WCFarmLifeStructures.TRIBULL_RANCH.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG));
        }
    }

    public final static ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(WCFarmLifeItems.TRIBULL_SHANK);}
    };
}