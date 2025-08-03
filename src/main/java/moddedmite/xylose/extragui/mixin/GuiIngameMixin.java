package moddedmite.xylose.extragui.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import fi.dy.masa.malilib.config.options.ConfigBase;
import moddedmite.xylose.extragui.config.ConfigInfo;
import moddedmite.xylose.extragui.config.ExtraGuiConfig;
import moddedmite.xylose.extragui.gui.*;
import net.minecraft.*;
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
import java.util.Objects;
import java.util.Random;

import static net.minecraft.Minecraft.inDevMode;

@Mixin(GuiIngame.class)
public class GuiIngameMixin extends Gui {
    @Shadow @Final private Minecraft mc;

    @Unique GuiDurability guiDurability = new GuiDurability();
    @Unique GuiDebugInfo guiDebugInfo = new GuiDebugInfo();
    @Unique GuiEffectRenderer inventoryEffectRenderer = new GuiEffectRenderer();
    @Unique BrownianTextRenderer brownianTextRenderer = new BrownianTextRenderer();

    @Inject(method = {"renderGameOverlay(FZII)V"},
            at = {@At(value = "INVOKE",
                    target = "Lnet/minecraft/Minecraft;inDevMode()Z",
                    shift = At.Shift.BEFORE)})
    private void injectRenderExtraGuiIngame(float par1, boolean par2, int par3, int par4, CallbackInfo ci) {
        if (mc.gameSettings.gui_mode == 0) {
//            GuiWorldTitle guiWorldTitle = new GuiWorldTitle();

            guiDurability.renderDurability();
            inventoryEffectRenderer.renderEffectHud(mc);
            guiDebugInfo.renderDebugInfoOverlay(mc);
//            if (Objects.equals(mc.gameSettings.language, "zh_CN") || Objects.equals(mc.gameSettings.language, "zh_TW"))
//                brownianTextRenderer.renderText(mc);
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
        if (!Objects.equals(GuiMiniInfoHandle.getInstance().getCustomString(), "")) strings.add(GuiMiniInfoHandle.getInstance().getCustomString());
        for (ConfigBase<?> value : ExtraGuiConfig.configValues) {
            ConfigInfo configInfo;
            if (!(value instanceof ConfigInfo) || !(configInfo = (ConfigInfo)value).getBooleanValue()) continue;
            if (configInfo.isShowName())
                strings.add(value.getConfigGuiDisplayName() + ": " + configInfo.getString(this.mc));
            else
                strings.add(configInfo.getString(this.mc));
        }

        GuiMiniInfoHandle.getInstance().renderMiniInfo(strings);
    }

    @Redirect(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;inDevMode()Z"), require = 0)
    private boolean disableDevInfoConfig() {
        return !ExtraGuiConfig.DisableDevInfo.getBooleanValue() && (!this.mc.gameSettings.showDebugInfo && inDevMode());
    }

    @WrapOperation(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;getErrorMessage()Ljava/lang/String;", ordinal = 0))
    private String disableErrorInfoConfig(Operation<String> original) {
        if (ExtraGuiConfig.DisableErrorInfo.getBooleanValue())
            return null;
        return original.call();
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
