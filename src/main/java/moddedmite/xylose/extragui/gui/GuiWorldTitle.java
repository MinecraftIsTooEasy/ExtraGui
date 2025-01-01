//package moddedmite.xylose.extragui.gui;
//
//import moddedmite.xylose.extragui.config.ExtraGuiConfig;
//import moddedmite.xylose.extragui.util.DisplayUtil;
//import net.minecraft.*;
//import org.lwjgl.opengl.GL11;
//
//import java.awt.*;
//
//public class GuiWorldTitle {
//    public BiomeGenBase previousBiome;
//    public int alpha = 250;
//    public int displayTime = 3000;
//
//    public void renderWorldTitle(Minecraft mc) {
//        ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
//        Point pos = new Point(ExtraGuiConfig.WorldTitleX.getIntegerValue(), ExtraGuiConfig.WorldTitleY.getIntegerValue());
//        Dimension size = DisplayUtil.displaySize();
//        int x = ((int) (size.width / ExtraGuiConfig.WorldTitleSize.getDoubleValue()) - 1) * pos.x / 100;
//        int y = ((int) (size.height / ExtraGuiConfig.WorldTitleSize.getDoubleValue()) - 1) * pos.y / 100;
//        World world = mc.theWorld;
//        FontRenderer fontRenderer = mc.fontRenderer;
//        EntityClientPlayerMP playerMP = mc.thePlayer;
//        int playerX = (int) playerMP.posX;
//        int playerY = (int) playerMP.posY;
//        int playerZ = (int) playerMP.posZ;
//        if (!Minecraft.getMinecraft().gameSettings.showDebugInfo) {
//            if (world != null) {
//                if (world.blockExists(playerX, playerY, playerZ) && playerY >= 0 && playerZ < 256) {
//                    Chunk chunk = world.getChunkFromBlockCoords(playerX, playerZ);
//
//                    if (!chunk.isEmpty()) {
//                        BiomeGenBase biome = world.getBiomeGenForCoords(playerX, playerZ);
//
//                        if (previousBiome != biome) {
//                            previousBiome = biome;
////                            displayTime = 3000;
////                            alpha = 250;
//                            displayTime--;
//                            System.out.println(displayTime);
//                        }
//
//                        if (displayTime > 0) {
//                            GL11.glPushMatrix();
//                            GL11.glScalef((float) ExtraGuiConfig.WorldTitleSize.getDoubleValue(), (float) ExtraGuiConfig.WorldTitleSize.getDoubleValue(), 1.0F);
//                            fontRenderer.drawString(biome.biomeName, x, y, ExtraGuiConfig.WorldTitleColorInt | (alpha << 24), true);
//                            GL11.glPopMatrix();
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    public void onUpdate() {
////        if (!ExtraGuiConfig.WorldTitleFadeOut.getBooleanValue() && alpha != 260) {
////            alpha = 250;
////        } else if
////            while (ExtraGuiConfig.WorldTitleFadeOut.getBooleanValue()) {
////        while (displayTime > 0) {
////                displayTime--;
////                System.out.println(displayTime);
////            }
////        while (alpha > 0) {
////                alpha -= 10;
////                System.out.println(alpha);
////            }
////        }
//    }
//}
