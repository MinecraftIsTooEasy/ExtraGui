package moddedmite.xylose.extragui;

import moddedmite.xylose.extragui.config.ExtraGuiConfig;

public class ExtraGuiModMenu implements io.github.prospector.modmenu.api.ModMenuApi {
    public io.github.prospector.modmenu.api.ConfigScreenFactory<?> getModConfigScreenFactory() {
        return screen -> {
            return ExtraGuiConfig.getInstance().getConfigScreen(screen);
        };
    }

}
