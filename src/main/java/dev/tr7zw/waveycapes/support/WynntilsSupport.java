package dev.tr7zw.waveycapes.support;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.wynntils.core.components.Services;
import dev.tr7zw.waveycapes.CapeRenderer;
import dev.tr7zw.waveycapes.NMSUtil;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class WynntilsSupport implements ModSupport {

    private WynntilsCapesRenderer render = new WynntilsCapesRenderer();
    private ResourceLocation cape = null;

    @Override
    public boolean shouldBeUsed(AbstractClientPlayer player) {
        if (!Services.Cosmetics.shouldRenderCape(player, false)) {
            return false;
        } else {
            cape = Services.Cosmetics.getCapeTexture(player);
            return true;
        }
    }

    @Override
    public CapeRenderer getRenderer() {
        return render;
    }

    private class WynntilsCapesRenderer implements CapeRenderer {

        @Override
        public void render(AbstractClientPlayer player, int part, ModelPart model, PoseStack poseStack,
                           MultiBufferSource multiBufferSource, int light, int overlay) {
            VertexConsumer vertexConsumer;
            if (Services.Cosmetics.shouldRenderCape(player, false) && cape != null) {
                // spotless:off
                //#if MC >= 12100
                vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource,
                        RenderType.armorCutoutNoCull(cape),
                        false);
                //#else
                //$$ vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource,
                //$$         RenderType.armorCutoutNoCull(cape), false,
                //$$         false);
                //#endif
                //spotless:on
            } else {
                // spotless:off
                //#if MC >= 12100
                vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource,
                        RenderType.armorCutoutNoCull(NMSUtil.getPlayerCape(player)), false);
                //#else
                //$$  vertexConsumer = ItemRenderer.getArmorFoilBuffer(multiBufferSource,
                //$$          RenderType.armorCutoutNoCull(NMSUtil.getPlayerCape(player)), false, false);
                //#endif
                //spotless:on
            }
            model.render(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);
        }

        @Override
        public VertexConsumer getVertexConsumer(MultiBufferSource multiBufferSource, AbstractClientPlayer player) {
            if (Services.Cosmetics.shouldRenderCape(player, false) && cape != null) {
                // spotless:off
                //#if MC >= 12100
                return ItemRenderer.getArmorFoilBuffer(multiBufferSource,
                        RenderType.armorCutoutNoCull(cape),
                        false);
                //#else
                //$$  return ItemRenderer.getArmorFoilBuffer(multiBufferSource,
                //$$          RenderType.armorCutoutNoCull(cape), false,
                //$$          false);
                //#endif
                //spotless:on
            } else {
                // spotless:off
                //#if MC >= 12100
                return ItemRenderer.getArmorFoilBuffer(multiBufferSource,
                        RenderType.armorCutoutNoCull(NMSUtil.getPlayerCape(player)), false);
                //#else
                //$$  return ItemRenderer.getArmorFoilBuffer(multiBufferSource,
                //$$          RenderType.armorCutoutNoCull(NMSUtil.getPlayerCape(player)), false, false);
                //#endif
                //spotless:on
            }
        }

        @Override
        public boolean vanillaUvValues() {
            return true;
        }

    }

    @Override
    public boolean blockFeatureRenderer(Object feature) {
        return false;
    }

}
