package moddedmite.xylose.extragui.util;

import net.minecraft.BiomeGenBase;
import net.minecraft.I18n;
import moddedmite.rustedironcore.api.world.BiomeAPI;

public class BiomeNameI18n {

    public static String getBiomeNameI18n(BiomeGenBase biome) {
        if (((BiomeAPI) biome).getBiomeUnlocalizedName() == null) return biome.biomeName;
        return I18n.getString(((BiomeAPI) biome).getBiomeUnlocalizedName());
    }
}
