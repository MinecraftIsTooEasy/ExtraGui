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
    private static String newline = new String(System.getProperty("line.separator").getBytes());
    private int page = 1;
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
        Minecraft mc = Minecraft.getMinecraft();
        EntityClientPlayerMP player = mc.thePlayer;
    }

    protected void actionPerformed(GuiButton par1GuiButton) {
        if (par1GuiButton.id == 1) {
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

    public void drawScreen(int par1, int par2, float par3) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityClientPlayerMP player = mc.thePlayer;
        if (this.entityLivingBase == null)
            entityLivingBase = player;
        StringBuilder sb = new StringBuilder();
        this.drawDefaultBackground();
        x = (this.width - 248) / 2 + 10;
        y = (this.height - 166) / 2 + 8;
        if (entityLivingBase != null) {
            if (page == 1) {
                if (entityLivingBase == player)
                    this.fontRenderer.drawString(player.getEntityName(), x, y, 2039583);
                else
                    this.fontRenderer.drawString(entityLivingBase.getEntityName(), x, y, 2039583);
                this.lineBreak();
                if (entityLivingBase == player) {
                    int level_modifier_harvesting = Math.round(player.getLevelModifier(EnumLevelBonus.HARVESTING) * 100.0F);
                    int level_modifier_crafting = Math.round(player.getLevelModifier(EnumLevelBonus.CRAFTING) * 100.0F);
                    int level_modifier_melee = Math.round(player.getLevelModifier(EnumLevelBonus.MELEE_DAMAGE) * 100.0F);
                    this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.level", player.getExperienceLevel()), x, y, 2039583);
                    this.lineBreak();
                    this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.level.modifier", level_modifier_harvesting, level_modifier_crafting, level_modifier_melee), x, y, 2039583);
                    this.lineBreak();
                    this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.exp", player.experience), x, y, 2039583);
                    this.lineBreak();
                    this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.health", StringHelper.formatFloat(player.getHealth(), 1, 1), player.getMaxHealth()), x, y, 2039583);
                    this.lineBreak();
                    this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.satiation", player.getSatiation(), player.getSatiationLimit()), x, y, 2039583);
                    this.lineBreak();
                    this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.nutrition", player.getNutrition(), player.getNutritionLimit()), x, y, 2039583);
                    this.lineBreak();
                    int protein = ClientPlayerAPI.getProtein(player);
                    int phytonutrients = ClientPlayerAPI.getProtein(player);
                    int essential_fats = ClientPlayerAPI.getEssentialFats(player);
                    int proteinPer = 100 * ClientPlayerAPI.getProtein(player) / 160000;
                    int phytonutrientsPer = 100 * ClientPlayerAPI.getProtein(player) / 160000;
                    int essential_fatsPer = 100 * ClientPlayerAPI.getEssentialFats(player) / 160000;
                    int insulin_response = player.getInsulinResistance();
                    this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.protein", protein, proteinPer), x, y, 2039583);
                    this.lineBreak();
                    this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.essential_fats", essential_fats, essential_fatsPer), x, y, 2039583);
                    this.lineBreak();
                    this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.phytonutrients", phytonutrients, phytonutrientsPer), x, y, 2039583);
                    this.lineBreak();
                    this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.insulin_response", insulin_response), x, y, 2039583);
                    this.lineBreak();
                    this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.insulin_response.detail", 100 * insulin_response / 48000, 100 * insulin_response / 96000, 100 * insulin_response / 144000, 100 * insulin_response / 192000), x, y, 2039583);
                    this.lineBreak();
                }
                if (entityLivingBase == player)
                    this.fontRenderer.drawString((getMeleeDamageString(player)), x, y, 2039583);
                else
                    this.fontRenderer.drawString((getMeleeDamageString(entityLivingBase)), x, y, 2039583);
            } else if (page == 2) {
                this.fontRenderer.drawString(I18n.getString("extragui.stats.protect"), x, y, 2039583);
                this.lineBreak();
                this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.protect.generic", customFloatFormat(entityLivingBase.getTotalProtection(DamageSource.causeMobDamage(null)))), x, y, 2039583);
                this.lineBreak();
                this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.protect.falls", customFloatFormat(entityLivingBase.getTotalProtection(DamageSource.fall))), x, y, 2039583);
                this.lineBreak();
                this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.protect.fire", customFloatFormat(entityLivingBase.getTotalProtection(DamageSource.onFire))), x, y, 2039583);
                this.lineBreak();
                EntityArrow entity_arrow = new EntityArrow(entityLivingBase.worldObj);
                entity_arrow.item_arrow = Item.arrowFlint;
                this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.protect.projectiles", customFloatFormat(entityLivingBase.getTotalProtection(DamageSource.causeArrowDamage(entity_arrow, null)))), x, y, 2039583);
                this.lineBreak();
                this.lineBreak();
                appendSectionResistance(entityLivingBase, sb);
            } else if (page == 3) {
                appendSectionPotionEffects(entityLivingBase, sb);
                appendSectionEquipment(entityLivingBase, sb);
                appendSectionInventory(entityLivingBase, sb);
            }
            super.drawScreen(par1, par2, par3);

        }
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
                    sb.append(" (" + base_melee_damage + " + " + held_item_stack.getDisplayName() + ")" + level_modifier_melee_string);
                } else if (level_modifier_melee != 0) {
                    sb.append(" (" + base_melee_damage + level_modifier_melee_string + ")");
                }
            } else if (damage_from_held_item > 0.0F) {
                sb.append(" (" + customFloatFormat(total_melee_damage - damage_from_held_item) + " + " + held_item_stack.getDisplayName() + ")");
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
                sb.append(" (" + item_stack.stackSize + ")");
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
                    Enchantment enchantment = Enchantment.get(((Integer) e.getKey()).intValue());
                    int level = ((Integer) e.getValue()).intValue();
                    StringHelper.addToStringArray(enchantment.getTranslatedName(level, item_stack), notes);
                }
            }

            if (notes[0] != null) {
                sb.append(" {");
                sb.append(StringHelper.implode(notes, ", ", true, false));
                sb.append("}");
            }

            if (item_stack.isItemDamaged()) {
                sb.append(" [" + item_stack.getRemainingDurability() + "/" + item_stack.getMaxDamage() + "]");
            }

            return sb.toString();
        }
    }

    private void appendSectionResistance(EntityLivingBase entity_living_base, StringBuilder sb) {
        this.fontRenderer.drawString(I18n.getString("extragui.stats.resistance"), x, y, 2039583);
        this.lineBreak();
        this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.resistance.poison", Math.round(entity_living_base.getResistanceToPoison() * 100.0F)), x, y, 2039583);
        this.lineBreak();
        this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.resistance.paralysis", Math.round(entity_living_base.getResistanceToParalysis() * 100.0F)), x, y, 2039583);
        this.lineBreak();
        this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.resistance.drain",  Math.round(entity_living_base.getResistanceToDrain() * 100.0F)), x, y, 2039583);
        this.lineBreak();
        this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.resistance.shadow", Math.round(entity_living_base.getResistanceToShadow() * 100.0F)), x, y, 2039583);
        this.lineBreak();
        this.lineBreak();
    }

    private void appendSectionPotionEffects(EntityLivingBase entity_living_base, StringBuilder sb) {
        this.fontRenderer.drawString(I18n.getString("extragui.stats.potion"), x, y, 2039583);
        this.lineBreak();
        if (!entity_living_base.hasActivePotionEffects()) {
            this.fontRenderer.drawString(I18n.getString("extragui.stats.none"), x, y, 2039583);
            this.lineBreak();
        } else {
            Collection potion_effects = entity_living_base.getActivePotionEffects();
            Iterator i = potion_effects.iterator();

            while (i.hasNext()) {
                PotionEffect potion_effect = (PotionEffect) i.next();
                Potion potion = Potion.get(potion_effect.getPotionID());
                int level = potion_effect.getAmplifier();
                this.fontRenderer.drawString((" " + I18n.getString(potion.getName())) + " " + StringHelper.getRomanNumeral(level + 1) + " [" + Potion.getDurationString(potion_effect) + "]", x, y, 2039583);
                this.lineBreak();
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
                    this.fontRenderer.drawString((" (" + StringHelper.implode(effect_descriptions, ", ", true, false) + ")"), x, y, 2039583);
                    this.lineBreak();
                }

            }

        }
        this.lineBreak();
    }

    private void appendSectionEquipment(EntityLivingBase entity_living_base, StringBuilder sb) {
        this.fontRenderer.drawString(I18n.getString("extragui.stats.equipment"), x, y, 2039583);
        this.lineBreak();

        if (entity_living_base.getHeldItemStack() != null) {
            this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.equipment.held", getItemStackDescriptorForStatsFile(entity_living_base.getHeldItemStack())), x, y, 2039583);
            this.lineBreak();
        }

        ItemStack[] worn_items = entity_living_base.getWornItems();

        if (worn_items != null) {
            for (int i = 0; i < worn_items.length; ++i) {
                this.fontRenderer.drawString(I18n.getStringParams("extragui.stats.equipment.worn", i, getItemStackDescriptorForStatsFile(worn_items[i])), x, y, 2039583);
                this.lineBreak();
            }
        }
        this.lineBreak();
    }

    private static void appendSectionInventory(EntityLivingBase entity_living_base, StringBuilder sb) {
        sb.append(I18n.getString("extragui.stats.invertory") + newline);
        int i;

        if (entity_living_base.isEntityPlayer()) {
            EntityPlayer entity_living = entity_living_base.getAsPlayer();
            boolean contained_items = true;

            for (i = 0; i < entity_living.inventory.mainInventory.length; ++i) {
                ItemStack guardian = entity_living.inventory.mainInventory[i];

                if (guardian != null) {
                    contained_items = false;
                    sb.append(" " + (i < 9 ? "Hotbar" : "Extended") + "[" + i + "]: " + getItemStackDescriptorForStatsFile(guardian) + newline);
                }
            }

            if (contained_items) {
                sb.append(" (none)" + newline);
            }
        } else if (entity_living_base instanceof EntityLiving) {
            EntityLiving var6 = entity_living_base.getAsEntityLiving();
            ItemStack[] var7 = var6.getContainedItems();

            if (var7 == null) {
                boolean var8 = false;

                if (entity_living_base instanceof EntityLongdeadGuardian) {
                    EntityLongdeadGuardian var9 = (EntityLongdeadGuardian) entity_living_base;

                    if (var9.getStowedItemStack() != null) {
                        var8 = true;
                        sb.append(" Stowed[0]: " + getItemStackDescriptorForStatsFile(var9.getStowedItemStack()) + newline);
                    }
                }

                if (!var8) {
                    sb.append(" (none)" + newline);
                }
            } else {
                for (i = 0; i < var7.length; ++i) {
                    sb.append(" Contained[" + i + "]: " + getItemStackDescriptorForStatsFile(var7[i]) + newline);
                }
            }
        } else {
            sb.append(" (Don\'t know how to handle)" + newline);
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
