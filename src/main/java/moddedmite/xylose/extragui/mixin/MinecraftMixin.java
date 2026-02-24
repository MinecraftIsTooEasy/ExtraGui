package moddedmite.xylose.extragui.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.GameSettings;
import net.minecraft.Minecraft;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Shadow public GameSettings gameSettings;

    @WrapOperation(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;inDevMode()Z", ordinal = 0))
    private boolean devFastF3(Operation<Boolean> original) {
        if (Keyboard.getEventKey() == 61) {
            this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
        }
        if (Keyboard.getEventKey() == 2 && Keyboard.isKeyDown(61)) {
            this.gameSettings.showDebugProfilerChart = !this.gameSettings.showDebugProfilerChart;
        }
        return true;
    }
}
