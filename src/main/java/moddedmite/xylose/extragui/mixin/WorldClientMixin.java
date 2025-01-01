//package moddedmite.xylose.extragui.mixin;
//
//import moddedmite.xylose.extragui.config.ExtraGuiConfig;
//import moddedmite.xylose.extragui.gui.GuiWorldTitle;
//import net.minecraft.WorldClient;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(WorldClient.class)
//public class WorldClientMixin {
//    @Inject(method = "tick", at = @At("TAIL"))
//    private void tick(CallbackInfo ci) {
//        GuiWorldTitle guiWorldTitle = new GuiWorldTitle();
//        guiWorldTitle.onUpdate();
//    }
//}
