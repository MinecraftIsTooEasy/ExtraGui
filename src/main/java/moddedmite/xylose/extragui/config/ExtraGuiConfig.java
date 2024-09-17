package moddedmite.xylose.extragui.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.KeyCodes;
import moddedmite.xylose.extragui.gui.GuiMiniInfoHandle;
import moddedmite.xylose.extragui.gui.GuiEntityStats;
import moddedmite.xylose.extragui.util.BiomeNameI18n;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;
import org.lwjgl.input.Keyboard;


import java.util.ArrayList;
import java.util.List;

public class ExtraGuiConfig extends SimpleConfigs {
    public static final ConfigBoolean DisplayDurability = new ConfigBoolean("extraGui.DisplayDurability", true);
    public static final ConfigBoolean DurabilityPercentageDisplay = new ConfigBoolean("extraGui.DurabilityPercentageDisplay", true);
    public static final ConfigBoolean DurabilityLine = new ConfigBoolean("extraGui.DurabilityLine", false);
    public static final ConfigInteger DurabilityX = new ConfigInteger("extraGui.DurabilityX", 90, 0, 95);
    public static final ConfigInteger DurabilityY = new ConfigInteger("extraGui.DurabilityY", 100, 0, 100);
    public static final ConfigDouble DurabilitySize = new ConfigDouble("extraGui.DurabilitySize", 1.0F, 0.1F, 2.0F);
    public static final ConfigColor DurabilityColor = new ConfigColor("extraGui.DurabilityColor", "#FFFFFFFF");

    public static final ConfigBoolean DisplayEffect = new ConfigBoolean("extraGui.DisplayEffect", true);
    public static final ConfigBoolean DisplayEffectBackGround = new ConfigBoolean("extraGui.DisplayEffectBackGround", true);
    public static final ConfigBoolean MiniEffect = new ConfigBoolean("extraGui.MiniEffect", true);
    public static final ConfigInteger EffectX = new ConfigInteger("extraGui.EffectX", 94, 0, 94);
    public static final ConfigInteger EffectY = new ConfigInteger("extraGui.EffectY", 0, 0, 88);
    public static final ConfigDouble EffectSize = new ConfigDouble("extraGui.EffectSize", 1.0F, 0.1F, 2.0F);
    public static final ConfigColor EffectColor = new ConfigColor("extraGui.EffectColor", "#FFFFFFFF");

    public static final ConfigBoolean DisableDevInfo = new ConfigBoolean("extraGui.cancelDevInfo", true);
    public static final ConfigBoolean ShowInfo = new ConfigBoolean("extraGui.showInfo", true);
    public static final ConfigBoolean RightAlign = new ConfigBoolean("extraGui.RightAlign", false);
    public static final ConfigInteger InfoXLevel = new ConfigInteger("extraGui.xLevel", 0, 0, 85);
    public static final ConfigInteger InfoYLevel = new ConfigInteger("extraGui.yLevel", 0, 0, 95);
    public static final ConfigDouble InfoSize = new ConfigDouble("extraGui.InfoSize", 1.0F, 0.1F, 2.0F);
    public static final ConfigColor infoColor = new ConfigColor("extraGui.infoColor", "#FFFFFFFF");

    public static final ConfigInfo FPS = new ConfigInfo("extraGui.fps", false, mc -> Minecraft.last_fps + "(" + Minecraft.last_fp10s + ")");
    public static final ConfigInfo Position = new ConfigInfo("extraGui.position", true, mc -> GuiMiniInfoHandle.getInstance().getPosition((Minecraft)mc));
    public static final ConfigInfo ChunkPosition = new ConfigInfo("extraGui.chunkPosition", false, mc -> GuiMiniInfoHandle.getInstance().getChunkPosition((Minecraft)mc));
    public static final ConfigInfo Direction = new ConfigInfo("extraGui.direction", true, mc -> GuiMiniInfoHandle.getInstance().getDirectionInfo((Minecraft)mc));
    public static final ConfigInfo Light = new ConfigInfo("extraGui.light", true, mc -> GuiMiniInfoHandle.getInstance().getLightInfo((Minecraft)mc));
    public static final ConfigInfo Biome = new ConfigInfo("extraGui.biome", true, mc -> BiomeNameI18n.getBiomeNameI18n(mc.thePlayer.getBiome()));
    public static final ConfigInfo Weather = new ConfigInfo("extraGui.weather", true, mc -> GuiMiniInfoHandle.getInstance().weatherInfo(mc.theWorld, Biome.getString((Minecraft)mc)));
    public static final ConfigInfo DayTime = new ConfigInfo("extraGui.time", true, mc -> GuiMiniInfoHandle.getInstance().getTime(mc.thePlayer.getWorld()));

