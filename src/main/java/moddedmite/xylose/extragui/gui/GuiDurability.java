package moddedmite.xylose.extragui.gui;

import moddedmite.xylose.extragui.config.ExtraGuiConfig;
import moddedmite.xylose.extragui.util.DisplayUtil;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

public class GuiDurability extends Gui {
    private final Minecraft mc = Minecraft.getMinecraft();
    private static final RenderItem renderItem = new RenderItem();
    private int x;
    private int y;

    public void renderDurability() {
        FontRenderer fontRenderer = mc.fontRenderer;
        TextureManager renderEngine = mc.getTextureManager();
        EntityClientPlayerMP player = mc.thePlayer;
        ItemStack tool = player.getHeldItemStack();
        ItemStack helmet = player.getCurrentArmor(3);
        ItemStack plate = player.getCurrentArmor(2);
        ItemStack legs = player.getCurrentArmor(1);
        ItemStack boots = player.getCurrentArmor(0);
        Point pos = new Point(ExtraGuiConfig.DurabilityX.getIntegerValue(), ExtraGuiConfig.DurabilityY.getIntegerValue());
        Dimension size = DisplayUtil.displaySize();
        x = ((int) (size.width / ExtraGuiConfig.DurabilitySize.getDoubleValue()) - 1) * pos.x / 100;
        y = ((int) (size.height / ExtraGuiConfig.DurabilitySize.getDoubleValue()) - 1) * pos.y / 100;

        GL11.glPushMatrix();

        GL11.glScalef((float) ExtraGuiConfig.DurabilitySize.getDoubleValue(), (float) ExtraGuiConfig.DurabilitySize.getDoubleValue(), 1.0f);

        if (ExtraGuiConfig.DisplayDurability.getBooleanValue()) {
            if (player.hasHeldItem()) {
                RenderHelper.enableGUIStandardItemLighting();
                renderItem.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, tool, x, y - 18);
                enableDurabilityLine(tool, y - 18);
                enable2DRender();
                this.drawString(fontRenderer, getDurability(tool), x, y - 13, ExtraGuiConfig.DurabilityColor.getColorInteger());
            }

            if (helmet != null) {
                renderItem.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, helmet, x, y - 78);
                enableDurabilityLine(helmet, y - 78);
                enable2DRender();
                this.drawString(fontRenderer, getDurabilityArmor(helmet), x, y - 73, ExtraGuiConfig.DurabilityColor.getColorInteger());
            }

            if (plate != null) {
                renderItem.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, plate, x, y - 63);
                enableDurabilityLine(plate, y - 63);
                enable2DRender();
                this.drawString(fontRenderer, getDurabilityArmor(plate), x, y - 58, ExtraGuiConfig.DurabilityColor.getColorInteger());
            }

            if (legs != null) {
                renderItem.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, legs, x, y - 48);
                enableDurabilityLine(legs, y - 48);
                enable2DRender();
                this.drawString(fontRenderer, getDurabilityArmor(legs), x, y - 43, ExtraGuiConfig.DurabilityColor.getColorInteger());
            }

            if (boots != null) {
                renderItem.renderItemAndEffectIntoGUI(fontRenderer, renderEngine, boots, x, y - 33);
                enableDurabilityLine(boots, y - 33);
                enable2DRender();
                this.drawString(fontRenderer, getDurabilityArmor(boots), x, y - 28, ExtraGuiConfig.DurabilityColor.getColorInteger());
            }
        }

        GL11.glPopMatrix();
    }

    public static void enable2DRender() {
        GL11.glDisable(GL11.GL_LIGHTING);
//        GL11.glDisable(GL11.GL_DEPTH_TEST);
    }
    public void enableDurabilityLine(ItemStack itemStack, int y) {
        if (ExtraGuiConfig.DurabilityLine.getBooleanValue())
            renderItem.renderItemOverlayIntoGUI(mc.fontRenderer, mc.getTextureManager(), itemStack, x, y);
    }

    public String getDurability(ItemStack itemStack) {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        if (!ExtraGuiConfig.DurabilityPercentageDisplay.getBooleanValue()) {
            if (itemStack.isTool() && itemStack.getRemainingDurability() > 0) {
                return itemStack.getRemainingDurability() + "/" + itemStack.getMaxDamage();
            } else {
                return itemStack.stackSize + "/" + itemStack.getMaxStackSize();
            }
        } else {
            if (itemStack.isTool() && itemStack.getRemainingDurability() > 0) {
                return decimalFormat.format(((float) itemStack.getRemainingDurability() / (float) itemStack.getMaxDamage()) * 100) + "%";
            } else {
                return itemStack.stackSize + "/" + itemStack.getMaxStackSize();
            }
        }
    }

    public String getDurabilityArmor(ItemStack itemStack) {
        DecimalFormat decimalFormat = new DecimalFormat("0");
        if (!ExtraGuiConfig.DurabilityPercentageDisplay.getBooleanValue())
            return itemStack.getRemainingDurability() + "/" + itemStack.getMaxDamage();
        else
            return decimalFormat.format(((float) itemStack.getRemainingDurability() / (float) itemStack.getMaxDamage()) * 100) + "%";
    }

    @Override
    public void drawString(FontRenderer fontRenderer, String text, int x, int y, int color) {
        int textX = ExtraGuiConfig.DurabilityTextRight.getBooleanValue() ? x + 20 : x - fontRenderer.getStringWidth(text) ;
        fontRenderer.drawString(text, textX, y, color);
    }
}
