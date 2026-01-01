package moddedmite.xylose.extragui.gui;

import moddedmite.rustedironcore.api.player.ClientPlayerAPI;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GuiEntityStats extends GuiScreen {
    private static final ResourceLocation demoGuiTextures = new ResourceLocation("textures/gui/demo_background.png");
    private static final String newline = new String(System.lineSeparator().getBytes());
    private int page = 0;
    private int x;
    private int y;
    private EntityLivingBase entityLivingBase;

    public GuiEntityStats() {
    }

    public GuiEntityStats(EntityLivingBase entityLivingBase) {
        this.entityLivingBase = entityLivingBase;
    }

    public void initGui() {
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, this.width / 2 + 2, this.height / 2 + 90, 114, 20, I18n.getString("gui.nextPage")));
    }

    protected void actionPerformed(GuiButton btn) {
        if (btn.id == 1) {
            page += 1;
            if (page >= 4)
                page = 1;
        }
    }

    public void drawDefaultBackground() {
        super.drawDefaultBackground();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(demoGuiTextures);
        int var1 = (this.width - 248) / 2;
        int var2 = (this.height - 166) / 2;
        this.drawTexturedModalRect(var1, var2, 0, 0, 248, 166);
    }

    private void lineBreak() {
        y += 12;
    }

    private void drawString(String str, int x, int y) {
        this.fontRenderer.drawString(str, x, y, 0x1F1F1F);
        lineBreak();
    }

    public void drawScreen(int par1, int par2, float par3) {
        EntityClientPlayerMP player = this.mc.thePlayer;
        if (this.entityLivingBase == null) entityLivingBase = player;

        this.drawDefaultBackground();
        x = (this.width - 248) / 2 + 10;
        y = (this.height - 166) / 2 + 8;

        if (entityLivingBase != null) {
            drawEntityStatsPage();
            super.drawScreen(par1, par2, par3);
        }
    }

    private void drawEntityStatsPage() {
        switch (page) {
            case 0:
                drawBasicInfoPage();
                break;
            case 1:
                drawProtectionInfoPage();
                break;
            case 2:
                drawDetailedInfoPage();
                break;
            case 3:
                drawInventory();
                break;
        }
    }

    private void drawBasicInfoPage() {
        drawEntityName();
        if (isPlayerEntity()) drawPlayerSpecificInfo();
        drawMeleeDamageInfo();
    }

    private void drawProtectionInfoPage() {
        this.drawString(I18n.getString("extragui.stats.protect"), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.protect.generic",
                customFloatFormat(entityLivingBase.getTotalProtection(DamageSource.causeMobDamage(null)))), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.protect.falls",
                customFloatFormat(entityLivingBase.getTotalProtection(DamageSource.fall))), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.protect.fire",
                customFloatFormat(entityLivingBase.getTotalProtection(DamageSource.onFire))), x, y);

        EntityArrow entity_arrow = new EntityArrow(entityLivingBase.worldObj);
        entity_arrow.item_arrow = Item.arrowFlint;
        this.drawString(I18n.getStringParams("extragui.stats.protect.projectiles",
                customFloatFormat(entityLivingBase.getTotalProtection(DamageSource.causeArrowDamage(entity_arrow, null)))), x, y);
        this.lineBreak();

        appendSectionResistance(entityLivingBase);
    }

    private void drawDetailedInfoPage() {
        appendSectionPotionEffects(entityLivingBase);
        appendSectionEquipment(entityLivingBase);
    }

    private void drawEntityName() {
        if (isPlayerEntity()) {
            EntityClientPlayerMP player = (EntityClientPlayerMP) entityLivingBase;
            this.drawString(player.getEntityName(), x, y);
        } else {
            this.drawString(entityLivingBase.getEntityName(), x, y);
        }
    }

    private void drawPlayerSpecificInfo() {
        EntityClientPlayerMP player = (EntityClientPlayerMP) entityLivingBase;

        int level_modifier_harvesting = Math.round(player.getLevelModifier(EnumLevelBonus.HARVESTING) * 100.0F);
        int level_modifier_crafting = Math.round(player.getLevelModifier(EnumLevelBonus.CRAFTING) * 100.0F);
        int level_modifier_melee = Math.round(player.getLevelModifier(EnumLevelBonus.MELEE_DAMAGE) * 100.0F);

        this.drawString(I18n.getStringParams("extragui.stats.level", player.getExperienceLevel()), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.level.modifier", level_modifier_harvesting, level_modifier_crafting, level_modifier_melee), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.exp", player.experience), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.health",
                StringHelper.formatFloat(player.getHealth(), 1, 1), player.getMaxHealth()), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.satiation", player.getSatiation(), player.getSatiationLimit()), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.nutrition", player.getNutrition(), player.getNutritionLimit()), x, y);

        int protein = ClientPlayerAPI.getProtein(player);
        int phytonutrients = ClientPlayerAPI.getProtein(player);
        int essential_fats = ClientPlayerAPI.getEssentialFats(player);
        int proteinPer = 100 * ClientPlayerAPI.getProtein(player) / 160000;
        int phytonutrientsPer = 100 * ClientPlayerAPI.getProtein(player) / 160000;
        int essential_fatsPer = 100 * ClientPlayerAPI.getEssentialFats(player) / 160000;
        int insulin_response = player.getInsulinResistance();

        this.drawString(I18n.getStringParams("extragui.stats.protein", protein, proteinPer), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.essential_fats", essential_fats, essential_fatsPer), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.phytonutrients", phytonutrients, phytonutrientsPer), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.insulin_response", insulin_response), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.insulin_response.detail",
                100 * insulin_response / 48000, 100 * insulin_response / 96000,
                100 * insulin_response / 144000, 100 * insulin_response / 192000), x, y);
    }

    private void drawMeleeDamageInfo() {
        if (isPlayerEntity()) {
            EntityClientPlayerMP player = (EntityClientPlayerMP) entityLivingBase;
            this.fontRenderer.drawString((getMeleeDamageString(player)), x, y, 2039583);
        } else {
            this.fontRenderer.drawString((getMeleeDamageString(entityLivingBase)), x, y, 2039583);
        }
    }

    private boolean isPlayerEntity() {
        return entityLivingBase instanceof EntityClientPlayerMP || entityLivingBase.isEntityPlayer();
    }

    private static String getMeleeDamageString(EntityLivingBase entity_living_base) {
        float total_melee_damage;

        if (entity_living_base.isEntityPlayer()) {
            total_melee_damage = entity_living_base.getAsPlayer().calcRawMeleeDamageVs(null, false, false);
        } else if (entity_living_base.hasEntityAttribute(SharedMonsterAttributes.attackDamage)) {
            total_melee_damage = (float) entity_living_base.getEntityAttributeValue(SharedMonsterAttributes.attackDamage);
        } else {
            total_melee_damage = 0.0F;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(I18n.getStringParams("extragui.stats.melee", customFloatFormat(total_melee_damage)));

        if (total_melee_damage > 0.0F) {
            ItemStack held_item_stack = entity_living_base.getHeldItemStack();
            float damage_from_held_item = held_item_stack == null ? 0.0F : held_item_stack.getMeleeDamageBonus();

            if (entity_living_base.isEntityPlayer()) {
                EntityPlayer player = entity_living_base.getAsPlayer();
                int level_modifier_melee = Math.round(player.getLevelModifier(EnumLevelBonus.MELEE_DAMAGE) * 100.0F);
                float base_melee_damage = (float) entity_living_base.getEntityAttributeBaseValue(SharedMonsterAttributes.attackDamage);
                String level_modifier_melee_string = level_modifier_melee == 0 ? "" : " + " + level_modifier_melee + "%";

                if (damage_from_held_item != 0.0F) {
                    sb.append(" (").append(base_melee_damage).append(" + ").append(held_item_stack.getDisplayName()).append(")").append(level_modifier_melee_string);
                } else if (level_modifier_melee != 0) {
                    sb.append(" (").append(base_melee_damage).append(level_modifier_melee_string).append(")");
                }
            } else if (damage_from_held_item > 0.0F) {
                sb.append(" (").append(customFloatFormat(total_melee_damage - damage_from_held_item)).append(" + ").append(held_item_stack.getDisplayName()).append(")");
            }
        }

        return sb.toString();
    }

    private static String getItemStackDescriptorForStatsFile(ItemStack item_stack) {
        if (item_stack == null) {
            return I18n.getString("extragui.stats.none");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(item_stack.getNameForReferenceFile());

            if (item_stack.stackSize != 1) {
                sb.append(" (").append(item_stack.stackSize).append(")");
            }

            String[] notes = new String[16];
            EnumQuality quality = item_stack.getQuality();

            if (quality != null && !quality.isAverage()) {
                StringHelper.addToStringArray(quality.getDescriptor(), notes);
            }

            if (item_stack.isItemEnchanted()) {
                Map enchantments = EnchantmentHelper.getEnchantmentsMap(item_stack);
                Set set = enchantments.entrySet();

                for (Object o : set) {
                    Map.Entry e = (Map.Entry) o;
                    Enchantment enchantment = Enchantment.get((Integer) e.getKey());
                    int level = (Integer) e.getValue();
                    StringHelper.addToStringArray(enchantment.getTranslatedName(level, item_stack), notes);
                }
            }

            if (notes[0] != null) {
                sb.append(" {");
                sb.append(StringHelper.implode(notes, ", ", true, false));
                sb.append("}");
            }

            if (item_stack.isItemDamaged()) {
                sb.append(" [").append(item_stack.getRemainingDurability()).append("/").append(item_stack.getMaxDamage()).append("]");
            }

            return sb.toString();
        }
    }

    private void appendSectionResistance(EntityLivingBase entity_living_base) {
        this.drawString(I18n.getString("extragui.stats.resistance"), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.resistance.poison", Math.round(entity_living_base.getResistanceToPoison() * 100.0F)), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.resistance.paralysis", Math.round(entity_living_base.getResistanceToParalysis() * 100.0F)), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.resistance.drain",  Math.round(entity_living_base.getResistanceToDrain() * 100.0F)), x, y);
        this.drawString(I18n.getStringParams("extragui.stats.resistance.shadow", Math.round(entity_living_base.getResistanceToShadow() * 100.0F)), x, y);
        this.lineBreak();
    }

    private void appendSectionPotionEffects(EntityLivingBase entity_living_base) {
        this.drawString(I18n.getString("extragui.stats.potion"), x, y);
        if (!entity_living_base.hasActivePotionEffects()) {
            this.drawString(I18n.getString("extragui.stats.none"), x, y);
        } else {
            Collection potion_effects = entity_living_base.getActivePotionEffects();

            for (Object potionEffect : potion_effects) {
                PotionEffect potion_effect = (PotionEffect) potionEffect;
                Potion potion = Potion.get(potion_effect.getPotionID());
                int level = potion_effect.getAmplifier();
                this.drawString((" " + I18n.getString(potion.getName())) + " " + StringHelper.getRomanNumeral(level + 1) + " [" + Potion.getDurationString(potion_effect) + "]", x, y);
                Map attribute_modifiers = potion.func_111186_k();
                Iterator iterator = attribute_modifiers.entrySet().iterator();
                String[] effect_descriptions = new String[16];

                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    Attribute attribute = (Attribute) entry.getKey();
                    AttributeModifier modifier = (AttributeModifier) entry.getValue();
                    modifier = new AttributeModifier(modifier.getID(), modifier.getName(), potion.func_111183_a(level, modifier), modifier.getOperation());
                    String effect_details = ItemPotion.getEffectDetails(attribute.getAttributeUnlocalizedName(), modifier);

                    if (effect_details != null) {
                        StringHelper.addToStringArray(StringUtils.stripControlCodes(effect_details), effect_descriptions);
                    }
                }

                if (effect_descriptions[0] != null) {
                    this.drawString((" (" + StringHelper.implode(effect_descriptions, ", ", true, false) + ")"), x, y);
                }

            }

        }
        this.lineBreak();
    }

    private void appendSectionEquipment(EntityLivingBase entity_living_base) {
        this.drawString(I18n.getString("extragui.stats.equipment"), x, y);

        if (entity_living_base.getHeldItemStack() != null) {
            this.drawString(I18n.getStringParams("extragui.stats.equipment.held", getItemStackDescriptorForStatsFile(entity_living_base.getHeldItemStack())), x, y);
        }

        ItemStack[] worn_items = entity_living_base.getWornItems();

        if (worn_items != null) {
            for (int i = 0; i < worn_items.length; ++i) {
                this.drawString(I18n.getStringParams("extragui.stats.equipment.worn", i, getItemStackDescriptorForStatsFile(worn_items[i])), x, y);
            }
        }
        this.lineBreak();
    }

    private void drawInventory() {
        this.drawString(I18n.getString("extragui.stats.inventory"), x, y);
        int i;

        if (entityLivingBase.isEntityPlayer()) {
            EntityPlayer entity_living = entityLivingBase.getAsPlayer();
            boolean has_items = false;

            for (i = 0; i < entity_living.inventory.mainInventory.length; ++i) {
                ItemStack item = entity_living.inventory.mainInventory[i];

                if (item != null) {
                    has_items = true;
                    this.drawString(" " + (i < 9 ? "Hotbar" : "Extended") + "[" + i + "]: " + getItemStackDescriptorForStatsFile(item), x, y);
                }
            }

            if (!has_items) {
                this.drawString(" " + I18n.getString("extragui.stats.none"), x, y);
            }
        } else if (entityLivingBase instanceof EntityLiving) {
            EntityLiving entity_living = entityLivingBase.getAsEntityLiving();
            ItemStack[] contained_items = entity_living.getContainedItems();

            if (contained_items == null) {
                boolean has_stowed_item = false;

                if (entityLivingBase instanceof EntityLongdeadGuardian longdeadGuardian) {
                    if (longdeadGuardian.getStowedItemStack() != null) {
                        has_stowed_item = true;
                        this.drawString(" Stowed[0]: " + getItemStackDescriptorForStatsFile(longdeadGuardian.getStowedItemStack()), x, y);
                    }
                }

                if (!has_stowed_item) {
                    this.drawString(" " + I18n.getString("extragui.stats.none"), x, y);
                }
            } else {
                for (i = 0; i < contained_items.length; ++i) {
                    this.drawString(" Contained[" + i + "]: " + getItemStackDescriptorForStatsFile(contained_items[i]), x, y);
                }
            }
        } else {
            this.drawString(" " + I18n.getString("extragui.stats.unknown"), x, y);
        }
    }

    private static String customFloatFormat(float f) {
        return f < 1.0F && (double) f != 0.0D ? StringHelper.formatFloat(f, 2, 2) : StringHelper.formatFloat(f, 1, 1);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
