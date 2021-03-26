package mod.coda.wcfarmlife;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import mod.coda.wcfarmlife.client.ClientEventHandler;
import mod.coda.wcfarmlife.entities.DomesticTribullEntity;
import mod.coda.wcfarmlife.entities.GalliraptorEntity;
import mod.coda.wcfarmlife.init.*;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber
@Mod(WCFarmLife.MOD_ID)
public class WCFarmLife {
    public static final String MOD_ID = "wcfarmlife";
    public static final Logger LOGGER = LogManager.getLogger();

    public WCFarmLife() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerClient);
        modEventBus.addListener(this::registerCommon);
        modEventBus.addListener(this::setup);

        WCFarmLifeItems.REGISTRY.register(modEventBus);
        WCFarmLifeEntities.REGISTRY.register(modEventBus);
        WCFarmLifeStructures.REGISTRY.register(modEventBus);
        WCFarmLifeSounds.REGISTRY.register(modEventBus);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
    }

    private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(WCFarmLifeEntities.DOMESTIC_TRIBULL.get(), DomesticTribullEntity.createAttributes().create());
        GlobalEntityTypeAttributes.put(WCFarmLifeEntities.GALLIRAPTOR.get(), GalliraptorEntity.createAttributes().create());
    }

    private void registerClient(FMLClientSetupEvent event) {
        ClientEventHandler.init();
    }

    public final static ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(WCFarmLifeItems.TRIBULL_SHANK.get());}
    };

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            WCFarmLifeStructures.setupStructures();
            WCFarmLifeConfiguredStructures.registerConfiguredStructures();

            WorldGenRegistries.NOISE_SETTINGS.getEntries().forEach(settings -> {
                Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().getStructures().func_236195_a_();

                if (structureMap instanceof ImmutableMap) {
                    Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                    tempMap.put(WCFarmLifeStructures.TRIBULL_RANCH.get(), DimensionStructuresSettings.field_236191_b_.get(WCFarmLifeStructures.TRIBULL_RANCH.get()));
                    tempMap.put(WCFarmLifeStructures.GREENHOUSE.get(), DimensionStructuresSettings.field_236191_b_.get(WCFarmLifeStructures.GREENHOUSE.get()));
                    settings.getValue().getStructures().field_236193_d_ = tempMap;
                }
                else {
                    structureMap.put(WCFarmLifeStructures.TRIBULL_RANCH.get(), DimensionStructuresSettings.field_236191_b_.get(WCFarmLifeStructures.TRIBULL_RANCH.get()));
                    structureMap.put(WCFarmLifeStructures.GREENHOUSE.get(), DimensionStructuresSettings.field_236191_b_.get(WCFarmLifeStructures.GREENHOUSE.get()));
                }
            });
        });
    }

    public void biomeModification(final BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.PLAINS) {
            event.getGeneration().getStructures().add(() -> WCFarmLifeConfiguredStructures.CONFIGURED_TRIBULL_RANCH);
        }
        if (event.getCategory() == Biome.Category.FOREST) {
            event.getGeneration().getStructures().add(() -> WCFarmLifeConfiguredStructures.CONFIGURED_GREENHOUSE);
        }
    }

    private static Method GETCODEC_METHOD;
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "getCodec");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR_CODEC.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkProvider().generator));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                WCFarmLife.LOGGER.error("Was unable to check if " + serverWorld.getDimensionKey().getLocation() + " is using Terraforged's ChunkGenerator.");
            }

            if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.getDimensionKey().equals(World.OVERWORLD)){
                return;
            }


            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
            tempMap.putIfAbsent(WCFarmLifeStructures.TRIBULL_RANCH.get(), DimensionStructuresSettings.field_236191_b_.get(WCFarmLifeStructures.TRIBULL_RANCH.get()));
            serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
        }
    }
}