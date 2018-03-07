package org.alora.api.data;

import org.alora.api.interactive.Players;
import org.alora.api.interfaces.Locatable;
import org.alora.api.wrappers.Tile;


/**
 * Created by Ethan on 3/2/2018.
 */
public class Walking {

    public static boolean walkTo(Tile t) {
        final int x = t.getX() - Game.getBaseX();
        final int y = t.getY() - Game.getBaseY();
        Menu.sendWalkingInteraction(y, x);
        return true;
    }

    public static boolean walkTo(Locatable locatable) {
        final int x = locatable.getLocation().getX() - Game.getBaseX();
        final int y = locatable.getLocation().getY() - Game.getBaseY();
        Menu.sendWalkingInteraction(y, x);
        return true;
    }

    public static Tile getClosestTileOnMap(Tile current) {
        if (!Calculations.isInWalkingDistance(current)) {
            Tile loc = Players.getLocal().getLocation();
            Tile walk = new Tile((loc.getX() + current.getX()) / 2, (loc.getY() + current.getY()) / 2);
            return Calculations.isInWalkingDistance(current) ? walk : getClosestTileOnMap(walk);
        }
        return current;
    }
}
