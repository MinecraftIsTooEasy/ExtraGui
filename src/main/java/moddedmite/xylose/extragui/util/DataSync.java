package moddedmite.xylose.extragui.util;

import net.minecraft.TileEntity;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;

public class DataSync {
    @Nullable
    public static TileEntity getServerSide(TileEntity tileEntity) {
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null) return null;
        return server.worldServerForDimension(tileEntity.getWorldObj().getDimensionId())
                .getBlockTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
    }
}
