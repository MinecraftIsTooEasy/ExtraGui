package moddedmite.xylose.extragui.mixin;

import moddedmite.xylose.extragui.gui.GuiInventoryRender;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public class GuiContainerMixin extends GuiScreen {
    @Shadow
    private Slot theSlot;
    @Shadow
    protected static RenderItem itemRenderer = new RenderItem();

    @Inject(
            method = "drawScreen",
            at = @At(
                    value = "TAIL"
            ))
    private void renderStack(int par1, int par2, float par3, CallbackInfo ci) {
        GuiInventoryRender guiInventoryRender = new GuiInventoryRender();
        guiInventoryRender.renderStack(theSlot, this.mc, itemRenderer);
    }
}
