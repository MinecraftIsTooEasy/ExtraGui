package moddedmite.xylose.extragui.mixin;

import fi.dy.masa.malilib.config.options.ConfigBase;
import moddedmite.rustedironcore.api.interfaces.IPotion;
import moddedmite.xylose.extragui.config.ConfigInfo;
import moddedmite.xylose.extragui.config.ExtraGuiConfig;
import moddedmite.xylose.extragui.gui.*;
import net.minecraft.*;
import net.xiaoyu233.fml.FishModLoader;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraft.Minecraft.inDevMode;

@Mixin(GuiIngame.class)
public class GuiIngameMixin extends Gui {
    @Shadow @Final private Minecraft mc;


    @Inject(method = {"renderGameOverlay(FZII)V"},
            at = {@At(value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z",
                    shift = At.Shift.BEFORE)})
    private void injectRenderExtraGuiIngame(float par1, boolean par2, int par3, int par4, CallbackInfo ci) {
        if (mc.gameSettings.gui_mode == 0) {
            GuiDurability guiDurability = new GuiDurability();
            GuiDebugInfo guiDebugInfo = new GuiDebugInfo();
            GuiEffectRenderer inventoryEffectRenderer = new GuiEffectRenderer();
//            GuiWorldTitle guiWorldTitle = new GuiWorldTitle();

            guiDurability.renderDurability();
            inventoryEffectRenderer.renderEffectHud(mc);
            guiDebugInfo.renderDebugInfoOverlay(mc);
//            guiWorldTitle.renderWorldTitle(mc);
        }
    }

    @Inject(
            method = {"renderGameOverlay(FZII)V"},
            at = {@At(value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z",
                    shift = At.Shift.BEFORE)})
    private void injectRenderInfoString(float par1, boolean par2, int par3, int par4, CallbackInfo ci) {
        if (!ExtraGuiConfig.ShowInfo.getBooleanValue()) {
            return;
        }
        if (mc.gameSettings.showDebugInfo) {
            return;
        }
        if (!(mc.gameSettings.gui_mode == 0)) {
            return;
        }
        GuiMiniInfoHandle.getInstance().updatePosition(this.mc);
        ArrayList<String> strings = new ArrayList<String>();
        for (ConfigBase<?> value : ExtraGuiConfig.configValues) {
            ConfigInfo configInfo;
            if (!(value instanceof ConfigInfo) || !(configInfo = (ConfigInfo)value).getBooleanValue()) continue;
            if (configInfo.isShowName())
                strings.add(value.getConfigGuiDisplayName() + ": " + configInfo.getString(this.mc));
            else
                strings.add(configInfo.getString(this.mc));
        }
        strings.add(GuiMiniInfoHandle.getInstance().getCustomString());

        GuiMiniInfoHandle.getInstance().renderDebugInfo(strings);
    }

    @Redirect(method={"renderGameOverlay"}, at=@At(value="INVOKE", target="Lnet/minecraft/Minecraft;inDevMode()Z"), require=0)
    private boolean disableDevInfoConfig() {
        return !ExtraGuiConfig.DisableDevInfo.getBooleanValue() && (!this.mc.gameSettings.showDebugInfo && inDevMode());
    }

    @Redirect(method = "renderGameOverlay", at = @At(value = "FIELD", target = "Lnet/minecraft/Debug;is_active:Z"), require = 0)
    private boolean disableIsActiveInfo() {
        return !this.mc.gameSettings.showDebugInfo && inDevMode() && Debug.is_active;
    }

    @Redirect(method = "renderGameOverlay", at = @At(value = "FIELD", target = "Lnet/minecraft/GameSettings;showDebugInfo:Z", opcode = Opcodes.GETFIELD), require = 0)
    private boolean doNotShowFPS(GameSettings instance) {
        return false;
    }

}
