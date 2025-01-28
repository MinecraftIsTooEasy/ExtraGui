package moddedmite.xylose.extragui;

import fi.dy.masa.malilib.config.ConfigManager;
import moddedmite.xylose.extragui.config.ExtraGuiConfig;
import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.ModResourceManager;

public class ExtraGui implements ModInitializer {
    @Override
    public void onInitialize() {
        ExtraGuiConfig.getInstance().load();
        ConfigManager.getInstance().registerConfig(ExtraGuiConfig.getInstance());
        ModResourceManager.addResourcePackDomain("extragui");
    }
}
