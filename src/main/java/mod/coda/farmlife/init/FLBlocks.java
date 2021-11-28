package mod.coda.farmlife.init;

import mod.coda.farmlife.FarmLife;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FLBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, FarmLife.MOD_ID);

    public static final RegistryObject<Block> PEACOCK_BURST_POPPY = REGISTRY.register("peacock_burst_poppy", () -> new FlowerBlock(Effects.LUCK, 5, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> ELECTRIC_BURST_POPPY = REGISTRY.register("electric_burst_poppy", () -> new FlowerBlock(Effects.HARM, 1, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> FANCY_BURST_POPPY = REGISTRY.register("fancy_burst_poppy", () -> new FlowerBlock(Effects.REGENERATION, 5, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> OLIVE_BURST_POPPY = REGISTRY.register("olive_burst_poppy", () -> new FlowerBlock(Effects.HUNGER, 5, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> RUSTY_BURST_POPPY = REGISTRY.register("rusty_burst_poppy", () -> new FlowerBlock(Effects.WEAKNESS, 5, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));
    public static final RegistryObject<Block> SUNSTREAK_BURST_POPPY = REGISTRY.register("sunstreak_burst_poppy", () -> new FlowerBlock(Effects.FIRE_RESISTANCE, 5, AbstractBlock.Properties.of(Material.PLANT).noCollission().instabreak().sound(SoundType.GRASS)));

    public static final RegistryObject<Block> POTTED_PEACOCK_BURST_POPPY = REGISTRY.register("potted_peacock_burst_poppy", () -> new FlowerPotBlock(PEACOCK_BURST_POPPY.get(), AbstractBlock.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final RegistryObject<Block> POTTED_ELECTRIC_BURST_POPPY = REGISTRY.register("potted_electric_burst_poppy", () -> new FlowerPotBlock(ELECTRIC_BURST_POPPY.get(), AbstractBlock.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final RegistryObject<Block> POTTED_FANCY_BURST_POPPY = REGISTRY.register("potted_fancy_burst_poppy", () -> new FlowerPotBlock(FANCY_BURST_POPPY.get(), AbstractBlock.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final RegistryObject<Block> POTTED_OLIVE_BURST_POPPY = REGISTRY.register("potted_olive_burst_poppy", () -> new FlowerPotBlock(OLIVE_BURST_POPPY.get(), AbstractBlock.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final RegistryObject<Block> POTTED_RUSTY_BURST_POPPY = REGISTRY.register("potted_rusty_burst_poppy", () -> new FlowerPotBlock(RUSTY_BURST_POPPY.get(), AbstractBlock.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
    public static final RegistryObject<Block> POTTED_SUNSTREAK_BURST_POPPY = REGISTRY.register("potted_sunstreak_burst_poppy", () -> new FlowerPotBlock(SUNSTREAK_BURST_POPPY.get(), AbstractBlock.Properties.of(Material.DECORATION).instabreak().noOcclusion()));
}