package net.dohaw.play.divisions.archetypes.spells;

import net.dohaw.play.divisions.archetypes.ArchetypeKey;

public class Smite extends SpellWrapper{

    public Smite(String customItemBindedToKey, ArchetypeKey archetype, Enum KEY, int levelUnlocked) {
        super(customItemBindedToKey, archetype, KEY, levelUnlocked);
    }

    @Override
    public void execute() {

    }
}
