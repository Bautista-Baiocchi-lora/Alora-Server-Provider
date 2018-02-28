package org.alora.api.definitions;

/**
 * Created by Ethan on 2/27/2018.
 */

import org.bot.Engine;


public class NPCDefinition {

    private Object raw;

    public NPCDefinition(Object raw) {
        this.raw = raw;

    }

    public int getId() {
        if (raw == null)
            return -1;
        return (int) Engine.getReflectionEngine().getFieldValue("FG", "j", raw);
    }

    public int getCombatLevel() {
        if (raw == null)
            return -1;
        return (int) Engine.getReflectionEngine().getFieldHookValue("NpcCombatLevel", raw);
    }

    public String getName() {
        if (raw == null)
            return null;
        return (String) Engine.getReflectionEngine().getFieldValue("FG", "V", raw);
    }

    public String[] getActions() {
        if (raw == null)
            return null;
        return (String[]) Engine.getReflectionEngine().getFieldValue("FG", "S", raw);
    }

    public boolean isValid() {
        return raw != null;
    }
}