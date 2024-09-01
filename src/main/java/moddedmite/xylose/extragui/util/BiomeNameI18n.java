package moddedmite.xylose.extragui.util;

import net.minecraft.BiomeGenBase;
import net.minecraft.I18n;

import static net.minecraft.BiomeGenBase.*;

public class BiomeNameI18n {

    public static String getBiomeNameI18n(BiomeGenBase biome) {
        if (biome == ocean)
            return I18n.getString("biome.ocean.name");
        if (biome == plains)
            return I18n.getString("biome.plains.name");
        if (biome == desert)
            return I18n.getString("biome.desert.name");
        if (biome == extremeHills)
            return I18n.getString("biome.extremeHills.name");
        if (biome == forest)
            return I18n.getString("biome.forest.name");
        if (biome == taiga)
            return I18n.getString("biome.taiga.name");
        if (biome == swampland)
            return I18n.getString("biome.swampland.name");
        if (biome == river)
            return I18n.getString("biome.river.name");
        if (biome == hell)
            return I18n.getString("biome.hell.name");
        if (biome == underworld)
            return I18n.getString("biome.underworld.name");
        if (biome == sky)
            return I18n.getString("biome.sky.name");
        if (biome == frozenOcean)
            return I18n.getString("biome.frozenOcean.name");
        if (biome == frozenRiver)
            return I18n.getString("biome.frozenRiver.name");
        if (biome == icePlains)
            return I18n.getString("biome.icePlains.name");
        if (biome == iceMountains)
            return I18n.getString("biome.iceMountains.name");
        if (biome == beach)
            return I18n.getString("biome.beach.name");
        if (biome == desertHills)
            return I18n.getString("biome.desertHills.name");
        if (biome == forestHills)
            return I18n.getString("biome.forestHills.name");
        if (biome == taigaHills)
            return I18n.getString("biome.taigaHills.name");
        if (biome == extremeHillsEdge)
            return I18n.getString("biome.extremeHillsEdge.name");
        if (biome == jungle)
            return I18n.getString("biome.jungle.name");
        if (biome == jungleHills)
            return I18n.getString("biome.jungleHills.name");
        if (biome == desertRiver)
            return I18n.getString("biome.desertRiver.name");
        if (biome == jungleRiver)
            return I18n.getString("biome.jungleRiver.name");
        if (biome == swampRiver)
            return I18n.getString("biome.swampRiver.name");
        return biome.biomeName;
    }
}
