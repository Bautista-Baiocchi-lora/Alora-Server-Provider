package org.alora.api.interactive;

/**
 * Created by Ethan on 2/27/2018.
 */

import org.alora.api.data.Game;
import org.alora.api.wrappers.NPC;
import org.alora.api.wrappers.Tile;
import org.alora.loader.Loader;
import org.ubot.util.Filter;
import org.ubot.util.Utilities;

import java.util.ArrayList;
import java.util.List;

public class Npcs {

    public static NPC[] getAll(final String... names) {
        if (names == null)
            return getAll();
        return getAll(npc -> npc.isValid() && npc.getName() != null && Utilities.inArray(npc.getName(), names));
    }

    public static NPC[] getAll(Filter<NPC> filter) {
        List<NPC> list = new ArrayList<>();
        if (!Game.isLoggedIn())
            return list.toArray(new NPC[list.size()]);
        final Object[] objects = (Object[]) Loader.getReflectionEngine().getFieldValue("YB", "H", null);
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
        return getAll(npc -> true);
    }


    public static NPC getNearest(Filter<NPC> filter) {
        if (!Game.isLoggedIn())
            return nil();
        return getNearest(Players.getLocal().getLocation(), filter);
    }

    public static NPC getNearest(Tile location, Filter<NPC> filter) {
        if (!Game.isLoggedIn())
            return nil();
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
        if (!Game.isLoggedIn())
            return nil();
        return getNearest(Players.getLocal().getLocation(), npc -> npc.isValid() && Utilities.inArray(npc.getId(), ids));
    }

    public static NPC getNearest(final String... names) {
        if (!Game.isLoggedIn())
            return nil();
        return getNearest(Players.getLocal().getLocation(), npc -> npc.isValid() && Utilities.inArray(npc.getName(), names));
    }


    public static NPC nil() {
        return new NPC(-1, null);
    }
}
