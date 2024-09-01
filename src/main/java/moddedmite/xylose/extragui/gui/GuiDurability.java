package moddedmite.xylose.extragui.gui;

import moddedmite.xylose.extragui.config.ExtraGuiConfig;
import moddedmite.xylose.extragui.util.DisplayUtil;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

public class GuiDurability extends Gui {
    private Minecraft mc = Minecraft.getMinecraft();
    private static RenderItem renderItem = new RenderItem();

    public void renderDurability() {
        FontRenderer fontRenderer = mc.fontRenderer;
        TextureManager renderEngine = mc.renderEngine;
        EntityClientPlayerMP player = mc.thePlayer;
        ItemStack tool = player.getHeldItemStack();
        ItemStack helmet = player.getCurrentArmor(3);
        ItemStack plate = player.getCurrentArmor(2);
        ItemStack legs = player.getCurrentArmor(1);
        ItemStack boots = player.getCurrentArmor(0);
        ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int screenWidth = scaledResolution.getScaledWidth();
        int screenHeight = scaledResolution.getScaledHeight();
        Point pos = new Point(ExtraGuiConfig.DurabilityX.getIntegerValue(), ExtraGuiConfig.DurabilityY.getIntegerValue());
        Dimension size = DisplayUtil.displaySize();
        int x = ((int) (size.width / ExtraGuiConfig.DurabilitySize.getDoubleValue()) - 1) * pos.x / 100;
        int y = ((int) (size.height / ExtraGuiConfig.DurabilitySize.getDoubleValue()) - 1) * pos.y / 100;

        GL11.glPushMatrix();

        GL11.glScalef((float) ExtraGuiConfig.DurabilitySize.getDoubleValue(), (float) ExtraGuiConfig.DurabilitySize.getDoubleValue(), 1.0f);

        if (ExtraGuiConfig.DisplayDurability.getBooleanValue()) {
            if (player.hasHeldItem()) {
                RenderHelper.enableGUIStandardItemLighting();
                renderItem.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, tool, x, y);
                enable2DRender();
                this.drawString(fontRenderer, getDurability(tool), x + 20, y + 5, ExtraGuiConfig.DurabilityColor.getColorInteger());
            }

            if (helmet != null) {
                renderItem.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, helmet, x, y + 15);
                enable2DRender();
                this.drawString(fontRenderer, getDurabilityArmor(helmet), x + 20, y + 20, ExtraGuiConfig.DurabilityColor.getColorInteger());
            }

            if (plate != null) {
                renderItem.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, plate, x, y + 30);
                enable2DRender();
                this.drawString(fontRenderer, getDurabilityArmor(plate), x + 20, y + 35, ExtraGuiConfig.DurabilityColor.getColorInteger());
            }

            if (legs != null) {
                renderItem.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, legs, x, y + 45);
                enable2DRender();
                this.drawString(fontRenderer, getDurabilityArmor(legs), x + 20, y + 50, ExtraGuiConfig.DurabilityColor.getColorInteger());
            }

            if (boots != null) {
                renderItem.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, boots, x, y + 60);
                enable2DRender();
                this.drawString(fontRenderer, getDurabilityArmor(boots), x + 20, y + 65, ExtraGuiConfig.DurabilityColor.getColorInteger());
            }
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glPopMatrix();
    }

    public static void enable2DRender() {
        GL11.glDisable(GL11.GL_LIGHTING);//玩家,箱子模型渲染错误
        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }

    public String getDurability(ItemStack itemStack) {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        if (!ExtraGuiConfig.DurabilityPercentageDisplay.getBooleanValue()) {
            if (itemStack.isTool() && itemStack.getRemainingDurability() != 0)
                return itemStack.getRemainingDurability() + "/" + itemStack.getMaxDamage();
            else
                return itemStack.stackSize + "/" + itemStack.getMaxStackSize();
        } else {
            if (itemStack.getRemainingDurability() != 0)
                return decimalFormat.format(((float) itemStack.getRemainingDurability() / (float) itemStack.getMaxDamage()) * 100) + "%";
            else
                return itemStack.stackSize + "/" + itemStack.getMaxStackSize();
        }
    }

    public String getDurabilityArmor(ItemStack itemStack) {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        if (!ExtraGuiConfig.DurabilityPercentageDisplay.getBooleanValue())
            return itemStack.getRemainingDurability() + "/" + itemStack.getMaxDamage();
        else
            return decimalFormat.format(((float) itemStack.getRemainingDurability() / (float) itemStack.getMaxDamage()) * 100) + "%";
    }

}
