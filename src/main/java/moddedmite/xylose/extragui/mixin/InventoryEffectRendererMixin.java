package moddedmite.xylose.extragui.mixin;

import moddedmite.xylose.extragui.config.ExtraGuiConfig;
import net.minecraft.Container;
import net.minecraft.GuiContainer;
import net.minecraft.InventoryEffectRenderer;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryEffectRenderer.class)
public abstract class InventoryEffectRendererMixin extends GuiContainer {
	@Unique
	private final int EFFECT_WIDTH = 124;

	public InventoryEffectRendererMixin(Container par1Container) {
		super(par1Container);
	}

	@Redirect(method = "initGui()V",
			at = @At(value = "FIELD",
					target = "Lnet/minecraft/InventoryEffectRenderer;guiLeft:I",
					opcode = Opcodes.PUTFIELD
			)
	)
	private void conNotBeyond(InventoryEffectRenderer inventoryEffectRenderer, int value) {
		guiLeft = Math.max(guiLeft, EFFECT_WIDTH);
	}

	@Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
	public void allowDisEffect(int par1, int par2, float par3, CallbackInfo ci) {
		if (!ExtraGuiConfig.DisplayEffect.getBooleanValue()) {
			ci.cancel();
			super.drawScreen(par1, par2, par3);
		}
	}
}
