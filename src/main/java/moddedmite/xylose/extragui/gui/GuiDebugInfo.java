package moddedmite.xylose.extragui.gui;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;


public class GuiDebugInfo  extends Gui {

    public void renderDebugInfoOverlay(Minecraft mc) {
        FontRenderer var8 = mc.fontRenderer;
        ScaledResolution var5 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int var6 = var5.getScaledWidth();
        int var22;
        int var23;
        if (mc.gameSettings.showDebugInfo && mc.gameSettings.gui_mode == 0) {
            mc.mcProfiler.startSection("debug");
            GL11.glPushMatrix();
            var8.drawStringWithShadow("Minecraft 1.6.4-MITE " + (Minecraft.inDevMode() ? EnumChatFormatting.RED + "DEV " + EnumChatFormatting.WHITE : "") + "(" + mc.debug + ")", 2, 2, 16777215);
            var8.drawStringWithShadow(mc.debugInfoRenders(), 2, 12, 16777215);
            var8.drawStringWithShadow(mc.getEntityDebug(), 2, 22, 16777215);
            var8.drawStringWithShadow(mc.debugInfoEntities(), 2, 32, 16777215);
            var8.drawStringWithShadow(mc.getWorldProviderName(), 2, 42, 16777215);
            long var38 = Runtime.getRuntime().maxMemory();
            long var41 = Runtime.getRuntime().totalMemory();
            long var43 = Runtime.getRuntime().freeMemory();
            long var45 = var41 - var43;
            String var20 = "Used memory: " + var45 * 100L / var38 + "% (" + var45 / 1024L / 1024L + "MB) of " + var38 / 1024L / 1024L + "MB";
            int var21 = 14737632;
            this.drawString(var8, var20, var6 - var8.getStringWidth(var20) - 2, 2, 14737632);
            var20 = "Allocated memory: " + var41 * 100L / var38 + "% (" + var41 / 1024L / 1024L + "MB)";
            this.drawString(var8, var20, var6 - var8.getStringWidth(var20) - 2, 12, 14737632);
            var22 = MathHelper.floor_double(mc.thePlayer.posX);
            var23 = MathHelper.floor_double(mc.thePlayer.posY);
            int var24 = MathHelper.floor_double(mc.thePlayer.posZ);
            this.drawString(var8, String.format("x: %.5f (%d) // c: %d (%d)", new Object[]{Double.valueOf(mc.thePlayer.posX), Integer.valueOf(var22), Integer.valueOf(var22 >> 4), Integer.valueOf(var22 & 15)}), 2, 64, 14737632);
            this.drawString(var8, String.format("y: %.3f (feet pos, %.3f eyes pos)", new Object[]{Double.valueOf(mc.thePlayer.boundingBox.minY), Double.valueOf(mc.thePlayer.posY)}), 2, 72, 14737632);
            this.drawString(var8, String.format("z: %.5f (%d) // c: %d (%d)", new Object[]{Double.valueOf(mc.thePlayer.posZ), Integer.valueOf(var24), Integer.valueOf(var24 >> 4), Integer.valueOf(var24 & 15)}), 2, 80, 14737632);
            int var25 = MathHelper.floor_double((double) (mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            this.drawString(var8, "f: " + var25 + " (" + Direction.directions[var25] + ") / " + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw), 2, 88, 14737632);

            if (mc.theWorld != null && mc.theWorld.blockExists(var22, var23, var24)) {
                Chunk var26 = mc.theWorld.getChunkFromBlockCoords(var22, var24);
                this.drawString(var8, "lc: " + (var26.getTopFilledSegment() + 15) + " b: " + ((Chunk) var26).getBiomeGenForWorldCoords(var22 & 15, var24 & 15, mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + var26.getSavedLightValue(EnumSkyBlock.Block, var22 & 15, var23, var24 & 15) + " sl: " + var26.getSavedLightValue(EnumSkyBlock.Sky, var22 & 15, var23, var24 & 15) + " rl: " + var26.getBlockLightValue(var22 & 15, var23, var24 & 15, 0), 2, 96, 14737632);
            }

            this.drawString(var8, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", new Object[]{Float.valueOf(mc.thePlayer.capabilities.getWalkSpeed()), Float.valueOf(mc.thePlayer.capabilities.getFlySpeed()), Boolean.valueOf(mc.thePlayer.onGround), Integer.valueOf(mc.theWorld.getHeightValue(var22, var24))}), 2, 104, 14737632);
            GL11.glPopMatrix();
            mc.mcProfiler.endSection();
        }
    }
}
