package moddedmite.xylose.extragui.gui;

import moddedmite.rustedironcore.api.interfaces.IPotion;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class GuiEffectRenderer extends Gui {
    private static final int FRAME_SIZE = 24;
    private static final int ICON_SIZE = 18;
    private static final int POTION_FRAME_X = 0;
    private static final int BEACON_FRAME_X = POTION_FRAME_X + FRAME_SIZE;
    private static final int FRAME_Y = 0;
    private static final int ICON_COLUMNS = 8;
    private static final int ICON_Y = 198;
    private static final int FLASH_TICKS = 200;

    private static final ResourceLocation potionFrame = new ResourceLocation("extragui","textures/gui/potion_frame.png");
    private static final ResourceLocation inventory = new ResourceLocation("textures/gui/container/inventory.png");
    private static final ResourceLocation MITE_icons = new ResourceLocation("textures/gui/MITE_icons.png");
    private static final Comparator<PotionEffect> COMPARATOR = (e1, e2) -> {
        if (e1.getIsAmbient()) {
            return -1;
        } else if (e2.getIsAmbient()) {
            return 1;
        }
        return Integer.compare(e1.getDuration(), e2.getDuration());
    };
    
    public void renderEffectHud(Minecraft mc) {
        Collection<PotionEffect> potionEffects = mc.thePlayer.getActivePotionEffects();
//        if (potionEffects.isEmpty()) { return; }
//        if (!(mc.thePlayer.isMalnourished())) { return; }
//        if (!(mc.thePlayer.is_cursed)) { return; }
//        if (!(mc.thePlayer.isInsulinResistant())) { return; }

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int scaledWidth = (new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight)).getScaledWidth();
        int offset = (FRAME_SIZE - ICON_SIZE) / 2;

        int iconXMITE = scaledWidth;
        int iconYMITE = 1;
        iconXMITE -= (FRAME_SIZE + 1);
        iconYMITE += FRAME_SIZE * 2 + 4;

        if (mc.thePlayer.isMalnourished()) {
            mc.renderEngine.bindTexture(potionFrame);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            drawTexturedModalRect(iconXMITE, iconYMITE, POTION_FRAME_X, FRAME_Y, FRAME_SIZE, FRAME_SIZE);
            mc.getTextureManager().bindTexture(MITE_icons);
            int ICON_Y_MITE = 18;
            drawTexturedModalRect(iconXMITE + offset, iconYMITE + offset, ICON_Y_MITE, 198, 18, 18);
            iconXMITE -= (FRAME_SIZE + 1);
        }
        if (mc.thePlayer.is_cursed) {
            mc.renderEngine.bindTexture(potionFrame);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            drawTexturedModalRect(iconXMITE, iconYMITE, POTION_FRAME_X, FRAME_Y, FRAME_SIZE, FRAME_SIZE);
            mc.getTextureManager().bindTexture(MITE_icons);
            int ICON_Y_MITE = 0;
            drawTexturedModalRect(iconXMITE + offset, iconYMITE + offset, ICON_Y_MITE, 198, 18, 18);
            iconXMITE -= (FRAME_SIZE + 1);
        }
        if (mc.thePlayer.isInsulinResistant()) {
            mc.renderEngine.bindTexture(potionFrame);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            drawTexturedModalRect(iconXMITE, iconYMITE, POTION_FRAME_X, FRAME_Y, FRAME_SIZE, FRAME_SIZE);
            mc.getTextureManager().bindTexture(MITE_icons);
            int ICON_Y_MITE = 54;
            drawTexturedModalRect(iconXMITE + offset, iconYMITE + offset, ICON_Y_MITE, 198, 18, 18);
            iconXMITE -= (FRAME_SIZE + 1);
        }

        List<PotionEffect> potionList = potionEffects.stream()
                .sorted(COMPARATOR)
                .collect(Collectors.toList());

        for (PotionEffect effect : potionList) {
            Potion potion = Potion.potionTypes[effect.getPotionID()];
            if (!potion.hasStatusIcon()) { return; }
            int goodCount = 0;
            int badCount = 0;

            // Determine coordinates to place icons.
            int iconX = scaledWidth;
            int iconY = 1;

            if (potion.isBadEffect()) {
                badCount++;
                iconX -= (FRAME_SIZE + 1) * badCount;
                iconY += FRAME_SIZE + 2;
            } else {
                goodCount++;
                iconX -= (FRAME_SIZE + 1) * goodCount;
            }

            // Draw background frame.
            mc.renderEngine.bindTexture(potionFrame);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            int frameX = effect.getIsAmbient() ? BEACON_FRAME_X : POTION_FRAME_X;
            drawTexturedModalRect(iconX, iconY, frameX, FRAME_Y, FRAME_SIZE, FRAME_SIZE);

            // Draw potion icon.
            if (potion.hasStatusIcon()) {
                mc.renderEngine.bindTexture(inventory);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, getAlpha(effect));
                int iconIndex = potion.getStatusIconIndex();
                drawTexturedModalRect(iconX + offset, iconY + offset,
                        (iconIndex % ICON_COLUMNS) * ICON_SIZE,
                        (iconIndex / ICON_COLUMNS) * ICON_SIZE + ICON_Y,
                        ICON_SIZE, ICON_SIZE);
            }
            if (((IPotion) potion).ric$UsesIndividualTexture()) {
                mc.getTextureManager().bindTexture(((IPotion) potion).ric$GetTexture());
                drawTexturedModalRect2(iconX + offset, iconY + offset, 18, 18);
            }
        }
    }

    private float getAlpha(PotionEffect effect) {
        if (effect.getIsAmbient() || effect.getDuration() > FLASH_TICKS) { return 1.0f; }

        int temp = 10 - effect.getDuration() / 20;
        return MathHelper.clamp_float(effect.getDuration() / 100.0f, 0.0f, 0.5f)
                + MathHelper.cos((float) (effect.getDuration() * Math.PI / 5.0f))
                * MathHelper.clamp_float(temp / 40.0f, 0.0f, 0.25f);
    }

}
