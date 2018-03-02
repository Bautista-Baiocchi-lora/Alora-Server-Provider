package org.alora.api.data;

import org.alora.api.interactive.Players;
import org.alora.api.wrappers.Tile;
import org.bot.Engine;

import java.awt.*;

/**
 * Created by Ethan on 2/27/2018.
 */
public class Calculations {

    public static int[] SINE = new int[2048];

    public static int[] COSINE = new int[2048];

    static {
        for (int i = 0; i < SINE.length; i++) {
            SINE[i] = (int) (65536.0D * Math.sin((double) i * 0.0030679615D));
            COSINE[i] = (int) (65536.0D * Math.cos((double) i * 0.0030679615D));
        }
    }

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

    public static Point worldToMap(int regX, int regY) {
        int delta = Game.getMapScale() + Game.getMapAngle() & 0x7FF;
        int radius = regY * regY + regX * regX;
        if (radius <= 5400) {
            int var11 = SINE[delta];
            int var10 = COSINE[delta];
            var11 = var11 * 256 / (Game.getMapScaleDelta() + 256);
            var10 = var10 * 256 / (Game.getMapScaleDelta() + 256);
            int xOffset = regX * var10 + regY * var11 >> 16;
            int yOffset = regX * var10 - regY * var11 >> 16;
            return new Point(649 + xOffset, 82 - yOffset);
        } else {
            return new Point(-1, -1);
        }

    }

    public static Point tileToMap(Tile tile) {
        int xMapTile = tile.getX() - Game.getBaseX();
        int yMapTile = tile.getY() - Game.getBaseY();
        Object myPlayer = Engine.getReflectionEngine().getFieldValue("MS", "FI", null);

        return worldToMap((xMapTile * 4 + 2) - (int) Engine.getReflectionEngine().getFieldValue("WS", "x", myPlayer) / 32, (yMapTile * 4 + 2) - (int) Engine.getReflectionEngine().getFieldValue("WS", "y", myPlayer) / 32);
    }
}
