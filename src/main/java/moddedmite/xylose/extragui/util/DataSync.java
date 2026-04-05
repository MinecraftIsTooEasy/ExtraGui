package moddedmite.xylose.extragui.util;

import moddedmite.rustedironcore.api.util.LogUtil;
import net.minecraft.TileEntity;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class DataSync {
    private static final Logger LOGGER = LogUtil.getLogger();

    @Nullable
    public static TileEntity getServerSide(TileEntity tileEntity) {
        MinecraftServer server = MinecraftServer.getServer();
        if (server == null) return null;
        try {
            return server.worldServerForDimension(tileEntity.getWorldObj().getDimensionId())
                    .getBlockTileEntity(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        } catch (Exception e) {
            LOGGER.warn(e);
            return null;
        }
    }
}
