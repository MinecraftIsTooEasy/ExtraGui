package moddedmite.xylose.extragui.gui;

import moddedmite.xylose.extragui.config.ExtraGuiConfig;
import moddedmite.xylose.extragui.util.DisplayUtil;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Collection;
import java.util.Iterator;

public class GuiEffectRenderer extends Gui {
    private boolean field_74222_o;
    private int initial_tick;
    private static final ResourceLocation sugar_icon = new ResourceLocation("textures/items/sugar.png");
    protected static final ResourceLocation inventory_texture = new ResourceLocation("textures/gui/container/inventory.png");
    protected static final ResourceLocation MITE_icons = new ResourceLocation("textures/gui/MITE_icons.png");
    private Minecraft mc = Minecraft.getMinecraft();
    FontRenderer fontRenderer = mc.fontRenderer;

    public GuiEffectRenderer() {
    }

    public void initGui() {
        if (!this.mc.thePlayer.getActivePotionEffects().isEmpty() || this.mc.thePlayer.isMalnourished() || this.mc.thePlayer.isInsulinResistant() || this.mc.thePlayer.is_cursed) {
            this.field_74222_o = true;
        }
        this.initial_tick = (int) this.mc.theWorld.getTotalWorldTime();
    }

    public void drawScreen(int par1, int par2, float par3) {
        if (this.field_74222_o) {
            this.displayDebuffEffects();
        }
    }

