package moddedmite.xylose.extragui.gui;

import moddedmite.xylose.extragui.config.ExtraGuiConfig;
import moddedmite.xylose.extragui.util.DisplayUtil;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiInventoryRender {
    public void renderStack(Slot slot, Minecraft mc, RenderItem renderItem) {
        ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();
        Point pos = new Point(ExtraGuiConfig.ItemRenderX.getIntegerValue(), ExtraGuiConfig.ItemRenderY.getIntegerValue());
        Dimension size = DisplayUtil.displaySize();
        int x = ((int) (size.width / ExtraGuiConfig.ItemRenderSize.getDoubleValue()) - 1) * pos.x / 100;
        int y = ((int) (size.height / ExtraGuiConfig.ItemRenderSize.getDoubleValue()) - 1) * pos.y / 100;
        GL11.glPushMatrix();
        if (slot != null && ExtraGuiConfig.DisplayItemRender.getBooleanValue()) {
            GL11.glScalef((float) ExtraGuiConfig.ItemRenderSize.getDoubleValue(), (float) ExtraGuiConfig.ItemRenderSize.getDoubleValue(), 1.0F);
//          RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            RenderHelper.enableStandardItemLighting();
            renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, slot.getStack(), x, y);

        }
//        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }
}
