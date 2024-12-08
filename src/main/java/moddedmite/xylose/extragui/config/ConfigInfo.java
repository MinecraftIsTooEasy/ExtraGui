package moddedmite.xylose.extragui.config;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import net.minecraft.Minecraft;

import java.util.function.Function;

public class ConfigInfo extends ConfigBoolean {
    private final Function<Minecraft, String> function;
    private final boolean showName;

    public ConfigInfo(String name, boolean defaultValue, boolean showName, Function<Minecraft, String> getString) {
        super(name, defaultValue);
        this.function = getString;
        this.showName = showName;
    }

    public ConfigInfo(String name, boolean defaultValue, Function<Minecraft, String> getString) {
        super(name, defaultValue);
        this.function = getString;
        this.showName = true;
    }

    public String getString(Minecraft mc) {
        return this.function.apply(mc);
    }

    public boolean isShowName() {
        return showName;
    }
}
