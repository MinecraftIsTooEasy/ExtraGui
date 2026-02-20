package moddedmite.xylose.extragui.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.BlockAnvil;
import net.minecraft.ContainerRepair;
import net.minecraft.GuiRepair;
import net.minecraft.I18n;
import net.minecraft.Minecraft;
import net.minecraft.TileEntity;
import net.minecraft.TileEntityAnvil;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiRepair.class)
public class GuiRepairMixin {
	@Shadow private TileEntityAnvil tile_entity_anvil;
	@Shadow private ContainerRepair repairContainer;
	
	@Inject(method = "drawGuiContainerBackgroundLayer", at = @At("TAIL"))
	private void drawAnvilDurability(float par1, int par2, int par3, CallbackInfo ci, @Local(name = "var4") int var4, @Local(name = "var5") int var5) {
		int maxDurability = ((BlockAnvil) this.repairContainer.block).getDurability();
		int durability = getAnvilDurability(this.tile_entity_anvil.getWorldObj().getDimensionId(), this.tile_entity_anvil.xCoord, this.tile_entity_anvil.yCoord, this.tile_entity_anvil.zCoord);
		if (durability == maxDurability) return;

		Minecraft.getMinecraft().fontRenderer.drawString(
				I18n.getStringParams(
						"extragui.gui.anvil.durability",
						maxDurability - durability, maxDurability),
				var4 + 25, var5 + 70, 4210752);
	}
	
	@Unique
	private int getAnvilDurability(int dimension, int x, int y, int z) {
		List<TileEntity> tes = MinecraftServer.getServer().worldServerForDimension(dimension).loadedTileEntityList;
		
		for (TileEntity te : tes) {
			if (!(te instanceof TileEntityAnvil tea)) continue;
			if (tea.xCoord == x && tea.yCoord == y && tea.zCoord == z) {
				return tea.damage;
			}
		}
		return 0;
	}
}
