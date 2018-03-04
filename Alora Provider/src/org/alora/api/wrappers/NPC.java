package org.alora.api.wrappers;

import org.alora.api.definitions.NPCDefinition;
import org.alora.api.interfaces.Identifiable;
import org.alora.api.interfaces.Nameable;
import org.alora.loader.Loader;

/**
 * Created by Ethan on 2/27/2018.
 */
public class NPC extends Actor implements Identifiable, Nameable {

    private NPCDefinition npcDefinition;
    private int index = -1;

    public NPC(int index, Object raw) {
        super(raw);
        this.index = index;
        if (raw != null) {
            this.npcDefinition = new NPCDefinition(Loader.getReflectionEngine().getFieldValue("DG", "pI", raw));
        }
    }

    public String getName() {
        return npcDefinition.getName();
    }

    public int getId() {
        return npcDefinition.getId();
    }

    public long getHash() {
        return (long) index;
    }


    public int getCombatLevel() {
        return npcDefinition.getCombatLevel();
    }

    public String[] getActions() {
        return npcDefinition.getActions();
    }

}