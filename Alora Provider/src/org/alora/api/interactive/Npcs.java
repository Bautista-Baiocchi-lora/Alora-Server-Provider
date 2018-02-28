package org.alora.api.interactive;

/**
 * Created by Ethan on 2/27/2018.
 */

import org.alora.api.wrappers.NPC;
import org.alora.api.wrappers.Tile;
import org.bot.Engine;
import org.bot.util.Filter;
import org.bot.util.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Npcs {

    public static NPC[] getAll(final String... names) {
        if (names == null)
            return getAll();
        return getAll(new Filter<NPC>() {
            @Override
            public boolean accept(NPC npc) {
                return npc.isValid() && npc.getName() != null && Utilities.inArray(npc.getName(), names);
            }
        });
    }

    public static NPC[] getAll(Filter<NPC> filter) {
        List<NPC> list = new ArrayList<>();

        final Object[] objects = (Object[]) Engine.getReflectionEngine().getFieldValue("YB", "H", null);
        int i = 0;
        for (Object npc : objects) {
            if (npc != null) {
                NPC wrapper = new NPC(i, npc);
                if ((filter == null || filter.accept(wrapper))) {
                    list.add(wrapper);
                }
            }
            i++;
        }
        return list.toArray(new NPC[list.size()]);
    }

    public static NPC[] getAll() {
        return getAll(new Filter<NPC>() {
            @Override
            public boolean accept(NPC npc) {
                return true;
            }
        });
    }


    public static NPC getNearest(Filter<NPC> filter) {
        return getNearest(Players.getLocal().getLocation(), filter);
    }

    public static NPC getNearest(Tile location, Filter<NPC> filter) {
        NPC closet = new NPC(-1, null);
        int distance = 9999;
        for (NPC npc : getAll(filter)) {
            if (npc.isValid() && distance > npc.distanceTo(location)) {
                closet = npc;
                distance = npc.distanceTo(location);
            }
        }
        return closet;
    }

    public static NPC getNearest(final int... ids) {

        return getNearest(Players.getLocal().getLocation(), new Filter<NPC>() {
            @Override
            public boolean accept(NPC npc) {
                return npc.isValid() && Utilities.inArray(npc.getId(), ids);
            }
        });
    }

    public static NPC getNearest(final String... names) {

        return getNearest(Players.getLocal().getLocation(), new Filter<NPC>() {
            @Override
            public boolean accept(NPC npc) {
                return npc.isValid() && Utilities.inArray(npc.getName(), names);
            }
        });
    }


    public static NPC nil() {
        return new NPC(-1, null);
    }
}
