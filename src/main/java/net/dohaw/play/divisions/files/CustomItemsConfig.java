package net.dohaw.play.divisions.files;

import net.dohaw.corelib.Config;
import net.dohaw.corelib.StringUtils;
import net.dohaw.play.divisions.Stat;
import net.dohaw.play.divisions.customitems.CustomItem;
import net.dohaw.play.divisions.customitems.ItemType;
import net.dohaw.play.divisions.customitems.Rarity;
import net.dohaw.play.divisions.customitems.types.Armor;
import net.dohaw.play.divisions.customitems.types.Consumable;
import net.dohaw.play.divisions.customitems.types.Gemstone;
import net.dohaw.play.divisions.customitems.types.Weapon;
import net.dohaw.play.divisions.utils.EnchantmentSerializer;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItemsConfig extends Config {

    public CustomItemsConfig(JavaPlugin plugin) {
        super(plugin, "customItems.yml");
    }

    public Map<String, CustomItem> loadCustomItems() {

        Map<String, CustomItem> cItems = new HashMap<>();

        for (ItemType itemType : ItemType.values()) {

            String itemTypeStr = itemType.toString().toLowerCase();
            ConfigurationSection customItemKeys = config.getConfigurationSection("Custom Items." + itemTypeStr);

            if (customItemKeys != null) {

                for (String key : customItemKeys.getKeys(false)) {

                    String startPath = "Custom Items." + itemTypeStr + "." + key;
                    Material material = Material.valueOf(config.getString(startPath + ".Material"));
                    String displayName = StringUtils.colorString(config.getString(startPath + ".Display Name"));

                    CustomItem cItem;
                    switch (itemType) {
                        case GEMSTONE:
                            cItem = new Gemstone(key, itemType, material, displayName);
                        case ARMOR:
                            cItem = new Armor(key, itemType, material, displayName);
                        case WEAPON:
                            cItem = new Weapon(key, itemType, material, displayName);
                        case CONSUMABLE:
                            cItem = new Consumable(key, itemType, material, displayName);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + itemType);
                    }

                    List<String> lore = StringUtils.colorLore(config.getStringList(startPath + ".Lore"));
                    cItem.setLore(lore);

                    List<String> enchLines = config.getStringList(startPath + ".Enchantments");
                    Map<Enchantment, Integer> enchants = new HashMap<>();
                    for (String line : enchLines) {
                        Object[] enchArr = EnchantmentSerializer.toEnchantment(line);
                        enchants.put((Enchantment) enchArr[0], (Integer) enchArr[1]);
                    }
                    cItem.setEnchants(enchants);

                    if(config.getString(startPath + ".Rarity") != null){
                        Rarity rarity = Rarity.valueOf(config.getString(startPath + ".Rarity"));
                        cItem.setRarity(rarity);
                    }else{
                        cItem.setRarity(Rarity.UNCOMMON);
                    }

                    boolean isSpellItem = config.getBoolean(startPath + ".Is Spell Item");
                    cItem.setSpellItem(isSpellItem);

                    Map<Stat, Double> addedStats = new HashMap<>();
                    for (Stat stat : Stat.values()) {
                        double additive = config.getDouble(startPath + ".Added Stats." + stat.toString());
                        addedStats.put(stat, additive);
                    }
                    cItem.setAddedStats(addedStats);

                    cItems.put(key, cItem);

                }

            }
        }
        return cItems;
    }

    public void saveCustomItems(Map<String, CustomItem> customItems){

        for(Map.Entry<String, CustomItem> entry : customItems.entrySet()){

            String customItemKey = entry.getKey();
            CustomItem customItem = entry.getValue();
            ItemType itemType = customItem.getItemType();
            String itemTypeStr = itemType.toString().toLowerCase();

            String startPath = "Custom Items." + itemTypeStr + "." + customItemKey;

            Material mat = customItem.getMaterial();
            String displayName = customItem.getDisplayName();
            List<String> lore = customItem.getLore();
            Rarity rarity = customItem.getRarity();
            boolean isSpellItem = customItem.isSpellItem();
            Map<Enchantment, Integer> enchants = customItem.getEnchants();
            Map<Stat, Double> addedStats = customItem.getAddedStats();

            /*
                Can't be null
             */
            config.set(startPath + ".Material", mat.toString());
            config.set(startPath + ".Display Name", displayName);
            config.set(startPath + ".Lore", lore);

            // Isn't always set
            if(rarity != null){
                config.set(startPath + ".Rarity", rarity.name());
            }

            config.set(startPath + ".Is Spell Item", isSpellItem);

            List<String> enchLines = new ArrayList<>();
            for(Map.Entry<Enchantment, Integer> enchEntry : enchants.entrySet()){
                String line = EnchantmentSerializer.toLine(enchEntry);
                enchLines.add(line);
            }
            config.set(startPath + ".Enchantments", enchLines);

            for(Map.Entry<Stat, Double> statEntry : addedStats.entrySet()){
                Stat stat = statEntry.getKey();
                double value = statEntry.getValue();
                config.set(startPath + ".Added Stats." + stat, value);
            }

            saveConfig();

        }

    }

    public void deleteItem(String key, ItemType type){
        String path = "Custom Items." + type.toString().toLowerCase() + "." + key;
        config.set(path, null);
        saveConfig();
    }

}
