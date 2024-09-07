package moddedmite.xylose.extragui.gui;

import moddedmite.xylose.extragui.config.ExtraGuiConfig;
import moddedmite.xylose.extragui.util.DisplayUtil;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;
import java.util.Random;

public class GuiMiniInfoHandle {
    private static final GuiMiniInfoHandle INSTANCE = new GuiMiniInfoHandle();
    private int x;
    private int y;
    private int z;
    private int cx;
    private int cz;

    public static GuiMiniInfoHandle getInstance() {
        return INSTANCE;
    }

    public String getLightInfo(Minecraft mc) {
        return mc.thePlayer.getWorld().getBlockLightValue(this.x, this.y, this.z) + "/" + (15 - mc.thePlayer.getWorld().skylightSubtracted);
    }

    public void drawStrings(Gui gui, FontRenderer fontRenderer, List<String> strings) {
        int size = strings.size();
        Point pos = new Point(ExtraGuiConfig.InfoXLevel.getIntegerValue(), ExtraGuiConfig.InfoYLevel.getIntegerValue());
        Dimension displaySize = DisplayUtil.displaySize();
        int x = ((int) (displaySize.width / ExtraGuiConfig.InfoSize.getDoubleValue()) - 1) * pos.x / 100;
        int y = ((int) (displaySize.height / ExtraGuiConfig.InfoSize.getDoubleValue()) - 1) * pos.y / 100;
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        for (int i = 0; i < size; ++i) {
            GL11.glPushMatrix();
            GL11.glScalef((float) ExtraGuiConfig.InfoSize.getDoubleValue(), (float) ExtraGuiConfig.InfoSize.getDoubleValue(), 1.0f);
            if (ExtraGuiConfig.RightAlign.getBooleanValue())
                gui.drawString(fontRenderer, strings.get(i), sr.getScaledWidth() - fontRenderer.getStringWidth(strings.get(i)) - 2 - x, 2 + 10 * (y / 10 + i), ExtraGuiConfig.infoColor.getColorInteger());
            else
                gui.drawString(fontRenderer, strings.get(i), 2 + x, 2 + 10 * (y / 10 + i), ExtraGuiConfig.infoColor.getColorInteger());
            GL11.glPopMatrix();
        }
    }

    public String getTime(World world) {
        Object minute;
        Object hour = String.valueOf(world.getHourOfDay());
        if (((String)hour).length() == 1) {
            hour = "0" + (String)hour;
        }
        if (((String)(minute = String.valueOf(world.getTimeOfDay() % 1000 * 3 / 50))).length() == 1) {
            minute = "0" + (String)minute;
        }
        return I18n.getStringParams((String)"extraGui.formatter.time", (Object[])new Object[]{world.getDayOfWorld(), hour, minute});
    }

    public String getPosition(Minecraft mc) {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public String getChunkPosition(Minecraft mc) {
        return "(" + this.cx + ", " + this.cz + ")";
    }

    public void updatePosition(Minecraft mc) {
        this.x = MathHelper.floor_double((double)mc.thePlayer.posX);
        this.y = MathHelper.floor_double((double)(mc.thePlayer.posY - (double)mc.thePlayer.yOffset));
        this.z = MathHelper.floor_double((double)mc.thePlayer.posZ);
        this.cx = MathHelper.floor_double(mc.thePlayer.posX / 16);
        this.cz = MathHelper.floor_double(mc.thePlayer.posZ / 16);
    }

    public String getDirectionInfo(Minecraft mc) {
        return this.directionTranslator(mc.thePlayer.getDirectionFromYaw().toString()) + "(" + String.format("%.2f", Float.valueOf(GuiMiniInfoHandle.getNormalizedYaw(mc.thePlayer.rotationYaw))) + "/" + String.format("%.2f", Float.valueOf(GuiMiniInfoHandle.getNormalizedYaw(mc.thePlayer.rotationPitch))) + ")";
    }

    public String directionTranslator(String direction) {
        return switch (direction) {
            case "EAST" -> I18n.getString((String)"extraGui.formatter.direction.east");
            case "WEST" -> I18n.getString((String)"extraGui.formatter.direction.west");
            case "NORTH" -> I18n.getString((String)"extraGui.formatter.direction.north");
            case "SOUTH" -> I18n.getString((String)"extraGui.formatter.direction.south");
            default -> I18n.getString((String)"extraGui.formatter.direction.unknown");
        };
    }

    public static float getNormalizedYaw(float yaw) {
        while (yaw < 0.0f) {
            yaw += 360.0f;
        }
        while (yaw >= 360.0f) {
            yaw -= 360.0f;
        }
        return yaw;
    }

    public String weatherInfo(WorldClient world, String biome) {
        String weatherInfo;
        boolean snowZone = biome.equals("Taiga") || biome.equals("TaigaHills");
        String rainSnow = snowZone ? I18n.getString((String)"extraGui.formatter.weather.snow") : I18n.getString((String)"extraGui.formatter.weather.rain");
        WeatherEvent event = world.getCurrentWeatherEvent();
        Random rand = new Random(world.getDayOfWorld());
        if (event != null) {
            boolean modular;
            String START = I18n.getStringParams((String)"extraGui.formatter.weather.duration", (Object[])new Object[]{event.start / 24000L + 1L, event.start % 24000L / 1000L + 6L});
            String END = I18n.getStringParams((String)"extraGui.formatter.weather.duration", (Object[])new Object[]{event.end / 24000L + 1L, event.end % 24000L / 1000L + 6L});
            boolean bl = modular = world.getDayOfWorld() % 32 == 0;
            if (modular) {
                weatherInfo = I18n.getStringParams((String)"extraGui.formatter.weather.storm", (Object[])new Object[]{START, END});
            } else {
                int i = rand.nextInt(3);
                String level = switch (i) {
                    case 0 -> I18n.getString((String)"extraGui.formatter.weather.light");
                    case 1 -> I18n.getString((String)"extraGui.formatter.weather.moderate");
                    default -> I18n.getString((String)"extraGui.formatter.weather.heavy");
                };
                weatherInfo = level + rainSnow + " " + START + " - " + END;
            }
        } else {
            event = world.getNextWeatherEvent(false);
            if (event != null) {
                weatherInfo = event.start - (long)world.getAdjustedTimeOfDay() < 6000L ? I18n.getString((String)"extraGui.formatter.weather.willRain") : I18n.getString((String)"extraGui.formatter.weather.overcast");
            } else {
                int i = rand.nextInt(3);
                weatherInfo = switch (i) {
                    case 0 -> I18n.getString((String)"extraGui.formatter.weather.clear");
                    case 1 -> I18n.getString((String)"extraGui.formatter.weather.cloudy");
                    default -> I18n.getString((String)"extraGui.formatter.weather.clearToOvercast");
                };
            }
        }
        return weatherInfo;
    }
}
