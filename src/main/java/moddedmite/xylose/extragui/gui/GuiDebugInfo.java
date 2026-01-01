package moddedmite.xylose.extragui.gui;

import net.minecraft.*;
import org.lwjgl.opengl.GL11;


public class GuiDebugInfo  extends Gui {

    public void renderDebugInfoOverlay(Minecraft mc) {
        FontRenderer fontRenderer = mc.fontRenderer;
        ScaledResolution var5 = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        int width = var5.getScaledWidth();
        if (mc.gameSettings.showDebugInfo && mc.gameSettings.gui_mode == 0) {
            mc.mcProfiler.startSection("debug");
            GL11.glPushMatrix();
            fontRenderer.drawStringWithShadow("Minecraft 1.6.4-MITE " + (Minecraft.inDevMode() ? EnumChatFormatting.RED + "DEV " + EnumChatFormatting.WHITE : "") + "(" + mc.debug + ")", 2, 2, 16777215);
            fontRenderer.drawStringWithShadow(mc.debugInfoRenders(), 2, 12, 16777215);
            fontRenderer.drawStringWithShadow(mc.getEntityDebug(), 2, 22, 16777215);
            fontRenderer.drawStringWithShadow(mc.debugInfoEntities(), 2, 32, 16777215);
            fontRenderer.drawStringWithShadow(mc.getWorldProviderName(), 2, 42, 16777215);
            long maxMemory = Runtime.getRuntime().maxMemory();
            long totalMemory = Runtime.getRuntime().totalMemory();
            long freeMemory = Runtime.getRuntime().freeMemory();
            long usedMemory = totalMemory - freeMemory;
            String momoeyStr = "Used memory: " + usedMemory * 100L / maxMemory + "% (" + usedMemory / 1024L / 1024L + "MB) of " + maxMemory / 1024L / 1024L + "MB";
            this.drawString(fontRenderer, momoeyStr, width - fontRenderer.getStringWidth(momoeyStr) - 2, 2, 14737632);
            momoeyStr = "Allocated memory: " + totalMemory * 100L / maxMemory + "% (" + totalMemory / 1024L / 1024L + "MB)";
            this.drawString(fontRenderer, momoeyStr, width - fontRenderer.getStringWidth(momoeyStr) - 2, 12, 14737632);
            int x = MathHelper.floor_double(mc.thePlayer.posX);
            int y = MathHelper.floor_double(mc.thePlayer.posY);
            int z = MathHelper.floor_double(mc.thePlayer.posZ);
            this.drawString(fontRenderer, String.format("x: %.5f (%d) // c: %d (%d)", mc.thePlayer.posX, x, x >> 4, x & 15), 2, 64, 14737632);
            this.drawString(fontRenderer, String.format("y: %.3f (feet pos, %.3f eyes pos)", mc.thePlayer.boundingBox.minY, mc.thePlayer.posY), 2, 72, 14737632);
            this.drawString(fontRenderer, String.format("z: %.5f (%d) // c: %d (%d)", mc.thePlayer.posZ, z, z >> 4, z & 15), 2, 80, 14737632);
            int faceTo = MathHelper.floor_double((double) (mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            this.drawString(fontRenderer, "f: " + faceTo + " (" + Direction.directions[faceTo] + ") / " + MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw), 2, 88, 14737632);

            if (mc.theWorld != null && mc.theWorld.blockExists(x, y, z)) {
                Chunk chunk = mc.theWorld.getChunkFromBlockCoords(x, z);
                this.drawString(fontRenderer, "lc: " + (chunk.getTopFilledSegment() + 15) + " b: " + chunk.getBiomeGenForWorldCoords(x & 15, z & 15, mc.theWorld.getWorldChunkManager()).biomeName + " bl: " + chunk.getSavedLightValue(EnumSkyBlock.Block, x & 15, y, z & 15) + " sl: " + chunk.getSavedLightValue(EnumSkyBlock.Sky, x & 15, y, z & 15) + " rl: " + chunk.getBlockLightValue(x & 15, y, z & 15, 0), 2, 96, 14737632);
            }

            this.drawString(fontRenderer, String.format("ws: %.3f, fs: %.3f, g: %b, fl: %d", mc.thePlayer.capabilities.getWalkSpeed(), mc.thePlayer.capabilities.getFlySpeed(), mc.thePlayer.onGround, mc.theWorld.getHeightValue(x, z)), 2, 104, 14737632);
            GL11.glPopMatrix();
            mc.mcProfiler.endSection();
        }
    }
}
