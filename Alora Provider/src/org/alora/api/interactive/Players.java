package org.alora.api.interactive;

/**
 * Created by Ethan on 2/27/2018.
 */

import org.alora.api.data.Game;
import org.alora.api.wrappers.Player;
import org.alora.api.wrappers.Tile;
import org.alora.loader.Loader;
import org.ubot.util.Filter;

import java.util.ArrayList;
import java.util.List;


public class Players {

    public static Player getLocal() {
        if (!Game.isLoggedIn())
            return nil();
        return new Player(Loader.getReflectionEngine().getFieldValue("MS", "FI", null));
    }

    public static Player[] getAll() {
        return getAll(null);
    }

    public static Player[] getAll(Filter<Player> filter) {
        List<Player> list = new ArrayList<>();
        if (!Game.isLoggedIn())
            return list.toArray(new Player[list.size()]);
        final Object[] objects = (Object[]) Loader.getReflectionEngine().getFieldValue("MS", "GI", null);
        for (Object player : objects) {
            if (player != null) {
                Player wrapper = new Player(player);
                if ((filter == null || filter.accept(wrapper))) {
                    list.add(wrapper);
                }
            }
        }
        return list.toArray(new Player[list.size()]);
    }

    public static Player getNearest(Tile location, Filter<Player> filter) {
        if (!Game.isLoggedIn())
            return nil();
        Player closet = new Player(null);
        int distance = 9999;
        for (Player player : getAll(filter)) {
            if (distance > player.distanceTo(location)) {
                closet = player;
            }
        }
        return closet;
    }

    public static Player getNearest(Filter<Player> filter) {
        if (!Game.isLoggedIn())
            return nil();
        return getNearest(Players.getLocal().getLocation(), filter);
    }

    public static Player nil() {
        return new Player(null);
    }

}
