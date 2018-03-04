package org.alora.api.definitions;

import org.alora.loader.Loader;

/**
 * Created by Ethan on 2/27/2018.
 */



public class NPCDefinition {

    private Object raw;

    public NPCDefinition(Object raw) {
        this.raw = raw;

    }

    public int getId() {
        if (raw == null)
            return -1;
        return (int) Loader.getReflectionEngine().getFieldValue("FG", "j", raw);
    }

    public int getCombatLevel() {
        if (raw == null)
            return -1;
        return (int) Loader.getReflectionEngine().getFieldValue("NpcCombatLevel", "", raw);
    }

    public String getName() {
        if (raw == null)
            return null;
        return (String) Loader.getReflectionEngine().getFieldValue("FG", "V", raw);
    }

    public String[] getActions() {
        if (raw == null)
            return null;
        return (String[]) Loader.getReflectionEngine().getFieldValue("FG", "S", raw);
    }

    public boolean isValid() {
        return raw != null;
    }
}