    public static final ConfigBoolean DisplayItemRender = new ConfigBoolean("extraGui.DisplayItemRender", true);
    public static final ConfigInteger ItemRenderX = new ConfigInteger("extraGui.ItemRenderX", 5, 0, 100);
    public static final ConfigInteger ItemRenderY = new ConfigInteger("extraGui.ItemRenderY", 25, 0, 100);
    public static final ConfigDouble ItemRenderSize = new ConfigDouble("extraGui.ItemRenderSize", 8.0F, 1.0F, 8.0F);

    public static final ConfigHotkey ToggleInfo = new ConfigHotkey("extraGui.toggleShowInfo", Keyboard.KEY_H);
    public static final ConfigHotkey Stats = new ConfigHotkey("extraGui.Stats", Keyboard.KEY_P);
    public static final ConfigHotkey MobStats = new ConfigHotkey("extraGui.MobStats", KeyCodes.getStorageString(29, 25));

    private static final ExtraGuiConfig Instance;
    public static final List<ConfigBase<?>> configValues;
    public static final List<ConfigBase<?>> durability;
    public static final List<ConfigBase<?>> effect;
    public static final List<ConfigBase<?>> presentInfo;
    public static final List<ConfigBase<?>> info;
    public static final List<ConfigBase<?>> itemRender;
    public static final List<ConfigHotkey> hotkeys;

    public static final List<ConfigTab> tabs = new ArrayList<>();

    public ExtraGuiConfig() {
        super("Extra Gui", hotkeys, durability);
    }

    static {
        durability = List.of(DisplayDurability, DurabilityPercentageDisplay, DurabilityLine, DurabilityX, DurabilityY, DurabilitySize, DurabilityColor);
        effect = List.of(DisplayEffect, DisplayEffectBackGround, MiniEffect, EffectX, EffectY, EffectSize, EffectColor);
        info = List.of(ShowInfo, DisableDevInfo, RightAlign, InfoXLevel, InfoYLevel, InfoSize, infoColor);
        presentInfo = List.of(FPS, Position, ChunkPosition, Direction, Light, Biome, Weather, DayTime);
        itemRender = List.of(DisplayItemRender, ItemRenderX, ItemRenderY, ItemRenderSize);
        hotkeys = List.of(ToggleInfo, Stats, MobStats);

        configValues = new ArrayList<>();
        configValues.addAll(durability);
        configValues.addAll(effect);
        configValues.addAll(info);
        configValues.addAll(presentInfo);
        configValues.addAll(itemRender);

        tabs.add(new ConfigTab("extraGui.durability", durability));
        tabs.add(new ConfigTab("extraGui.effect", effect));
        tabs.add(new ConfigTab("extraGui.info", info));
        tabs.add(new ConfigTab("extraGui.presentInfo", presentInfo));
        tabs.add(new ConfigTab("extraGui.itemRender", itemRender));
        tabs.add(new ConfigTab("extraGui.hotkeys", hotkeys));

        Instance = new ExtraGuiConfig();

        ToggleInfo.setHotKeyPressCallBack(minecraft -> ShowInfo.toggleBooleanValue());
        Stats.setHotKeyPressCallBack(minecraft -> minecraft.displayGuiScreen(new GuiEntityStats()));
        MobStats.setHotKeyPressCallBack(minecraft -> minecraft.displayGuiScreen(new GuiEntityStats(minecraft.objectMouseOver != null ? minecraft.objectMouseOver.getEntityHit().getAsEntityLiving() : minecraft.thePlayer)));
    }

    @Override
    public List<ConfigTab> getConfigTabs() {
        return tabs;
    }

    public static ExtraGuiConfig getInstance() {
        return Instance;
    }

    @Override
    public void save() {
        JsonObject root = new JsonObject();
        ConfigUtils.writeConfigBase(root, "耐久", durability);
        ConfigUtils.writeConfigBase(root, "状态效果", effect);
        ConfigUtils.writeConfigBase(root, "信息", info);
        ConfigUtils.writeConfigBase(root, "显示信息", presentInfo);
        ConfigUtils.writeConfigBase(root, "物品渲染", itemRender);
        ConfigUtils.writeConfigBase(root, "快捷键", hotkeys);
        JsonUtils.writeJsonToFile(root, this.optionsFile);
    }

    @Override
    public void load() {
        if (!this.optionsFile.exists()) {
            this.save();
        } else {
            JsonElement jsonElement = JsonUtils.parseJsonFile(this.optionsFile);
            if (jsonElement != null && jsonElement.isJsonObject()) {
                JsonObject root = jsonElement.getAsJsonObject();
                ConfigUtils.readConfigBase(root, "耐久", durability);
                ConfigUtils.readConfigBase(root, "状态效果", effect);
                ConfigUtils.readConfigBase(root, "信息", info);
                ConfigUtils.readConfigBase(root, "显示信息", presentInfo);
                ConfigUtils.readConfigBase(root, "物品渲染", itemRender);
                ConfigUtils.readConfigBase(root, "快捷键", hotkeys);
            }
        }
    }
}
