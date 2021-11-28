package mod.coda.farmlife.client;

import mod.coda.farmlife.FarmLife;
import mod.coda.farmlife.client.renderer.DomesticTribullRenderer;
import mod.coda.farmlife.client.renderer.GalliraptorRenderer;
import mod.coda.farmlife.init.FLBlocks;
import mod.coda.farmlife.init.FlLifeEntities;
import mod.coda.farmlife.items.FLSpawnEggItem;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = FarmLife.MOD_ID)
public class ClientEventHandler {

    @OnlyIn(Dist.CLIENT)
    public static void init() {
        RenderingRegistry.registerEntityRenderingHandler(FlLifeEntities.DOMESTIC_TRIBULL.get(), DomesticTribullRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(FlLifeEntities.GALLIRAPTOR.get(), GalliraptorRenderer::new);
        RenderTypeLookup.setRenderLayer(FLBlocks.PEACOCK_BURST_POPPY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLBlocks.ELECTRIC_BURST_POPPY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLBlocks.FANCY_BURST_POPPY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLBlocks.OLIVE_BURST_POPPY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLBlocks.RUSTY_BURST_POPPY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLBlocks.SUNSTREAK_BURST_POPPY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLBlocks.POTTED_PEACOCK_BURST_POPPY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLBlocks.POTTED_ELECTRIC_BURST_POPPY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLBlocks.POTTED_FANCY_BURST_POPPY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLBlocks.POTTED_OLIVE_BURST_POPPY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLBlocks.POTTED_RUSTY_BURST_POPPY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(FLBlocks.POTTED_SUNSTREAK_BURST_POPPY.get(), RenderType.cutout());
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void itemColors(ColorHandlerEvent.Item event) {
        ItemColors handler = event.getItemColors();
        IItemColor eggColor = (stack, tintIndex) -> ((FLSpawnEggItem) stack.getItem()).getColor(tintIndex);
        for (FLSpawnEggItem e : FLSpawnEggItem.UNADDED_EGGS) handler.register(eggColor, e);
    }
}