    public void displayDebuffEffects() {
        Point pos = new Point(ExtraGuiConfig.EffectX.getIntegerValue(), ExtraGuiConfig.EffectY.getIntegerValue());
        Dimension size = DisplayUtil.displaySize();
        int x = ((int) (size.width / ExtraGuiConfig.EffectSize.getDoubleValue()) - 1) * pos.x / 100;
        int y = ((int) (size.height / ExtraGuiConfig.EffectSize.getDoubleValue()) - 1) * pos.y / 100;
        GL11.glPushMatrix();

        GL11.glScalef((float) ExtraGuiConfig.EffectSize.getDoubleValue(), (float) ExtraGuiConfig.EffectSize.getDoubleValue(), 1.0f);


        Collection var4 = this.mc.thePlayer.getActivePotionEffects();
        int var5 = var4.size();

        if (ExtraGuiConfig.DisplayEffect.getBooleanValue() && !this.mc.gameSettings.showDebugInfo) {
            if (this.mc.thePlayer.isMalnourished()) {
                ++var5;
            }

            if (this.mc.thePlayer.isInsulinResistant()) {
                ++var5;
            }

            if (this.mc.thePlayer.is_cursed) {
                ++var5;
            }

            if (var5 > 0) {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_LIGHTING);
                int var6 = 33;

                if (var5 > 5) {
                    var6 = 132 / (var5 - 1);
                }

                String var7;
                String var8;
                TextureManager var10000;

                if (this.mc.thePlayer.isMalnourished()) {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.mc.getTextureManager().bindTexture(inventory_texture);
                    if (ExtraGuiConfig.DisplayEffectBackGround.getBooleanValue())
                        this.drawTexturedModalRect(x, y, 0, 166, 140, 32);
                    var10000 = this.mc.getTextureManager();
                    var10000.bindTexture(MITE_icons);
                    this.drawTexturedModalRect(x + 6, y + 7, 18, 198, 18, 18);
                    var7 = I18n.getString("effect.malnourished");
                    this.fontRenderer.drawStringWithShadow(var7, x + 10 + 18 - 1, y + 6 + 1, ExtraGuiConfig.EffectColor.getColorInteger());
                    var8 = ((int) this.mc.theWorld.getTotalWorldTime() - this.initial_tick) / 100 % 2 == 0 ? I18n.getString("effect.malnourished.slowHealing") : I18n.getString("effect.malnourished.plus50PercentHunger");
                    this.fontRenderer.drawStringWithShadow(var8, x + 10 + 18 - 1, y + 6 + 10 + 1, 8355711);
                    y += var6;
                }

                if (this.mc.thePlayer.isInsulinResistant()) {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.mc.getTextureManager().bindTexture(inventory_texture);
                    if (ExtraGuiConfig.DisplayEffectBackGround.getBooleanValue())
                        this.drawTexturedModalRect(x, y, 0, 166, 140, 32);
                    this.mc.getTextureManager().bindTexture(sugar_icon);
                    this.drawTexturedModalRect2(x + 7, y + 8, 16, 16);
                    EnumInsulinResistanceLevel var12 = this.mc.thePlayer.getInsulinResistanceLevel();
                    GL11.glColor4f(var12.getRedAsFloat(), var12.getGreenAsFloat(), var12.getBlueAsFloat(), 1.0F);
                    var10000 = this.mc.getTextureManager();
                    var10000.bindTexture(MITE_icons);
                    this.drawTexturedModalRect(x + 6, y + 7, 54, 198, 18, 18);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    var8 = I18n.getString("effect.insulinResistance");
                    this.fontRenderer.drawStringWithShadow(var8, x + 10 + 18 - 1, y + 6 + 1, ExtraGuiConfig.EffectColor.getColorInteger());
                    String var9 = StringUtils.ticksToElapsedTime(this.mc.thePlayer.getInsulinResistance());
                    this.fontRenderer.drawStringWithShadow(var9, x + 10 + 18 - 1, y + 6 + 10 + 1, 8355711);
                    y += var6;
                }

                if (this.mc.thePlayer.is_cursed) {
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.mc.getTextureManager().bindTexture(inventory_texture);
                    if (ExtraGuiConfig.DisplayEffectBackGround.getBooleanValue())
                        this.drawTexturedModalRect(x, y, 0, 166, 140, 32);
                    var10000 = this.mc.getTextureManager();
                    var10000.bindTexture(MITE_icons);
                    this.drawTexturedModalRect(x + 6, y + 7, 0, 198, 18, 18);
                    var7 = I18n.getString("effect.cursed");
                    this.fontRenderer.drawStringWithShadow(var7, x + 10 + 18 - 1, y + 6 + 1, ExtraGuiConfig.EffectColor.getColorInteger());
                    var8 = this.mc.thePlayer.curse_effect_known ? EnumChatFormatting.DARK_PURPLE + this.mc.thePlayer.getCurse().getTitle() : Translator.get("curse.unknown");
                    this.fontRenderer.drawStringWithShadow(var8, x + 10 + 18 - 1, y + 6 + 10 + 1, 8355711);
                    y += var6;
                }

                for (Iterator var13 = this.mc.thePlayer.getActivePotionEffects().iterator(); var13.hasNext(); y += var6) {
                    PotionEffect var14 = (PotionEffect) var13.next();
                    Potion var15 = Potion.potionTypes[var14.getPotionID()];
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.mc.getTextureManager().bindTexture(inventory_texture);
                    if (ExtraGuiConfig.DisplayEffectBackGround.getBooleanValue())
                        this.drawTexturedModalRect(x, y, 0, 166, 140, 32);

                    if (var15.hasStatusIcon()) {
                        int var10 = var15.getStatusIconIndex();
                        this.drawTexturedModalRect(x + 6, y + 7, 0 + var10 % 8 * 18, 198 + var10 / 8 * 18, 18, 18);
                    }

                    String var16 = I18n.getString(var15.getName());

                    if (var14.getAmplifier() == 1) {
                        var16 = var16 + " II";
                    } else if (var14.getAmplifier() == 2) {
                        var16 = var16 + " III";
                    } else if (var14.getAmplifier() == 3) {
                        var16 = var16 + " IV";
                    }

                    this.fontRenderer.drawStringWithShadow(var16, x + 10 + 18 - 1, y + 6 + 1, ExtraGuiConfig.EffectColor.getColorInteger());
                    String var11 = Potion.getDurationString(var14);
                    this.fontRenderer.drawStringWithShadow(var11, x + 10 + 18 - 1, y + 6 + 10 + 1, 8355711);
                }
            }
        }
        GL11.glPopMatrix();
    }

}
