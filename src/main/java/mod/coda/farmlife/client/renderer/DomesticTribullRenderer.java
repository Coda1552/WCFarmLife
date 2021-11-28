package mod.coda.farmlife.client.renderer;

import mod.coda.farmlife.FarmLife;
import mod.coda.farmlife.client.model.DomesticTribullModel;
import mod.coda.farmlife.entities.DomesticTribullEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DomesticTribullRenderer extends MobRenderer<DomesticTribullEntity, DomesticTribullModel<DomesticTribullEntity>> {
    private static final ResourceLocation ADULT = new ResourceLocation(FarmLife.MOD_ID, "textures/entity/domestic_tribull.png");
    private static final ResourceLocation BABY = new ResourceLocation(FarmLife.MOD_ID, "textures/entity/domestic_tribull_baby.png");

    public DomesticTribullRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new DomesticTribullModel<>(), 0.7F);
    }

    public ResourceLocation getTextureLocation(DomesticTribullEntity entity) {
        if (entity.isBaby()) {
            return BABY;
        }
        else {
            return ADULT;
        }
    }
}