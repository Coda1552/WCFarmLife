package mod.coda.farmlife;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import mod.coda.farmlife.client.ClientEventHandler;
import mod.coda.farmlife.entities.DomesticTribullEntity;
import mod.coda.farmlife.entities.GalliraptorEntity;
import mod.coda.farmlife.init.*;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
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

import net.minecraft.block.ComposterBlock;

@Mod.EventBusSubscriber
@Mod(FarmLife.MOD_ID)
public class FarmLife {
    public static final String MOD_ID = "farmlife";
    public static final Logger LOGGER = LogManager.getLogger();

    public FarmLife() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::registerClient);
        modEventBus.addListener(this::registerCommon);
        modEventBus.addListener(this::setup);

        FLBlocks.REGISTRY.register(modEventBus);
        FLItems.REGISTRY.register(modEventBus);
        FlLifeEntities.REGISTRY.register(modEventBus);
        FLStructures.REGISTRY.register(modEventBus);
        FLSounds.REGISTRY.register(modEventBus);

        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        forgeBus.addListener(EventPriority.NORMAL, this::addDimensionalSpacing);
        forgeBus.addListener(EventPriority.HIGH, this::biomeModification);
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        registerEntityAttributes();
        registerCompostable(0.3F, FLItems.PEACOCK_BURST_POPPY.get());
        registerCompostable(0.3F, FLItems.ELECTRIC_BURST_POPPY.get());
        registerCompostable(0.3F, FLItems.FANCY_BURST_POPPY.get());
        registerCompostable(0.3F, FLItems.OLIVE_BURST_POPPY.get());
        registerCompostable(0.3F, FLItems.RUSTY_BURST_POPPY.get());
        registerCompostable(0.3F, FLItems.SUNSTREAK_BURST_POPPY.get());
    }

    public static void registerCompostable(float chance, IItemProvider item) {
        ComposterBlock.COMPOSTABLES.put(item.asItem(), chance);
    }

    private void registerEntityAttributes() {
        GlobalEntityTypeAttributes.put(FlLifeEntities.DOMESTIC_TRIBULL.get(), DomesticTribullEntity.createAttributes().build());
        GlobalEntityTypeAttributes.put(FlLifeEntities.GALLIRAPTOR.get(), GalliraptorEntity.createAttributes().build());
    }

    private void registerClient(FMLClientSetupEvent event) {
        ClientEventHandler.init();
    }

    public final static ItemGroup GROUP = new ItemGroup(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(FLItems.TRIBULL_SHANK.get());}
    };

    public void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FLStructures.setupStructures();
            FLConfiguredStructures.registerConfiguredStructures();

            WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
                Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();

                if (structureMap instanceof ImmutableMap) {
                    Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                    tempMap.put(FLStructures.TRIBULL_RANCH.get(), DimensionStructuresSettings.DEFAULTS.get(FLStructures.TRIBULL_RANCH.get()));
                    tempMap.put(FLStructures.GREENHOUSE.get(), DimensionStructuresSettings.DEFAULTS.get(FLStructures.GREENHOUSE.get()));
                    settings.getValue().structureSettings().structureConfig = tempMap;
                }
                else {
                    structureMap.put(FLStructures.TRIBULL_RANCH.get(), DimensionStructuresSettings.DEFAULTS.get(FLStructures.TRIBULL_RANCH.get()));
                    structureMap.put(FLStructures.GREENHOUSE.get(), DimensionStructuresSettings.DEFAULTS.get(FLStructures.GREENHOUSE.get()));
                }
            });
        });
    }

    public void biomeModification(final BiomeLoadingEvent event) {
        if (event.getCategory() == Biome.Category.PLAINS) {
            event.getGeneration().getStructures().add(() -> FLConfiguredStructures.CONFIGURED_TRIBULL_RANCH);
        }
        if (event.getCategory() == Biome.Category.FOREST) {
            event.getGeneration().getStructures().add(() -> FLConfiguredStructures.CONFIGURED_GREENHOUSE);
        }
    }

    private static Method GETCODEC_METHOD;
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld){
            ServerWorld serverWorld = (ServerWorld)event.getWorld();

            try {
                if(GETCODEC_METHOD == null) GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "getCodec");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(serverWorld.getChunkSource().generator));
                if(cgRL != null && cgRL.getNamespace().equals("terraforged")) return;
            }
            catch(Exception e){
                FarmLife.LOGGER.error("Was unable to check if " + serverWorld.dimension().location() + " is using Terraforged's ChunkGenerator.");
            }

            if(serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.dimension().equals(World.OVERWORLD)){
                return;
            }


            Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
            tempMap.putIfAbsent(FLStructures.TRIBULL_RANCH.get(), DimensionStructuresSettings.DEFAULTS.get(FLStructures.TRIBULL_RANCH.get()));
            serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
        }
    }
}