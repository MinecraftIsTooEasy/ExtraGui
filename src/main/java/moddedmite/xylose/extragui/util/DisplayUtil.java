package moddedmite.xylose.extragui.util;

import net.minecraft.Minecraft;
import net.minecraft.ScaledResolution;

import java.awt.*;

public class DisplayUtil {
    public static Dimension displaySize() {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution res = new ScaledResolution(mc.gameSettings, mc.displayWidth, mc.displayHeight);
        return new Dimension(res.getScaledWidth(), res.getScaledHeight());
    }
}
