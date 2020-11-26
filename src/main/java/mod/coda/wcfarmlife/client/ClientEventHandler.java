package mod.coda.wcfarmlife.client;

import mod.coda.wcfarmlife.client.renderer.DomesticTribullRenderer;
import mod.coda.wcfarmlife.client.renderer.GalliraptorRenderer;
import mod.coda.wcfarmlife.init.WCFarmLifeEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientEventHandler {
    @OnlyIn(Dist.CLIENT)
    public static void init() {
        RenderingRegistry.registerEntityRenderingHandler(WCFarmLifeEntities.DOMESTIC_TRIBULL, DomesticTribullRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(WCFarmLifeEntities.GALLIRAPTOR, GalliraptorRenderer::new);
    }
}