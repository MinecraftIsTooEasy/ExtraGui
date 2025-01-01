package moddedmite.xylose.extragui.mixin;

import net.minecraft.Container;
import net.minecraft.GuiContainer;
import net.minecraft.GuiFurnace;
import net.minecraft.TileEntityFurnace;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiFurnace.class)
public abstract class GuiFurnaceMixin extends GuiContainer {
    @Shadow private TileEntityFurnace furnaceInventory;

    public GuiFurnaceMixin(Container par1Container) {
        super(par1Container);
    }

    @Inject(method = "drawGuiContainerForegroundLayer", at = @At("TAIL"))
    private void drawBurnTime(int par1, int par2, CallbackInfo ci) {
        String burnTime = this.furnaceInventory.furnaceBurnTime == 0 ? "" : String.valueOf(this.furnaceInventory.furnaceBurnTime / 20);
        String allBurnTime = "";
        if (this.furnaceInventory.getFuelItemStack() != null)
            allBurnTime = String.valueOf((this.furnaceInventory.getFuelItemStack().getItem().getBurnTime(this.furnaceInventory.getFuelItemStack()) * this.furnaceInventory.getFuelItemStack().stackSize + this.furnaceInventory.furnaceBurnTime) / 20);
        this.fontRenderer.drawString(burnTime, this.xSize / 2 - this.fontRenderer.getStringWidth(burnTime) / 2, this.ySize - 106 + 2, 4210752);
        this.fontRenderer.drawString(allBurnTime, this.xSize / 2 - this.fontRenderer.getStringWidth(burnTime) / 2, this.ySize - 96 + 2, 4210752);
    }
}
