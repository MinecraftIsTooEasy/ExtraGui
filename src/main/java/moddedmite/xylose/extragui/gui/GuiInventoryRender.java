package moddedmite.xylose.extragui.gui;

import moddedmite.xylose.extragui.config.ExtraGuiConfig;
import moddedmite.xylose.extragui.util.DisplayUtil;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.awt.*;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL12.GL_RESCALE_NORMAL;

public class GuiInventoryRender {
    public void renderStack(Slot slot, Minecraft mc, RenderItem renderItem) {
        if (slot == null || !ExtraGuiConfig.DisplayItemRender.getBooleanValue()) {
            return;
        }

        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
        GL11.glPushMatrix();

        try {
            Point pos = new Point(ExtraGuiConfig.ItemRenderX.getIntegerValue(), ExtraGuiConfig.ItemRenderY.getIntegerValue());
            Dimension size = DisplayUtil.displaySize();
            int x = ((int) (size.width / ExtraGuiConfig.ItemRenderSize.getDoubleValue()) - 1) * pos.x / 100;
            int y = ((int) (size.height / ExtraGuiConfig.ItemRenderSize.getDoubleValue()) - 1) * pos.y / 100;
            GL11.glScalef((float) ExtraGuiConfig.ItemRenderSize.getDoubleValue(), (float) ExtraGuiConfig.ItemRenderSize.getDoubleValue(), 1.0F);
            RenderHelper.disableStandardItemLighting();
            RenderHelper.enableGUIStandardItemLighting();
            renderItem.renderItemAndEffectIntoGUI(mc.fontRenderer, mc.renderEngine, slot.getStack(), x, y);
            renderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.renderEngine, slot.getStack(), x, y);
            RenderHelper.disableStandardItemLighting();
        } finally {
            GL11.glPopMatrix();
            GL11.glPopAttrib();
        }
    }
}
