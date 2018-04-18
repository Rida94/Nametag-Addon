package me.conortheoreo.nametag.visual;

import cc.hyperium.event.InvokeEvent;
import cc.hyperium.event.RenderHUDEvent;
import cc.hyperium.event.RenderPlayerEvent;
import me.conortheoreo.nametag.NametagMod;
import me.conortheoreo.nametag.all.TrustedUsers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

import java.util.function.Predicate;

public class TagRenderer {

    private RenderManager renderManager;
    private Minecraft mc;

    public TagRenderer() {
        this.mc = Minecraft.getMinecraft();
        this.renderManager = this.mc.getRenderManager();
    }

    @InvokeEvent
    public void onRender(final RenderPlayerEvent event) {
        if (event.getEntity().isEntityEqual(this.renderManager.livingPlayer) && NametagMod.SHOW_NAMETAG) {
            this.renderTag(event.getEntity(), 0.0, 0.25, 0.0);
        }
    }

    protected void renderTag(final EntityLivingBase entity, final double x, final double y, final double z) {
        final String displayTag = NametagMod.NAMETAG_COLOR + EnumChatFormatting.getTextWithoutFormattingCodes(entity.getDisplayName().getFormattedText());
        final FontRenderer fontrenderer = this.mc.fontRendererObj;
        final float f = 1.6f;
        final float f2 = 0.016666668f * f;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.0f, (float)y + entity.height + 0.5f, (float)z);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GlStateManager.rotate(this.renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GlStateManager.scale(-f2, -f2, f2);
        if (entity.isSneaking()) {
            GlStateManager.translate(0.0f, 9.374999f, 0.0f);
        }
        GlStateManager.disableLighting();
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        final int i = fontrenderer.getStringWidth(displayTag) / 2;
        final Tessellator tessellator = Tessellator.getInstance();
        final WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        worldrenderer.pos((double)(-i - 1), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        worldrenderer.pos((double)(-i - 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        worldrenderer.pos((double)(i + 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        worldrenderer.pos((double)(i + 1), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        if (entity.isSneaking()) {
            GlStateManager.depthMask(true);
            fontrenderer.drawString(displayTag, -fontrenderer.getStringWidth(displayTag) / 2, 0, 553648127);
        }
        else {
            fontrenderer.drawString(displayTag, -fontrenderer.getStringWidth(displayTag) / 2, 0, 553648127);
            GlStateManager.depthMask(true);
            fontrenderer.drawString(displayTag, -fontrenderer.getStringWidth(displayTag) / 2, 0, -1);
        }
        GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        GlStateManager.popMatrix();
    }

}
