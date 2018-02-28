package org.alora.api.data;

import org.alora.api.interactive.Players;
import org.alora.api.wrappers.Tile;

import java.awt.*;

/**
 * Created by Ethan on 2/27/2018.
 */
public class Calculations {

    private static int distanceBetween(int x, int y, int x1, int y1) {
        return (int) Math.sqrt(Math.pow(x1 - x, 2) + Math.pow(y1 - y, 2));
    }

    public static int distanceBetween(Point a, Point b) {
        return distanceBetween(a.x, a.y, b.x, b.y);
    }

    public static int distanceBetween(Tile a, Tile b) {
        return distanceBetween(a.getX(), a.getY(), b.getX(), b.getY());
    }

    public static int distanceTo(Tile a) {
        final Tile loc = Players.getLocal().getLocation();
        return distanceBetween(a.getX(), a.getY(), loc.getX(), loc.getY());
    }
}
