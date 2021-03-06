package net.dohaw.play.divisions.archetypes.spells;

import net.dohaw.play.divisions.PlayerData;
import net.dohaw.play.divisions.archetypes.*;
import net.dohaw.play.divisions.archetypes.spells.active.*;
import net.dohaw.play.divisions.archetypes.spells.active.archer.Escape;
import net.dohaw.play.divisions.archetypes.spells.active.archer.Sporadic;
import net.dohaw.play.divisions.archetypes.spells.active.tree.Cocoon;
import net.dohaw.play.divisions.archetypes.spells.active.tree.Root;
import net.dohaw.play.divisions.archetypes.spells.bowspell.BowSpell;
import net.dohaw.play.divisions.archetypes.spells.bowspell.CripplingShot;
import net.dohaw.play.divisions.archetypes.spells.active.duelist.Leap;
import net.dohaw.play.divisions.archetypes.spells.active.duelist.Rage;
import net.dohaw.play.divisions.archetypes.spells.active.duelist.Stun;
import net.dohaw.play.divisions.archetypes.spells.passive.duelist.DuelWielder;
import net.dohaw.play.divisions.archetypes.spells.passive.duelist.Finisher;
import net.dohaw.play.divisions.archetypes.spells.passive.PassiveSpell;
import net.dohaw.play.divisions.archetypes.spells.passive.archer.HeatingUp;
import net.dohaw.play.divisions.archetypes.spells.passive.tree.PhotosynthesisP1;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class Spell extends WrapperHolder {

    public static final ActiveSpell INVISIBLE_STRIKE = new InvisibleStrike("invisible_strike_spell", Archetype.EVOKER, SpellKey.INVISIBLE_STRIKE, 1);
    public static final ActiveSpell SMITE = new Smite("smite_spell", Archetype.CLERIC, SpellKey.SMITE, 1);
    public static final ActiveSpell FROST_STRIKE = new FrostStrike("frost_strike_spell", Archetype.WIZARD, SpellKey.FROST_STRIKE, 1);

    /*
        Archer Spells
     */
    public static final PassiveSpell HEATING_UP = new HeatingUp(Archetype.ARCHER, SpellKey.HEATING_UP, 1);
    public static final ActiveSpell ESCAPE = new Escape("escape_spell", Archetype.ARCHER, SpellKey.ESCAPE, 2);
    public static final BowSpell CRIPPLING_SHOT = new CripplingShot(Archetype.ARCHER, SpellKey.CRIPPLING_SHOT, 4);
    public static final ActiveSpell SPORADIC = new Sporadic("sporadic_spell", Archetype.ARCHER, SpellKey.SPORADIC, 7);

    /*
        Duelist Spells
     */
    public static final ActiveSpell STUN = new Stun("stun_spell", Archetype.DUELIST, SpellKey.STUN, 2);
    public static final ActiveSpell RAGE = new Rage("rage_spell", Archetype.DUELIST, SpellKey.RAGE, 3);
    public static final PassiveSpell FINISHER = new Finisher(Archetype.DUELIST, SpellKey.FINISHER,3);
    public static final ActiveSpell LEAP = new Leap("leap_spell", Archetype.DUELIST, SpellKey.LEAP, 6);
    public static final PassiveSpell DUEL_WIELDER = new DuelWielder(Archetype.DUELIST, SpellKey.DUEL_WIELDER, 6);

    /*
        Tree Spells
     */
    public static final PassiveSpell PHOTOSYNTHESIS_P1 = new PhotosynthesisP1(Archetype.TREE, SpellKey.PHOTOSYNTHESIS_P, 1);
    public static final ActiveSpell ROOT = new Root("root_spell", Archetype.TREE, SpellKey.ROOT, 2);
    public static final ActiveSpell COCOON = new Cocoon("cocoon_spell", Archetype.TREE, SpellKey.COCOON, 2);

    public static ActiveSpell getSpellByItemKey(String customItemKey) {
        for (Map.Entry<Enum, Wrapper> entry : wrappers.entrySet()) {
            Wrapper wrapper = entry.getValue();
            if (wrapper instanceof ActiveSpell) {
                ActiveSpell spell = (ActiveSpell) wrapper;
                if (spell.getCustomItemBindedToKey().equalsIgnoreCase(customItemKey)) {
                    return spell;
                }
            }
        }
        return null;
    }

    public static BowSpell getBowSpellByKey(String bowSpellKeyStr){

        SpellKey bowSpellKey = SpellKey.valueOf(bowSpellKeyStr);
        for(Map.Entry<Enum, Wrapper> entry : wrappers.entrySet()){
            Wrapper wrapper = entry.getValue();
            if(wrapper instanceof BowSpell){
                BowSpell bowSpell = (BowSpell) wrapper;
                if(bowSpell.getKEY() == bowSpellKey){
                    return bowSpell;
                }
            }
        }

        return null;

    }

    public static List<SpellWrapper> getArchetypeSpells(ArchetypeWrapper archetypeWrapper) {
        List<SpellWrapper> spells = new ArrayList<>();
        for (Map.Entry<Enum, Wrapper> entry : wrappers.entrySet()) {
            Wrapper wrapper = entry.getValue();
            if(wrapper instanceof SpellWrapper){
                SpellWrapper spell = (SpellWrapper) wrapper;
                if (spell.getArchetype().getKEY() == archetypeWrapper.getKEY()) {
                    spells.add(spell);
                }
            }
        }
        return spells;
    }

    public static List<SpellWrapper> getSpells(){
        List<SpellWrapper> spells = new ArrayList<>();
        for (Map.Entry<Enum, Wrapper> entry : wrappers.entrySet()) {
            Wrapper wrapper = entry.getValue();
            if(wrapper instanceof SpellWrapper){
                spells.add((SpellWrapper) wrapper);
            }
        }
        return spells;
    }

    public static List<SpellWrapper> getArchetypeSpellsUnlocked(ArchetypeWrapper archetype, int level) {
        List<SpellWrapper> archetypeSpells = getArchetypeSpells(archetype);
        List<SpellWrapper> spellsUnlocked = new ArrayList<>();
        for (SpellWrapper wrapper : archetypeSpells) {
            if (wrapper.getLevelUnlocked() == level) {
                spellsUnlocked.add(wrapper);
            }
        }
        return spellsUnlocked;
    }

    public static List<BowSpell> getUnlockedBowSpells(ArchetypeWrapper archetype, int level){

        List<SpellWrapper> archetypeSpells = getArchetypeSpells(archetype);
        List<BowSpell> bowSpellsUnlocked = new ArrayList<>();

        for(SpellWrapper wrapper : archetypeSpells){
            if (wrapper instanceof BowSpell && wrapper.getLevelUnlocked() == level) {
                bowSpellsUnlocked.add((BowSpell) wrapper);
            }
        }

        return bowSpellsUnlocked;

    }

    public static boolean isSpellEntity(Entity entity) {
        return entity.hasMetadata("spell_key");
    }

    public static Entity getEntityInSights(Player player, double range) {
        
        List<Entity> nearbyE = player.getNearbyEntities(range,
                range, range);
        ArrayList<LivingEntity> livingE = new ArrayList<>();

        for (Entity e : nearbyE) {
            if (e instanceof LivingEntity) {
                livingE.add((LivingEntity) e);
            }
        }

        Entity target = null;
        BlockIterator bItr = new BlockIterator(player.getLocation(), range);
        Block block;
        Location loc;
        int bx, by, bz;
        double ex, ey, ez;
        // loop through player's line of sight
        while (bItr.hasNext()) {

            block = bItr.next();
            if(block.getType() != Material.AIR){
                bx = block.getX();
                by = block.getY();
                bz = block.getZ();
                // check for entities near this block in the line of sight
                for (LivingEntity e : livingE) {
                    loc = e.getLocation();
                    ex = loc.getX();
                    ey = loc.getY();
                    ez = loc.getZ();
                    if ((bx - .75 <= ex && ex <= bx + 1.75) && (bz - .75 <= ez && ez <= bz + 1.75) && (by - 1 <= ey && ey <= by + 2.5)) {
                        // entity is close enough, set target and stop
                        target = e;
                        break;
                    }
                }
            }

        }

        return target;

    }

    public static long getSchedulerInterval(Cooldownable cooldownable){
        return (long) (cooldownable.getBaseCooldown() * 20);
    }

    public static long getSchedulerInterval(PlayerData pd, CooldownDecreasable cooldownable){
        return (long) (cooldownable.getAdjustedCooldown(pd) * 20);
    }

}
