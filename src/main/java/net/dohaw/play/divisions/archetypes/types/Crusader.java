package net.dohaw.play.divisions.archetypes.types;

import net.dohaw.play.divisions.Stat;
import net.dohaw.play.divisions.archetypes.ArchetypeKey;
import net.dohaw.play.divisions.archetypes.ArchetypeWrapper;
import net.dohaw.play.divisions.archetypes.RegenType;
import net.dohaw.play.divisions.archetypes.specializations.Speciality;
import net.dohaw.play.divisions.archetypes.specializations.SpecialityKey;
import net.dohaw.play.divisions.archetypes.specializations.SpecialityWrapper;
import net.dohaw.play.divisions.archetypes.spells.SpellKey;
import net.dohaw.play.divisions.archetypes.spells.SpellWrapper;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.*;

public class Crusader extends ArchetypeWrapper {

    public Crusader(ArchetypeKey ARCHETYPE) {
        super(ARCHETYPE);
    }

    @Override
    public List<Material> getProficientItems() {
        return null;
    }

    @Override
    public RegenType getRegenType() {
        return null;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GOLD;
    }

    @Override
    public EnumMap<Stat, Double> getDefaultStats() {
        return new EnumMap<Stat, Double>(Stat.class){{
            put(Stat.STRENGTH, 1.0);
            put(Stat.SPELL_POWER, 1.0);
            put(Stat.FORTITUDE, 2.0);
            put(Stat.MITIGATION, 1.0);
            put(Stat.QUICKNESS, 1.0);
            put(Stat.STEALTHINESS, 1.0);
            put(Stat.ACCURACY, 1.0);
            put(Stat.LUCK, 1.0);
            put(Stat.PIERCING, 1.0);
            put(Stat.MAX_HEALTH, 2.0);
            put(Stat.RESTORATION, 1.0);
        }};
    }

    @Override
    public List<Object> getDefaultItems() {
        return Arrays.asList(
                Material.GOLDEN_HELMET,
                Material.GOLDEN_CHESTPLATE,
                Material.GOLDEN_LEGGINGS,
                Material.GOLDEN_BOOTS,
                "default_feeble_sword",
                Material.SHIELD
        );
    }

    @Override
    public EnumMap<SpecialityKey, SpecialityWrapper> getSpecialities() {
        return new EnumMap<SpecialityKey, SpecialityWrapper>(SpecialityKey.class){{
            put(SpecialityKey.ORDER, Speciality.ORDER);
            put(SpecialityKey.PROTECTION, Speciality.PROTECTION);
        }};
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("crusader", "crus", "cr");
    }

}
