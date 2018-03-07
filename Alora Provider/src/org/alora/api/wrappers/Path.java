package org.alora.api.wrappers;

import org.alora.api.data.Calculations;
import org.alora.api.data.Walking;
import org.alora.api.interactive.Players;
import org.ubot.util.Condition;

/**
 * Created by Ethan on 3/7/2018.
 */
public abstract class Path {

    private boolean end;

    public abstract Tile getStart();

    public abstract Tile getEnd();

    public abstract Tile[] getTiles();


    public boolean traverse() {
        Tile[] tiles = getTiles();

        if (tiles[tiles.length - 1].distanceTo() < 5)
            return true;
        final Tile next = getNext();
        final Tile endTile = tiles[tiles.length - 1];

        if (next.equals(endTile)) {
            if (Calculations.distanceTo(next) <= 1 || (end && Players.getLocal().isMoving())) {
                return false;
            }
            end = true;
        } else {
            end = false;
        }
        Walking.walkTo(next);
        for (int i = 0; i < 10 && Players.getLocal().isMoving(); i++, Condition.sleep(150)) ;
        return true;
    }

    private Tile getNext() {
        Tile[] tiles = getTiles();
        for (int i = tiles.length - 1; i >= 0; --i) {
            if (Calculations.isInWalkingDistance(tiles[i])) {
                return tiles[i];
            }
        }
        return Walking.getClosestTileOnMap(tiles[tiles.length - 1]);
    }

    public Tile[] getReversedTiles() {
        Tile[] tiles = getTiles();
        if (tiles == null)
            return null;
        Tile[] t = new Tile[tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            t[tiles.length - 1 - i] = tiles[i];
        }
        return t;
    }

}
