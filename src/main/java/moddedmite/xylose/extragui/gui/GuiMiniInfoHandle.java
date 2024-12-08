package moddedmite.xylose.extragui.gui;

import com.google.common.base.Strings;
import moddedmite.xylose.extragui.config.ExtraGuiConfig;
import moddedmite.xylose.extragui.util.DisplayUtil;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class GuiMiniInfoHandle {
    private static final GuiMiniInfoHandle INSTANCE = new GuiMiniInfoHandle();
    private int x;
    private int y;
    private int z;
    private float fx;
    private float fy;
    private float fz;
    private int cx;
    private int cz;
    private float nx;
    private float nz;
    private float ox;
    private float oz;

    public static GuiMiniInfoHandle getInstance() {
        return INSTANCE;
    }

    public String getLightInfo(Minecraft mc) {
        return mc.thePlayer.getWorld().getBlockLightValue(this.x, this.y, this.z) + "/" + (15 - mc.thePlayer.getWorld().skylightSubtracted);
    }

    public void renderDebugInfo(List<String> strings) {
        Minecraft.getMinecraft().mcProfiler.startSection("mini_info");
        GL11.glPushMatrix();
        GL11.glScalef((float) ExtraGuiConfig.InfoSize.getDoubleValue(), (float) ExtraGuiConfig.InfoSize.getDoubleValue(), 1.0f);
        this.renderDebugInfoLeft(strings);
        GL11.glPopMatrix();
        Minecraft.getMinecraft().mcProfiler.endSection();
    }

    protected void renderDebugInfoLeft(List<String> strings) {
        Point pos = new Point(ExtraGuiConfig.InfoXLevel.getIntegerValue(), ExtraGuiConfig.InfoYLevel.getIntegerValue());
        Dimension displaySize = DisplayUtil.displaySize();
        int x = ((int) (displaySize.width / ExtraGuiConfig.InfoSize.getDoubleValue()) - 1) * pos.x / 100;
        int y = ((int) (displaySize.height / ExtraGuiConfig.InfoSize.getDoubleValue()) - 1) * pos.y / 100;
        Minecraft mc = Minecraft.getMinecraft();
        FontRenderer fontRenderer = mc.fontRenderer;
        ScaledResolution sr = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        for (int i = 0; i < strings.size(); ++i) {
            String s = strings.get(i);
            if (!Strings.isNullOrEmpty(s)) {
                int fontHeight = fontRenderer.FONT_HEIGHT;
                int stringWidth = fontRenderer.getStringWidth(s);
                int yBase = (1 + fontHeight * (y / 10 + i)) ;
                int backgroundX = 2 + (1 + x);
                int backgroundX_1 = 2 + (2 + x + stringWidth + 1);
                int stringX = 2 + (2 + x);
                if (ExtraGuiConfig.RightAlign.getBooleanValue()) {
                    backgroundX = (sr.getScaledWidth() - fontRenderer.getStringWidth(strings.get(i)) - 1 + x) - 2;
                    backgroundX_1 = (sr.getScaledWidth() - fontRenderer.getStringWidth(strings.get(i)) - 2 + x + stringWidth - 1) - 2;
                    stringX = (sr.getScaledWidth() - fontRenderer.getStringWidth(strings.get(i)) - 2 + x) - 2;
                }
                if (ExtraGuiConfig.background.getBooleanValue()) {
                    Gui.drawRect(backgroundX, 2 + (yBase - 1), backgroundX_1, 2 + (yBase + fontHeight - 1), -1873784752);
                }
                fontRenderer.drawString(s, stringX, 2 + yBase, 14737632);
            }
        }
    }

    public String getMem() {
        long usedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024L / 1024L;
        long totalMemory = Runtime.getRuntime().totalMemory() / 1024L / 1024L;
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024L / 1024L;
        double usedPercentage = ((double) usedMemory / (double) maxMemory) * 100;
        double totalPercentage = ((double) totalMemory / (double) maxMemory) * 100;
        return String.format("%.0f%% %d/%dMB", usedPercentage, usedMemory, maxMemory) + " | " + I18n.getString("extraGui.formatter.mem.allocated") + " " + String.format("%.0f%% %dMB", totalPercentage, totalMemory);
    }

    public String getTime(World world) {
        String minute;
        String hour = String.valueOf(world.getHourOfDay());
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (((minute = String.valueOf(world.getTimeOfDay() % 1000 * 3 / 50))).length() == 1) {
            minute = "0" + minute;
        }
        return I18n.getStringParams("extraGui.formatter.time", world.getDayOfWorld(), hour, minute);
    }

    public String getRealTime() {
        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time.setTimeZone(TimeZone.getTimeZone(ExtraGuiConfig.timeZone.getStringValue()));
        return time.format(new Date());
    }

    public String getPosition(World world) {
        String dimensionPos = "";
        if (ExtraGuiConfig.DimensionPosition.getBooleanValue())
            dimensionPos =  !Objects.equals(world.getDimensionName(), "Nether") ? " / " +  I18n.getString("extraGui.formatter.nether") + " x: " + this.nx + " y: " + this.fy + " z: " + this.nz : " / " + I18n.getString("extraGui.formatter.overworld") + " x: " + this.ox + " y: " + this.fy + " z: " + this.oz;
        return "x: " + this.fx + " y: " + this.fy + " z: " + this.fz + dimensionPos;
    }

    public String getChunkPosition() {
        return "(" + this.cx + ", " + this.cz + ")" + " / " + "r." + (cx >> 5) + "." + (cz >> 5) + ".mca";
    }

    public void updatePosition(Minecraft mc) {
        this.x = MathHelper.floor_double(mc.thePlayer.posX);
        this.y = MathHelper.floor_double((mc.thePlayer.posY - (double)mc.thePlayer.yOffset));
        this.z = MathHelper.floor_double(mc.thePlayer.posZ);
        this.fx = Float.parseFloat(String.format("%.1f", mc.thePlayer.posX));
        this.fy = Float.parseFloat(String.format("%.1f", (mc.thePlayer.posY - (double)mc.thePlayer.yOffset)));
        this.fz = Float.parseFloat(String.format("%.1f", mc.thePlayer.posZ));
        this.cx = x / 16;
        this.cz = z / 16;
        this.nx = Float.parseFloat(String.format("%.1f", fx / 8));
        this.nz = Float.parseFloat(String.format("%.1f", fz / 8));
        this.ox = Float.parseFloat(String.format("%.1f", fx * 8));
        this.oz = Float.parseFloat(String.format("%.1f", fz * 8));
    }

    public String getDirectionInfo(Minecraft mc) {
        return this.directionTranslator(mc.thePlayer.getDirectionFromYaw().toString());
    }

    public String getYawPitchSpeedInfo(Minecraft mc) {
        return I18n.getStringParams("extraGui.formatter.yaw", String.format("%.1f", Float.valueOf(GuiMiniInfoHandle.getNormalizedYaw(mc.thePlayer.rotationYaw)))) + " / " +
                I18n.getStringParams("extraGui.formatter.pitch", String.format("%.1f", Float.valueOf(GuiMiniInfoHandle.getNormalizedYaw(mc.thePlayer.rotationPitch))));
        //TODO
        // speed need apply
    }

    public String directionTranslator(String direction) {
        return switch (direction) {
            case "EAST" -> I18n.getString("extraGui.formatter.direction.east");
            case "WEST" -> I18n.getString("extraGui.formatter.direction.west");
            case "NORTH" -> I18n.getString("extraGui.formatter.direction.north");
            case "SOUTH" -> I18n.getString("extraGui.formatter.direction.south");
            default -> I18n.getString("extraGui.formatter.direction.unknown");
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

    public String weatherInfo(World world) {
        String weatherInfo;
        boolean snowZone = world.isBiomeFreezing(this.x, this.z);
        String rainSnow = snowZone ? I18n.getString("extraGui.formatter.weather.snow") : I18n.getString("extraGui.formatter.weather.rain");
        WeatherEvent event = world.getCurrentWeatherEvent();
        Random rand = new Random(world.getDayOfWorld());
        if (event != null) {
            boolean modular;
            long startDay = event.start / 24000L;
            long startHour = event.start % 24000L / 1000L;
            long endDay = event.end / 24000L;
            long endHour = event.end % 24000L / 1000L;
            if (startHour + 6 >= 24) {
                startDay += 1;
                startHour = (startHour + 6) % 24;
            } else {
                startHour += 6;
            }
            if (endHour + 6 >= 24) {
                endDay += 1;
                endHour = (endHour + 6) % 24;
            } else {
                endHour += 6;
            }
            String START = I18n.getStringParams("extraGui.formatter.weather.duration", startDay + 1L, startHour);
            String END = I18n.getStringParams("extraGui.formatter.weather.duration", endDay + 1L, endHour);
            boolean bl = modular = world.getDayOfWorld() % 32 == 0;
            if (modular) {
                weatherInfo = I18n.getStringParams("extraGui.formatter.weather.storm", START, END);
            } else {
//                int i = rand.nextInt(3);
//                String level = switch (i) {
//                    case 0 -> I18n.getString("extraGui.formatter.weather.light");
//                    case 1 -> I18n.getString("extraGui.formatter.weather.moderate");
//                    default -> I18n.getString("extraGui.formatter.weather.heavy");
//                };
                weatherInfo = rainSnow + " " + START + " - " + END;
            }
        } else {
            event = world.getNextWeatherEvent(false);
            if (event != null) {
                weatherInfo = event.start - (long) world.getAdjustedTimeOfDay() < 6000L ? I18n.getString("extraGui.formatter.weather.willRain") : I18n.getString("extraGui.formatter.weather.overcast");
            } else {
//                int i = rand.nextInt(3);
//                weatherInfo = switch (i) {
//                    case 0 -> I18n.getString("extraGui.formatter.weather.clear");
//                    case 1 -> I18n.getString("extraGui.formatter.weather.cloudy");
//                    default -> I18n.getString("extraGui.formatter.weather.clearToOvercast");
//                };
                return I18n.getString("extraGui.formatter.weather.clear");
            }
        }
        return weatherInfo;
    }

    public String getMoonPhases(World world) {
        if (world.isHarvestMoon(false))
            return I18n.getString("extraGui.formatter.moon_phases.harvest_moon");
        if (world.isBloodMoon(false))
            return I18n.getString("extraGui.formatter.moon_phases.blood_moon");
        if (world.isMoonDog(false))
            return I18n.getString("extraGui.formatter.moon_phases.moon_dog");
        if (world.isBlueMoon(false))
            return I18n.getString("extraGui.formatter.moon_phases.blue_moon");
        return switch (world.getMoonPhase()) {
            case 0 -> I18n.getString("extraGui.formatter.moon_phases.full_moon");
            case 1 -> I18n.getString("extraGui.formatter.moon_phases.waning_gibbous_moon");
            case 2 -> I18n.getString("extraGui.formatter.moon_phases.last_quarter_moon");
            case 3 -> I18n.getString("extraGui.formatter.moon_phases.waning_crescent_moon");
            case 4 -> I18n.getString("extraGui.formatter.moon_phases.new_moon");
            case 5 -> I18n.getString("extraGui.formatter.moon_phases.waxing_crescent_moon");
            case 6 -> I18n.getString("extraGui.formatter.moon_phases.first_quarter_moon");
            case 7 -> I18n.getString("extraGui.formatter.moon_phases.waxing_gibbous_moon");
            default -> I18n.getString("extraGui.formatter.moon_phases.unknown");
        };
    }

    public String getDimension(World world) {
        return world.getDimensionName() + " / ID: " + world.getDimensionId();
    }
}
